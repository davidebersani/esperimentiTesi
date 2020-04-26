#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama in parallelo (su un altro thread) il servizio B.

N=30  # Numero di chiamate da effettuare

echo "Eseguo $N chiamate consecutive al servizio A il quale chiama in parallelo (su un altro thread) il servizio B."

for (( i=0; i<$N; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/a/prosegui "concurrent[call b {};];"
done
echo "Fatto."
