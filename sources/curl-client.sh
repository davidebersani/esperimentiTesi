#!/bin/sh
# Semplice client curl
if [ "$1" = "help" -o "$#" -ne 2 ]
then
  echo "Utilizzo: ./curl-client.sh <indirizzo> \"<stringa parametro>\""
else
  # Correggo la stringa se necessario. Ai caratteri {,},[,],; viene aggiunto uno spazio prima e dopo. Successivamente vengono tolti quelli in eccesso.
  param=`echo "$2" | sed -e "s/{/ { /g" | sed -e "s/}/ } /g" | sed -e "s/\[/ \[ /g" | sed -e "s/]/ ] /g" | sed -e "s/;/ ; /g"| sed -e "s/\"/ \" /g"| sed -e "s/ \+/ /g"`
  #echo "$param"
  curl -X POST -H "Content-Type: text/plain" --data "$param" $1
fi