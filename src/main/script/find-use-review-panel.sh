#!/bin/bash

# Find all supervision user preferences with UseReviewPanel=True.

# Serialized object looks like this:
#   188~1~
#   1~~2~~19~~~~...~~~~HeaderSearchLocation~~~~UseReviewPanel~~~~SupervisionLandingPage~~~~...~~~~~~~
#   1~~2~~19~~~~...~~~~False~~~~True~~~~Dashboard~~~~...~~~~~~~

# Preferences are key/value pairs serialized with keys first then values.
# The UseReviewPanel preference can be found anywhere in the serialized string.

echo "`date`: start..."

mysql=/usr/local/fortiva/mysql/bin/mysql
user="<username>"
pass="<password>"
ip=`hostname -i`

if [ ! -f $mysql ]
then
	mysql=/usr/bin/mysql
fi

total=0
for db in `$mysql -u$user -p$pass -Bse "show databases"`
do
        if [[ "$db" != "fortiva_fldr"* ]]
        then
                continue
        fi

	echo "`date`: ip=$ip store=$db processing..."

        count=0

	while read -r row;
	do
		rowSplit=(${row///t/ })
		userId=${rowSplit[0]}
		itemData=${rowSplit[1]}
		itemDataSplit=(${itemData//~1~~2~~/ })
		keys=(${itemDataSplit[1]//~~~~/ })
		values=(${itemDataSplit[2]//~~~~/ })

		keyLength=${#keys[@]}

		for (( i=0; i<${keyLength}; i++ ));
		do
			if [[ "UseReviewPanel" == ${keys[$i]} && "True" == ${values[$i]} ]]; then
				echo "userId: $userId"
				count=$[$count+1]
			fi
		done
        done< <($mysql -u$user -p$pass -D $db -Bse "SELECT archiveuserid, itemdata FROM folderitems WHERE name = 'Supervision' AND itemtype = 11 AND itemdata LIKE '%UseReviewPanel%'")

        echo "`date`: ip=$ip store=$db count=$count done"
	total=$[$total+$count]
done

echo "`date`: done total=$total"
exit 0

