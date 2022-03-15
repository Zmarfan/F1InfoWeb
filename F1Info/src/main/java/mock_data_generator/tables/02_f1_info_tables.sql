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
