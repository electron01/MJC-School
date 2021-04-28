CREATE TABLE gift_certificate
(
    gift_certificate_id  BIGINT  NOT NULL PRIMARY KEY IDENTITY,
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
    tag_id   BIGINT  NOT NULL PRIMARY KEY IDENTITY,
    name    VARCHAR(255)           NOT NULL,
    remove BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE tag_gift_certificate
(
    gift_certificate_id BIGINT,
    tag_id   BIGINT,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (gift_certificate_id),
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);
