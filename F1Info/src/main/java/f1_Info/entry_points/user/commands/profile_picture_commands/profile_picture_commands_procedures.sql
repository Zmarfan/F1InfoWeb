drop procedure if exists upload_user_profile_picture;
create procedure upload_user_profile_picture(in p_user_id int, in p_image_data longblob)
begin
  insert into user_profile_pictures (user_id, image_data) values (p_user_id, p_image_data) on duplicate key update image_data = p_image_data;
end;

drop procedure if exists get_user_profile_picture;
create procedure get_user_profile_picture(in p_user_id int)
begin
  select image_data from user_profile_pictures where user_id = p_user_id;
end;