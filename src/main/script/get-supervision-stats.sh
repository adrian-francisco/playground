#!/bin/bash
 
hosts=( <host1> <host2> <host3> ... )
version=3.5
year=2018
month=Jan
supervisionFile=supervision.dat
structureFile=structure.dat
 
>${supervisionFile}
>${structureFile}
 
for host in ${hosts[@]}; do
        echo "processing ${host}"
        for log in /data/logs/fortiva/${host}/${version}/fortiva-webservicev35/${year}/${month}/*; do
                echo -e "\tprocessing ${log}"
                echo -e "\t\tprocessing supervision store log"
                zgrep "getSupervisedMessagesToReview supervision store" ${log} | awk '{print $9}' | sed 's/ms//' >> ${supervisionFile}
                echo -e "\t\tprocessing structure store log"
                zgrep "getSupervisedMessagesToReview structure store" ${log} | awk '{print $9}' | sed 's/ms//' >> ${structureFile}
        done
done
 
echo "supervision store statistics (in ms)"
echo -e "avg\tmax\tmin" 
cat ${supervisionFile} | awk '{if(min==""){min=max=$1}; if($1>max) {max=$1}; if($1<min) {min=$1}; total+=$1; count+=1} END {print total/count,"\t",max,"\t",min}'
 
echo "structure store statistics (in ms)"
echo -e "avg\tmax\tmin" 
cat ${structureFile} | awk '{if(min==""){min=max=$1}; if($1>max) {max=$1}; if($1<min) {min=$1}; total+=$1; count+=1} END {print total/count,"\t",max,"\t",min}'
 
