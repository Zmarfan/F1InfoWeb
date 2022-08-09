drop procedure if exists get_race_report_overview;
create procedure get_race_report_overview(in p_season int, in p_sort_direction varchar(5), in p_sort_column varchar(30))
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
       results.result_type = 'race' and races.year = p_season and results.finish_position_order = 1
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