drop procedure if exists get_all_feedback_items;
create procedure get_all_feedback_items(in p_user_id int)
begin
  select
    feedback_items.id,
    feedback_items.text,
    feedback_items.date,
    user_info.display_name as authour_display_name,
    sum(if(likes.user_id = p_user_id or likes.user_id is null, 0, 1)) as likes_not_from_user,
    if(feedback_items.author_user_id = p_user_id, 'Y', 'N') as is_own,
    if(sum(if(likes.user_id = p_user_id, 1, 0)), 'Y', 'N') as liked_by_user
  from
    feedback_items
    inner join latest_user_information_v user_info on user_info.user_id = feedback_items.author_user_id
    left join feedback_item_likes likes on likes.feedback_item_id = feedback_items.id
  group by
    feedback_items.id, feedback_items.text, feedback_items.date
  order by
    feedback_items.date desc;
end;

drop procedure if exists create_feedback_item;
create procedure create_feedback_item(in p_user_id int, in p_text varchar(255))
begin
  insert into feedback_items (author_user_id, text, date) values (p_user_id, p_text, current_timestamp);
end;