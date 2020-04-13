#!/bin/sh
# Script per buildare un singolo servizio e far ripartire i container che hanno subito cambiamenti
if [ "$1" = "help" -o "$#" -ne 1 ]
then
  echo "Utilizzo: ./build-and-deply-service.sh <nome-servizio>. Il nome del servizio è indicato in settings.gradle."
else
  echo "Avvio build"
  gradle :"$1":build
  echo "Riavvio i container che hanno subito cambiamenti"
  sudo docker-compose up --detach --build
fi