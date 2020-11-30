insert into role (role_id, name) values (1, 'GUEST');
insert into role (role_id, name) values (2, 'READER');
insert into role (role_id, name) values (3, 'AUTHOR');
insert into role (role_id, name) values (4, 'ADMINISTRATOR');

insert into action (action_id, name) values (1, 'READ_ARTICLE');
insert into action (action_id, name) values (2, 'EDIT_ARTICLE');
insert into action (action_id, name) values (3, 'COMMENT');
insert into action (action_id, name) values (6, 'APPROVE_AUTHOR');
insert into action (action_id, name) values (8, 'REQUEST_FOR_AUTHOR');

-- guest smije samo čitati članke
insert into role_action(role_id, action_id) values (1, 1);

-- reader smije čitati članke, komentirati i tražiti da postane author
insert into role_action(role_id, action_id) values (2, 1);
insert into role_action(role_id, action_id) values (2, 3);
insert into role_action(role_id, action_id) values (2, 8);

-- author smije čitati članke i uređivati ih
insert into role_action(role_id, action_id) values (3, 1);
insert into role_action(role_id, action_id) values (3, 2);

-- administrator smije čitati članke i odobravati readere da postanu authori
insert into role_action(role_id, action_id) values (4, 1);
insert into role_action(role_id, action_id) values (4, 6);

-- password guest
insert into user_account(user_account_id, email, password, username, status, author_status) values
	(nextval('user_account_sequence'), 'guest', '$2y$12$RV18Os1r6gWlZMDXMq.J6OuPSukpZzqQ/ctvzfLECk6EgONmJYXB2', 'guest', 1, 0);

-- password user-reader1
insert into user_account(user_account_id, email, password, username, status, author_status) values
	(nextval('user_account_sequence'), 'user_reader1@mail.hr', '$2y$12$yE/ffPFnYRBHwgR2/2FZn.2DlkrtWk8z7kfYwNTFJuEmmnz3zxAti', 'user_reader1', 1, 0);

-- password user-reader2
insert into user_account(user_account_id, email, password, username, status, author_status) values
	(nextval('user_account_sequence'), 'user_reader2@mail.hr', '$2y$12$WOAMbhASma4myQA6la3xOO0Gr0YE46sTAkhVI2nctmLVQCX426ukq', 'user_reader2', 1, 0);

-- password user-author1
insert into user_account(user_account_id, email, password, username, status, author_status) values
	(nextval('user_account_sequence'), 'user_author1@mail.hr', '$2y$12$rffyt/0GRYAVhIJvjts5Le6PoiDfgBK/B3ME9dX/WYipoD6nxk07q', 'user_author1', 1, 1);

-- password 4dm1n
insert into user_account(user_account_id, email, password, username, status, author_status) values
	(nextval('user_account_sequence'), 'admin@mail.hr', '$2y$12$lhgBHrXGGVWK54EYskloZO/V2vE5ZBheQ/2tCaZ7EAKArCjJR3JnK', 'admin', 1, 0);

insert into user_account_role(user_account_id, role_id) values 
	((select user_account_id from user_account where username like 'guest'), 1);
	
insert into user_account_role(user_account_id, role_id) values 
	((select user_account_id from user_account where username like 'user_reader1'), 2);

insert into user_account_role(user_account_id, role_id) values 
	((select user_account_id from user_account where username like 'user_reader2'), 2);

insert into user_account_role(user_account_id, role_id) values 
	((select user_account_id from user_account where username like 'user_author1'), 2);

insert into user_account_role(user_account_id, role_id) values 
	((select user_account_id from user_account where username like 'user_author1'), 3);

insert into user_account_role(user_account_id, role_id) values 
	((select user_account_id from user_account where username like 'admin'), 4);


insert into article(article_id, author_id, type, created, last_modified, title, content) values
	(nextval('article_sequence'), (select user_account_id from user_account where username like 'user_author1'), 0, current_timestamp, current_timestamp, 'first article', decode('746869732069732066697273742061727469636c652e', 'hex'));

insert into article(article_id, author_id, type, created, last_modified, title, content) values
	(nextval('article_sequence'), (select user_account_id from user_account where username like 'user_author1'), 0, current_timestamp, current_timestamp, 'second article', decode('74686973206973207365636f6e642c206d756368206c6f6e6765722061727469636c6521', 'hex'));


insert into comment (comment_id, article_id, author_id, created, last_modified, content) values
	(nextval('comment_sequence'), (select article_id from article where title like 'first article'), (select user_account_id from user_account where username like 'user_reader2'), current_timestamp, current_timestamp, decode('7468697320697320617765736f6d652061727469636c6521', 'hex'));



commit;