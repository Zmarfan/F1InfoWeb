create table background_tasks(
  id int not null unique,
  name varchar(255) not null unique,

  constraint background_tasks_pk primary key (id)
);

create table background_jobs(
  id int not null auto_increment,
  task_id int not null,
  start_timestamp timestamp not null,
  done_timestamp timestamp,
  error_message varchar(255),

  constraint background_jobs_pk primary key (id, task_id),
  constraint background_jobs_task_id foreign key (task_id) references background_tasks(id)
);

create table countries(
  country_code varchar(255) not null,
  country_name varchar(255) not null,

  constraint countries_pk primary key (country_code)
);
