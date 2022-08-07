drop procedure if exists user_login_reset_fail_amount_if_needed;
create procedure user_login_reset_fail_amount_if_needed(in p_ip varchar(100))
begin
  update failed_login_attempts set failed_attempts = 0 where ip = p_ip and last_failed_attempt < (current_timestamp - interval 10 minute);
end;

drop procedure if exists user_login_get_amount_of_failed_requests;
create procedure user_login_get_amount_of_failed_requests(in p_ip varchar(100))
begin
  select ifnull(min(failed_attempts), 0) from failed_login_attempts where ip = p_ip;
end;

drop procedure if exists user_login_add_failed_login_attempt;
create procedure user_login_add_failed_login_attempt(in p_ip varchar(100))
begin
  declare v_exists_in_table char;
  set v_exists_in_table := (select if(count(*) = 1, 'Y', 'N') from failed_login_attempts where ip = p_ip);

  if (v_exists_in_table = 'N') then
    insert into failed_login_attempts (ip, failed_attempts, last_failed_attempt) values (p_ip, 0, current_timestamp);
  end if;

  update failed_login_attempts set failed_attempts = failed_attempts + 1, last_failed_attempt = current_timestamp where ip = p_ip;
end;

drop procedure if exists user_login_reset_failed_attempts;
create procedure user_login_reset_failed_attempts(in p_ip varchar(100))
begin
  update failed_login_attempts set failed_attempts = 0 where ip = p_ip;
end;