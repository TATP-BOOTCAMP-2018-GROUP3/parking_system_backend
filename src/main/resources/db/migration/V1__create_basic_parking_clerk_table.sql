create table parking_clerk(
    id bigint not null auto_increment,
    employee_id bigint not null,
    parking_status varchar(64),
    primary key (id)
)