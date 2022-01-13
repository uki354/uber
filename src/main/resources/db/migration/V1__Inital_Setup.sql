CREATE TABLE car
(
    car_id           BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted       BIT(1) NULL,
    created_at       timestamp NULL,
    updated_at       timestamp NULL,
    deleted_at       timestamp NULL,
    brand            VARCHAR(255) NOT NULL,
    model            VARCHAR(255) NOT NULL,
    image_path       VARCHAR(255) NOT NULL,
    chassis_number   VARCHAR(255) NOT NULL,
    kw               INT          NOT NULL,
    manufacture_year date         NOT NULL,
    fuel_type        INT          NOT NULL,
    color            VARCHAR(255) NOT NULL,
    number_of_seats  TINYINT      NOT NULL,
    CONSTRAINT pk_car PRIMARY KEY (car_id)
);

CREATE TABLE driver_car_contract
(
    driver_car_contract_id BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted             BIT(1) NULL,
    created_at             timestamp NULL,
    updated_at             timestamp NULL,
    deleted_at             timestamp NULL,
    `car+id`               BIGINT NULL,
    driver_id              BIGINT NULL,
    CONSTRAINT pk_driver_car_contract PRIMARY KEY (driver_car_contract_id)
);

CREATE TABLE driver_model
(
    driver_id    BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted   BIT(1) NULL,
    created_at   timestamp NULL,
    updated_at   timestamp NULL,
    deleted_at   timestamp NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    birthdate    date         NOT NULL,
    image_path   VARCHAR(255) NULL,
    mobile_phone VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    address      VARCHAR(255) NOT NULL,
    jmbg         VARCHAR(255) NOT NULL,
    gender       INT          NOT NULL,
    CONSTRAINT pk_drivermodel PRIMARY KEY (driver_id)
);

CREATE TABLE uber_ride
(
    uber_ride_id      BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted        BIT(1) NULL,
    created_at        timestamp NULL,
    updated_at        timestamp NULL,
    deleted_at        timestamp NULL,
    user_location     VARCHAR(255) NOT NULL,
    user_destination  VARCHAR(255) NOT NULL,
    driver_arrival_at timestamp NULL,
    finished_at       timestamp NULL,
    driver_driver_id  BIGINT NULL,
    user_uber_user_id BIGINT NULL,
    vote_id           BIGINT NULL,
    duration          DOUBLE NULL,
    CONSTRAINT pk_uber_ride PRIMARY KEY (uber_ride_id)
);

CREATE TABLE uber_user
(
    uber_user_id BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted   BIT(1) NULL,
    created_at   timestamp NULL,
    updated_at   timestamp NULL,
    deleted_at   timestamp NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NULL,
    image_path   VARCHAR(255) NULL,
    email        VARCHAR(255) NOT NULL,
    mobile_phone VARCHAR(255) NULL,
    address      VARCHAR(255) NULL,
    CONSTRAINT pk_uber_user PRIMARY KEY (uber_user_id)
);

CREATE TABLE user_vote
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted        BIT(1) NULL,
    created_at        timestamp NULL,
    updated_at        timestamp NULL,
    deleted_at        timestamp NULL,
    user_uber_user_id BIGINT NULL,
    score             TINYINT NULL,
    CONSTRAINT pk_user_vote PRIMARY KEY (id)
);

ALTER TABLE driver_model
    ADD CONSTRAINT uc_drivermodel_email UNIQUE (email);

ALTER TABLE driver_model
    ADD CONSTRAINT uc_drivermodel_jmbg UNIQUE (jmbg);

ALTER TABLE uber_user
    ADD CONSTRAINT uc_uber_user_email UNIQUE (email);

ALTER TABLE uber_user
    ADD CONSTRAINT uc_uber_user_mobile_phone UNIQUE (mobile_phone);

ALTER TABLE driver_car_contract
    ADD CONSTRAINT `FK_DRIVER_CAR_CONTRACT_ON_CAR+ID` FOREIGN KEY (`car+id`) REFERENCES car (car_id);

ALTER TABLE driver_car_contract
    ADD CONSTRAINT FK_DRIVER_CAR_CONTRACT_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver_model (driver_id);

ALTER TABLE uber_ride
    ADD CONSTRAINT FK_UBER_RIDE_ON_DRIVER_DRIVER FOREIGN KEY (driver_driver_id) REFERENCES driver_model (driver_id);

ALTER TABLE uber_ride
    ADD CONSTRAINT FK_UBER_RIDE_ON_USER_UBER_USER FOREIGN KEY (user_uber_user_id) REFERENCES uber_user (uber_user_id);

ALTER TABLE uber_ride
    ADD CONSTRAINT FK_UBER_RIDE_ON_VOTE FOREIGN KEY (vote_id) REFERENCES user_vote (id);

ALTER TABLE user_vote
    ADD CONSTRAINT FK_USER_VOTE_ON_USER_UBER_USER FOREIGN KEY (user_uber_user_id) REFERENCES uber_user (uber_user_id);