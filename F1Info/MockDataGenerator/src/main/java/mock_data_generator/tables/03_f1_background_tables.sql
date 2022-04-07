create table races_fetching_history(
   season int not null,
   is_active char not null,

   constraint pit_stops_fetching_history_pk primary key (season),
   constraint pit_stops_fetching_season foreign key (season) references seasons(year)
);