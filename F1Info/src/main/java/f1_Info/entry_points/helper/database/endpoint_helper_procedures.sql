drop procedure if exists endpoint_helper_is_user_manager;
create procedure endpoint_helper_is_user_manager(in p_user_id int)
begin
  select is_admin from users where id = p_user_id;
end;
