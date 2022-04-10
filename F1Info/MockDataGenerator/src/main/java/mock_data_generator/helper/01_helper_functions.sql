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