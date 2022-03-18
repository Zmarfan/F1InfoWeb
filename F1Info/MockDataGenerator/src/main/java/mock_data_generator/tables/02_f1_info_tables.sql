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

create table result_types(
  type varchar(255) not null unique,

  constraint result_types_pk primary key (type)
);

create table position_types(
  type varchar(255) not null unique,

  constraint position_types primary key (type)
);

create table fastest_laps(
  id int not null auto_increment,
  lap_fastest_lap_achieved int,
  fastest_lap_rank int,
  fastest_lap_time_in_milliseconds int,
  fastest_lap_time varchar(255),
  fastest_lap_speed varchar(255),

  constraint fastest_laps_pk primary key (id)
);

create table results(
  id int not null auto_increment,
  race_id int not null,
  driver_id int not null,
  constructor_id int not null,
  result_type varchar(255) not null,
  driver_number int,
  starting_position int not null,
  finish_position int,
  finish_position_type varchar(255) not null,
  finish_position_order int not null,
  points float not null,
  completed_laps int not null,
  finish_time_or_gap varchar(255),
  length_in_milliseconds int,
  fastest_lap_id int,
  status varchar(255) not null,

  constraint results_pk primary key (id, race_id, driver_id, constructor_id),
  constraint results_race_id foreign key (race_id) references races(id),
  constraint results_driver_id foreign key (driver_id) references drivers(id),
  constraint results_constructor_id foreign key (constructor_id) references constructors(id),
  constraint results_result_type foreign key (result_type) references result_types(type),
  constraint results_finish_position_type foreign key (finish_position_type) references position_types(type),
  constraint results_fastest_lap_id foreign key (fastest_lap_id) references fastest_laps(id)
);

create table qualifying(
  id int not null auto_increment,
  race_id int not null,
  driver_id int not null,
  constructor_id int not null,
  driver_number int not null,
  position int,
  q1_time_in_milliseconds int,
  q1_time varchar(255),
  q2_time_in_milliseconds int,
  q2_time varchar(255),
  q3_time_in_milliseconds int,
  q3_time varchar(255),

  constraint qualifying_pk primary key (id, race_id, driver_id),
  constraint qualifying_race_id foreign key (race_id) references races(id),
  constraint qualifying_driver_id foreign key (driver_id) references drivers(id),
  constraint qualifying_constructor_id foreign key (constructor_id) references constructors(id)
);

create table driver_standings(
  id int not null auto_increment,
  race_id int not null,
  driver_id int not null,
  points float not null,
  position int,
  position_type varchar(255),
  win_amount int not null,

  constraint driver_standings_pk primary key (id, race_id, driver_id),
  constraint driver_standings_race_id foreign key (race_id) references races(id),
  constraint driver_standings_driver_id foreign key (driver_id) references drivers(id),
  constraint driver_standings_position_type foreign key (position_type) references position_types(type)
);

create table constructor_standings(
  id int not null auto_increment,
  race_id int not null,
  constructor_id int not null,
  points float not null,
  position int,
  position_type varchar(255),
  win_amount int not null,

  constraint constructor_standings_pk primary key (id, race_id, constructor_id),
  constraint constructor_standings_race_id foreign key (race_id) references races(id),
  constraint constructor_standings_constructor_id foreign key (constructor_id) references constructors(id),
  constraint constructor_standings_position_type foreign key (position_type) references position_types(type)
);

create table constructor_results(
  id int not null auto_increment,
  race_id int not null,
  constructor_id int not null,
  points float,
  disqualified boolean not null,

  constraint constructor_results_pk primary key (id, race_id, constructor_id),
  constraint constructor_results_race_id foreign key (race_id) references races(id),
  constraint constructor_results_constructor_id foreign key (constructor_id) references constructors(id)
);