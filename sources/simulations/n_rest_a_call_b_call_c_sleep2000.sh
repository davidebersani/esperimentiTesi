#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama prima il servizio B e poi chiama il
# servizio C il quale esegue uno sleep di 2 sec.

N=50 # Numero di chiamate da effettuare

echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama prima il servizio B e poi chiama il servizio C il quale esegue uno sleep di 2 sec"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    ./curl-client.sh http://localhost:8080/a/prosegui " call B { call C {sleep 2000;} ; };"
    echo ""
done

echo "Fatto"