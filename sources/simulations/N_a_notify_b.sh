#!/bin/bash
# Scenario: il servizio A invia una serie di messaggi al servizio B.

echo "Eseguo chiamate in modo sincrono."

NUM=50  # Numero di chiamate da effettuare

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/a/prosegui "notify b {};"
done

echo "Fatto."