drop procedure if exists register_token_service_find_user_from_token;
create procedure register_token_service_find_user_from_token(in p_token varchar(255))
begin
  select
    user_id,
    tokens.creation_time
  from
    users
    inner join user_registration_tokens tokens on tokens.user_id = users.id
  where
    users.enabled = 'N' and tokens.token = p_token;
end;