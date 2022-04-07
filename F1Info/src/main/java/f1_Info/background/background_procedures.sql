drop procedure if exists test_procedure;
create procedure test_procedure()
begin
  select
    1
  from
    dual;
end;