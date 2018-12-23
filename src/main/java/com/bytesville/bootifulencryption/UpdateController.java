package com.bytesville.bootifulencryption;

import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.example.db.h2.tables.pojos.Customer;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.jooq.example.db.h2.Tables.CUSTOMER;

@RestController
@RequestMapping("/update")
public class UpdateController {

  @Autowired CustomerRepository repository;

  @Autowired DSLContext dsl;

  @GetMapping
  public ResponseEntity update(@RequestParam String version) {

    List<Customer> customers = dsl
        .selectFrom(CUSTOMER)
        .where(CUSTOMER.CREDIT_CARD_NUMBER.startsWith(version))
        .fetchInto(Customer.class);

    VaultOperations vaultOperations = BeanUtil.getBean(VaultOperations.class);
    try {
      List<Query> queries = new ArrayList<Query>();
      for (Customer customer: customers) {
        String cipherText = vaultOperations.opsForTransit().rewrap("customer", customer
            .getCreditCardNumber());
        queries.add(dsl.update(CUSTOMER)
            .set(CUSTOMER.CREDIT_CARD_NUMBER, cipherText)
            .where(CUSTOMER.ID.eq(customer.getId())));
      }
      int[] status = dsl.batch(queries).execute();
      return ResponseEntity.ok("updated");
    } catch (DataAccessException e) {
      return ResponseEntity.unprocessableEntity().body(e);
    }
  }
}
