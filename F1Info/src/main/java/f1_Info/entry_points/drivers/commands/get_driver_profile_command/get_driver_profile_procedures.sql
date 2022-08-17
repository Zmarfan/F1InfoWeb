drop procedure if exists get_driver_profile_get;
create procedure get_driver_profile_get(in p_driver_identifier varchar(100))
begin
  declare v_current_season int;
  declare v_current_race_id int;
  set v_current_season := (select max(races.year) from races);
  set v_current_race_id := (select max(races.id) from races inner join driver_standings on driver_standings.race_id = races.id where year = 2022);

  select
    drivers.wikipedia_page,
    drivers.first_name,
    drivers.last_name,
    drivers.date_of_birth,
    drivers.country_code as country,
    current_constructor.name as constructor,
    season_finish_positions.amount_of_championships,
    season_finish_positions.amount_of_runner_up_championships,
    first_and_last_races.first_race_name,
    first_and_last_races.last_race_name,
    race_starts.years_in_f1,
    race_starts.race_starts,
    race_starts.best_position,
    race_starts.amount_of_best_position,
    race_starts.best_start_position,
    race_starts.amount_of_best_start_position,
    teammate_aggregation.teammates,
    lap_data.laps_led,
    race_starts.amount_of_podiums,
    lap_data.laps_raced
  from
    drivers
    left join (
      select
        driver_standings.driver_id,
        constructors.name
      from
        driver_standings
        inner join constructors on constructors.id = driver_standings.constructor_id
      where
        driver_standings.race_id = v_current_race_id
    ) current_constructor on current_constructor.driver_id = drivers.id
    left join (
      select
        driver_standings.driver_id,
        sum(if(driver_standings.position = 1, 1, 0)) as amount_of_championships,
        sum(if(driver_standings.position = 2, 1, 0)) as amount_of_runner_up_championships
      from
        races
        inner join driver_standings on driver_standings.race_id = races.id
        inner join (
          select
            max(round) as round,
            races.year
          from
            driver_standings
            inner join races on races.id = driver_standings.race_id
          group by
            races.year
        ) last_race_per_year on last_race_per_year.year = races.year and last_race_per_year.round = races.round
      where
        races.round = (select max(round) from races where races.year = last_race_per_year.year)
      group by
        driver_standings.driver_id
    ) season_finish_positions on season_finish_positions.driver_id = drivers.id
    left join (
      select
        drivers.id as driver_id,
        concat(first_race.name, ' ', first_race.year) as first_race_name,
        concat(last_race.name, ' ', last_race.year) as last_race_name
      from
        drivers
        inner join (
          select
            results.driver_id,
            min(races.id) as first_race_id,
            max(races.id) as latest_race_id
          from
            results
            inner join races on races.id = results.race_id
          where
            results.result_type = 'race'
          group by
            results.driver_id
        ) race_ids_first_and_last_race on race_ids_first_and_last_race.driver_id = drivers.id
        inner join races first_race on first_race.id = race_ids_first_and_last_race.first_race_id
        inner join races last_race on last_race.id = race_ids_first_and_last_race.latest_race_id
    ) first_and_last_races on first_and_last_races.driver_id = drivers.id
    left join (
      select
        results.driver_id,
        aggregated_results.best_position,
        sum(if(results.finish_position_order = aggregated_results.best_position, 1, 0)) as amount_of_best_position,
        sum(if(results.finish_position_order >= 1 and results.finish_position_order <= 3, 1, 0)) as amount_of_podiums,
        aggregated_results.best_start_position,
        sum(if(results.starting_position = aggregated_results.best_start_position, 1, 0)) as amount_of_best_start_position,
        count(*) as race_starts,
        count(distinct races.year) as years_in_f1
      from
        results
        inner join races on races.id = results.race_id
        inner join (
          select
            results.driver_id,
            min(results.finish_position_order) as best_position,
            min(nullif(results.starting_position, 0)) as best_start_position
          from
            results
          where
            results.result_type = 'race'
          group by
            results.driver_id
        ) aggregated_results on aggregated_results.driver_id = results.driver_id
      group by
        results.driver_id
    ) race_starts on race_starts.driver_id = drivers.id
    left join (
      select
        results.driver_id,
        count(distinct teammate_results.driver_id) - 1 as teammates
      from
        results
          inner join results teammate_results on teammate_results.race_id = results.race_id and teammate_results.constructor_id = results.constructor_id
      group by
        results.driver_id
    ) teammate_aggregation on teammate_aggregation.driver_id = drivers.id
    left join (
      select
        lap_times.driver_id,
        count(*) as laps_raced,
        sum(if(lap_times.position = 1, 1, 0)) as laps_led
      from
        lap_times
      group by
        lap_times.driver_id
    ) lap_data on lap_data.driver_id = drivers.id
  where
    drivers.driver_identifier = p_driver_identifier;
end;
