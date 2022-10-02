drop procedure if exists can_remove_friend;
create procedure can_remove_friend(in p_user_id int, in p_friend_user_id int)
begin
  select
    if(count(*) = 1, 'Y', 'N')
  from
    latest_user_friends_v friends
  where
    friends.user_id = p_user_id and friends.friend_user_id = p_friend_user_id;
end;

drop procedure if exists remove_friend;
create procedure remove_friend(in p_user_id int, in p_friend_user_id int)
begin
  call insert_user_friend_status(p_user_id, p_friend_user_id, 'not_friend', create_event(p_user_id, 1, 1004, 'Removed friend'), current_timestamp);
  call insert_user_friend_status(p_friend_user_id, p_user_id, 'not_friend', create_event(p_user_id, 1, 1004, 'Got removed as friend'), current_timestamp);
end;
