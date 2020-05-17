#!/bin/bash

# Avvio namespace
kubectl apply -f template-ns.yml

# Prometheus
kubectl create -f role.yml
kubectl apply -f prometheus-server/
#kubectl apply -f prometheus-server/config-map.yml
#kubectl apply -f prometheus-server/prometheus-deploy.yml
#kubectl apply -f prometheus-server/prometheus-svc.yml

# Avvio kafka
kubectl apply -f zookeeper/
kubectl apply -f kafka/kafka-svc.yml
# echo "==> N.B. Avviare manualmente kafka-deploy.yml"    # Trovare un modo per farlo automaticamente.
# Ottengo ip del kafka service e lo sostituisco nel kafka deploy
ip="$(kubectl get service/kafka-svc -n template-ns -o jsonpath='{.spec.clusterIP}')"
sed "s/<IPFromService>/$ip/g" kafka/kafka-deploy.yml > kafka/kafka-deploy-modified.yml
kubectl apply -f kafka/kafka-deploy-modified.yml
rm kafka/kafka-deploy-modified.yml

# Avvio zipkin
kubectl apply -f zipkin/

# Avvio servizi
kubectl apply -f template-service/a-service/
kubectl apply -f template-service/b-service/
kubectl apply -f template-service/c-service/
kubectl apply -f spring-cloud-gateway/

# Riepilogo
echo ""
echo "==> Sono stati creati i seguenti pods"
echo ""
kubectl get pods -n template-ns