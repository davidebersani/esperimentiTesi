#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama prima il servizio B e poi chiama il servizio C.

N=50 # Numero di chiamate da effettuare

echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama prima il servizio B e poi chiama il servizio C"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    ./curl-client.sh http://localhost:8080/prosegui "call A { call B {}; call C {}; };"
    echo ""
done

echo "Fatto"