drop procedure if exists get_driver_chart_info_get_season_points;
create procedure get_driver_chart_info_get_season_points(in p_driver_identifier varchar(100))
begin
  select
    races.year,
    races.round,
    driver_standings.points
  from
    drivers
      inner join driver_standings on driver_standings.driver_id = drivers.id
      inner join races on races.id = driver_standings.race_id
  where
      drivers.driver_identifier = p_driver_identifier;
end;
