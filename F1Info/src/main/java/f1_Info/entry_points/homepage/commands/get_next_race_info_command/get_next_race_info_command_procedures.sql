drop procedure if exists get_next_race_info;
create procedure get_next_race_info()
begin
  select
    races.round,
    circuits.country_code as country,
    races.name,
    time_zone.time_zone_identifier,
    fp1.date as fp1_date,
    fp1.time as fp1_time,
    fp2.date as fp2_date,
    fp2.time as fp2_time,
    fp3.date as fp3_date,
    fp3.time as fp3_time,
    sprint.date as sprint_date,
    sprint.time as sprint_time,
    qualifying.date as qualifying_date,
    qualifying.time as qualifying_time,
    race.date as race_date,
    race.time as race_time
  from
    races
    inner join circuits on circuits.id = races.circuit_id
    inner join circuit_time_zones time_zone on time_zone.circuit_id = circuits.id
    inner join time_and_dates race on race.id = races.race_time_and_date_id
    left join time_and_dates fp1 on fp1.id = races.first_practice_time_and_date_id
    left join time_and_dates fp2 on fp2.id = races.second_practice_time_and_date_id
    left join time_and_dates fp3 on fp3.id = races.third_practice_time_and_date_id
    left join time_and_dates sprint on sprint.id = races.sprint_time_and_date_id
    left join time_and_dates qualifying on qualifying.id = races.qualifying_time_and_date_id
  where
    race.date >= current_date
  order by year, round limit 1;
end;