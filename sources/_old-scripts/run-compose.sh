#!/bin/bash

echo "ATTENZIONE: Il provisioning di Kibana non è effettuato automaticamente. E' consigliato eseguire, dopo che Kibana è stato avviato, lo script provision-kibana.sh per configurare automaticamente Kibana."
sudo docker-compose up &
