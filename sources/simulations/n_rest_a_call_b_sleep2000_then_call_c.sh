#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama prima il servizio B e poi chiama il servizio C.
# Il servizio B esegue uno sleep di 2 sec.

N=50
echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama prima il servizio B e poi chiama il servizio C. Il servizio B esegue uno sleep di 2 sec"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    ./curl-client.sh http://localhost:8080/prosegui "call A { call B {sleep 2000;}; call C {}; };"
    echo ""
done

echo "Fatto"