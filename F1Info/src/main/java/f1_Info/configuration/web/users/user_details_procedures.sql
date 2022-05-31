drop procedure if exists user_details_load_user_by_email;
create procedure user_details_load_user_by_email(in p_email varchar(255))
begin
    select
       users.id as user_id,
       users.email,
       users.password,
       user_authorities.authority_name as authority,
       users.enabled
    from
      users
      inner join user_authorities on user_authorities.user_id = users.id
    where
      users.email = p_email;
end;

drop procedure if exists user_details_create_user;
create procedure user_details_create_user(in p_email varchar(255), in p_password varchar(100), in p_authority varchar(50), in p_enabled char)
begin
    declare v_user_id int;

    insert into users (email, password, enabled) values (p_email, p_password, p_enabled);
    select last_insert_id() into v_user_id;
    insert into user_authorities (user_id, authority_name) values (v_user_id, p_authority);

    select v_user_id;
end;