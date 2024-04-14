create table if not exists product (
    product_id varchar(50) not null primary key,
    product_name varchar(128) not null,
    category varchar(32) not null,
    image_url varchar(256) not null,
    price int not null,
    stock int not null,
    description varchar(1024) not null,
    create_date timestamp default current_timestamp,
    last_modify_date timestamp default current_timestamp
);