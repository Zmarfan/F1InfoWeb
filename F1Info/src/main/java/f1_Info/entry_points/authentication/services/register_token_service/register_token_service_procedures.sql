drop procedure if exists register_token_service_insert_registration_token;
create procedure register_token_service_insert_registration_token(in p_user_id int, in p_token varchar(255))
begin
  insert into user_registration_tokens (user_id, token, creation_time) values (p_user_id, p_token, current_timestamp);
end;

drop procedure if exists register_token_service_find_user_from_token;
create procedure register_token_service_find_user_from_token(in p_token varchar(255))
begin
  select
    users.id as user_id,
    users.enabled,
    users.email,
    tokens.creation_time
  from
    users
    inner join user_registration_tokens tokens on tokens.user_id = users.id
  where
    tokens.token = p_token;
end;