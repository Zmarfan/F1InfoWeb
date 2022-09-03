drop procedure if exists get_all_feedback_items;
create procedure get_all_feedback_items(in p_user_id int)
begin
  select
    feedback_items.id,
    feedback_items.text,
    feedback_items.date,
    if(status.type = 'completed', 'Y', 'N') as completed,
    if(status.type = 'not-doing', 'Y', 'N') as not_doing,
    user_info.display_name as authour_display_name,
    sum(if(likes.user_id = p_user_id or likes.user_id is null, 0, 1)) as likes_not_from_user,
    if(feedback_items.author_user_id = p_user_id, 'Y', 'N') as is_own,
    if(sum(if(likes.user_id = p_user_id, 1, 0)), 'Y', 'N') as liked_by_user
  from
    feedback_items
    inner join latest_user_information_v user_info on user_info.user_id = feedback_items.author_user_id
    inner join latest_feedback_item_status_v status on status.feedback_item_id = feedback_items.id
    left join latest_feedback_item_like_status_v likes on likes.feedback_item_id = feedback_items.id
  where
    status.type != 'deleted'
  group by
    feedback_items.id, feedback_items.text, feedback_items.date, status.type
  order by
    field(status.type, 'open', 'completed', 'not-doing'), feedback_items.date desc;
end;

drop procedure if exists create_feedback_item;
create procedure create_feedback_item(in p_user_id int, in p_text varchar(255))
begin
  insert into feedback_items (author_user_id, text, date) values (p_user_id, p_text, current_timestamp);
  call insert_feedback_item_status(last_insert_id(), 'open', create_event(p_user_id, 1, 1003, 'create feedback item'), current_timestamp);
end;

drop procedure if exists can_user_delete_feedback_item;
create procedure can_user_delete_feedback_item(in p_user_id int, in p_item_id int)
begin
  select
    if(status.type != 'deleted', 'Y', 'N')
  from
    feedback_items
    inner join latest_feedback_item_status_v status on status.feedback_item_id = p_item_id
  where
    author_user_id = p_user_id and id = p_item_id;
end;

drop procedure if exists delete_feedback_item;
create procedure delete_feedback_item(in p_user_id int, in p_item_id int)
begin
  call insert_feedback_item_status(p_item_id, 'deleted', create_event(p_user_id, 1, 1003, 'delete feedback item'), current_timestamp);
end;

drop procedure if exists can_like_feedback_item;
create procedure can_like_feedback_item(in p_user_id int, in p_item_id int)
begin
  select
    if(count(*) = 1 and ifnull(likes.liked, 'N') = 'N', 'Y', 'N')
  from
    feedback_items
    inner join latest_feedback_item_status_v status on status.feedback_item_id = feedback_items.id
    left join latest_feedback_item_like_status_v likes on likes.feedback_item_id = feedback_items.id and likes.user_id = p_user_id
  where
    feedback_items.id = p_item_id and status.type = 'open';
end;

drop procedure if exists like_feedback_item;
create procedure like_feedback_item(in p_user_id int, in p_item_id int)
begin
  call insert_feedback_item_like_status(p_item_id, p_user_id, 'Y', create_event(p_user_id, 1, 1002, 'like feedback item'), current_timestamp);
end;

drop procedure if exists can_remove_like_from_feedback_item;
create procedure can_remove_like_from_feedback_item(in p_user_id int, in p_item_id int)
begin
  select
    if(count(*) = 1 and likes.user_id is not null, 'Y', 'N')
  from
    feedback_items
    inner join latest_feedback_item_status_v status on status.feedback_item_id = feedback_items.id
    left join latest_feedback_item_like_status_v likes on likes.feedback_item_id = feedback_items.id and likes.user_id = p_user_id
  where
    feedback_items.id = p_item_id and status.type = 'open';
end;

drop procedure if exists remove_like_from_feedback_item;
create procedure remove_like_from_feedback_item(in p_user_id int, in p_item_id int)
begin
  call insert_feedback_item_like_status(p_item_id, p_user_id, 'N', create_event(p_user_id, 1, 1002, 'remove like for feedback item'), current_timestamp);
end;