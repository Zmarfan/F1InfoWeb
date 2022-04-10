drop procedure if exists tasks_get_next_race_to_fetch_driver_standings_for;
create procedure tasks_get_next_race_to_fetch_driver_standings_for(in p_first_f1_season int)
begin
  declare v_last_fetched_season int;
  declare v_last_fetched_round int;

  set v_last_fetched_season := ifnull((select season from driver_standings_fetching_history where is_active = 'Y'), p_first_f1_season);
  set v_last_fetched_round := ifnull((select round from driver_standings_fetching_history where is_active = 'Y'), 0);

  call f1_helper_get_next_race_from_current(v_last_fetched_season, v_last_fetched_round, p_first_f1_season);
end;



drop procedure if exists tasks_set_last_fetched_race_for_driver_standings_fetching;
create procedure tasks_set_last_fetched_race_for_driver_standings_fetching(in p_last_fetched_season int, in p_last_fetched_round int)
begin
  update driver_standings_fetching_history set is_active = 'N' where is_active = 'Y';
  insert driver_standings_fetching_history (season, round, is_active) values (p_last_fetched_season, p_last_fetched_round, 'Y');
end;