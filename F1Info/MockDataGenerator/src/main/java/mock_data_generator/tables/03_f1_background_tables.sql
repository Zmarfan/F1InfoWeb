create table races_fetching_history(
   season int not null,
   is_active char not null,

   constraint races_fetching_history_pk primary key (season),
   constraint races_fetching_history_season foreign key (season) references seasons(year)
);

create table pit_stop_fetching_history(
   season int not null,
   round int not null,
   is_active char not null,

   constraint pit_stop_fetching_history_pk primary key (season, round),
   constraint pit_stop_fetching_history_season foreign key (season) references seasons (year)
);

create table lap_times_fetching_history(
  season int not null,
  round int not null,
  is_active char not null,

  constraint lap_times_fetching_history_pk primary key (season, round),
  constraint lap_times_fetching_history_season foreign key (season) references seasons (year)
);

create table driver_standings_fetching_history(
  season int not null,
  round int not null,
  is_active char not null,

  constraint driver_standings_fetching_history_pk primary key (season, round),
  constraint driver_standings_fetching_history_season foreign key (season) references seasons (year)
);

create table constructor_standings_fetching_history(
  season int not null,
  round int not null,
  is_active char not null,

  constraint constructor_standings_fetching_history_pk primary key (season, round),
  constraint constructor_standings_fetching_history_season foreign key (season) references seasons (year)
);

create table sprint_results_fetching_history(
  season int not null,
  is_active char not null,

  constraint sprint_results_fetching_history_pk primary key (season),
  constraint sprint_results_fetching_history_season foreign key (season) references seasons(year)
);

create table race_results_fetching_history(
  season int not null,
  is_active char not null,

  constraint race_results_fetching_history_pk primary key (season),
  constraint race_results_fetching_history_season foreign key (season) references seasons(year)
);