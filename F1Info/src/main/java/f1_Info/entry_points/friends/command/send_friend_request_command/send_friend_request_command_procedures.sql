drop procedure if exists can_send_friend_request;
create procedure can_send_friend_request(in p_user_id int, in p_send_request_user_id int)
begin
  select
    if(ifnull(min(friend_status.friend_status), 'not_friend') = 'not_friend', 'Y', 'N')
  from
    latest_user_friend_status_v friend_status
  where
    friend_status.user_id = p_user_id and friend_status.friend_user_id = p_send_request_user_id;
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
