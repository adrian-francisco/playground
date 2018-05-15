#!/bin/bash

echo -n "Number of scanners (-k to kill all): "
ps -ef | grep VShieldScanner | grep -v grep | wc -l

echo
echo "Latest logs:"
tail /var/log/McAfeeSecurity.log

if [ "$1" = "-k" ]; then
  echo
  echo "Killing all..."
  sudo killall VShieldScanner
fi
