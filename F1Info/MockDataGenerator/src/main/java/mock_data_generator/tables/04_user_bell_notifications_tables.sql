create table bell_notification_icon_types(
  type varchar(20) not null,

  constraint bell_notification_icon_types_pk primary key (type)
);

create table bell_notifications(
  id int not null auto_increment,
  receiver_user_id int not null,
  icon_type varchar(20) not null,
  translation_key varchar(100) not null,
  creation_date timestamp not null,

  constraint bell_notifications_pk primary key (id),
  constraint bell_notifications_receiver_user_id_fk foreign key (receiver_user_id) references users(id),
  constraint bell_notifications_icon_type_fk foreign key (icon_type) references bell_notification_icon_types(type)
);

create table bell_notification_parameters(
  notification_id int not null,
  parameter_key varchar(100) not null,
  parameter_value varchar(500) not null,

  constraint bell_notification_parameters_pk primary key (notification_id, parameter_key),
  constraint bell_notification_parameters_notification_id_fk foreign key (notification_id) references bell_notifications(id)
);