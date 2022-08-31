drop procedure if exists get_user_bell_notifications_to_display;
create procedure get_user_bell_notifications_to_display(in p_user_id int)
begin
  select
    notifications.id as notification_id,
    notifications.icon_type,
    notifications.translation_key,
    ifnull(open_status.opened, 'N') as opened,
    group_concat(params.parameter_key, ':', params.parameter_value) as parameters
  from
    bell_notifications notifications
    left join bell_notification_parameters params on params.notification_id = notifications.id
    left join latest_bell_notification_open_status_v open_status on open_status.notification_id = notifications.id
  where
    notifications.receiver_user_id = p_user_id and (ifnull(open_status.opened, 'N') = 'N' or notifications.creation_date > (current_timestamp - interval 14 day))
  group by
    notifications.id, notifications.creation_date
  order by
    notifications.creation_date desc ;
end;

drop procedure if exists mark_bell_notifications_as_opened;
create procedure mark_bell_notifications_as_opened(in p_user_id int, in p_notification_id int)
begin
  call insert_bell_notification_open_status(p_notification_id, 'Y', create_event(1, 1001, 'Opened notification normally'), current_timestamp);
end;
