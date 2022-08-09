drop procedure if exists get_all_driver_report_get_rows;
create procedure get_all_driver_report_get_rows(in p_season int, in p_round int, in p_sort_direction varchar(5), in p_sort_column varchar(30))
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
       row_number() over (order by driver_standings.points desc) as position,
       drivers.first_name,
       drivers.last_name,
       drivers.country_code as driver_country,
       countries.country_ico_code,
       constructors.name as constructor,
       driver_standings.points as points
     from
       driver_standings
       inner join drivers on drivers.id = driver_standings.driver_id
       inner join countries on countries.country_code = drivers.country_code
       inner join constructors on constructors.id = driver_standings.constructor_id
       inner join races on races.id = driver_standings.race_id
     where
         races.year = p_season and round = p_round
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
  in p_result_type varchar(100),
  in p_sort_direction varchar(5),
  in p_sort_column varchar(30)
)
begin
  select
    stats.race_name,
    stats.race_country,
    stats.date,
    stats.constructor,
    stats.race_position,
    stats.points
  from (
     select
       races.name as race_name,
       circuits.country_code as race_country,
       if(p_result_type = 'race', race_date.date, sprint_date.date) as date,
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
       inner join circuits on circuits.id = races.circuit_id
       left join time_and_dates race_date on race_date.id = races.race_time_and_date_id
       left join time_and_dates sprint_date on sprint_date.id = races.sprint_time_and_date_id
     where
         results.result_type = p_result_type and races.year = p_season and drivers.driver_identifier = p_driver_identifier
  ) stats
  order by
    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'asc' then stats.race_name end),
    (case when p_sort_column = 'date' and p_sort_direction = 'asc' then stats.date end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'racePosition' and p_sort_direction = 'asc' then stats.race_position end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'desc' then stats.race_name end) desc,
    (case when p_sort_column = 'date' and p_sort_direction = 'desc' then stats.date end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'racePosition' and p_sort_direction = 'desc' then stats.race_position end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;