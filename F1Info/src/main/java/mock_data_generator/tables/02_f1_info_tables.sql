create table seasons(
  year int not null unique,
  wikipedia_page varchar(255) not null unique,

  constraint seasons_pk primary key (year)
);

create table drivers(
  id int not null auto_increment,
  driver_identifier varchar(255) not null unique,
  number int,
  code varchar(255),
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  date_of_birth date,
  country_code varchar(255),
  wikipedia_page varchar(255) not null unique,

  constraint drivers_pk primary key (id, driver_identifier),
  constraint drivers_country_code foreign key (country_code) references countries(country_code)
);

create table constructors(
  id int not null auto_increment,
  constructor_identifier varchar(255) not null unique,
  name varchar(255) not null unique ,
  country_code varchar(255),
  wikipedia_page varchar(255) not null unique,

  constraint constructors_pk primary key (id, constructor_identifier),
  constraint constructors_country_code foreign key (country_code) references countries(country_code)
);

create table circuits(
  id int not null auto_increment,
  circuit_identifier varchar(255) not null unique,
  name varchar(255) not null unique,
  location_name varchar(255),
  country_code varchar(255),
  latitude float,
  longitude float,
  wikipedia_page varchar(255) not null unique,

  constraint circuits_pk primary key (id, circuit_identifier),
  constraint circuits_country_code foreign key (country_code) references countries(country_code)
);

create table races(
  id int not null auto_increment,
  year int not null,
  round int not null,
  circuit_id int not null,
  name varchar(255) not null,
  date date not null unique,
  time time unique,
  wikipedia_page varchar(255) unique,

  constraint races_pk primary key (id, year, round),
  constraint races_year foreign key (year) references seasons(year),
  constraint races_circuit_id foreign key (circuit_id) references circuits(id)
);

create table pit_stops(
  race_id int not null,
  driver_id int not null,
  stop int not null,
  lap int not null,
  time time not null,
  duration varchar(255),
  length_in_milliseconds float,

  constraint pit_stops_pk primary key (race_id, driver_id, stop),
  constraint pit_stops_race_id foreign key (race_id) references races(id),
  constraint pit_stops_driver_id foreign key (driver_id) references drivers(id)
);

create table lap_times(
  race_id int not null,
  driver_id int not null,
  lap int not null,
  position int,
  time varchar(255),
  length_in_milliseconds int,

  constraint lap_times_pk primary key (race_id, driver_id, lap),
  constraint lap_times_race_id foreign key (race_id) references races(id),
  constraint lap_times_driver_id foreign key (driver_id) references drivers(id)
);

create table status(
  status varchar(255) not null,

  constraint status_pk primary key (status)
);

create table results(
  id int not null auto_increment,
  race_id int not null,
  driver_id int not null,
  constructor_id int not null,
  driver_number int,
  starting_position int not null,
  finish_position int,
  finish_position_text varchar(255) not null,
  finish_position_order int not null,
  points float not null,
  completed_laps int not null,
  finish_time_or_gap varchar(255),
  length_in_milliseconds int,
  lap_fastest_lap_achieved int,
  fastest_lap_rank int,
  fastest_lap_time varchar(255),
  fastest_lap_speed varchar(255),
  status varchar(255) not null,

  constraint results_pk primary key (id, race_id, driver_id, constructor_id),
  constraint results_race_id foreign key (race_id) references races(id),
  constraint results_driver_id foreign key (driver_id) references drivers(id),
  constraint results_constructor_id foreign key (constructor_id) references constructors(id),
  constraint results_status foreign key (status) references status(status)
);