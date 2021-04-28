DROP DATABASE IF EXISTS Gift_Certificates;
CREATE DATABASE Gift_Certificates DEFAULT CHARACTER SET utf8;
USE Gift_Certificates;


CREATE TABLE gift_certificate
(
  gift_certificate_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
  tag_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  remove BOOLEAN  DEFAULT FALSE NOT NULL
);

CREATE TABLE tag_gift_certificate
(
  gift_certificate_id BIGINT,
  tag_id BIGINT,
  remove BOOLEAN  DEFAULT FALSE NOT NULL,
  PRIMARY KEY (gift_certificate_id, tag_id),
  FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (gift_certificate_id),
  FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);


  ALTER TABLE tag_gift_certificate ADD FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate(gift_certificate_id) ON DELETE CASCADE;
  ALTER TABLE tag_gift_certificate ADD FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE;

