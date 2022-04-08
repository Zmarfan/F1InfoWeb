drop procedure if exists tasks_insert_season_if_not_present;
create procedure tasks_insert_season_if_not_present(in p_season int, in p_url varchar(255))
begin
  insert into seasons (year, wikipedia_page) values (p_season, p_url) on duplicate key update year = year;
end;