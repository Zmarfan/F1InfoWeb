drop procedure if exists background_start_background_job;
create procedure background_start_background_job(in p_task_type_id int)
begin
  insert into background_jobs (task_id, start_timestamp) values (p_task_type_id, current_timestamp);
  select last_insert_id();
end;

drop procedure if exists background_stop_background_job;
create procedure background_stop_background_job(in p_task_id int, in p_error_message varchar(1000))
begin
  update background_jobs set done_timestamp = current_timestamp, error_message = p_error_message where id = p_task_id;
end;