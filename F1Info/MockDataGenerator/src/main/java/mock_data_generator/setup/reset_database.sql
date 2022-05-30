set foreign_key_checks = 0;
set session group_concat_max_len = 10000;
set @tables = null;
select
  group_concat('`', table_schema, '`.`', table_name, '`') into @tables
from
  information_schema.tables
where
  table_schema = 'f1database';

set @tables = concat('drop table ', @tables);
prepare stmt from @tables;
execute stmt;
deallocate prepare stmt;
set foreign_key_checks = 1;
set session group_concat_max_len = 1024;

set autocommit='OFF';
set global log_bin_trust_function_creators = 1;