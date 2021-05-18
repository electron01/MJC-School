DROP DATABASE IF EXISTS Gift_Certificates;
CREATE DATABASE Gift_Certificates DEFAULT CHARACTER SET utf8;
USE Gift_Certificates;


CREATE TABLE gift_certificate
(
  Id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(300) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  duration INT NOT NULL,
  create_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  remove BOOLEAN  DEFAULT FALSE NOT NULL
);

CREATE TABLE tag
(
  Id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  remove BOOLEAN  DEFAULT FALSE NOT NULL
);

CREATE TABLE tag_gift_certificate
(
  gift_certificate_id BIGINT,
  tag_id BIGINT,
  PRIMARY KEY (gift_certificate_id, tag_id),
  FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id),
  FOREIGN KEY (tag_id) REFERENCES tag (id)
);


  ALTER TABLE tag_gift_certificate ADD FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate(id) ON DELETE CASCADE;
  ALTER TABLE tag_gift_certificate ADD FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE;



INSERT INTO tag (name) VALUES('Extreme');
INSERT INTO tag (name) VALUES('For him and for her');
INSERT INTO tag (name) VALUES('Training');
INSERT INTO tag (name) VALUES('Flying');
INSERT INTO tag (name) VALUES('In another country');
INSERT INTO tag (name) VALUES('Ride and Walk');

INSERT INTO gift_certificate (name,description,price,duration,create_date,last_update_date) VALUES
(
'Martial art training','Ten martial arts training sessions with the best trainers','70.00','100','2021-01-01 10:21:34','2021-02-20 19:25:16'
);
INSERT INTO gift_certificate (name,description,price,duration,create_date,last_update_date) VALUES
(
'Bungee jump','Three jumps from the tower (35m)','100.00','50','2021-01-07 17:29:12','2021-02-27 21:37:23'
);
INSERT INTO gift_certificate (name,description,price,duration,create_date,last_update_date) VALUES
(
'Airsoft','Airsoft games in a large area for 5 hours','90.00','150','2021-01-12 07:56:14','2021-03-01 09:52:31'
);
INSERT INTO gift_certificate (name,description,price,duration,create_date,last_update_date) VALUES
(
'Scuba diving','Scuba diving in the black sea for 2 hours (video recording is available)','250.00','70','2021-01-19 22:13:21','2021-02-01 15:39:46'
);
INSERT INTO gift_certificate (name,description,price,duration,create_date,last_update_date) VALUES
(
'Quad biking','Quad biking for 3 people outside the city','80.00','120','2021-01-29 17:12:59','2021-03-05 23:21:28'
);


INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Martial art training'), (SELECT id FROM tag WHERE name = 'Training')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Martial art training'), (SELECT id FROM tag WHERE name = 'For him and for her')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Bungee jump'), (SELECT id FROM tag WHERE name = 'Extreme')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Bungee jump'), (SELECT id FROM tag WHERE name = 'For him and for her')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Bungee jump'), (SELECT id FROM tag WHERE name = 'Flying')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Airsoft'), (SELECT id FROM tag WHERE name = 'Extreme')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Airsoft'), (SELECT id FROM tag WHERE name = 'For him and for her')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Scuba diving'), (SELECT id FROM tag WHERE name = 'For him and for her')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Scuba diving'), (SELECT id FROM tag WHERE name = 'In another country')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Quad biking'), (SELECT id FROM tag WHERE name = 'Extreme')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT id FROM gift_certificate WHERE name ='Quad biking'), (SELECT id FROM tag WHERE name = 'Ride and Walk')
);


CREATE TABLE users
(
 id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 login VARCHAR(255) NOT NULL,
 password VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL,
 remove BOOLEAN  DEFAULT FALSE NOT NULL
);


CREATE TABLE orders
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  all_cost decimal (10,2) NOT NULL,
   creation_date datetime default CURRENT_TIMESTAMP null,
);

CREATE TABLE users_orders
(
 order_id BIGINT NOT NULL,
 user_id BIGINT NOT NULL,
 PRIMARY KEY (order_id, user_id),
  FOREIGN KEY (order_id) REFERENCES orders (id),
  FOREIGN KEY (user_id) REFERENCES users (id)

);
CREATE TABLE certificates_orders
(
 order_id BIGINT NOT NULL,
 gift_certificate_id BIGINT NOT NULL,
 PRIMARY KEY (order_id, gift_certificate_id),
  FOREIGN KEY (order_id) REFERENCES orders (id),
  FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id)

);

create table revinfo
(
    rev      int auto_increment
        primary key,
    revtstmp bigint null
);

CREATE TABLE tag_aud
(
  Id BIGINT NOT NULL,
  rev     int          not null,
  revtype tinyint      null,
  name VARCHAR(255) NOT NULL,
  remove BOOLEAN  DEFAULT FALSE NOT NULL,
 primary key (id, rev),
 foreign key (rev) references revinfo (rev)
);

CREATE TABLE gift_certificate_aud
(
  Id BIGINT NOT NULL,
  rev     int          not null,
  revtype tinyint      null,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(300) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  duration INT NOT NULL,
  create_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  remove BOOLEAN  DEFAULT FALSE NOT NULL,
  primary key (id, rev),
  foreign key (rev) references revinfo (rev)
);

CREATE TABLE users_aud
(
 id BIGINT NOT NULL,
  rev     int          not null,
  revtype tinyint      null,
  login VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  remove BOOLEAN  DEFAULT FALSE NOT NULL,
  primary key (id, rev),
  foreign key (rev) references revinfo (rev)
);
CREATE TABLE orders_aud
(
  id BIGINT NOT NULL,
  rev     int          not null,
  revtype tinyint      null,
  allCost decimal (10,2) NOT NULL,
   creation_date datetime default CURRENT_TIMESTAMP null,
  primary key (id, rev),
  foreign key (rev) references revinfo (rev)
);





