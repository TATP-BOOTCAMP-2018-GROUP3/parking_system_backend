create table parking_order(
    id bigint not null auto_increment,
    car_Id varchar(64) not null,
    parking_lot_id varchar(64),
    status varchar(64),
    primary key (id)
)