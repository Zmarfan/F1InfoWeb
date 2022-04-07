drop procedure if exists tasks_insert_driver_if_not_present;
create procedure tasks_insert_driver_if_not_present(
  in p_driver_identifier varchar(255),
  in p_permanent_number int,
  in p_driver_code varchar(255),
  in p_first_name varchar(255),
  in p_last_name varchar(255),
  in p_date_of_birth date,
  in p_country varchar(255),
  in p_url varchar(255)
)
begin
  declare v_exists_in_table char;
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from drivers where driver_identifier = p_driver_identifier);

  if (v_exists_in_table = 'N') then
    insert into drivers (driver_identifier, number, code, first_name, last_name, date_of_birth, country_code, wikipedia_page)
      values (p_driver_identifier, p_permanent_number, p_driver_code, p_first_name, p_last_name, p_date_of_birth, p_country, p_url);
  end if;
end;
