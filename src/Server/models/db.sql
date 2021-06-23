USE MASTER;
DROP DATABASE IF EXISTS BEEKEY;

CREATE DATABASE BEEKEY;
USE BEEKEY;

CREATE TABLE ACCOUNT(
	username VARCHAR(30),
	name VARCHAR(30),
	password CHAR(64),
	type INT,
	gender BOOLEAN,
	phone VARCHAR(12),
	address VARCHAR(30),
	coin INT,
	
	PRIMARY KEY(username)
);

CREATE TABLE CATEGORY(
	category_id INT AUTO_INCREMENT,
	category_name VARCHAR(30),

	PRIMARY KEY(category_id)
);

CREATE TABLE TASK(
	task_id INT AUTO_INCREMENT,
	description TEXT,
	offer INT,
	deadline DATETIME,
	category_id INT,
	lancer_id VARCHAR(30),
	user_id VARCHAR(30),

	PRIMARY KEY(task_id),
	FOREIGN KEY(category_id) REFERENCES CATEGORY(category_id),
	FOREIGN KEY(lancer_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(user_id) REFERENCES ACCOUNT(username)
);

CREATE TABLE FEEDBACK(
	feedback_id INT AUTO_INCREMENT,
	description TEXT,
	star INT,
	lancer_id VARCHAR(30),
	user_id VARCHAR(30),
	task_id INT,
	
	PRIMARY KEY(feedback_id),
	FOREIGN KEY(user_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(lancer_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(task_id) REFERENCES ACCOUNT(task_id)
);

CREATE TABLE COUNTER_OFFER(
	task_id INT,
	lancer_id VARCHAR(30),
	offer INT,
	reason TEXT,

	PRIMARY KEY(task_id,lancer_id),
	FOREIGN KEY(task_id) REFERENCES TASK(task_id),
	FOREIGN KEY(lancer_id) REFERENCES ACCOUNT(username)
);

CREATE TABLE CHAT(
	send_id VARCHAR(30),
	receive_id VARCHAR(30),
	date_time DATETIME,
	content TEXT,

	PRIMARY KEY(send_id,receive_id,date_time),
	FOREIGN KEY(send_id) REFERENCES ACCOUNT(username),
	FOREIGN KEY(receive_id) REFERENCES ACCOUNT(username)
);

CREATE TABLE ATTACH_FILE(
	task_id INT,
	file_path VARCHAR(500),

	PRIMARY KEY(task_id,file_path),
	FOREIGN KEY(task_id) REFERENCES TASK(task_id)
);

CREATE TABLE REPORT(
	report_id INT AUTO_INCREMENT,
	username VARCHAR(30),
	date_time DATETIME,
	content TEXT,

	PRIMARY KEY(report_id),
	FOREIGN KEY(username) REFERENCES ACCOUNT(username)
);

--------------------------------------------------------------------

INSERT INTO ACCOUNT(username,password,type) VALUES
("admin","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918",0);

INSERT INTO CATEGORY(category_name) VALUES
("Testing"),
("Mobile application"),
("Desktop application"),
("Web application"),
("Cyber security"),
("Data analysis");
