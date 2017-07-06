-- create tables
create table item (
   id              bigint primary key
  ,name           varchar(256) not null
  ,price          decimal not null
  ,description    varchar(4000) not null
  ,category_id    bigint not null
);