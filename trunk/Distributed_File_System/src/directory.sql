CREATE DATABASE directory;
USE directory;

CREATE TABLE users (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(30) NOT NULL,
	  pass VARCHAR(30) NOT NULL,
     PRIMARY KEY (id)
) ENGINE=MyISAM;

CREATE TABLE files (
	id VARCHAR(200) NOT NULL,
	name VARCHAR(30) NOT NULL,
	owner INT NOT NULL,
	father VARCHAR(200),
	childs INT,
	permissions VARCHAR(2),
	type INT,
	PRIMARY KEY (id),
	FOREIGN KEY (owner) REFERENCES users(id) on delete cascade,
	FOREIGN KEY (father) REFERENCES files(id) on delete cascade

) ENGINE=MyISAM;

Insert into users(name, pass) values("nicolas", "1234");
Insert into files(id, name, owner, childs, permissions, type) values ("0", "-", "1", "0","rw","0");
