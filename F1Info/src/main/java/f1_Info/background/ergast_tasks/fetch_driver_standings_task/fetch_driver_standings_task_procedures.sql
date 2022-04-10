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



drop procedure if exists tasks_insert_driver_standing_if_not_present;
create procedure tasks_insert_driver_standing_if_not_present(
  in p_race_id int,
  in p_driver_identification varchar(255),
  in p_constructor_identification varchar(255),
  in p_points float,
  in p_position int,
  in p_amount_of_wins int
)
begin
  declare v_driver_id int;
  declare v_constructor_id int;
  declare v_exists_in_table char;

  set v_driver_id := (select id from drivers where driver_identifier = p_driver_identification);
  set v_constructor_id := (select id from constructors where constructor_identifier = p_constructor_identification);
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from driver_standings where race_id = p_race_id and driver_id = v_driver_id);

  if (v_exists_in_table = 'N') then
    insert into driver_standings (race_id, driver_id, constructor_id, points, position, win_amount)
      values (p_race_id, v_driver_id, v_constructor_id, p_points, p_position, p_amount_of_wins);
  end if;
end;