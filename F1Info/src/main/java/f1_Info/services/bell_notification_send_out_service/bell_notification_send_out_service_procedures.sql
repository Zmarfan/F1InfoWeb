drop procedure if exists create_bell_notification;
create procedure create_bell_notification(in p_user_id int, in p_icon_code varchar(30), in p_key varchar(100))
begin
  insert into bell_notifications (receiver_user_id, icon_type, translation_key, creation_date) values (p_user_id, p_icon_code, p_key, current_timestamp);
  select last_insert_id();
end;

drop procedure if exists create_bell_notification_parameter;
create procedure create_bell_notification_parameter(in p_notification_id int, in p_key varchar(100), in p_value varchar(500))
begin
  insert into bell_notification_parameters (notification_id, parameter_key, parameter_value) values (p_notification_id, p_key, p_value);
end;
