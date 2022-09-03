drop procedure if exists can_mark_feedback_item_as_complete;
create procedure can_mark_feedback_item_as_complete(in p_item_id int)
begin
  select
    if(count(*) = 1 and status.type != 'completed', 'Y', 'N')
  from
    feedback_items
    inner join latest_feedback_item_status_v status on feedback_items.id = status.feedback_item_id
  where
    id = p_item_id;
end;

drop procedure if exists mark_feedback_item_as_complete;
create procedure mark_feedback_item_as_complete(in p_user_id int, in p_item_id int)
begin
  call insert_feedback_item_status(p_item_id, 'completed', create_event(p_user_id, 1, 1003, 'complete feedback item'), current_timestamp);
end;

drop procedure if exists get_feedback_author_info;
create procedure get_feedback_author_info(in p_item_id int)
begin
  select
    feedback_items.author_user_id as user_id,
    feedback_items.text as feedback
  from
    feedback_items
  where
    feedback_items.id = p_item_id;
end;

drop procedure if exists can_mark_feedback_item_as_will_not_do;
create procedure can_mark_feedback_item_as_will_not_do(in p_item_id int)
begin
  select
    if(count(*) = 1 and status.type != 'not-doing', 'Y', 'N')
  from
    feedback_items
    inner join latest_feedback_item_status_v status on feedback_items.id = status.feedback_item_id
  where
    id = p_item_id;
end;

drop procedure if exists mark_feedback_item_as_will_not_do;
create procedure mark_feedback_item_as_will_not_do(in p_user_id int, in p_item_id int)
begin
  call insert_feedback_item_status(p_item_id, 'not-doing', create_event(p_user_id, 1, 1003, 'not doing feedback item'), current_timestamp);
end;