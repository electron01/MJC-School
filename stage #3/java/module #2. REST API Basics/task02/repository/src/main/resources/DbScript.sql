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





