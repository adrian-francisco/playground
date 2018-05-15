#!/bin/bash

DB_STARTS_WITH="fortiva"
MUSER="fortivaUnitTest"
MPWD="fortivaUnitTest"
MYSQL="mysql"

DBS="$($MYSQL -u$MUSER -p$MPWD -Bse 'show databases')"
for db in $DBS
do
  echo "database --> $db"
  
  if [[ "$db" == "$DB_STARTS_WITH"* ]]
  then
      echo "Deleting $db"
      $MYSQL -u$MUSER -p$MPWD -Bse "drop database $db"
  fi
done
