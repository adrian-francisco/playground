#!/bin/bash

# Finds the customer guid and name for a give store type from the list of hosts.
# Upload this script and customer.csv to the respective host.
 
stype=structure
host=`hostname -I | sed 's/ *$//g'`
 
echo "Host,StoreGuid,CustomerGuid,CustomerName"
for guid in `ls /data/fortiva-stores/fortiva-data/*/stores/${stype}`; do
	if [[ $guid = /data* ]]
	then
		continue
	fi

	for config in `ls /data/fortiva-stores/fortiva-data/*/stores/structure/${guid}/config/config.xml`; do
		custGuid=`xmllint --xpath "/GenericStoreConfig/GenericStoreSettings/CustomerGUID/text()" ${config}`
		custName=`grep ${custGuid} customers.csv | cut -d',' -f 2`
		echo $host,$guid,$custGuid,$custName
	done
done
 
