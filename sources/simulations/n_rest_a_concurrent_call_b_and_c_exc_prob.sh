#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama in parallelo i servizi B e C.
# Uno dei due servizi, o entrambi, falliscono con una certa probabilità.
# Le chiamate a B e C vengono svolte in parallelo, non conta l'ordine con cui vengono specificate.

echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama in parallelo i servizi B e C.
Uno dei due servizi, o entrambi, falliscono con una certa probabilità."

N=10  # Numero di chiamate da effettuare
PROB_FAILURE_B=20   # Probabilità di fallimento per B [0-100]
PROB_FAILURE_C=20   # Probabilità di fallimento per C [0-100]

count=0    # Numero di chiamate fallite

for (( i=0; i<$N; i++ ))
do
  fail=0
  echo "Chiamata $(($i+1))"
  rand=$((RANDOM%100+1))
  #echo "$RAND"
  if (( rand > PROB_FAILURE_B ))
  then
    # Non inserisco eccezione
    echo
    payload_b="exception \"Fallimento!\";"
    echo "B fallisce."
    fail=1
  else
    # Inserisco eccezione
    payload_b=" "
  fi
  rand=$((RANDOM%100+1))
  if (( rand > PROB_FAILURE_C ))
  then
    # Non inserisco eccezione
    payload_c="exception \"Fallimento!\";"
    echo "C fallisce."
    fail=1
  else
    # Inserisco eccezione
    payload_c=" "
  fi

  if ((fail==1))
  then
    ((count=count+1))
  fi

  ./curl-client.sh http://localhost:8080/a/prosegui "concurrent[call b {$payload_b}; call c {$payload_c};];"
done

echo "Fatto. Sono fallite $count chiamate."