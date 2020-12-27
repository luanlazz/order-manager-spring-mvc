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

CREATE TYPE OrderState AS ENUM ('CREATED', 'VALIDATED', 'FAILED', 'SHIPPED');
CREATE TYPE Product AS ENUM ('JUMPERS', 'UNDERPANTS', 'STOCKINGS');

create table "order"
(
    id         integer primary key not null,
    customerId integer             not null,
    state      OrderState          not null,
    product    Product             not null,
    quantity   integer             not null,
    price      numeric(10, 2)      not null
);
alter table "order"
    add constraint cat_customer_pk
        foreign key (customerId) references customer (id);

create table orderValue
(
    orderId    integer            not null,
    value      numeric(10, 2)      not null
);
alter table orderValue
    add constraint cat_orderValue_pk
        foreign key (orderId) references "order" (id);

create table orderEnriched
(
    id              integer primary key not null,
    customerId      integer             not null,
    customerLevel   varchar(50)         not null
);
alter table orderEnriched
    add constraint cat_orderEnriched_pk
        foreign key (customerId) references customer (id);

CREATE TYPE OrderValidationType AS ENUM ('INVENTORY_CHECK', 'FRAUD_CHECK', 'ORDER_DETAILS_CHECK');
CREATE TYPE OrderValidationResult AS ENUM ('PASS', 'FAIL', 'ERROR');

create table OrderValidation
(
    orderId             integer                 not null,
    checkType           OrderValidationType     not null,
    validationResult    OrderValidationResult   not null
);
alter table OrderValidation
    add constraint cat_OrderValidation_pk
        foreign key (orderId) references "order" (id);

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

create sequence customer_seq increment 1 start 1;
create sequence order_seq increment 1 start 1;
create sequence orderEnriched_seq increment 1 start 1;
create sequence payment_seq increment 1 start 1;