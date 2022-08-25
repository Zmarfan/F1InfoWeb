create table change_log(
  time timestamp not null,
  item_text text not null,

  constraint change_log_pk primary key (time)
);