create table return_order(
    id bigint not null auto_increment,
    parking_order_id bigint not null,
    phone_number varchar(64) not null,
    status varchar(64),
    primary key (id)
)