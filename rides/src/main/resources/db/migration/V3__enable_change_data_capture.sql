SET CLUSTER SETTING kv.rangefeed.enabled = true;

CREATE CHANGEFEED FOR TABLE events INTO 'kafka://broker:29092' WITH full_table_name, format='json';