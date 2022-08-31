drop procedure if exists update_user_settings_get_current_settings;
create procedure update_user_settings_get_current_settings(in p_user_id int)
begin
  select
    settings.display_name
  from
    latest_user_information_v settings
  where
    settings.user_id = p_user_id;
end;

drop procedure if exists update_user_settings_update_settings;
create procedure update_user_settings_update_settings(in p_user_id int, in p_display_name varchar(50))
begin
  call insert_user_information(p_user_id, p_display_name, create_event(p_user_id, 1, 1000, 'Update user settings'), current_timestamp);
end;