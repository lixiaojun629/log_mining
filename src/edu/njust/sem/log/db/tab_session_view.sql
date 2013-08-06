use log;

drop table if exists tab_session_view;
create table if not exists tab_session_view(
 id int auto_increment primary key,
 path varchar(2000)
);
