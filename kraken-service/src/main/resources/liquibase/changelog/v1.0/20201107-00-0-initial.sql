CREATE SCHEMA IF NOT EXISTS kraken_service;
------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE kraken_service.position_seq START 1 INCREMENT BY 1;
CREATE TABLE kraken_service.position
(
    position_id INT           NOT NULL DEFAULT nextval('kraken_service.position_seq')::regclass,
    name        VARCHAR(128)  NOT NULL,
    description VARCHAR(2048) NOT NULL,
    CONSTRAINT position_pk PRIMARY KEY (position_id)
);

CREATE UNIQUE INDEX position_name_uk ON kraken_service.position (lower(name));
CREATE INDEX position_name_idx ON kraken_service.position (name);

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE kraken_service.user_position
(
    user_id     UUID    NOT NULL,
    position_id INT     NOT NULL,
    is_deleted  BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT user_position_pk PRIMARY KEY (user_id, position_id),
    CONSTRAINT user_position_position_id_fk FOREIGN KEY (position_id) REFERENCES kraken_service.position (position_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX user_position_position_id_idx ON kraken_service.user_position (position_id);
CREATE INDEX user_position_main_idx ON kraken_service.user_position (user_id, position_id, is_deleted);
