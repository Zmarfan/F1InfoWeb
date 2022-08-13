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

drop procedure if exists get_driver_report_filter_values_get_races_in_season;
create procedure get_driver_report_filter_values_get_races_in_season(p_season int)
begin
  select distinct
    races.name,
    races.round
  from
    races
    inner join circuits on circuits.id = races.circuit_id
    inner join driver_standings on driver_standings.race_id = races.id
  where
    races.year = p_season
  order by
    races.round;
end;

drop procedure if exists get_driver_report_filter_values_get_season_has_sprints;
create procedure get_driver_report_filter_values_get_season_has_sprints(in p_season int)
begin
  select
    if(max(races.sprint_time_and_date_id) is not null, 'Y', 'N') as has_sprints
  from
    races
    inner join driver_standings on driver_standings.race_id = races.id
  where
    races.year = p_season;
end;

drop procedure if exists get_constructor_report_filter_values_constructors;
create procedure get_constructor_report_filter_values_constructors(in p_season int)
begin
  select distinct
    constructors.constructor_identifier,
    constructors.name
  from
    constructors
    inner join constructor_standings on constructor_standings.constructor_id = constructors.id
    inner join races on races.id = constructor_standings.race_id
  where
    races.year = p_season;
end;

drop procedure if exists get_constructor_report_filter_values_get_races_in_season;
create procedure get_constructor_report_filter_values_get_races_in_season(p_season int)
begin
  select distinct
    races.name,
    races.round
  from
    races
    inner join circuits on circuits.id = races.circuit_id
    inner join constructor_standings on constructor_standings.race_id = races.id
  where
    races.year = p_season
  order by
    races.round;
end;

drop procedure if exists get_constructor_report_filter_values_get_season_has_sprints;
create procedure get_constructor_report_filter_values_get_season_has_sprints(in p_season int)
begin
  select
    if(max(races.sprint_time_and_date_id) is not null, 'Y', 'N') as has_sprints
  from
    races
    inner join constructor_standings on constructor_standings.race_id = races.id
  where
    races.year = p_season;
end;