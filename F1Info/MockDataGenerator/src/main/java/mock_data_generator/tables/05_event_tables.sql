create table user_information_history(
  user_id int not null,
  display_name varchar(40),
  from_date timestamp not null,
  to_date timestamp,
  event_id int not null,

  constraint user_information_history_pk primary key (user_id, event_id),
  constraint user_information_history_user_id_fk foreign key (user_id) references users (id),
  constraint user_information_history_event_id_fk foreign key (event_id) references events (id)
);

create table user_information_changes(
  user_id int not null,
  display_name varchar(40),
  from_date timestamp not null,
  event_id int not null,

  constraint user_information_changes_pk primary key (user_id, event_id),
  constraint user_information_changes_user_id_fk foreign key (user_id) references users (id),
  constraint user_information_changes_event_id_fk foreign key (event_id) references events (id)
);

create or replace view latest_user_information_v as
  select
    user_id,
    display_name,
    from_date,
    event_id
  from
    user_information_history
  where
    from_date <= current_timestamp and (to_date is null or to_date > current_timestamp);

drop procedure if exists insert_user_information;
create procedure insert_user_information(in p_user_id int, in p_display_name varchar(40), in p_event_id int, in p_from timestamp)
begin
  insert into user_information_changes (user_id, display_name, from_date, event_id) values (p_user_id, p_display_name, p_from, p_event_id);

  delete from user_information_history where user_id = p_user_id and from_date >= p_from;
  update user_information_history set to_date = p_from where user_id = p_user_id and (to_date is null or to_date > p_from);
  insert into user_information_history (user_id, display_name, from_date, to_date, event_id) values (p_user_id, p_display_name, p_from, null, p_event_id);
end;

create table bell_notification_open_status_history(
  notification_id int not null,
  opened char not null,
  from_date timestamp not null,
  to_date timestamp,
  event_id int not null,

  constraint bell_notification_open_status_history_pk primary key (notification_id, event_id),
  constraint bell_notification_open_status_history_notification_id_fk foreign key (notification_id) references bell_notifications (id),
  constraint bell_notification_open_status_history_fk_event_id_fk foreign key (event_id) references events (id)
);

create table bell_notification_open_status_changes(
  notification_id int not null,
  opened char not null,
  from_date timestamp not null,
  event_id int not null,

  constraint bell_notification_open_status_changes_pk primary key (notification_id, event_id),
  constraint bell_notification_open_status_changes_notification_id_fk foreign key (notification_id) references bell_notifications (id),
  constraint bell_notification_open_status_changes_event_id_fk foreign key (event_id) references events (id)
);

create or replace view latest_bell_notification_open_status_v as
select
  notification_id,
  opened,
  from_date,
  event_id
from
  bell_notification_open_status_history
where
  from_date <= current_timestamp and (to_date is null or to_date > current_timestamp);

drop procedure if exists insert_bell_notification_open_status;
create procedure insert_bell_notification_open_status(in p_notification_id int, in p_open_status char, in p_event_id int, in p_from timestamp)
begin
  insert into bell_notification_open_status_changes (notification_id, opened, from_date, event_id) values (p_notification_id, p_open_status, p_from, p_event_id);

  delete from bell_notification_open_status_history where notification_id = p_notification_id and from_date >= p_from;
  update bell_notification_open_status_history set to_date = p_from where notification_id = p_notification_id and (to_date is null or to_date > p_from);
  insert into bell_notification_open_status_history (notification_id, opened, from_date, to_date, event_id) values (p_notification_id, p_open_status, p_from, null, p_event_id);
end;