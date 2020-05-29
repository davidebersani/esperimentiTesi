#!/bin/bash
# Script per buildare un servizio passato come parametro
if [ "$1" = "help" -o "$#" -ne 2 ]
then
  echo "Utilizzo: ./build-publich-rollout.sh <directory> <nome immagine Docker, sarà il suffisso di esperimentesi/>."
else
  cd "$1"
  echo "==> Avvio build del servizio in $1"
  gradle build && \

  echo "==> Avvio build dell'immagine Docker" && \
  docker build . -t esperimentesi/"$2" && \

  echo "==> Carico l'immagine Docker su DockerHub" && \
  docker push esperimentesi/"$2" && \

  echo "==> Avvio il rollout"
  # Si presuppone che i deployment si chiamino <nome immagine>-deployment
  kubectl rollout restart deployment/"$2"-deployment -n template-ns && \
  echo "==> Rollout avviato, controlla il completamento"
fi
