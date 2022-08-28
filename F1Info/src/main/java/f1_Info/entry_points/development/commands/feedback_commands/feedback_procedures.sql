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

drop procedure if exists is_user_owner_of_feedback_item;
create procedure is_user_owner_of_feedback_item(in p_user_id int, in p_item_id int)
begin
  select if(count(*) = 0, 'N', 'Y') from feedback_items where author_user_id = p_user_id and id = p_item_id;
end;

drop procedure if exists delete_feedback_item;
create procedure delete_feedback_item(in p_item_id int)
begin
  delete from feedback_item_likes where feedback_item_id = p_item_id;
  delete from feedback_items where id = p_item_id;
end;

drop procedure if exists can_like_feedback_item;
create procedure can_like_feedback_item(in p_user_id int, in p_item_id int)
begin
  select
    if(count(*) = 1 and likes.user_id is null, 'Y', 'N')
  from
    feedback_items
    left join feedback_item_likes likes on likes.feedback_item_id = feedback_items.id and likes.user_id = p_user_id
  where
    feedback_items.id = p_item_id;
end;

drop procedure if exists like_feedback_item;
create procedure like_feedback_item(in p_user_id int, in p_item_id int)
begin
  insert into feedback_item_likes (feedback_item_id, user_id) values (p_item_id, p_user_id);
end;

drop procedure if exists can_remove_like_from_feedback_item;
create procedure can_remove_like_from_feedback_item(in p_user_id int, in p_item_id int)
begin
  select
    if(count(*) = 1 and likes.user_id is not null, 'Y', 'N')
  from
    feedback_items
    left join feedback_item_likes likes on likes.feedback_item_id = feedback_items.id and likes.user_id = p_user_id
  where
    feedback_items.id = p_item_id;
end;

drop procedure if exists remove_like_from_feedback_item;
create procedure remove_like_from_feedback_item(in p_user_id int, in p_item_id int)
begin
  delete from feedback_item_likes where user_id = p_user_id and feedback_item_id = p_item_id;
end;