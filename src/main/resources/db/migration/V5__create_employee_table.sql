create table employee(
    id bigint not null auto_increment,
    name varchar(64) not null,
    email varchar(64),
    phone_num varchar(64),
    role varchar(64),
    account_name varchar(64),
    hashed_pw varchar(64),
    token varchar(64),
    working_status varchar(64),
    primary key (id)

)