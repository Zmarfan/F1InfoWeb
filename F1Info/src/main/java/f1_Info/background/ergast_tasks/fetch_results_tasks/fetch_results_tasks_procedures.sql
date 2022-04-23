drop procedure if exists tasks_insert_result_if_not_present;
create procedure tasks_insert_result_if_not_present(
  in p_result_type varchar(255),
  in p_season int,
  in p_round int,
  in p_driver_identifier varchar(255),
  in p_constructor_identifier varchar(255),
  in p_driver_number int,
  in p_finish_position_order int,
  in p_position_type varchar(255),
  in p_points float,
  in p_grid_position int,
  in p_completed_laps int,
  in p_finish_status_type varchar(255),
  in p_displayTime varchar(255),
  in p_time_in_seconds float,
  in p_fastest_lap_rank int,
  in p_fastest_lap_lap int,
  in p_fastest_lap_display_time varchar(255),
  in p_fastest_lap_in_seconds float,
  in p_fastest_lap_average_speed_unit varchar(3),
  in p_fastest_lap_average_speed float
)
begin
  declare v_race_id int;
  declare v_driver_id int;
  declare v_exists_in_table char;
  declare v_constructor_id int;
  declare v_fastest_lap_id int;

  set v_race_id := (select id from races where year = p_season and round = p_round);
  set v_driver_id := (select id from drivers where driver_identifier = p_driver_identifier);
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from results where race_id = v_race_id and driver_id = v_driver_id);

  if (v_exists_in_table = 'N') then
    set v_constructor_id := (select id from constructors where constructor_identifier = p_constructor_identifier);

    if (p_fastest_lap_lap is not null) then
      insert into fastest_laps (lap_achieved, lap_rank, time_in_seconds, display_time, speed_unit, speed)
      values (p_fastest_lap_lap, p_fastest_lap_rank, p_fastest_lap_in_seconds, p_fastest_lap_display_time, p_fastest_lap_average_speed_unit, p_fastest_lap_average_speed);
      set v_fastest_lap_id := (select last_insert_id());
    end if;

    insert into results (
      race_id,
      driver_id,
      constructor_id,
      result_type,
      driver_number,
      starting_position,
      position_type,
      finish_position_order,
      points,
      completed_laps,
      finish_time_or_gap,
      length_in_seconds,
      fastest_lap_id,
      finish_status_type
    ) values (
      v_race_id,
      v_driver_id,
      v_constructor_id,
      p_result_type,
      p_driver_number,
      p_grid_position,
      p_position_type,
      p_finish_position_order,
      p_points,
      p_completed_laps,
      p_displayTime,
      p_time_in_seconds,
      v_fastest_lap_id,
      p_finish_status_type
    );
  end if;
end;