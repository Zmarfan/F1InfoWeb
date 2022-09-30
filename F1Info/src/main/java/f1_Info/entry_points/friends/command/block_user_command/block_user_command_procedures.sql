drop procedure if exists can_block_user;
create procedure can_block_user(in p_user_id int, in p_block_user_id int)
begin
  select
    if(ifnull(min(users.id), p_user_id) = p_user_id, 'N', 'Y')
  from
    users
  where
    users.id = p_block_user_id;
end;

drop procedure if exists block_user;
create procedure block_user(in p_user_id int, in p_block_user_id int)
begin
  call insert_user_friend_status(p_user_id, p_block_user_id, 'blocked', create_event(p_user_id, 1, 1004, 'Blocked user'), current_timestamp);
  call insert_user_friend_status(p_block_user_id, p_user_id, 'not_friend', create_event(p_user_id, 1, 1004, 'Got blocked by user'), current_timestamp);
end;