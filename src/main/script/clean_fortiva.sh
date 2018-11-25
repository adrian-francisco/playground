#!/bin/bash

DB_STARTS_WITH="fortiva"
MUSER="fortivaUnitTest"
MPWD="fortivaUnitTest"
MYSQL="mysql"

DBS="$($MYSQL -u$MUSER -p$MPWD -Bse 'show databases' 2>/dev/null)"
for db in $DBS
do
    if [[ "$db" == "$DB_STARTS_WITH"* ]]
    then
        echo "Deleting $db"
        $MYSQL -u$MUSER -p$MPWD -Bse "drop database $db" 2>/dev/null
    fi
done
