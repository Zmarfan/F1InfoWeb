drop procedure if exists get_overview_constructor_report_get_rows;
create procedure get_overview_constructor_report_get_rows(in p_season int, in p_round int, in p_sort_direction varchar(5), in p_sort_column varchar(30))
begin
  select
    stats.position,
    stats.position_move_id,
    stats.name,
    stats.constructor_country,
    stats.points
  from (
     select
       constructor_standings.position,
       case
         when constructor_standings.position < last_race.position then 1
         when constructor_standings.position > last_race.position then 2
         when constructor_standings.position = last_race.position then 3
       end as position_move_id,
       constructors.name,
       constructors.country_code as constructor_country,
       countries.country_ico_code,
       constructor_standings.points as points
     from
       constructor_standings
       inner join constructors on constructors.id = constructor_standings.constructor_id
       inner join countries on countries.country_code = constructors.country_code
       inner join races on races.id = constructor_standings.race_id
       left join (
         select
           races.year,
           races.round,
           constructor_standings.constructor_id,
           constructor_standings.position
         from
           races
           inner join constructor_standings on constructor_standings.race_id = races.id
       ) last_race on last_race.year = races.year and last_race.round = races.round - 1 and last_race.constructor_id = constructors.id
     where
       races.year = p_season and races.round = p_round
     group by
       constructors.name
  ) stats
  order by
    (case when p_sort_column = 'position' and p_sort_direction = 'asc' then stats.position end),
    (case when p_sort_column = 'constructor' and p_sort_direction = 'asc' then stats.name end),
    (case when p_sort_column = 'nationality' and p_sort_direction = 'asc' then stats.country_ico_code end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'position' and p_sort_direction = 'desc' then stats.position end) desc,
    (case when p_sort_column = 'constructor' and p_sort_direction = 'desc' then stats.name end) desc,
    (case when p_sort_column = 'nationality' and p_sort_direction = 'desc' then stats.country_ico_code end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;

drop procedure if exists get_individual_constructor_report_get_rows;
create procedure get_individual_constructor_report_get_rows(
  in p_season int,
  in p_constructor_identifier varchar(100),
  in p_result_type varchar(100),
  in p_sort_direction varchar(5),
  in p_sort_column varchar(30)
)
begin
  select
    stats.race_name,
    stats.race_country,
    stats.date,
    stats.points
  from (
     select
       races.name as race_name,
       circuits.country_code as race_country,
       if(p_result_type = 'race', race_date.date, sprint_date.date) as date,
       results.points
     from
       constructor_standings
       inner join constructors on constructors.id = constructor_standings.constructor_id
       inner join races on races.id = constructor_standings.race_id
       inner join results on results.race_id = races.id and results.constructor_id = constructors.id
       inner join circuits on circuits.id = races.circuit_id
       left join time_and_dates race_date on race_date.id = races.race_time_and_date_id
       left join time_and_dates sprint_date on sprint_date.id = races.sprint_time_and_date_id
     where
       results.result_type = p_result_type and races.year = p_season and constructors.constructor_identifier = p_constructor_identifier
  ) stats
  order by
    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'asc' then stats.race_name end),
    (case when p_sort_column = 'date' and p_sort_direction = 'asc' then stats.date end),
    (case when p_sort_column = 'points' and p_sort_direction = 'asc' then stats.points end),

    (case when p_sort_column = 'grandPrix' and p_sort_direction = 'desc' then stats.race_name end) desc,
    (case when p_sort_column = 'date' and p_sort_direction = 'desc' then stats.date end) desc,
    (case when p_sort_column = 'points' and p_sort_direction = 'desc' then stats.points end) desc;
end;