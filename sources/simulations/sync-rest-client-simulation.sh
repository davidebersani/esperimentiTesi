#!/bin/bash
# Script che simula un determinato comportamento di un client, utile per verifiacare il comportamento del sistema, per esempio utilizzando grafana.
# In particolare, questo script esegue delle semplici chiamate rest ai servizi A,B e C. Esegue le chiamate una dopo l'altra.
# Le prime chiamate sono senza ulteriori istruzioni all'interno. Le successive introducono un ritardo. per ciascuna chiamata.
# Quindi si dovrebbe notare, in un primo momento, un alto numero di chiamate al secondo per i vari servizi e una latenza molto bassa per l'endopoint /prosegui.
# Successivamente il numero di chiamate al secondo dovrebbe diminuire in quanto si alzerà la latenza dell'endpoint.

echo "Eseguo chiamate semplici."

NUM=20

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $i"
  ./curl-client.sh http://localhost:8080/a/prosegui " "
  echo ""
  ./curl-client.sh http://localhost:8080/b/prosegui " "
  echo ""
  ./curl-client.sh http://localhost:8080/c/prosegui " "
  echo ""
done

echo "Aspetto 10 secondi per avviare il secondo blocco di chiamate con ritardo."
sleep 10

for (( i=0; i<$NUM; i++ ))
do
  echo "Chiamata $i"
  ./curl-client.sh http://localhost:8080/a/prosegui "sleep 3000;"
  echo ""
  ./curl-client.sh http://localhost:8080/b/prosegui "sleep 3000;"
  echo ""
  ./curl-client.sh http://localhost:8080/c/prosegui "sleep 3000;"
  echo ""
done