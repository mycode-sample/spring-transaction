create table spring_tx.demo_entity
(
    id int not null primary key,
    username varchar(60) not null default '',
    age int not null default 18
);

create table spring_tx.account
(
    id varchar(18) not null primary key,
    username varchar(32) not null default '默认用户',
    nickname varchar(32) not null default 'default_user',
    create_time timestamp not null default current_timestamp,
    status varchar(2) not null default '1',
    update_time timestamp,
    delete_time timestamp
);

create table spring_tx.associate
(
    id varchar(18) not null primary key,
    first_account_id varchar(18) not null default '',
    second_account_id varchar(18) not null default '',
    status varchar(2) not null default '',
    create_time timestamp not null default current_timestamp,
    update_time timestamp,
    delete_time timestamp
);

create table spring_tx.contact
(
    id varchar(18) not null primary key,
    account_id varchar(18) not null default '',
    type varchar(16) not null default '',
    value varchar(32) not null default '',
    status varchar(2) not null default '',
    create_time timestamp not null default current_timestamp,
    update_time timestamp,
    delete_time timestamp
);
