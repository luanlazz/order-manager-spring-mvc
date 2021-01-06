-- noinspection SqlNoDataSourceInspectionForFile

drop database ordermanagerdb;
drop user ordermvc;
create user ordermvc with password '12345';
create database ordermanagerdb with template=template0 owner=ordermvc;
\connect ordermanagerdb;
alter default privileges grant all on tables to ordermvc;
alter default privileges grant all on sequences to ordermvc;

create table customer
(
    id        integer primary key not null,
    firstName varchar(50)         not null,
    lastName  varchar(50)         not null,
    email     varchar(50)         not null,
    address   varchar(50)         not null,
    level     varchar(50)         not null default 'bronze'
);

INSERT INTO customer VALUES (1, 'Joao', 'Silva', 'joao@hotmail.com', 'Rua X');
INSERT INTO customer VALUES (2, 'Pedro', 'Ala', 'pedro@hotmail.com', 'Rua Y');
INSERT INTO customer VALUES (3, 'Lucas', 'Napoles', 'lucas@hotmail.com', 'Rua Z');

CREATE TYPE OrderState AS ENUM ('CREATED', 'VALIDATED', 'FAILED', 'SHIPPED');
CREATE TYPE Product AS ENUM ('JUMPERS', 'UNDERPANTS', 'STOCKINGS');

create table "order"
(
    id         integer primary key not null,
    customerId integer             not null,
    state      OrderState          not null,
    product    Product             not null,
    quantity   integer             not null,
    price      numeric(10, 2)      not null,
    created_at timestamptz         not null default now()
);
alter table "order"
    add constraint cat_customer_pk
        foreign key (customerId) references customer (id);

CREATE TYPE OrderValidationType AS ENUM ('INVENTORY_CHECK', 'FRAUD_CHECK', 'ORDER_DETAILS_CHECK');
CREATE TYPE OrderValidationResult AS ENUM ('PASS', 'FAIL', 'ERROR');

create table payment
(
    id      integer primary key not null,
    orderId integer             not null,
    ccy     varchar(50)         not null,
    amount  numeric(10, 2)      not null
);
alter table payment
    add constraint cat_order_pk
        foreign key (orderId) references "order" (id);

create table inventory
(
    product         Product     primary key not null,
    qtyTotal        integer     not null,
    qtyReserved     integer     not null
);

INSERT INTO inventory VALUES ('JUMPERS', 20, 0);
INSERT INTO inventory VALUES ('UNDERPANTS', 10, 0);
INSERT INTO inventory VALUES ('STOCKINGS', 5, 0);

create sequence customer_seq increment 1 start 1;
create sequence order_seq increment 1 start 1;
create sequence payment_seq increment 1 start 1;