INSERT IGNORE INTO privilege(name)
VALUES  ("READ_PRIVILEGE"),
		("WRITE_PRIVILEGE");
INSERT IGNORE INTO roles(name)
VALUES  ("ROLE_ADMIN"),			
		("ROLE_USER"),
		("ROLE_AUTHOR");	
INSERT IGNORE INTO roles_privileges(role_id,privilege_id)
VALUES  ((select role_id from roles where name="ROLE_ADMIN"),(select privilege_id from privilege where name="WRITE_PRIVILEGE")),
		((select role_id from roles where name="ROLE_ADMIN"),(select privilege_id from privilege where name="READ_PRIVILEGE")),
		((select role_id from roles where name="ROLE_AUTHOR"),(select privilege_id from privilege where name="READ_PRIVILEGE")),
		((select role_id from roles where name="ROLE_AUTHOR"),(select privilege_id from privilege where name="WRITE_PRIVILEGE")),
		((select role_id from roles where name="ROLE_USER"),(select privilege_id from privilege where name="READ_PRIVILEGE"));		
INSERT IGNORE INTO users(name,email,password,enabled)
VALUES  ("Frodo Baggins","Frodo@mordor.com","ring1234",true),
		("Bilbo Baggins","Bilbo@mordor.com","ring1234",false),
		("Samwise","Sam@mordor.com","ring1234",true);
INSERT IGNORE INTO users_roles(role_id,user_id)
VALUES  ((select role_id from roles where name="ROLE_ADMIN"),(SELECT user_id from users where email="Frodo@mordor.com" )),
		((select role_id from roles where name="ROLE_USER"),(SELECT user_id from users where email="Bilbo@mordor.com")),
		((select role_id from roles where name="ROLE_AUTHOR"),(SELECT user_id from users where email="Sam@mordor.com"));
INSERT IGNORE INTO confirmationtoken(confirmation_token,user_id,createdDate,expiryDate)
VALUES 	(UUID(),(SELECT user_id from users where email="Frodo@mordor.com"),now(),NOW() + INTERVAL 1 DAY),
		(UUID(),(SELECT user_id from users where email="Bilbo@mordor.com"),now(),NOW() + INTERVAL 1 DAY);
INSERT IGNORE INTO tags(name)
VALUES ("Drama"),("Sci-Fi"),("Comedy"),("Poetry"),("Literature"),("History");
INSERT IGNORE INTO authors(author_id)
VALUES ((select user_id from users where email="Sam@mordor.com"));
INSERT IGNORE INTO books(title,author,uniquetitle,stock,instock)
VALUES ("Homo Deus",(select a.author_id from authors a inner join users u on a.author_id=u.user_id where u.email="Sam@mordor.com"),"Homo Deus Book By Samwise",150,true),
	   ("Homo Sapiens",(select a.author_id from authors a inner join users u on a.author_id=u.user_id where u.email="Sam@mordor.com"),"Homo Sapiens Book By Samwise",100,true),
	   ("A Brief History of Time",(select a.author_id from authors a inner join users u on a.author_id=u.user_id where u.email="Sam@mordor.com"),"A Brief History of Time Book By Samwise",50,true),
	   ("A Brief History of English Literature",(select a.author_id from authors a inner join users u on a.author_id=u.user_id where u.email="Sam@mordor.com"),"A Brief History of English Literature Book By Samwise",0,false);
INSERT IGNORE INTO books_tags(book_id,tag_id)
VALUES  ((select book_id from books where uniquetitle="Homo Deus Book By Samwise"),(SELECT tag_id from tags where name="Sci-Fi" )),
		((select book_id from books where uniquetitle="Homo Sapiens Book By Samwise"),(SELECT tag_id from tags where name="History" )),
		((select book_id from books where uniquetitle="A Brief History of English Literature Book By Samwise"),(SELECT tag_id from tags where name="Literature" ));	   

