#!/bin/bash
# Vengono effettuate N chiamate REST consecutive al servizio A il quale ritarda almeno 2000 ms

N=50
echo "Eseguo $N chiamate REST consecutive al servizio A il quale ritarda almeno 2000 ms"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    ./curl-client.sh http://localhost:8080/a/prosegui "sleep 2000;"
    echo ""
done

echo "Fatto"