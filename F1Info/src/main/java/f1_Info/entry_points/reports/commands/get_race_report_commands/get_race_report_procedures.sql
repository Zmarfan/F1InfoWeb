drop procedure if exists get_race_report_overview;
create procedure get_race_report_overview(in p_season int, in p_result_type varchar(10), in p_sort_direction varchar(5), in p_sort_column varchar(30))
begin
  select
    stats.race_name,
    stats.country,
    stats.date,
    stats.winner_first_name,
    stats.winner_last_name,
    stats.constructor,
    stats.laps,
    stats.time
  from (
     select
       races.name as race_name,
       circuits.country_code as country,
       race_times.date,
       drivers.first_name as winner_first_name,
       drivers.last_name as winner_last_name,
       constructors.name as constructor,
       results.completed_laps as laps,
       results.finish_time_or_gap as time
     from
       races
       inner join circuits on circuits.id = races.circuit_id
       inner join time_and_dates race_times on race_times.id = races.race_time_and_date_id
       inner join results on results.race_id = races.id
       inner join drivers on drivers.id = results.driver_id
       inner join constructors on constructors.id = results.constructor_id
     where
       results.result_type = p_result_type and races.year = p_season and results.finish_position_order = 1
   ) stats
  order by
    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'asc' then stats.race_name end),
    (case when p_sort_column = 'date' and p_sort_direction = 'asc' then stats.date end),
    (case when p_sort_column = 'winner' and p_sort_direction = 'asc' then stats.winner_first_name end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'laps' and p_sort_direction = 'asc' then stats.laps end),
    (case when p_sort_column = 'time' and p_sort_direction = 'asc' then stats.time end),

    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'desc' then stats.race_name end) desc,
    (case when p_sort_column = 'date' and p_sort_direction = 'desc' then stats.date end) desc,
    (case when p_sort_column = 'winner' and p_sort_direction = 'desc' then stats.winner_first_name end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'laps' and p_sort_direction = 'desc' then stats.laps end) desc,
    (case when p_sort_column = 'time' and p_sort_direction = 'desc' then stats.time end) desc;
end;

drop procedure if exists get_race_result_report;
create procedure get_race_result_report(
  in p_season int,
  in p_round int,
  in p_result_type varchar(20),
  in p_sort_direction varchar(5),
  in p_sort_column varchar(30)
)
begin
  select
    stats.position,
    stats.start_position,
    stats.driver_number,
    stats.first_name,
    stats.last_name,
    stats.driver_country,
    stats.constructor,
    stats.laps,
    stats.time_retired,
    stats.points
  from (
     select
       results.finish_position_order as position,
       results.starting_position as start_position,
       drivers.number as driver_number,
       drivers.first_name,
       drivers.last_name,
       drivers.country_code as driver_country,
       countries.country_ico_code,
       constructors.name as constructor,
       results.completed_laps as laps,
       ifnull(
         results.finish_time_or_gap,
         case
           when results.finish_status_type like '+% Lap%' then results.finish_status_type
           when results.position_type = 'retired' then 'DNF'
           when results.position_type = 'not classified' then 'DNF'
           when results.position_type = 'Not classified' then 'DNF'
           when results.position_type = 'disqualified' then 'DSQ'
           when results.position_type = 'excluded' then 'DNS'
           when results.position_type = 'withdrawn' then 'DNS'
           when results.position_type = 'failed to qualify' then 'DNS'
           else 'DNF' end
       ) as time_retired,
       results.points
     from
       races
       inner join results on results.race_id = races.id
       inner join drivers on drivers.id = results.driver_id
       inner join countries on countries.country_code = drivers.country_code
       inner join constructors on constructors.id = results.constructor_id
     where
       results.result_type = p_result_type and races.year = p_season and races.round = p_round
  ) stats
  order by
    (case when p_sort_column = 'position' and p_sort_direction = 'asc' then stats.position end),
    (case when p_sort_column = 'startPosition' and p_sort_direction = 'asc' then stats.start_position end),
    (case when p_sort_column = 'driverNumber' and p_sort_direction = 'driverNumber' then stats.driver_number end),
    (case when p_sort_column = 'driver' and p_sort_direction = 'asc' then stats.first_name end),
    (case when p_sort_column = 'nationality' and p_sort_direction = 'asc' then stats.country_ico_code end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'laps' and p_sort_direction = 'asc' then stats.laps end),
    (case when p_sort_column = 'timeRetired' and p_sort_direction = 'asc' then stats.time_retired end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'position' and p_sort_direction = 'desc' then stats.position end) desc,
    (case when p_sort_column = 'startPosition' and p_sort_direction = 'desc' then stats.start_position end) desc,
    (case when p_sort_column = 'driverNumber' and p_sort_direction = 'desc' then stats.driver_number end) desc,
    (case when p_sort_column = 'driver' and p_sort_direction = 'desc' then stats.first_name end) desc,
    (case when p_sort_column = 'nationality' and p_sort_direction = 'desc' then stats.country_ico_code end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'laps' and p_sort_direction = 'desc' then stats.laps end) desc,
    (case when p_sort_column = 'timeRetired' and p_sort_direction = 'desc' then stats.time_retired end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;

drop procedure if exists get_fastest_laps_report;
create procedure get_fastest_laps_report(
  in p_season int,
  in p_round int,
  in p_sort_direction varchar(5),
  in p_sort_column varchar(30)
)
begin
  select
    stats.position,
    stats.driver_number,
    stats.first_name,
    stats.last_name,
    stats.driver_country,
    stats.constructor,
    stats.lap,
    stats.time,
    stats.average_speed
  from (
    select
      fastest_laps.lap_rank as position,
      drivers.number as driver_number,
      drivers.first_name,
      drivers.last_name,
      drivers.country_code as driver_country,
      countries.country_ico_code,
      constructors.name as constructor,
      fastest_laps.lap_achieved as lap,
      fastest_laps.display_time as time,
      fastest_laps.speed as average_speed
    from
      races
      inner join results on results.race_id = races.id
      inner join drivers on drivers.id = results.driver_id
      inner join countries on countries.country_code = drivers.country_code
      inner join constructors on constructors.id = results.constructor_id
      left join fastest_laps on fastest_laps.id = results.fastest_lap_id
    where
        results.result_type = 'race' and races.year = p_season and races.round = p_round
  ) stats
  order by
    (case when p_sort_column = 'position' and p_sort_direction = 'asc' then -stats.position end) desc,
    (case when p_sort_column = 'driverNumber' and p_sort_direction = 'asc' then stats.driver_number end),
    (case when p_sort_column = 'driver' and p_sort_direction = 'asc' then stats.first_name end),
    (case when p_sort_column = 'nationality' and p_sort_direction = 'asc' then stats.country_ico_code end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'lap' and p_sort_direction = 'asc' then -stats.lap end) desc,
    (case when p_sort_column = 'time' and p_sort_direction = 'asc' then -stats.time end) desc,
    (case when p_sort_column = 'averageSpeed' and p_sort_direction = 'asc' then -stats.average_speed end) desc,

    (case when p_sort_column = 'position' and p_sort_direction = 'desc' then -stats.position end),
    (case when p_sort_column = 'driverNumber' and p_sort_direction = 'desc' then stats.driver_number end) desc,
    (case when p_sort_column = 'driver' and p_sort_direction = 'desc' then stats.first_name end) desc,
    (case when p_sort_column = 'nationality' and p_sort_direction = 'desc' then stats.country_ico_code end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'lap' and p_sort_direction = 'desc' then -stats.lap end),
    (case when p_sort_column = 'time' and p_sort_direction = 'desc' then -stats.time end),
    (case when p_sort_column = 'averageSpeed' and p_sort_direction = 'desc' then -stats.average_speed end);
end;