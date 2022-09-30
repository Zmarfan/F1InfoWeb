drop procedure if exists send_friend_request_info;
create procedure send_friend_request_info(in p_user_id int, in p_send_request_user_id int)
begin
  select
    if(ifnull(min(friend_status.friend_status), 'not_friend') in ('not_friend', 'blocked'), 'Y', 'N') as can_send_friend_request,
    if(blocked_status.friend_status = 'blocked', 'Y', 'N') as receiver_has_blocked_user,
    own_user_info.display_name
  from
    latest_user_information_v own_user_info
    left join latest_user_friend_status_v friend_status on friend_status.user_id = own_user_info.user_id and friend_status.friend_user_id = p_send_request_user_id
    left join latest_user_friend_status_v blocked_status on blocked_status.friend_user_id = own_user_info.user_id and blocked_status.user_id = p_send_request_user_id
  where
      own_user_info.user_id = p_user_id;
end;

drop procedure if exists send_friend_request;
create procedure send_friend_request(in p_user_id int, in p_send_request_user_id int)
begin
  call insert_user_friend_status(
    p_user_id,
    p_send_request_user_id,
    'pending',
    create_event(p_user_id, 1, 1004, 'Friend Request'),
    current_timestamp
  );
end;
