drop procedure if exists get_driver_report_filter_values_drivers;
create procedure get_driver_report_filter_values_drivers(in p_season int)
begin
  select distinct
    drivers.driver_identifier,
    drivers.first_name,
    drivers.last_name
  from
    drivers
    inner join driver_standings on driver_standings.driver_id = drivers.id
    inner join races on races.id = driver_standings.race_id
  where
    races.year = p_season;
end;