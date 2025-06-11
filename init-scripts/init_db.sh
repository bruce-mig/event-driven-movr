#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

URL="postgresql://root@127.0.0.1:26257/defaultdb?sslmode=disable"

function db_exists {
    dbname=$1
    cockroach sql --url "$URL" --execute "SELECT 1 FROM [SHOW DATABASES] WHERE database_name = '$dbname';" --format=csv | grep -q 1
}

echo "----------------------------------------------------------------------"
echo "CONNECTING TO COCKROACH_DB"
echo "$URL"
echo "----------------------------------------------------------------------"

for db in movr_rides movr_vehicles movr_users; do
    if db_exists "$db"; then
        echo "Database $db already exists, skipping initialization for $db."
    else
        echo "----------------------------------------------------------------------"
        echo "INITIALIZING $db DATABASE: /data/${db#movr_}_database.sql"
        echo "----------------------------------------------------------------------"
        cockroach sql --url "$URL" --file "/data/${db#movr_}_database.sql"
    fi
done

#echo "----------------------------------------------------------------------"
#echo "UPDATING DEMO PASSWORD (movr) "
#echo "----------------------------------------------------------------------"
#cockroach sql --url "$URL" --execute 'ALTER USER demo WITH PASSWORD "movr";'