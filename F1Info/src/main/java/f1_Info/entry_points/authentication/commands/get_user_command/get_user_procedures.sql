drop procedure if exists get_user_command_get_user;
create procedure get_user_command_get_user(in p_user_id int)
begin
  select
     email
  from
     users
  where
     users.id = p_user_id;
end;