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
       countries.country_name,
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
    (case when p_sort_column = 'nationality' and p_sort_direction = 'asc' then stats.country_name end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.constructor end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'position' and p_sort_direction = 'desc' then stats.position end) desc,
    (case when p_sort_column = 'driver' and p_sort_direction = 'desc' then stats.first_name end) desc,
    (case when p_sort_column = 'nationality' and p_sort_direction = 'desc' then stats.country_name end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.constructor end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;