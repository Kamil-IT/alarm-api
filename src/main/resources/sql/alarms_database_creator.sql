create table alarms (
                        id varchar(255) not null,
                        alarm_frequency_costume varchar(255),
                        alarm_frequency_type varchar(255),
                        alarm_turn_off_type varchar(255),
                        description varchar(255),
                        is_active boolean,
                        name varchar(255),
                        ring_name varchar(255),
                        ring_type varchar(255),
                        snooze varchar(255),
                        time varchar(255),
                        time_create_in_millis int8,
                        user_id varchar(255),
                        primary key (id)
);
create table users (
                       id varchar(255) not null,
                       account_non_expired boolean default true not null,
                       account_non_locked boolean default true not null,
                       credentials_non_expired boolean default true not null,
                       email varchar(255),
                       enabled boolean default true not null,
                       password varchar(255) not null,
                       username varchar(255) not null,
                       primary key (id)
);

alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table alarms add constraint FKilo4nfh1j7nsepgoq8qf3208a foreign key (user_id) references users(id);
