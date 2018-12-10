create table parking_order(
    id bigint not null auto_increment,
    car_Id varchar(64) not null,
    parking_lot_id bigint,
    status varchar(64),
    phone_num varchar(64),
    owned_by_employee_id bigint,
    created_time timestamp,
    completed_time timestamp,
    primary key (id)
)