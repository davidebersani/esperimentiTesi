#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST al servizio A il quale invia un messaggio al servizio B.

N=50 # Numero di chiamate da effettuare

echo "Eseguo $N chiamate REST al servizio A il quale invia un messaggio al servizio B"

for (( i=0; i<$N; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/prosegui "call A { notify b {}; };"
done

echo "Fatto."
