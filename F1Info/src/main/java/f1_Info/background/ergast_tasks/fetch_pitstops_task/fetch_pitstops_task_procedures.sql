drop procedure if exists tasks_get_next_race_to_fetch_pitstops_for;
create procedure tasks_get_next_race_to_fetch_pitstops_for(in p_first_season_with_pitstop_data int)
begin
  declare v_last_fetched_season int;
  declare v_last_fetched_round int;

  set v_last_fetched_season := ifnull((select season from pit_stop_fetching_history where is_active = 'Y'), p_first_season_with_pitstop_data);
  set v_last_fetched_round := ifnull((select round from pit_stop_fetching_history where is_active = 'Y'), 0);

  call f1_helper_get_next_race_from_current(v_last_fetched_season, v_last_fetched_round, p_first_season_with_pitstop_data);
end;



drop procedure if exists tasks_set_last_fetched_race_for_pitstop_fetching;
create procedure tasks_set_last_fetched_race_for_pitstop_fetching(in p_last_fetched_season int, in p_last_fetched_round int)
begin
  update pit_stop_fetching_history set is_active = 'N' where is_active = 'Y';
  insert pit_stop_fetching_history (season, round, is_active) values (p_last_fetched_season, p_last_fetched_round, 'Y');
end;



drop procedure if exists tasks_insert_pit_stop_if_not_present;
create procedure tasks_insert_pit_stop_if_not_present(
  in p_race_id int,
  in p_driver_identification varchar(255),
  in p_lap int,
  in p_stop int,
  in p_time time,
  in p_duration_in_seconds float
)
begin
  declare v_driver_id int;
  declare v_exists_in_table char;

  set v_driver_id := (select id from drivers where driver_identifier = p_driver_identification);
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from pit_stops where race_id = p_race_id and driver_id = v_driver_id and stop = p_stop);

  if (v_exists_in_table = 'N') then
    insert into pit_stops (race_id, driver_id, stop, lap, time, length_in_seconds)
      values (p_race_id, v_driver_id, p_stop, p_lap, p_time, p_duration_in_seconds);
  end if;
end;
