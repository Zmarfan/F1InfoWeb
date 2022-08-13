drop procedure if exists tasks_get_next_season_to_fetch_for_races;
create procedure tasks_get_next_season_to_fetch_for_races(in p_first_formula_1_race int)
begin
  select ifnull((
    select
      case when seasons.year is not null then history.season + 1 else -1 end as next_season_to_fetch
    from
      races_fetching_history history
      left join seasons on seasons.year = history.season + 1
    where
        is_active = 'Y'
    ), p_first_formula_1_race) as next_fetch_year;
end;



drop procedure if exists tasks_set_last_fetched_season_for_race_fetching;
create procedure tasks_set_last_fetched_season_for_race_fetching(in p_last_fetched_season int)
begin
  declare p_previous_fetched_season int;
  set p_previous_fetched_season = (select season from races_fetching_history where is_active = 'Y');
  if (p_last_fetched_season != p_previous_fetched_season) then
    update races_fetching_history set is_active = 'N' where is_active = 'Y';
    insert races_fetching_history (season, is_active) values (p_last_fetched_season, 'Y');
  end if;
end;



drop procedure if exists tasks_insert_race_if_not_present;
create procedure tasks_insert_race_if_not_present(
  in p_year int,
  in p_round int,
  in p_url varchar(255),
  in p_race_name varchar(255),
  in p_race_time time,
  in p_race_date date,
  in p_qualifying_time time,
  in p_qualifying_date date,
  in p_sprint_time time,
  in p_sprint_date date,
  in p_first_practice_time time,
  in p_first_practice_date date,
  in p_second_practice_time time,
  in p_second_practice_date date,
  in p_third_practice_time time,
  in p_third_practice_date date,
  in p_circuit_identifier varchar(255)
)
begin
  declare v_exists_in_table char;
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from races where year = p_year and round = p_round);

  if (v_exists_in_table = 'N') then
    insert into races (
      year,
      round,
      circuit_id,
      name,
      race_time_and_date_id,
      qualifying_time_and_date_id,
      sprint_time_and_date_id,
      first_practice_time_and_date_id,
      second_practice_time_and_date_id,
      third_practice_time_and_date_id,
      wikipedia_page
    ) values (
      p_year,
      p_round,
      (select id from circuits where circuit_identifier = p_circuit_identifier),
      p_race_name,
      tasks_insert_time_and_date_if_data_present(p_race_time, p_race_date),
      tasks_insert_time_and_date_if_data_present(p_qualifying_time, p_qualifying_date),
      tasks_insert_time_and_date_if_data_present(p_sprint_time, p_sprint_date),
      tasks_insert_time_and_date_if_data_present(p_first_practice_time, p_first_practice_date),
      tasks_insert_time_and_date_if_data_present(p_second_practice_time, p_second_practice_date),
      tasks_insert_time_and_date_if_data_present(p_third_practice_time, p_third_practice_date),
      p_url
    );
  end if;
end;

drop function if exists tasks_insert_time_and_date_if_data_present;
create function tasks_insert_time_and_date_if_data_present(p_time time, p_date date) returns int
begin
  if (p_time is null and p_date is null) then
    return null;
  end if;

  insert into time_and_dates (date, time) values (p_date, p_time);
  return last_insert_id();
end;