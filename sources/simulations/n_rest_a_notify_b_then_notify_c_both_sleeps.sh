#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale invia un messaggio
# al servizio B e poi ne invia un altro al servizio C.
# Entrambi i servizi chiamati fanno uno sleep ma con diversa durata.

N=30  # Numero di chiamate da effettuare
SLEEP_B=3000    # Millisecondi di sleep per il servizio B
SLEEP_C=1000    # Millisecondi di sleep per il servizio C

echo "Eseguo $N chiamate REST consecutive al servizio A che invia prima un messaggio al servizio B e poi uno al servizio C.
Entrambi i servizi chiamati fanno uno sleep ma con diversa durata"

for (( i=0; i<$N; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/prosegui "call A { notify B {sleep $SLEEP_B;}; notify C {sleep $SLEEP_C;}; };"
done

echo "Fatto."