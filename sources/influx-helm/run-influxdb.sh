#!/bin/bash

helm upgrade --install my-release -f influx-helm/values.yml influxdata/influxdb --namespace template-ns