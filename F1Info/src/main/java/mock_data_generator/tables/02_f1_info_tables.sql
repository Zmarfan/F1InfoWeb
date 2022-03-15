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
    constraint drivers_country foreign key (country_code) references countries(country_code)
);
