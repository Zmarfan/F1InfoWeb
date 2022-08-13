drop procedure if exists tasks_get_next_season_to_fetch_qualifying_results_for;
create procedure tasks_get_next_season_to_fetch_qualifying_results_for(in p_first_season_with_data int)
begin
  select ifnull((
    select if(seasons.year is not null, history.season + 1, history.season) as next_season_to_fetch
    from
      qualifying_results_fetching_history history
      left join seasons on seasons.year = history.season + 1
    where
      is_active = 'Y'
  ), p_first_season_with_data) as next_fetch_year;
end;



drop procedure if exists tasks_set_last_fetched_season_for_qualifying_results_fetching;
create procedure tasks_set_last_fetched_season_for_qualifying_results_fetching(in p_last_fetched_season int)
begin
  declare p_previous_fetched_season int;
  set p_previous_fetched_season = (select season from qualifying_results_fetching_history where is_active = 'Y');
  if (p_last_fetched_season != p_previous_fetched_season) then
    update qualifying_results_fetching_history set is_active = 'N' where is_active = 'Y';
    insert qualifying_results_fetching_history (season, is_active) values (p_last_fetched_season, 'Y');
  end if;
end;



drop procedure if exists tasks_insert_qualifying_result_if_not_present;
create procedure tasks_insert_qualifying_result_if_not_present(
  in p_season int,
  in p_round int,
  in p_driver_identifier varchar(255),
  in p_constructor_identifier varchar(255),
  in p_driver_number int,
  in p_position int,
  in p_q1_display_time varchar(255),
  in p_q1_time_in_seconds float,
  in p_q2_display_time varchar(255),
  in p_q2_time_in_seconds float,
  in p_q3_display_time varchar(255),
  in p_q3_time_in_seconds float
)
begin
  declare v_race_id int;
  declare v_driver_id int;
  declare v_exists_in_table char;
  declare v_constructor_id int;

  set v_race_id := (select id from races where year = p_season and round = p_round);
  set v_driver_id := (select id from drivers where driver_identifier = p_driver_identifier);
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from qualifying where race_id = v_race_id and driver_id = v_driver_id);

  if (v_exists_in_table = 'N') then
    set v_constructor_id := (select id from constructors where constructor_identifier = p_constructor_identifier);

    insert into qualifying (
      race_id,
      driver_id,
      constructor_id,
      driver_number,
      position,
      q1_time_in_seconds,
      q1_time,
      q2_time_in_seconds,
      q2_time,
      q3_time_in_seconds,
      q3_time
    ) values (
      v_race_id,
      v_driver_id,
      v_constructor_id,
      p_driver_number,
      p_position,
      p_q1_time_in_seconds,
      p_q1_display_time,
      p_q2_time_in_seconds,
      p_q2_display_time,
      p_q3_time_in_seconds,
      p_q3_display_time
    );
  end if;
end;