drop procedure if exists tasks_insert_constructor_if_not_present;
create procedure tasks_insert_constructor_if_not_present(
  in p_constructor_identifier varchar(255),
  in p_name varchar(255),
  in p_country varchar(255),
  in p_url varchar(255)
)
begin
  declare v_exists_in_table char;
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from constructors where constructor_identifier = p_constructor_identifier);

  if (v_exists_in_table = 'N') then
    insert into constructors (constructor_identifier, name, country_code, wikipedia_page) values (p_constructor_identifier, p_name, p_country, p_url);
  end if;
end;
