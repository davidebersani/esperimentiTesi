#!/bin/bash

# dove vengono montate le risorse e i download condivisi 
HOME=/home/vagrant 
DOWNLOADS=${HOME}/provisioning/kubernetes/downloads
RESOURCES=${HOME}/provisioning/kubernetes/resources

function resourceExists {
	FILE=${RESOURCES}/$1
	if [ -e $FILE ]
	then
		return 0
	else
		return 1
	fi
}

function downloadExists {
	FILE=${DOWNLOADS}/$1
	if [ -e $FILE ]
	then
		return 0
	else
		return 1
	fi
}

function fileExists {
	FILE=$1
	if [ -e $FILE ]
	then
		return 0
	else
		return 1
	fi
}

#echo "common loaded"
