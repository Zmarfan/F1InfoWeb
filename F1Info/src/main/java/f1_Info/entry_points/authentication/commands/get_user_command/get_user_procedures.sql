drop procedure if exists get_user_command_get_user;
create procedure get_user_command_get_user(in p_user_id int)
begin
  select
     if(user_authorities.authority_name = 'admin', 'Y', 'N') as is_admin,
     users.email,
     user_info.display_name
  from
     users
     inner join user_authorities on user_authorities.user_id = users.id
     inner join latest_user_information_v user_info on user_info.user_id = users.id
  where
     users.id = p_user_id;
end;
