drop procedure if exists tasks_insert_circuit_if_not_present;
create procedure tasks_insert_circuit_if_not_present(
  in p_circuit_identifier varchar(255),
  in p_circuit_name varchar(255),
  in p_location_name varchar(255),
  in p_country varchar(255),
  in p_latitude float,
  in p_longitude float,
  in p_url varchar(255)
)
begin
declare v_exists_in_table char;
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from circuits where circuit_identifier = p_circuit_identifier);

  if (v_exists_in_table = 'N') then
    insert into circuits (circuit_identifier, name, location_name, country_code, latitude, longitude, wikipedia_page)
      values (p_circuit_identifier, p_circuit_name, p_location_name, p_country, p_latitude, p_longitude, p_url);
  end if;
end;
