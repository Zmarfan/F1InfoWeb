drop procedure if exists get_driver_profile_get;
create procedure get_driver_profile_get(in p_driver_identifier varchar(100))
begin
  select
    drivers.first_name,
    drivers.last_name,
    drivers.wikipedia_page
  from
    drivers
  where
    drivers.driver_identifier = p_driver_identifier;
end;
