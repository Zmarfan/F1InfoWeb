drop procedure if exists get_friend_requests;
create procedure get_friend_requests(in p_user_id int)
begin
  select
    user_info.user_id,
    user_info.display_name,
    friend_status.user_id
  from
    users
    inner join latest_user_friend_status_v friend_status on friend_status.friend_user_id = users.id
    inner join latest_user_information_v user_info on user_info.user_id = friend_status.user_id
    left join (
      select
        my_status.user_id,
        my_status.friend_user_id
      from
        latest_user_friend_status_v my_status
      where
        my_status.friend_status = 'blocked'
    ) blocked_friends on blocked_friends.user_id = users.id and blocked_friends.friend_user_id = friend_status.user_id
  where
    users.id = p_user_id and friend_status.friend_status = 'pending' and blocked_friends.friend_user_id is null;
end;

drop procedure if exists get_friends;
create procedure get_friends(in p_user_id int)
begin
  select
    friends.friend_user_id as user_id,
    friend_info.display_name
  from
    latest_user_friends_v friends
    inner join latest_user_information_v friend_info on friend_info.user_id = friends.friend_user_id
  where
    friends.user_id = p_user_id;
end;