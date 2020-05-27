#!/bin/sh
# Script di provisioning automatico di Kibana. Imposta un index pattern (corrispondente alla configurazione con cui elastic search salva i log)
# in modo tale da rendere Kibana pronto per la consultazione dei log. La configurazione impostata è scritta in base alla configurazione 
# di fluentd. Quindi è da tenere aggiornata.

# Pubblico l'index pattern
curl -H 'Content-Type: application/json; chartset=utf-8' -H 'kbn-xsrf: true' -X POST 'http://localhost:5601/api/saved_objects/index-pattern/logging-all?overwrite=true' -d "@./kibana/template.json"
