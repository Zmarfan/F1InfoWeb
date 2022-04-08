drop procedure if exists tasks_get_next_race_to_fetch_pitstops_for;
create procedure tasks_get_next_race_to_fetch_pitstops_for(in p_first_season_with_pistop_data int)
begin
declare v_last_fetched_season int;
declare v_last_fetched_round int;
declare v_next_round_in_season_exists char;
declare v_next_season_race_exists char;

  set v_last_fetched_season := ifnull((select season from pit_stop_fetching_history where is_active = 'Y'), p_first_season_with_pistop_data);
  set v_last_fetched_round := ifnull((select round from pit_stop_fetching_history where is_active = 'Y'), 0);
  set v_next_round_in_season_exists := (select if(count(*) = 1, 'Y', 'N') from races where year = v_last_fetched_season and round = v_last_fetched_round + 1);

  if (v_last_fetched_season = p_first_season_with_pistop_data and v_last_fetched_round = 0 and v_next_round_in_season_exists = 'N') then
    select 1 from dual where false;
  end if;

  if (v_next_round_in_season_exists = 'Y') then
    select v_last_fetched_season, v_last_fetched_round + 1;
  end if;

  set v_next_season_race_exists := (select if(count(*) > 0, 'Y', 'N') from races where year = v_last_fetched_season + 1 and round = 1);

  if (v_next_season_race_exists) then
    select v_last_fetched_season + 1, 1;
  end if;

  select 1 from dual where false;
end;
