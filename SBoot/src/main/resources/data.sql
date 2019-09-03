INSERT IGNORE INTO tags(name)
VALUES ("Drama"),("Sci-Fi"),("Comedy"),("Poetry"),("Literature"),("History");
INSERT IGNORE INTO authors(name,email)
VALUES ("No Author Yet","NAY@gmail.com"),
	   ("Yuval Noah Harari","yuval@gmail.com"),
	   ("Stephen Hawking","shawking@gmail.com"),
	   ("John Peck","jpeck@gmail.com");
INSERT IGNORE INTO books(title,author,uniquetitle,stock,instock)
VALUES ("Homo Deus",(select author_id from authors where email="yuval@gmail.com"),"Homo Deus Book By Yuval Noah Harari",150,true),
	   ("Homo Sapiens",(select author_id from authors where email="yuval@gmail.com"),"Homo Sapiens Book By Yuval Noah Harari",100,true),
	   ("A Brief History of Time",(select author_id from authors where email="shawking@gmail.com"),"A Brief History of Time Book By Stephen Hawking",50,true),
	   ("A Brief History of English Literature",(select author_id from authors where email="jpeck@gmail.com"),"A Brief History of English Literature Book By John Peck",0,false);
INSERT IGNORE INTO books_tags(book_id,tag_id)
VALUES  ((select book_id from books where uniquetitle="Homo Deus Book By Yuval Noah Harari"),(SELECT tag_id from tags where name="Sci-Fi" )),
		((select book_id from books where uniquetitle="Homo Sapiens Book By Yuval Noah Harari"),(SELECT tag_id from tags where name="History" )),
		((select book_id from books where uniquetitle="A Brief History of English Literature Book By John Peck"),(SELECT tag_id from tags where name="Literature" ));	   
