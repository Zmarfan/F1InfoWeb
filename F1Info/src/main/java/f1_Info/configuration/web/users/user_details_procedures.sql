drop procedure if exists user_details_load_user_by_email;
create procedure user_details_load_user_by_email(in p_email varchar(255))
begin
    select
       users.id as user_id,
       users.email,
       users.password,
       user_authorities.authority_name as authority,
       users.enabled
    from
      users
      inner join user_authorities on user_authorities.user_id = users.id
    where
      users.email = p_email;
end;