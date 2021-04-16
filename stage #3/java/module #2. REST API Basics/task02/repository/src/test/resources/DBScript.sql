CREATE TABLE gift_certificate
(
    gift_certificate_id  INT  NOT NULL PRIMARY KEY IDENTITY,
    name             VARCHAR(255)    NOT NULL,
    description      VARCHAR(300)      NOT NULL,
    price            DECIMAL(10, 2)  NOT NULL,
    duration         INT       NOT NULL,
    create_date      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    remove           BOOLEAN  DEFAULT FALSE     NOT NULL
);

CREATE TABLE tag
(
    tag_id   INT  NOT NULL PRIMARY KEY IDENTITY,
    name    VARCHAR(255)           NOT NULL,
    remove BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE tag_gift_certificate
(
    gift_certificate_id INT,
    tag_id              INT,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (gift_certificate_id),
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);


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
'Quad biking','Quad biking for 3 people outside the city','80.00','70','2021-01-29 17:12:59','2021-03-05 23:21:28'
);


INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Martial art training'), (SELECT tag_id FROM tag WHERE name = 'Training')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Martial art training'), (SELECT tag_id FROM tag WHERE name = 'For him and for her')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Bungee jump'), (SELECT tag_id FROM tag WHERE name = 'Extreme')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Bungee jump'), (SELECT tag_id FROM tag WHERE name = 'For him and for her')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Bungee jump'), (SELECT tag_id FROM tag WHERE name = 'Flying')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Airsoft'), (SELECT tag_id FROM tag WHERE name = 'Extreme')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Airsoft'), (SELECT tag_id FROM tag WHERE name = 'For him and for her')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Scuba diving'), (SELECT tag_id FROM tag WHERE name = 'For him and for her')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Scuba diving'), (SELECT tag_id FROM tag WHERE name = 'In another country')
);

INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Quad biking'), (SELECT tag_id FROM tag WHERE name = 'Extreme')
);
INSERT INTO tag_gift_certificate (gift_certificate_id, tag_id) VALUES (
(SELECT gift_certificate_id FROM gift_certificate WHERE name ='Quad biking'), (SELECT tag_id FROM tag WHERE name = 'Ride and Walk')
);




