#!/bin/bash
# Vengono effettuate N chiamate REST consecutive al servizio A il quale chiama il servizio B il quale chiama il servizio C

N=50
echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama il servizio B il quale chiama il servizio C"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    ./curl-client.sh http://localhost:8080/a/prosegui " call B { call C {}; };"
    echo ""
done

echo "Fatto"