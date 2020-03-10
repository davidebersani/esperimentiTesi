#!/bin/sh
# Script per buildare tutti i servizi
echo "Avvio build dei progetti"
gradle build

echo "Avvio build delle immagini Docker"
sudo docker-compose build