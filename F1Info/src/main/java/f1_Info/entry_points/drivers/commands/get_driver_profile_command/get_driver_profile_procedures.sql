drop procedure if exists get_driver_profile_get;
create procedure get_driver_profile_get(in p_driver_identifier varchar(100))
begin
  declare v_current_season int;
  declare v_current_race_id int;
  set v_current_season := (select max(races.year) from races);
  set v_current_race_id := (select max(races.id) from races inner join driver_standings on driver_standings.race_id = races.id where year = v_current_season);

  select
    drivers.wikipedia_page,
    drivers.first_name,
    drivers.last_name,
    drivers.date_of_birth,
    drivers.country_code as country,
    current_constructor.name as constructor
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
  where
    drivers.driver_identifier = p_driver_identifier;
end;