create table users(
  id int not null auto_increment,
  email varchar(50) not null,
  password varchar(100) not null,
  enabled char not null,

  constraint users_pk primary key (id)
);

create unique index index_users_email_id on users (id, email);

create table authorities(
  name varchar(50) not null,

  constraint authorities_pk primary key (name)
);

create table user_authorities(
  user_id int not null,
  authority_name varchar(50) not null,

  constraint user_authorities_pk primary key (user_id),
  constraint user_authorities_user_id_fk foreign key (user_id) references users(id),
  constraint user_authorities_authority_name_fk foreign key (authority_name) references authorities(name)
);

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
