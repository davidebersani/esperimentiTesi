#!/bin/bash
# Scenario: il servizio A invia un messaggio al servizio B e C. Entrambi i servizi chiamati fanno uno sleep.

echo "Eseguo chiamate in modo sincrono."

NUM=30  # Numero di chiamate da effettuare
SLEEP_B=3000    # Millisecondi di sleep per il servizio B
SLEEP_C=1000    # Millisecondi di sleep per il servizio C

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/a/prosegui "notify b {sleep $SLEEP_B;}; notify c {sleep $SLEEP_C;};"
done

echo "Fatto."