#!/bin/bash

CUSTOMER_GUID=1_ead79c54cb454daa_36c65e2c_196120beca4__8000
NAME=ingestName


CONTEXT=$(jq -ncM '{
    customerGuid: "'$CUSTOMER_GUID'",
    userName: "dept1user1",
    userRoles: ["ApiUser", "ImportAdministrator"]
}')

jq -ncM '{
    name: "Ingestion Point '$NAME'",
    srcShortName: "'$NAME'",
    description: "Ingestion Point '$NAME'",
    typeDetails: {
        type: "liveMta",
        dailyVolume: "2048",
        dailySize: "1024"
    }
}' | curl -ksS "http://localhost:9090/ingestionPoints" -XPOST -H "X-Archive-RequestContext:$CONTEXT" -H "Content-Type: application/json" -d @- 

