#!/bin/bash

# Find all saved searches with a BEFORE criteria in the new UI format
# (i.e. the before date ends with a 00, old ui ends with a 59)
# (ex: ~8~~~~~~~~1~~~~~~~~1000-01-01 00:00:00~~~~~~~~2018-05-13 03:00:00~)

# Execute: ssh afrancisco@<ip> "bash -s" < find-saved-searches.sh > <ip>.txt &

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

# Folder Stores:
# CA1
#   10.98.10.11 (done)
# CA2
#   10.130.64.11 (done)
# EU1
#   10.215.10.11 (done)
# US2
#   10.102.10.23 
#   10.102.10.13 (done)
#   10.102.11.29
#   10.102.10.14
#   10.102.11.12 (done)
#   10.102.10.20
#   10.102.10.24
#   10.102.13.11 (done)
#   10.102.11.21 (done)
# FED1
#   10.109.65.13	
#   10.109.65.14

echo "`date`: start..."

mysql=/usr/local/fortiva/mysql/bin/mysql
user="fortro"
#pass="Rh1n0ceR0$"
pass="@Rm@d1ll0"

#regex='.*~((8|44|178)~*1~*1000-01-01|(8|44|178)~*1~*\d\d\d\d-\d\d-\d\d\s\d\d:\d\d:\d\d~*9000-12-31).*'
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

        index=0
        count=0
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
		#echo $row | egrep --only-matching --max-count=1 $regex
                index=$[$index+1]
        done< <($mysql -u$user -p$pass -Bse "select folderitemid,name,itemtype,itemdata from ${db}.folderitems where itemtype in (1, 2, 5, 6, 7)")

        echo "ip=`hostname -i` store=$db count=$count"
	total=$[$total+$count]
done

e#cho "`date`: done total=$total"
exit 0
