#!/bin/bash
# Scenario: il servizio A invia un messaggio a B facendogli fare uno sleep di 5 secondi.
# Poi invia un altro messaggio a B conuno sleep di 1 secondo.
# Si vede dal tracing che B non processa il secondo messaggio finchè non ha finito di processare il primo.

echo "Invio primo messaggio"
./curl-client.sh http://localhost:8080/prosegui "call A { notify B {sleep 5000;}; };"

echo "Invio secondo messaggio"
./curl-client.sh http://localhost:8080/prosegui "call A { notify B {sleep 1000;}; };"

echo "Fatto."