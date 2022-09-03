create table feedback_items(
  id int not null auto_increment,
  author_user_id int not null,
  text varchar(255) not null,
  date timestamp not null,
  completed char default 'N' not null,
  open char default 'Y' not null,

  constraint feedback_items_pk primary key (id),
  constraint feedback_items_author_user_id_fk foreign key (author_user_id) references users(id)
);