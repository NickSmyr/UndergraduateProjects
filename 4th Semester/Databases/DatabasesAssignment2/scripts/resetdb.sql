\echo ########Deleting tables....########
\i drop_tables.sql
\echo ########Creating tables and importing data....########
\i create_tables.sql
\i import_data.sql
\echo ########Adjusting foreign keys...########
\i alter_tables.sql
\echo ########Creating host table ....########
\i host_table.sql
\echo ########Listings.sql.....########
\i listings.sql
\echo ########Calendar sum...########
\i calendar_summary.sql
\echo ########Neighborhoodtabl...########
\i neighborhood_table.sql
\echo ########Calendar ...########
\i calendar.sql
\echo ########CHANGING NAMES########
\i all_tables.sql
