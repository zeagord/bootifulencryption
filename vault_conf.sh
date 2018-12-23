
#!/bin/bash

export VAULT_TOKEN=s.1wjnHEGOpmLFVaSC6WazGDqA
export VAULT_ADDR='http://127.0.0.1:8200'

echo 'path "secret/spring-vault-demo" {
  capabilities = ["create", "read", "update", "delete", "list"]
}
path "secret/application" {
  capabilities = ["create", "read", "update", "delete", "list"]
}
path "transit/decrypt/customer" {
  capabilities = ["update"]
}
path "transit/encrypt/customer" {
  capabilities = ["update"]
}
path "database/creds/customer" {
  capabilities = ["read"]
}
path "sys/renew/*" {
  capabilities = ["update"]
}' | vault policy write customer -

#Mount transit backend
vault secrets enable transit

#Create transit key
vault write -f transit/keys/customer