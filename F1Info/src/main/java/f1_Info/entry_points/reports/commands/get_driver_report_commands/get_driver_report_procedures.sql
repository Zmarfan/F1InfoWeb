drop procedure if exists get_all_driver_report_get_rows;
create procedure get_all_driver_report_get_rows(in p_season int, in p_sort_direction varchar(5), in p_sort_column varchar(30))
begin
  select
    stats.position,
    stats.first_name,
    stats.last_name,
    stats.driver_country,
    stats.constructor,
    stats.points
  from (
     select
       row_number() over (order by max(driver_standings.points) desc) as position,
       drivers.first_name,
       drivers.last_name,
       drivers.country_code as driver_country,
       countries.country_ico_code,
       constructors.name as constructor,
       max(driver_standings.points) as points
     from
       driver_standings
         inner join drivers on drivers.id = driver_standings.driver_id
         inner join countries on countries.country_code = drivers.country_code
         inner join constructors on constructors.id = driver_standings.constructor_id
         inner join races on races.id = driver_standings.race_id
     where
         races.year = p_season
     group by
       drivers.first_name, drivers.last_name
  ) stats
  order by
    (case when p_sort_column = 'position' and p_sort_direction = 'asc' then stats.position end),
    (case when p_sort_column = 'driver' and p_sort_direction = 'asc' then stats.first_name end),
    (case when p_sort_column = 'nationality' and p_sort_direction = 'asc' then stats.country_ico_code end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'position' and p_sort_direction = 'desc' then stats.position end) desc,
    (case when p_sort_column = 'driver' and p_sort_direction = 'desc' then stats.first_name end) desc,
    (case when p_sort_column = 'nationality' and p_sort_direction = 'desc' then stats.country_ico_code end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;

drop procedure if exists get_individual_driver_report_get_rows;
create procedure get_individual_driver_report_get_rows(
  in p_season int,
  in p_driver_identifier varchar(100),
  in p_sort_direction varchar(5),
  in p_sort_column varchar(30)
)
begin
  select
    stats.circuit_name,
    stats.circuit_country,
    stats.date,
    stats.constructor,
    stats.race_position,
    stats.points
  from (
     select
       circuits.name as circuit_name,
       circuits.country_code as circuit_country,
       time_and_dates.date,
       constructors.name as constructor,
       case
         when results.position_type = 'finished' then results.finish_position_order
         when results.position_type = 'retired' then 'DNF'
         when results.position_type = 'disqualified' then 'DSQ'
         when results.position_type = 'excluded' then 'DNS'
         when results.position_type = 'withdrawn' then 'DNS'
         when results.position_type = 'failed to qualify' then 'DNS'
         else '-'
       end as race_position,
       results.points
     from
       driver_standings
       inner join constructors on constructors.id = driver_standings.constructor_id
       inner join drivers on drivers.id = driver_standings.driver_id
       inner join races on races.id = driver_standings.race_id
       inner join results on results.race_id = races.id and results.driver_id = drivers.id
       inner join time_and_dates on time_and_dates.id = races.race_time_and_date_id
       inner join circuits on circuits.id = races.circuit_id
     where
         results.result_type = 'race' and races.year = p_season and drivers.driver_identifier = p_driver_identifier
  ) stats
  order by
    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'asc' then stats.circuit_name end),
    (case when p_sort_column = 'date' and p_sort_direction = 'asc' then stats.date end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'racePosition' and p_sort_direction = 'asc' then stats.race_position end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'desc' then stats.circuit_name end) desc,
    (case when p_sort_column = 'date' and p_sort_direction = 'desc' then stats.date end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'racePosition' and p_sort_direction = 'desc' then stats.race_position end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;