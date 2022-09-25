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

create table feedback_item_like_status_history(
  feedback_item_id int not null,
  user_id int not null,
  liked char not null,
  from_date timestamp not null,
  to_date timestamp,
  event_id int not null,

  constraint feedback_item_like_status_history_pk primary key (feedback_item_id, user_id, event_id),
  constraint feedback_item_like_status_history_user_id_fk foreign key (user_id) references users (id),
  constraint feedback_item_like_status_history_feedback_item_id_fk foreign key (feedback_item_id) references feedback_items (id),
  constraint feedback_item_like_status_history_fk_event_id_fk foreign key (event_id) references events (id)
);

create table feedback_item_like_status_changes(
  feedback_item_id int not null,
  user_id int not null,
  liked char not null,
  from_date timestamp not null,
  event_id int not null,

  constraint feedback_item_like_status_changes_pk primary key (feedback_item_id, user_id, event_id),
  constraint feedback_item_like_status_changes_user_id_fk foreign key (user_id) references users (id),
  constraint feedback_item_like_status_changes_feedback_item_id_fk foreign key (feedback_item_id) references feedback_items (id),
  constraint feedback_item_like_status_changes_event_id_fk foreign key (event_id) references events (id)
);

create or replace view latest_feedback_item_like_status_v as
select
  feedback_item_id,
  user_id,
  liked,
  from_date,
  event_id
from
  feedback_item_like_status_history
where
  from_date <= current_timestamp and (to_date is null or to_date > current_timestamp);

drop procedure if exists insert_feedback_item_like_status;
create procedure insert_feedback_item_like_status(in p_feedback_item_id int, in p_user_id int, in p_like_status char, in p_event_id int, in p_from timestamp)
begin
  insert into feedback_item_like_status_changes (feedback_item_id, user_id, liked, from_date, event_id) values (p_feedback_item_id, p_user_id, p_like_status, p_from, p_event_id);

  delete from feedback_item_like_status_history where feedback_item_id = p_feedback_item_id and user_id = p_user_id and from_date >= p_from;
  update feedback_item_like_status_history set to_date = p_from where feedback_item_id = p_feedback_item_id and user_id = p_user_id and (to_date is null or to_date > p_from);
  insert into feedback_item_like_status_history (feedback_item_id, user_id, liked, from_date, to_date, event_id) values (p_feedback_item_id, p_user_id, p_like_status, p_from, null, p_event_id);
end;

create table feedback_item_status_history(
  feedback_item_id int not null,
  type varchar(10) not null,
  from_date timestamp not null,
  to_date timestamp,
  event_id int not null,

  constraint feedback_item_status_history_pk primary key (feedback_item_id, event_id),
  constraint feedback_item_status_history_feedback_item_id_fk foreign key (feedback_item_id) references feedback_items (id),
  constraint feedback_item_status_history_fk_event_id_fk foreign key (event_id) references events (id)
);

create table feedback_item_status_changes(
  feedback_item_id int not null,
  type varchar(10) not null,
  from_date timestamp not null,
  event_id int not null,

  constraint feedback_item_status_changes_pk primary key (feedback_item_id, event_id),
  constraint feedback_item_status_changes_feedback_item_id_fk foreign key (feedback_item_id) references feedback_items (id),
  constraint feedback_item_status_changes_event_id_fk foreign key (event_id) references events (id)
);

create or replace view latest_feedback_item_status_v as
select
  feedback_item_id,
  type,
  from_date,
  event_id
from
  feedback_item_status_history
where
  from_date <= current_timestamp and (to_date is null or to_date > current_timestamp);

drop procedure if exists insert_feedback_item_status;
create procedure insert_feedback_item_status(in p_feedback_item_id int, in p_type varchar(10), in p_event_id int, in p_from timestamp)
begin
  insert into feedback_item_status_changes (feedback_item_id, type, from_date, event_id) values (p_feedback_item_id, p_type, p_from, p_event_id);

  delete from feedback_item_status_history where feedback_item_id = p_feedback_item_id and from_date >= p_from;
  update feedback_item_status_history set to_date = p_from where feedback_item_id = p_feedback_item_id and (to_date is null or to_date > p_from);
  insert into feedback_item_status_history (feedback_item_id, type, from_date, to_date, event_id) values (p_feedback_item_id, p_type, p_from, null, p_event_id);
end;

create table user_friend_status_history(
  user_id int not null,
  friend_user_id int not null,
  friend_status varchar(10) not null,
  from_date timestamp not null,
  to_date timestamp,
  event_id int not null,

  constraint user_friend_status_history_pk primary key (user_id, friend_user_id, event_id),
  constraint user_friend_status_history_user_id_fk foreign key (user_id) references users (id),
  constraint user_friend_status_history_friend_user_id_fk foreign key (friend_user_id) references users (id),
  constraint user_friend_status_history_friend_friend_status_fk foreign key (friend_status) references friend_status (type),
  constraint user_friend_status_history_event_id_fk foreign key (event_id) references events (id)
);

create table user_friend_status_changes(
  user_id int not null,
  friend_user_id int not null,
  friend_status varchar(10) not null,
  from_date timestamp not null,
  event_id int not null,

  constraint user_friend_status_changes_pk primary key (user_id, friend_user_id, event_id),
  constraint user_friend_status_changes_user_id_fk foreign key (user_id) references users (id),
  constraint user_friend_status_changes_friend_user_id_fk foreign key (friend_user_id) references users (id),
  constraint user_friend_status_changes_friend_friend_status_fk foreign key (friend_status) references friend_status (type),
  constraint user_friend_status_changes_event_id_fk foreign key (event_id) references events (id)
);

create or replace view latest_user_friend_status_v as
select
  user_id,
  friend_user_id,
  friend_status,
  from_date,
  event_id
from
  user_friend_status_history
where
  from_date <= current_timestamp and (to_date is null or to_date > current_timestamp);

drop procedure if exists insert_user_friend_status;
create procedure insert_user_friend_status(in p_user_id int, in p_friend_user_id int, in p_friend_status varchar(10), in p_event_id int, in p_from timestamp)
begin
  insert into user_friend_status_changes (user_id, friend_user_id, friend_status, from_date, event_id) values (p_user_id, p_friend_user_id, p_friend_status, p_from, p_event_id);

  delete from user_friend_status_history where user_id = p_user_id and friend_user_id = p_friend_user_id and from_date >= p_from;
  update user_friend_status_history set to_date = p_from where user_id = p_user_id and friend_user_id = p_friend_user_id and (to_date is null or to_date > p_from);
  insert into user_friend_status_history (user_id, friend_user_id, friend_status, from_date, to_date, event_id) values (p_user_id, p_friend_user_id, p_friend_status, p_from, null, p_event_id);
end;

create or replace view latest_user_friends_v as
select
  user_id,
  friend_user_id,
  from_date,
  event_id
from
  latest_user_friend_status_v
where
  latest_user_friend_status_v.friend_status = 'friend';