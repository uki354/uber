CREATE TABLE `role`
(
    role_id BIGINT AUTO_INCREMENT NOT NULL,
    name    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE uber_user_role
(
    role_id      BIGINT NOT NULL,
    uber_user_id BIGINT NOT NULL,
    CONSTRAINT pk_uber_user_role PRIMARY KEY (role_id, uber_user_id)
);

ALTER TABLE uber_user
    ADD username VARCHAR(255) NULL;

ALTER TABLE uber_user
    MODIFY username VARCHAR (255) NOT NULL;

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE uber_user
    ADD CONSTRAINT uc_uber_user_username UNIQUE (username);

ALTER TABLE uber_user_role
    ADD CONSTRAINT fk_ubeuserol_on_role FOREIGN KEY (role_id) REFERENCES `role` (role_id);

ALTER TABLE uber_user_role
    ADD CONSTRAINT fk_ubeuserol_on_user_model FOREIGN KEY (uber_user_id) REFERENCES uber_user (uber_user_id);