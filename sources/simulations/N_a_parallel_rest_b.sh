#!/bin/bash
# Scenario: il servizio A chiama in parallelo (su un altro thread) il servizio B.


echo "Eseguo chiamate in modo sincrono."

NUM=30  # Numero di chiamate da effettuare

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/a/prosegui "concurrent[call b {};];"
done
echo "Fatto."
