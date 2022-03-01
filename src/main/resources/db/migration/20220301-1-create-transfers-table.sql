--liquibase formatted sql

--changeset siliutin_a:20220301-1-create-transfers-table
CREATE TABLE transfers
(
    id         uuid primary key,
    datetime   timestamp with time zone not null,
    created_at timestamp with time zone not null,
    amount     numeric(20, 10)          not null
);
