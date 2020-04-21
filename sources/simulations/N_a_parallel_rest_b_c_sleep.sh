#!/bin/bash
# Scenario: il servizio A chiama in parallelo il servizio B e C. Entrambi i servizi chiamati fanno uno sleep. 
# Comportamento atteso: La latenza è ridotta rispetto alle chiamate in catena (A che chiama B, che a sua volta chiama C). 
#                       Le chiamate a B e C vengono svolte in parallelo, non conta l'ordine con cui vengono specificate.

echo "Eseguo chiamate in modo sincrono."

NUM=20  # Numero di chiamate da effettuare
SLEEP_B=3000    # Millisecondi di sleep per il servizio B
SLEEP_C=1000    # Millisecondi di sleep per il servizio C

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/a/prosegui "concurrent[call b {sleep $SLEEP_B;}; call c {sleep $SLEEP_C;};];"
done
echo "Fatto."