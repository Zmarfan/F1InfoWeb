drop function if exists create_event;
create function create_event(p_entity_id int, p_type_id int, p_comment varchar(255)) returns int
begin
  insert into events (entity_id, type_id, time, comment) values (p_entity_id, p_type_id, current_timestamp, p_comment);
  return last_insert_id();
end;

drop procedure if exists return_null;
create procedure return_null()
begin
  select 1 from dual where false;
end;

drop function if exists to_date;
create function to_date(p_date_string varchar(255)) returns date
begin
  return str_to_date(p_date_string, '%Y-%m-%d');
end;

drop function if exists to_time;
create function to_time(p_time_string varchar(255)) returns time
begin
  return str_to_date(p_time_string, '%H:%i:%s');
end;