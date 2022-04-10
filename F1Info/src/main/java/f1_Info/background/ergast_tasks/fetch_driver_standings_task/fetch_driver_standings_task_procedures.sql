drop procedure if exists tasks_get_next_race_to_fetch_driver_standings_for;
create procedure tasks_get_next_race_to_fetch_driver_standings_for(in p_first_f1_season int)
begin
  declare v_last_fetched_season int;
  declare v_last_fetched_round int;
  declare v_next_round_race_id int;
  declare v_next_season_race_id int;

  set v_last_fetched_season := ifnull((select season from driver_standings_fetching_history where is_active = 'Y'), p_first_f1_season);
  set v_last_fetched_round := ifnull((select round from driver_standings_fetching_history where is_active = 'Y'), 0);
  set v_next_round_race_id := (select id from races where year = v_last_fetched_season and round = v_last_fetched_round + 1);

  if (v_last_fetched_season = p_first_f1_season and v_last_fetched_round = 0 and v_next_round_race_id is null) then
    call return_null();
  end if;

  if (v_next_round_race_id is not null) then
    select v_last_fetched_season, v_last_fetched_round + 1, v_next_round_race_id;
  end if;

  set v_next_season_race_id := (select id from races where year = v_last_fetched_season + 1 and round = 1);

  if (v_next_season_race_id is not null) then
    select v_last_fetched_season + 1, 1, v_next_season_race_id;
  end if;

  call return_null();
end;