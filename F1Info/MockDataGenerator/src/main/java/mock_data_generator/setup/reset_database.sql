set foreign_key_checks = 0;
set session group_concat_max_len = 10000;
set @tables = null;
set @views = null;
select
  group_concat('`', table_schema, '`.`', table_name, '`') into @tables
from
  information_schema.tables
where
  table_schema = 'f1database' and TABLE_TYPE = 'BASE TABLE';

select
  group_concat('`', table_schema, '`.`', table_name, '`') into @views
from
  information_schema.tables
where
    table_schema = 'f1database' and TABLE_TYPE = 'VIEW';

set @tables = concat('drop table ', @tables);
prepare stmt from @tables;
execute stmt;
deallocate prepare stmt;

set @views = concat('drop view ', @views);
prepare stmt from @views;
execute stmt;
deallocate prepare stmt;

set foreign_key_checks = 1;
set session group_concat_max_len = 1024;

set autocommit='OFF';
set global log_bin_trust_function_creators = 1;