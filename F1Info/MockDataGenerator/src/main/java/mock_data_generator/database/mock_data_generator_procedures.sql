drop procedure if exists mock_data_generator_create_users;
create procedure mock_data_generator_create_users(in p_email varchar(50), in p_password varchar(100))
begin
  call mock_data_generator_create_user('Kellie Chandler', '1', p_email, p_password);
  call mock_data_generator_create_user('Dana Mack', '2', p_email, p_password);
  call mock_data_generator_create_user('Terry Hammond', '3', p_email, p_password);
  call mock_data_generator_create_user('Lance Dixon', '4', p_email, p_password);
  call mock_data_generator_create_user('Christian Casey', '5', p_email, p_password);
  call mock_data_generator_create_user('Joey Peterson', '6', p_email, p_password);
  call mock_data_generator_create_user('Jesse Paul', '7', p_email, p_password);
  call mock_data_generator_create_user('Juanita Haynes', '8', p_email, p_password);
  call mock_data_generator_create_user('Lewis Floyd', '9', p_email, p_password);
  call mock_data_generator_create_user('Angelo Gill', '10', p_email, p_password);

 call mock_data_generator_create_friends();
end;

drop procedure if exists mock_data_generator_create_user;
create procedure mock_data_generator_create_user(in p_display_name varchar(50), in p_no varchar(2), in p_email varchar(50), in p_password varchar(100))
begin
  declare v_user_id int;
  insert into users (email, password, enabled)  values (concat(p_no, p_email), p_password, 'Y');
  set v_user_id := (select last_insert_id());

  insert into user_authorities (user_id, authority_name) values (v_user_id, 'admin');

  call insert_user_information(v_user_id, concat(p_display_name, ' ', p_no), create_event(v_user_id, 1, 1000, 'init'), current_timestamp);
end;

drop procedure if exists mock_data_generator_create_friends;
create procedure mock_data_generator_create_friends()
begin
  call insert_user_friend_status(1, 3, 'pending', create_event(1, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(1, 4, 'pending', create_event(1, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(1, 5, 'not_friend', create_event(1, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(1, 6, 'friend', create_event(1, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(1, 7, 'friend', create_event(1, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(2, 3, 'friend', create_event(2, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(2, 5, 'friend', create_event(2, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(2, 7, 'friend', create_event(2, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(3, 1, 'blocked', create_event(3, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(3, 2, 'friend', create_event(3, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(3, 4, 'friend', create_event(3, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(4, 3, 'friend', create_event(4, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(4, 5, 'friend', create_event(4, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(5, 1, 'not_friend', create_event(5, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(5, 2, 'friend', create_event(5, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(5, 3, 'pending', create_event(5, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(5, 4, 'friend', create_event(5, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(6, 1, 'friend', create_event(6, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(6, 3, 'pending', create_event(6, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(7, 1, 'friend', create_event(7, 1, 1004, 'init'), current_timestamp);
  call insert_user_friend_status(7, 2, 'friend', create_event(7, 1, 1004, 'init'), current_timestamp);

  call insert_user_friend_status(8, 1, 'blocked', create_event(8, 1, 1004, 'init'), current_timestamp);
end;