#!/bin/bash

# Avvio namespace
kubectl apply -f template-ns.yml 

# Avvio kafka
kubectl apply -f zookeeper.yml
kubectl apply -f kafka-svc.yml
# echo "==> N.B. Avviare manualmente kafka-deploy.yml"    # Trovare un modo per farlo automaticamente.
# Ottengo ip del kafka service e lo sostituisco nel kafka deploy
ip="$(kubectl get service/kafka-svc -n template-ns -o jsonpath='{.spec.clusterIP}')"
sed "s/<IPFromService>/$ip/g" kafka-deploy.yml > kafka-deploy-modified.yml
kubectl apply -f kafka-deploy-modified.yml
rm kafka-deploy-modified.yml

# Avvio servizi
kubectl apply -f a-service.yml
kubectl apply -f b-service.yml
kubectl apply -f c-service.yml


