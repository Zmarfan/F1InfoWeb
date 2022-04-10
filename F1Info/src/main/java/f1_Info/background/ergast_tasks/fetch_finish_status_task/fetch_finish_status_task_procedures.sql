drop procedure if exists tasks_insert_finish_status_if_not_present;
create procedure tasks_insert_finish_status_if_not_present(in p_id int, in p_status varchar(255))
begin
  insert into finish_status (id, type) values (p_id, p_status) on duplicate key update id = id;
end;
