CREATE TABLE refresh_token
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    token    TEXT NOT NULL,
    is_valid TINYINT(1) NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

ALTER TABLE refresh_token
    ADD checksum_token VARCHAR(255) NULL;

CREATE UNIQUE INDEX idx_82c23351180eb490cc2a69ff7 ON refresh_token (checksum_token);

