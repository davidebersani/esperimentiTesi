#!/bin/bash
# Vengono effettuate N chiamate REST consecutive al servizio A di cui il 20% fallisce

N=50
FAIL_PERC=20

# quante richieste dovranno fallire approssimate per difetto
TOT_FAIL=$((N*FAIL_PERC/100))

ARR_FAIL=()

# generazione TOT_FAIL numeri random diversi che corrispondono alle richieste che falliranno
for (( i=0; i<$TOT_FAIL; i++ ))
do
    R=$((RANDOM%N))
    #echo "num: " $R
    # controllo se è duplicato
    while [[ " ${ARR_FAIL[@]} " =~ " $R " ]]
    do
        #echo "uguale"
        R=$((RANDOM%N))
        #echo "nuovo:" $R
    done
    ARR_FAIL+=($R)
    #echo "array: " ${ARR_FAIL[@]}
done
#echo "array: " ${ARR_FAIL[@]}

echo "Eseguo $N chiamate REST consecutive al servizio A di cui $TOT_FAIL falliscono"

for (( i=0; i<$N; i++ ))
do
    echo "Chiamata $((i+1))"
    if [[ " ${ARR_FAIL[@]} " =~ " $((i+1)) " ]]
    then
        PAYLOAD="exception \" errore \";"
    else
        PAYLOAD=" "
    fi
    ./curl-client.sh http://localhost:8080/a/prosegui "$PAYLOAD"
    echo ""
done

echo "Fatto"