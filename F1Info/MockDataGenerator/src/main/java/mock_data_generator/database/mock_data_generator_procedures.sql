drop procedure if exists mock_data_generator_create_users;
create procedure mock_data_generator_create_users(
  in p_email varchar(50),
  in p_password varchar(100),
  in p_user_icon_1 longblob,
  in p_user_icon_2 longblob,
  in p_user_icon_3 longblob,
  in p_user_icon_4 longblob,
  in p_user_icon_5 longblob,
  in p_user_icon_6 longblob,
  in p_user_icon_7 longblob,
  in p_user_icon_8 longblob,
  in p_user_icon_9 longblob,
  in p_user_icon_10 longblob
)
begin
  call mock_data_generator_create_user('Alexander Albon', '1', p_email, p_password, p_user_icon_1);
  call mock_data_generator_create_user('Sebastian Vettel', '2', p_email, p_password, p_user_icon_2);
  call mock_data_generator_create_user('Max Verstappen', '3', p_email, p_password, p_user_icon_3);
  call mock_data_generator_create_user('Lewis Hamilton', '4', p_email, p_password, p_user_icon_4);
  call mock_data_generator_create_user('Lance Stroll', '5', p_email, p_password, p_user_icon_5);
  call mock_data_generator_create_user('Daniel Ricciardo', '6', p_email, p_password, p_user_icon_6);
  call mock_data_generator_create_user('Charles Leclerc', '7', p_email, p_password, p_user_icon_7);
  call mock_data_generator_create_user('Carlos Sainz', '8', p_email, p_password, p_user_icon_8);
  call mock_data_generator_create_user('Zhou Guanyu', '9', p_email, p_password, p_user_icon_9);
  call mock_data_generator_create_user('Kevin Magnussen', '10', p_email, p_password, p_user_icon_10);

 call mock_data_generator_create_friends();

 call mock_data_generator_create_bell_notifications();
end;

drop procedure if exists mock_data_generator_create_user;
create procedure mock_data_generator_create_user(in p_display_name varchar(50), in p_no varchar(2), in p_email varchar(50), in p_password varchar(100), in p_user_icon longblob)
begin
  declare v_user_id int;
  insert into users (email, password, enabled)  values (concat(p_no, p_email), p_password, 'Y');
  set v_user_id := (select last_insert_id());

  insert into user_authorities (user_id, authority_name) values (v_user_id, 'admin');

  call insert_user_information(v_user_id, concat(p_display_name, ' ', p_no), create_event(v_user_id, 1, 1000, 'init'), current_timestamp);
  insert into user_profile_pictures (user_id, image_data) values (v_user_id, p_user_icon);
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

drop procedure if exists mock_data_generator_create_bell_notifications;
create procedure mock_data_generator_create_bell_notifications()
begin
  insert into bell_notifications (id, receiver_user_id, icon_type, click_type, translation_key, creation_date) values (1, 1, 'happy-smiley', 'feedback', 'bellMessages.completeFeedback', current_timestamp);
  insert into bell_notification_parameters (notification_id, parameter_key, parameter_value) values (1, 'feedback', 'Feedback text 1');

  insert into bell_notifications (id, receiver_user_id, icon_type, click_type, translation_key, creation_date) values (2, 1, 'happy-smiley', 'feedback', 'bellMessages.completeFeedback', current_timestamp);
  insert into bell_notification_parameters (notification_id, parameter_key, parameter_value) values (2, 'feedback', 'Feedback text 2');

  insert into bell_notifications (id, receiver_user_id, icon_type, click_type, translation_key, creation_date) values (3, 1, 'person-circle-question', 'friends', 'bellMessages.receivedFriendRequest', current_timestamp);
  insert into bell_notification_parameters (notification_id, parameter_key, parameter_value) values (3, 'user', 'Fredrik Larsson');
end;