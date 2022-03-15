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
