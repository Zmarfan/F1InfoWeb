create table races_fetching_history(
   season int not null,
   is_active char not null,

   constraint pit_stops_fetching_history_pk primary key (season),
   constraint pit_stops_fetching_season foreign key (season) references seasons(year)
);

select id from races where year = ? and round = ?;
update races_fetching_history set is_active = 'Y' where season = 1951;
delete from races_fetching_history where season = 1952;

select * from races;
select * from races_fetching_history;