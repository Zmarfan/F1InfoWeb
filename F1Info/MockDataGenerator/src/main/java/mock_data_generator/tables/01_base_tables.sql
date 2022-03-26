create table background_jobs(
  id int not null auto_increment,
  name varchar(255) not null,
  start_timestamp timestamp not null,
  done_timestamp timestamp,
  error_message varchar(255),

  constraint background_jobs_pk primary key (id, name)
);

create table countries(
  country_code varchar(255) not null,
  country_name varchar(255) not null,

  constraint countries_pk primary key (country_code)
);
