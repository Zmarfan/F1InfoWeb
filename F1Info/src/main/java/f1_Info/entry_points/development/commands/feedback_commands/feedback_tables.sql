create table feedback_items(
  id int not null auto_increment,
  author_user_id int not null,
  text varchar(255) not null,
  date timestamp not null,

  constraint feedback_items_pk primary key (id),
  constraint feedback_items_author_user_id_fk foreign key (author_user_id) references users(id)
);

create table feedback_item_likes(
  feedback_item_id int not null,
  user_id int not null,

  constraint feedback_item_likes_pk primary key (feedback_item_id, user_id),
  constraint feedback_item_likes_user_id_fk foreign key (user_id) references users(id)
);