create table failed_login_attempts(
  ip varchar(100) not null,
  failed_attempts int not null,
  last_failed_attempt timestamp not null,

  constraint failed_login_attempts_pk primary key (ip)
);