create table users(
  id int not null auto_increment,
  email varchar(50) not null,
  password varchar(100) not null,
  enabled char not null,
  is_admin char not null default 'N',

  constraint users_pk primary key (id)
);

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

create table user_registration_tokens(
    user_id int not null,
    token varchar(255) not null,
    creation_time timestamp not null,

    constraint user_registration_tokens_pk primary key (user_id, token)
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
  error_message text,

  constraint background_jobs_pk primary key (id, task_id),
  constraint background_jobs_task_id foreign key (task_id) references background_tasks(id)
);

create table countries(
  country_code varchar(255) not null,
  country_ico_code varchar(3),
  country_name varchar(255) not null,

  constraint countries_pk primary key (country_code)
);

create table event_entities(
  id int not null,
  name varchar(255),

  constraint event_entities_pk primary key (id)
);

create table event_types(
  id int not null,
  name varchar(255),

  constraint event_types_pk primary key (id)
);


create table events(
  id int not null auto_increment,
  user_id int not null,
  entity_id int not null,
  type_id int not null,
  time timestamp,
  comment varchar(255) not null,

  constraint events_pk primary key (id),
  constraint event_user_id foreign key (user_id) references users(id),
  constraint events_entity_id_fk foreign key (entity_id) references event_entities (id),
  constraint events_type_id_fk foreign key (type_id) references event_types (id)
);