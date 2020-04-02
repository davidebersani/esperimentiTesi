#!/bin/sh
# Semplice client curl
if [ $1 = "help" ]
then
  echo "Utilizzo: ./curl-client.sh <indirizzo> <stringa parametro>"
else
  # Correggo la stringa se necessario. Ai caratteri {,},[,],; viene aggiunto uno spazio prima e dopo. Successivamente vengono tolti quelli in eccesso.
  param=`echo "$2" | sed -e "s/{/ { /g" | sed -e "s/}/ } /g" | sed -e "s/\[/ \[ /g" | sed -e "s/]/ ] /g" | sed -e "s/;/ ; /g"| sed -e "s/ \+/ /g"`
  curl -X POST -H "Content-Type: application/json" --data "$param" $1
fi