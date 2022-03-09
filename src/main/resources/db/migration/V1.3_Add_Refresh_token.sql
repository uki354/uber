CREATE TABLE refresh_token
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    token    VARCHAR(255) NOT NULL,
    is_valid TINYINT(1) NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_499e99c29a6e5c6ffd15f4bfa ON refresh_token (token);