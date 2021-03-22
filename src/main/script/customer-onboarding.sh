#!/bin/bash

echo "`date`: start..."

mysql=/usr/local/fortiva/mysql/bin/mysql
user="<username>"
pass="<password>"

if [ ! -f $mysql ]
then
    mysql=/usr/bin/mysql
fi

for host in {<host1>,<host2>,<host3>,...}
do
    #echo "$host processing..."
    for db in `ssh $host MYSQL_PWD=$pass $mysql -s -u$user -Bse \"show databases\"`
    do
        if [[ "$db" != "fortiva_cust"* ]]
        then
        #echo "$db skipping..."
            continue
        fi

        #echo "$db processing..."
        creation_date=`ssh $host MYSQL_PWD=$pass $mysql -s -u$user -D $db -Bse \"SELECT MIN\(received\) FROM intakebatches\"`
        name=`ssh $host MYSQL_PWD=$pass $mysql -s -u$user -D $db -Bse \"SELECT PropertyValue FROM internalcustomerproperties WHERE PropertyName = \'InternalCustomerName\'\"`
        status=`ssh $host MYSQL_PWD=$pass $mysql -s -u$user -D $db -Bse \"SELECT IFNULL \(\(SELECT PropertyValue FROM internalcustomerproperties WHERE PropertyName = \'InternalCustomerStatus\'\), \'ACTIVE\'\)\"`
        echo "$host,$db,$creation_date,$name,$status"
    done
done

echo "`date`: done"
exit 0
