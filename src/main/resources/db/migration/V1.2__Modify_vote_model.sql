ALTER TABLE user_vote
    ADD driver_driver_id BIGINT NULL;

ALTER TABLE user_vote
    MODIFY driver_driver_id BIGINT NOT NULL;

ALTER TABLE user_vote
    ADD CONSTRAINT FK_USER_VOTE_ON_DRIVER_DRIVER FOREIGN KEY (driver_driver_id) REFERENCES driver_model (driver_id);

ALTER TABLE user_vote
    MODIFY user_uber_user_id BIGINT NOT NULL;