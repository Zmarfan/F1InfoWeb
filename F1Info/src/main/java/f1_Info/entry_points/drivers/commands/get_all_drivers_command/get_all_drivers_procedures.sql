drop procedure if exists get_all_drivers;
create procedure get_all_drivers()
begin
  select distinct
    drivers.driver_identifier,
    drivers.first_name,
    drivers.last_name
  from
    drivers
  order by
    drivers.first_name;
end;
