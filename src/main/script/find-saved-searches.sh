#!/bin/bash

# Find all saved searches with a BEFORE criteria in the new UI format
# (i.e. the before date ends with a 00, old ui ends with a 59)
# (ex: ~8~~~~~~~~1~~~~~~~~1000-01-01 00:00:00~~~~~~~~2018-05-13 03:00:00~)

# Execute Remotely: ssh <you>@<ip> "bash -s" < find-saved-searches.sh > <ip>.txt &

# Folder Item Types (UserFolderItem):
# FOLDER_ITEM_TYPE_SIMPLE_SEARCH = 1;
# FOLDER_ITEM_TYPE_ADVANCED_SEARCH = 2;
# FOLDER_ITEM_TYPE_STANDARD_SEARCH = 5;
# FOLDER_ITEM_TYPE_SUPERVISION_NG_REVIEW_SEARCH = 6;
# FOLDER_ITEM_TYPE_INVESTIGATION_SEARCH = 7;

# Serialized Ids:
# SEND_DATE_RANGE_QUERY = 8;
# ARCHIVED_DATE_RANGE_QUERY = 44;
# RECEIVE_DATE_RANGE_QUERY = 178;

# Additional Search Notes:
# There can be many "~" between the field and value in the serialized blob

echo "`date`: start..."

mysql=/usr/local/fortiva/mysql/bin/mysql
user="<username>"
pass="<password>"
regex='~(8|44|178)~*1~*1000-01-01\s00:00:00~*[0-9]{4}-[0-9]{2}-[0-9]{2}\s[0-9]{2}:[0-9]0:00~'

if [ ! -f $mysql ]
then
	mysql=/usr/bin/mysql
fi

total=0
for db in `$mysql -u$user -p$pass -Bse "show databases"`
do
        if [[ "$db" != "fortiva_fldr"* ]]
        then
		echo "$db skipping..."
                continue
        fi

	echo "$db processing..."

	offset=0
	limit=10000000
	max=`$mysql -u$user -p$pass -D $db -Bse "select max(folderitemid) from folderitems"`
        index=0
        count=0

	while [ $offset -lt $max ]
	do
		while read -r output;
		do
	                row=`echo $output | awk -F"\t" '{print $0}'`
                        short=`echo $row | cut -c1-80`
                        echo -n "$index: $short "
                        if echo $row | egrep --quiet --max-count=1 $regex
                        then
                        	echo "(HIT)"
                                count=$[$count+1]
                        else
                        	echo "(MISS)"
                        fi
                        index=$[$index+1]
                done< <($mysql -u$user -p$pass -D $db -Bse "select f.folderitemid,f.name,f.itemtype,f.itemdata from (select folderitemid from folderitems where folderitemid>$offset order by folderitemid limit $limit) q join folderitems f on f.folderitemid=q.folderitemid and f.itemtype in (1,2,5,6,7)")
		offset=`$mysql -u$user -p$pass -D $db -Bse "select max(f.folderitemid) from (select folderitemid from folderitems where folderitemid>$offset order by folderitemid limit $limit) q join folderitems f on f.folderitemid=q.folderitemid"` 
	done

        echo "ip=`hostname -i` store=$db count=$count"
	total=$[$total+$count]
done

echo "`date`: done total=$total"
exit 0
