drop procedure if exists f1_helper_get_next_race_from_current;
create procedure f1_helper_get_next_race_from_current(in p_season int, in p_round int, in p_initial_season int)
begin
  declare v_next_round_race_id int;
  declare v_next_season_race_id int;

  set v_next_round_race_id := (select id from races where year = p_season and round = p_round + 1);

  if (p_season = p_initial_season and p_round = 0 and v_next_round_race_id is null) then
    call return_null();
  end if;

  if (v_next_round_race_id is not null) then
    select p_season, p_round + 1, v_next_round_race_id;
  end if;

  set v_next_season_race_id := (select id from races where year = p_season + 1 and round = 1);

  if (v_next_season_race_id is not null) then
    select p_season + 1, 1, v_next_season_race_id;
  end if;

  call return_null();
end;