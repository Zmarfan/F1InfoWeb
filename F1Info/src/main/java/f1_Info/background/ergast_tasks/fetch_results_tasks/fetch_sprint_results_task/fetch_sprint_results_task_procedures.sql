drop procedure if exists tasks_get_next_season_to_fetch_sprint_results_for;
create procedure tasks_get_next_season_to_fetch_sprint_results_for(in p_first_season_with_data int)
begin
  select ifnull((
    select if(seasons.year is not null, history.season + 1, history.season) as next_season_to_fetch
    from
      sprint_results_fetching_history history
      left join seasons on seasons.year = history.season + 1
    where
      is_active = 'Y'
  ), p_first_season_with_data) as next_fetch_year;
end;



drop procedure if exists tasks_set_last_fetched_season_for_sprint_results_fetching;
create procedure tasks_set_last_fetched_season_for_sprint_results_fetching(in p_last_fetched_season int)
begin
  update sprint_results_fetching_history set is_active = 'N' where is_active = 'Y';
  insert sprint_results_fetching_history (season, is_active) values (p_last_fetched_season, 'Y');
end;