drop procedure if exists get_search_friend_info;
create procedure get_search_friend_info(in p_user_id int, in p_search_user_id int)
begin
  select
    search_user_info.display_name,
    shared_friends.friends_in_common,
    ifnull(friend_status.friend_status, 'not_friend') as friend_status
  from
    users
    inner join (
      select
        users.id as user_id,
        sum(if(own_friends.friend_user_id = search_friends.friend_user_id, 1, 0)) as friends_in_common
      from
        users
        left join latest_user_friends_v own_friends on own_friends.user_id = users.id
        left join latest_user_friends_v search_friends on search_friends.user_id = p_search_user_id
      where
        users.id = p_user_id
    ) shared_friends on shared_friends.user_id = users.id
    left join latest_user_information_v search_user_info on search_user_info.user_id = p_search_user_id
    left join latest_user_friend_status_v friend_status on friend_status.user_id = users.id and friend_status.friend_user_id = search_user_info.user_id
  where
    users.id = p_user_id;
end;
