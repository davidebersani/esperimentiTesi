#!/bin/bash

echo "========================================="
echo "installing nginx ingress add-on (master)"
echo "========================================="

# Installazione Nginx ingress plugin
# Doc: https://docs.nginx.com/nginx-ingress-controller/installation/installation-with-manifests/

git clone https://github.com/nginxinc/kubernetes-ingress/
cd kubernetes-ingress/deployments
git checkout v1.7.0

# Create a namespace and a service account for the Ingress controller
kubectl apply -f common/ns-and-sa.yaml

# Create a cluster role and cluster role binding for the service account
kubectl apply -f rbac/rbac.yaml

# Create a secret with a TLS certificate and a key for the default server in NGINX
kubectl apply -f common/default-server-secret.yaml

# Create a config map for customizing NGINX configuration:
kubectl apply -f common/nginx-config.yaml

# Create custom resource definitions for VirtualServer and VirtualServerRoute and TransportServer resources:
kubectl apply -f common/vs-definition.yaml
kubectl apply -f common/vsr-definition.yaml
kubectl apply -f common/ts-definition.yaml

# Create a custom resource definition for GlobalConfiguration resource:
kubectl apply -f common/gc-definition.yaml

# Create a GlobalConfiguration resource:
kubectl apply -f common/global-configuration.yaml

# Avvio ingress control
kubectl apply -f daemon-set/nginx-ingress.yaml