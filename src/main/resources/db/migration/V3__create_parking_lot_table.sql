create table parking_lot(
    id bigint not null auto_increment,
    parking_lot_name varchar(64) not null,
    capacity int not null,
    available_position_count int,
    employee_id bigint,
    status varchar(64),
    primary key(id)

)