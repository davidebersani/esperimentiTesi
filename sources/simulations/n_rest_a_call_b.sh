#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama il servizio B.

N=50

echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama il servizio B"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    ./curl-client.sh http://localhost:8080/a/prosegui " call B {};"
    echo ""
done

echo "Fatto"