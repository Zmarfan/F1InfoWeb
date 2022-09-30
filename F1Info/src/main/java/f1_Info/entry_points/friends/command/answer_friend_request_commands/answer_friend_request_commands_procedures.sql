drop procedure if exists answer_friend_request_for_user_info;
create procedure answer_friend_request_for_user_info(in p_user_id int, in p_answer_user_id int)
begin
  select
    if (friend_status.friend_status = 'pending', 'Y', 'N') as can_answer_friend_request,
    answer_info.display_name
  from
    latest_user_friend_status_v friend_status
    inner join latest_user_information_v answer_info on answer_info.user_id = friend_status.friend_user_id
  where
    friend_status.user_id = p_answer_user_id and friend_status.friend_user_id = p_user_id;
end;

drop procedure if exists accept_friend_request;
create procedure accept_friend_request(in p_user_id int, in p_answer_user_id int)
begin
  call insert_user_friend_status(p_user_id, p_answer_user_id, 'friend', create_event(p_user_id, 1, 1004, 'Accept friend request: accepter'), current_timestamp);
  call insert_user_friend_status(p_answer_user_id, p_user_id, 'friend', create_event(p_user_id, 1, 1004, 'Accept friend request: requester'), current_timestamp);
end;

drop procedure if exists decline_friend_request;
create procedure decline_friend_request(in p_user_id int, in p_answer_user_id int)
begin
  call insert_user_friend_status(p_answer_user_id, p_user_id, 'not_friend', create_event(p_user_id, 1, 1004, 'Decline friend request'), current_timestamp);
end;