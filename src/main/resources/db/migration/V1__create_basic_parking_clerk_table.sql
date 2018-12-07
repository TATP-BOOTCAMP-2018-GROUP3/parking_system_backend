create table parking_clerk(
    id bigint not null,
    account_name varchar(64) not null,
    primary key (id),
    unique(account_name)
)