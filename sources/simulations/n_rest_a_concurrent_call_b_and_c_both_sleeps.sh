#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama in parallelo il servizio B e C.
# Entrambi i servizi chiamati fanno uno sleep ma con diversa durata.

# Comportamento atteso:
# La latenza è ridotta rispetto alle chiamate in catena (A che chiama B, che a sua volta chiama C).
# Le chiamate a B e C vengono svolte in parallelo, non conta l'ordine con cui vengono specificate.

echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama in parallelo i servizi B e C.
Entrambi i servizi chiamati fanno uno sleep ma con diversa durata"

N=20  # Numero di chiamate da effettuare
SLEEP_B=3000    # Millisecondi di sleep per il servizio B
SLEEP_C=1000    # Millisecondi di sleep per il servizio C

for (( i=0; i<$N; i++ ))
do
  echo "Chiamata $(($i+1))"
  ./curl-client.sh http://localhost:8080/prosegui "call a { concurrent[call b {sleep $SLEEP_B;}; call c {sleep $SLEEP_C;};]; };"
done
echo "Fatto."