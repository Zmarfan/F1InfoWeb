drop procedure if exists get_change_log_items;
create procedure get_change_log_items()
begin
  select
    change_log.item_text,
    change_log.time
  from
    change_log
  order by
    change_log.time desc ;
end;
