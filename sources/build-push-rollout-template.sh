#!/bin/sh
# Script per buildare il servizio template
echo "==> Avvio build del servizio template"
gradle :template:build && \

echo "==> Avvio build dell'immagine Docker" && \
docker build ./template-service -t esperimentesi/template && \

echo "==> Carico l'immagine Docker su DockerHub" && \
docker push esperimentesi/template && \

echo "==> Avvio il rollout dei Deployment A,B,C"
kubectl rollout restart deployment/a-deployment -n template-ns && \
kubectl rollout restart deployment/b-deployment -n template-ns && \
kubectl rollout restart deployment/c-deployment -n template-ns && \
echo "==> Rollout avviato, controlla il completamento"
