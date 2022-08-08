drop procedure if exists get_drivers_from_season_get;
create procedure get_drivers_from_season_get(in p_season int)
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