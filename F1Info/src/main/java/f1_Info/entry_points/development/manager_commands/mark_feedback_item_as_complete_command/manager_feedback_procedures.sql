drop procedure if exists can_mark_feedback_item_as_complete;
create procedure can_mark_feedback_item_as_complete(in p_item_id int)
begin
  select if(count(*) = 1 and completed = 'N', 'Y', 'N') from feedback_items where id = p_item_id;
end;

drop procedure if exists mark_feedback_item_as_complete;
create procedure mark_feedback_item_as_complete(in p_item_id int)
begin
  update feedback_items set completed = 'Y' where id = p_item_id;
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