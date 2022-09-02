drop procedure if exists mock_data_generator_create_users;
create procedure mock_data_generator_create_users(in p_email varchar(50), in p_password varchar(100))
begin
    declare v_user_id int;
    insert into users (email, password, enabled)  values (p_email, p_password, 'Y');
    set v_user_id := (select last_insert_id());

    insert into user_authorities (user_id, authority_name) values (v_user_id, 'admin');

    call insert_user_information(v_user_id, 'New User', create_event(v_user_id, 1, 1000, 'init'), current_timestamp);
end;