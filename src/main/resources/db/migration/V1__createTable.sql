CREATE TABLE currency
(
    id            bigserial NOT NULL,
    code          varchar(255) NULL,
    currency_date varchar(255) NULL,
    name          varchar(255) NULL,
    nominal       varchar(255) NULL,
    val_type      varchar(255) NULL,
    value         varchar(255) NULL,
    CONSTRAINT currency_pkey PRIMARY KEY (id)
);