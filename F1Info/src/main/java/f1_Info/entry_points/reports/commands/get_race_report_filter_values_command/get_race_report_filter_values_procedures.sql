drop procedure if exists get_race_report_filter_values_get_races_info;
create procedure get_race_report_filter_values_get_races_info(in p_season int)
begin
  select distinct
    races.name,
    races.round,
    circuits.country_code as country
  from
    races
    inner join circuits on circuits.id = races.circuit_id
    inner join driver_standings on driver_standings.race_id = races.id
  where
    races.year = p_season;
end;