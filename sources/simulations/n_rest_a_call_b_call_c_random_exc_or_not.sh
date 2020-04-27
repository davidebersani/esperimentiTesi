#!/bin/bash
# Scenario:
# vengono effettuate N chiamate REST consecutive al servizio A il quale chiama il servizio B il quale chiama il servizio C.
# Randomicamente uno dei 3 può fallire, quindi la richiesta può anche andare a buon fine.

N=50 # Numero di chiamate da effettuare
EXCEPTION="exception \"eccezione generata\";"

echo "Eseguo $N chiamate REST consecutive al servizio A il quale chiama il servizio B il quale chiama il servizio C.
Randomicamente uno dei 3 può fallire, quindi la richiesta può anche andare a buon fine"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"

    FAILING_SERVICE=$((RANDOM%4))
    PAYLOAD_A=" "
    PAYLOAD_B=" "
    PAYLOAD_C=" "

    if [[ "$FAILING_SERVICE" == "0" ]]
    then
        PAYLOAD_A=$EXCEPTION
    elif [[ $FAILING_SERVICE == "1" ]]
    then
        PAYLOAD_B=$EXCEPTION
    elif [[ $FAILING_SERVICE == "2" ]]
    then
        PAYLOAD_C=$EXCEPTION
    fi

    echo "$PAYLOAD_A call B {$PAYLOAD_B call C {$PAYLOAD_C}; };"
    ./curl-client.sh http://localhost:8080/prosegui "call A {$PAYLOAD_A call B {$PAYLOAD_B call C {$PAYLOAD_C}; }; };"
    echo ""
done

echo "Fatto"