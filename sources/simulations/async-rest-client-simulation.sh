#!/bin/bash
# Script che simula un determinato comportamento di un client, utile per verifiacare il comportamento del sistema, per esempio utilizzando grafana.
# In particolare, questo script esegue delle semplici chiamate rest ai servizi A,B e C. Le chiamate vengono lanciate senza aspettare che le precedenti terminino.
# Le prime chiamate sono senza ulteriori istruzioni all'interno. Le successive introducono un ritardo. per ciascuna chiamata.
# Quindi si dovrebbe notare, in un primo momento una latenza molto bassa per l'endopoint /prosegui.
# Successivamente il numero di chiamate al secondo dovrebbe rimanere invariato e si alzerà la latenza dell'endpoint.

echo "Eseguo chiamate semplici in modo asincrono."

NUM=50

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $i"
  ./curl-client.sh http://localhost:8080/prosegui "call A {};" >/dev/null &
  ./curl-client.sh http://localhost:8080/prosegui "call B {};" >/dev/null &
  ./curl-client.sh http://localhost:8080/prosegui "call C {};" >/dev/null &
done

echo "Aspetto 10 secondi per avviare il secondo blocco di chiamate con ritardo."
sleep 10

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $i"
  ./curl-client.sh http://localhost:8080/prosegui "call A {sleep 3000;};" >/dev/null &
  ./curl-client.sh http://localhost:8080/prosegui "call B {sleep 3000;};" >/dev/null &
  ./curl-client.sh http://localhost:8080/prosegui "call C {sleep 3000;};" >/dev/null &
done