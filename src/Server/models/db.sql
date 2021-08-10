USE MASTER;
DROP DATABASE IF EXISTS BEEKEY;

CREATE DATABASE BEEKEY;
USE BEEKEY;

CREATE TABLE ACCOUNT(
	username VARCHAR(30) NOT NULL,
	name VARCHAR(30) NOT NULL,
	password CHAR(64) NOT NULL,
	phone VARCHAR(12) NOT NULL,
	email VARCHAR(30) NOT NULL,
	coin INT NOT NULL,
	
	PRIMARY KEY(username)
);

CREATE TABLE CATEGORY(
	category_id INT AUTO_INCREMENT,
	category_name VARCHAR(30) NOT NULL,

	PRIMARY KEY(category_id)
);

CREATE TABLE TASK(
	task_id INT AUTO_INCREMENT,
	title VARCHAR(30) NOT NULL,
	description TEXT NOT NULL,
	offer INT NOT NULL,
	deadline DATETIME,
	category_id INT NOT NULL,
	status INT NOT NULL,
	lancer_id VARCHAR(30),
	user_id VARCHAR(30) NOT NULL,

	PRIMARY KEY(task_id),
	FOREIGN KEY(category_id) REFERENCES CATEGORY(category_id),
	FOREIGN KEY(lancer_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(user_id) REFERENCES ACCOUNT(username)
);

CREATE TABLE FEEDBACK(
	feedback_id INT AUTO_INCREMENT,
	title VARCHAR(30) NOT NULL,
	description TEXT NOT NULL,
	lancer_id VARCHAR(30) NOT NULL,
	user_id VARCHAR(30) NOT NULL,
	task_id INT NOT NULL,
	
	PRIMARY KEY(feedback_id),
	FOREIGN KEY(user_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(lancer_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(task_id) REFERENCES TASK(task_id)
);

CREATE TABLE COUNTER_OFFER(
	task_id INT NOT NULL,
	lancer_id VARCHAR(30) NOT NULL,
	offer INT NOT NULL,
	reason TEXT NOT NULL, 

	PRIMARY KEY(task_id,lancer_id),
	FOREIGN KEY(task_id) REFERENCES TASK(task_id),
	FOREIGN KEY(lancer_id) REFERENCES ACCOUNT(username)
);

CREATE TABLE CHAT(
	send_id VARCHAR(30) NOT NULL,
	receive_id VARCHAR(30) NOT NULL,
	date_time DATETIME NOT NULL,
	content TEXT NOT NULL,

	PRIMARY KEY(send_id,receive_id,date_time),
	FOREIGN KEY(send_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(receive_id) REFERENCES ACCOUNT(username)
);

CREATE TABLE ATTACH_FILE(
	task_id INT NOT NULL,
	file_path VARCHAR(500) NOT NULL,

	PRIMARY KEY(task_id,file_path),
	FOREIGN KEY(task_id) REFERENCES TASK(task_id)
);

CREATE TABLE REPORT(
	report_id INT AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	date_time DATETIME NOT NULL,
	content TEXT NOT NULL,

	PRIMARY KEY(report_id),
	FOREIGN KEY(username) REFERENCES ACCOUNT(username)
);

--------------------------------------------------------------------

INSERT INTO ACCOUNT VALUES
("admin","admin","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","0123456789","admin@admin",0),
("client","client","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","0123456789","client@admin",0),
("lancer","lancer","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","0123456789","lancer@admin",0);

INSERT INTO CATEGORY(category_name) VALUES
("Testing"),
("Mobile application"),
("Desktop application"),
("Web application"),
("Cyber security"),
("Data analysis");
