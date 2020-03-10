#!/bin/sh
# Alcuni script scritti su Windows sono interpretati male, si deve sostituire il terminatore di riga.
sed -i -e 's/\r$//' $1
