drop procedure if exists return_null;
create procedure return_null()
begin
  select 1 from dual where false;
end;