#!/bin/bash

# Find archiving and supervision metrics for select customers.

echo "`date`: start..."

#
# Customer Info
#

customers=(glic lpl rjf bofa)

customerStoreHosts_glic="<host>"
customerStoreHosts_lpl="<host>"
customerStoreHosts_rjf="<host>"
customerStoreHosts_bofa="<host>"

customerStores_glic="fortiva_cust_1_4bb9530c05959bdf__be503a2_16387d6e845__8000"
customerStores_lpl="fortiva_cust_1_acece357d4776abd_70db0e60_167f573a9c7__8000"
customerStores_rjf="fortiva_cust_1_c996dd02de5c23a2_25c537bf_13697dc2840__8000"
customerStores_bofa="fortiva_cust_1_60cff1fe4c86f23e_2eda01c7_16ca0f3938e__8000"

supervisionStoreHosts_glic="<host>"
supervisionStoreHosts_lpl="<host>"
supervisionStoreHosts_rjf="<host>"
supervisionStoreHosts_bofa="<host>"

supervisionStores_glic="fortiva_superng_1_388e07e165241c0b_5d7a69d7_16387f35e53__7ffc"
supervisionStores_lpl="fortiva_superng_1_42925e3a343dd167_2679447c_169160daf44__7ffc"
supervisionStores_rjf="fortiva_superng_1_3f77a473c5d254b7_636062c6_167cda2beb8__7ffc"
supervisionStores_bofa="fortiva_superng_1_f3a080003270dc5e_2c31933a_16ca104f75f__7ffc"

reportStoreHosts_glic="<host>"
reportStoreHosts_lpl="<host>"
reportStoreHosts_rjf="<host>"
reportStoreHosts_bofa="<host>"

reportStores_glic="fortiva_rpt_1_61e41dafe0aadce8__7fb4b7bc_16387f026c2__7ffc"
reportStores_lpl="fortiva_rpt_1_d9f1eb95cf82344d_37a73784_167f5805745__7ffc"
reportStores_rjf="fortiva_rpt_1_a29ff66b33f198ff_327635c_14b6d096f79__7ffe"
reportStores_bofa="fortiva_rpt_1_dbfe61a283197c70_4674e60e_16ca10b17db__7ffc"

#
# Date Ranges
#

ranges=(year quarter month week day)

from_year="2019-01-01"
to_year="2020-01-01"

from_quarter="2019-08-01"
to_quarter="2020-01-01"

from_month="2019-12-01"
to_month="2020-01-01"

from_week="2019-12-01"
to_week="2019-12-08"

from_day="2019-12-02"
to_day="2019-12-03"

#
# Main
#

mysql=/usr/local/fortiva/mysql/bin/mysql
user="<username>"
pass="<password>"

IFS='-'

if [ ! -f $mysql ]
then
    mysql=/usr/bin/mysql
fi

for customer in "${customers[@]}"
do
    echo "processing customer: $customer ..."

    i=supervisionStoreHosts_$customer
    supervisionStoreHost=${!i}
    i=supervisionStores_$customer
    supervisionStore=${!i}

    i=reportStoreHosts_$customer
    reportStoreHost=${!i}
    i=reportStores_$customer
    reportStore=${!i}

    echo "    report store: $reportStore @ $reportStoreHost"
    echo "    supervision store: $supervisionStore @ $supervisionStoreHost"
    echo

    for range in "${ranges[@]}"
    do
        i=from_$range
        from=${!i}
        i=to_$range
        to=${!i}

        # report store

        fromSplit=(${from///t/-})
        fromYear=${fromSplit[0]}
        fromMonth=${fromSplit[1]}
        fromDay=${fromSplit[2]}

        toSplit=(${to///t/-})
        toYear=${toSplit[0]}
        toMonth=${toSplit[1]}
        toDay=${toSplit[2]}

        while read -r row
        do
            echo "    $range ($from -> $to): messages archived = $row"
        done< <(ssh $reportStoreHost "MYSQL_PWD=$pass $mysql -u$user -D $reportStore -Bse 'SELECT COALESCE((SELECT f.NumberOfMessages FROM days d JOIN archivesizedailysenderfact f ON d.DayId=f.Dim_DayId WHERE d.year=$toYear AND d.month=$toMonth AND d.day=$toDay),0) - COALESCE((SELECT f.NumberOfMessages FROM days d JOIN archivesizedailysenderfact f ON d.DayId=f.Dim_DayId WHERE d.year=$fromYear AND d.month=$fromMonth AND d.day=$fromDay),0);'")

        # supervision store

        reviewStateTables=()
        reviewActionTables=()
        while read -r table
        do
            if [[ "$table" == "messagerulereviewstate"* ]]
            then
                reviewStateTables+=($table)
            fi
            if [[ "$table" == "reviewactionlog"* ]]
            then
                reviewActionTables+=($table)
            fi
        done< <(ssh $supervisionStoreHost "MYSQL_PWD=$pass $mysql -u$user -D $supervisionStore -Bse 'show tables;'")

        for reviewStateTable in "${reviewStateTables[@]}"
        do
            while read -r row
            do
                echo "    $range ($from -> $to): queued messages for $reviewStateTable = $row"
            done< <(ssh $supervisionStoreHost "MYSQL_PWD=$pass $mysql -u$user -D $supervisionStore -Bse 'SELECT COUNT(DISTINCT(MessageEntryId)) FROM $reviewStateTable WHERE archivedon >= \"$from\" AND archivedon < \"$to\";'")
        done

        for reviewActionsTable in "${reviewActionTables[@]}"
        do
            while read -r row
            do
                echo "    $range ($from -> $to): messages with at least one action for $reviewActionsTable = $row"
            done< <(ssh $supervisionStoreHost "MYSQL_PWD=$pass $mysql -u$user -D $supervisionStore -Bse 'SELECT COUNT(DISTINCT(messageentryid)) FROM $reviewActionsTable WHERE archivedon >= \"$from\" AND archivedon < \"$to\" AND action NOT IN (10,11);'")
        done

        for reviewStateTable in "${reviewStateTables[@]}"
        do
            while read -r row
            do
                echo "    $range ($from -> $to): total violations for $reviewStateTable = $row"
            done< <(ssh $supervisionStoreHost "MYSQL_PWD=$pass $mysql -u$user -D $supervisionStore -Bse 'SELECT COUNT(*) FROM $reviewStateTable WHERE archivedon >= \"$from\" AND archivedon < \"$to\" AND identifiedby<>3;'")
        done

        for reviewStateTable in "${reviewStateTables[@]}"
        do
            while read -r row
            do
                echo "    $range ($from -> $to): average violations per message for $reviewStateTable = $row"
            done< <(ssh $supervisionStoreHost "MYSQL_PWD=$pass $mysql -u$user -D $supervisionStore -Bse 'SELECT AVG(v.violations) FROM (SELECT COUNT(*) AS violations FROM $reviewStateTable WHERE archivedon >= \"$from\" AND archivedon < \"$to\" AND identifiedby<>3 GROUP BY messageentryid) v;'")
        done
    done

    echo
done

echo "`date`: done"
exit 0

