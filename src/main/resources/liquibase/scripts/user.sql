-- liquibase formatted sql

-- changeset jrembo:1
CREATE TABLE users (
    id SERIAL,
    email TEXT
)

-- changeset sconnor:2
ALTER TABLE users ADD name TEXT;

liquibase --changeLogFile=liquibase/changelog-master.yml update