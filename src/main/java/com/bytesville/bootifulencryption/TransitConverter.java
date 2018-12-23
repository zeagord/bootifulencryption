package com.bytesville.bootifulencryption;

import javax.persistence.AttributeConverter;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

public class TransitConverter implements AttributeConverter<String, String> {

  @NewSpan(name = "convertToDatabaseColumn")
  @Override public String convertToDatabaseColumn(String creditCardNumber) {
    VaultOperations vaultOperations = BeanUtil.getBean(VaultOperations.class);
    Plaintext plaintext = Plaintext.of(creditCardNumber);
    return vaultOperations.opsForTransit().encrypt("customer", plaintext)
        .getCiphertext();
  }

  @NewSpan(name = "convertToEntityAttribute")
  @Override public String convertToEntityAttribute(String creditCardNumber) {
    VaultOperations vaultOperations = BeanUtil.getBean(VaultOperations.class);
    Ciphertext ciphertext = Ciphertext.of(creditCardNumber);
    return vaultOperations.opsForTransit().decrypt("customer",
        ciphertext).asString();
  }
}
