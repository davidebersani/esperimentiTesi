#!/bin/bash

# Avvio namespace
echo "==> Creo il namespace"
kubectl apply -f template-ns.yml

# Influxdb
echo ""
echo "==> Rilascio Influxdb"
kubectl apply -f influx-helm/persistent-volume.yml
helm upgrade --install my-release -f influx-helm/values.yml influxdata/influxdb --namespace template-ns

# Prometheus
echo ""
echo "==> Rilascio Prometheus"
kubectl create -f role.yml
kubectl apply -f prometheus-server/

# Grafana
echo ""
echo "==> Rilascio grafana"
kubectl apply -f grafana/

# Avvio kafka
echo ""
echo "==> Rilascio Kafka"
kubectl apply -f zookeeper/
kubectl apply -f kafka/kafka-svc.yml
# echo "==> N.B. Avviare manualmente kafka-deploy.yml"    # Trovare un modo per farlo automaticamente.
# Ottengo ip del kafka service e lo sostituisco nel kafka deploy
ip="$(kubectl get service/kafka-svc -n template-ns -o jsonpath='{.spec.clusterIP}')"
sed "s/<IPFromService>/$ip/g" kafka/kafka-deploy.yml > kafka/kafka-deploy-modified.yml
kubectl apply -f kafka/kafka-deploy-modified.yml
rm kafka/kafka-deploy-modified.yml

# Avvio zipkin
#echo ""
#echo "==> Rilascio Zipkin"
#kubectl apply -f zipkin/

# Avvio servizi
echo ""
#echo "==> Rilascio i servizi A, B e C e il Gateway"
echo "==> Rilascio il servizio stub"
kubectl apply -f template-service/stub-service/
#kubectl apply -f template-service/a-service/
#kubectl apply -f template-service/b-service/
#kubectl apply -f template-service/c-service/
#kubectl apply -f spring-cloud-gateway/

echo ""
#echo "==> Rilascio i servizi A, B e C e il Gateway"
echo "==> Rilascio il servizio Analytics2"
kubectl apply -f analytics2/

# Riepilogo
echo ""
echo "==> Sono stati creati i seguenti pods"
echo ""
kubectl get pods -n template-ns