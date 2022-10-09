create table user_profile_pictures(
  user_id int not null,
  image_data longblob not null,

  constraint user_profile_pictures_pk primary key (user_id),
  constraint user_profile_pictures_user_id_fk foreign key (user_id) references users(id)
);
