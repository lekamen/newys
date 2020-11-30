create table role( 
	role_id int not null,
	name varchar(100) not null,
	constraint role_pk primary key(role_id),
	constraint role_name_u unique(name)
);

create table action (
	action_id int not null,
	name varchar(100) not null,
	constraint action_pk primary key(action_id),
	constraint action_name_u unique(name)
);

create table role_action(
	role_id int not null, 
	action_id int not null,
	constraint role_action_role_fk foreign key(role_id) references role(role_id),
	constraint role_action_action_fk foreign key(action_id) references action(action_id),
	constraint role_action_pk primary key (role_id, action_id)
);

create table user_account (
	user_account_id int not null,
	email varchar(254) not null,
	password varchar(60) not null,
	username varchar(30) not null,
	status int not null,
	author_status int not null,
	constraint user_account_status_check check (status in (0, 1)),
	constraint user_account_author_status_check check (author_status in (0, 1, 2, 3)),
	constraint user_account_pk primary key(user_account_id),
	constraint user_account_email_u unique(email),
	constraint user_account_username_u unique(username)
);

create sequence user_account_sequence;
comment on column user_account.status is '0 - inactive, 1 - active';
comment on column user_account.author_status is '0 - none, 1 - accepted, 2 - requested, 3 - declined';

create table user_account_role (
	user_account_id int not null,
	role_id int not null,
	constraint user_account_role_user_account_fk foreign key(user_account_id) references user_account(user_account_id),
	constraint user_account_role_role_fk foreign key(role_id) references role(role_id),
	constraint user_account_role_pk primary key (user_account_id, role_id)
);

create table article (
	article_id int not null,
	author_id int not null,
	type int not null,
	created timestamp not null,
	last_modified timestamp not null,
	title varchar(200) not null,
	content bytea not null,
	constraint article_user_account_fk foreign key(author_id) references user_account(user_account_id),
	constraint article_type_check check (type in (0, 1)),
	constraint article_pk primary key(article_id)
);

create sequence article_sequence;
comment on column article.type is '0 - news, 1 - other';

create table comment (
	comment_id int not null,
	article_id int not null, 
	author_id int not null,
	created timestamp not null,
	last_modified timestamp not null,
	content bytea not null,
	constraint comment_article_fk foreign key(article_id) references article(article_id),
	constraint comment_user_account_fk foreign key(author_id) references user_account(user_account_id),
	constraint comment_pk primary key(comment_id)
);

create sequence comment_sequence;

commit;