#!/bin/bash

# Stop kafka
# kubectl delete -f kafka-deploy.yml
# kubectl delete -f kafka-svc.yml
# kubectl delete -f zookeeper-deploy.yml

kubectl get pv | tail -n+2 | awk '{print $1}' | xargs -I{} kubectl patch pv {} --type='merge' -p '{"metadata":{"finalizers": null}}'
kubectl delete -f template-ns.yml
kubectl delete pv data3