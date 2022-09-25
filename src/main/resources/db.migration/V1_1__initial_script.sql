create sequence if not exists class_class_id_seq
    as integer;

alter sequence class_class_id_seq owner to postgres;

create sequence if not exists cars_car_id_seq;

alter sequence cars_car_id_seq owner to postgres;

create sequence if not exists l_users_cars_id_seq;

alter sequence l_users_cars_id_seq owner to postgres;

create table if not exists users
(
    id                bigserial
        constraint users_pk
            primary key,
    user_name         varchar(20)  default 'User'::character varying,
    surname           varchar(20),
    is_deleted        boolean      default false                                 not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                  not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)                  not null,
    user_login        varchar(100) default 123                                   not null,
    user_password     varchar(200) default 'default_password'::character varying not null
);

alter table users
    owner to postgres;

create unique index if not exists users_id_uindex
    on users (id);

create index if not exists users_is_deleted_index
    on users (is_deleted);

create index if not exists users_user_name_surname_index
    on users (user_name, surname);

create index if not exists users_password_index
    on users (user_password);

create unique index if not exists users_user_login_uindex
    on users (user_login);

create table if not exists cars_manufactury
(
    brand_id          serial
        constraint cars_manufactury_pk
            primary key,
    brand             varchar(20)                               not null,
    country_of_origin varchar(20)                               not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table cars_manufactury
    owner to postgres;

create unique index if not exists cars_manufactury_brand_uindex
    on cars_manufactury (brand);

create unique index if not exists cars_manufactury_brand_id_uindex
    on cars_manufactury (brand_id);

create table if not exists classes
(
    class_id          integer      default nextval('cars_rent.class_class_id_seq'::regclass) not null
        constraint class_pk
            primary key,
    class             varchar(1)                                                             not null,
    description       varchar(20)                                                            not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                              not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)                              not null,
    is_deleted        boolean      default false                                             not null
);

alter table classes
    owner to postgres;

alter sequence class_class_id_seq owned by classes.class_id;

create unique index if not exists class_description_uindex
    on classes (description);

create unique index if not exists classes_class_uindex
    on classes (class);

create unique index if not exists class_class_id_uindex
    on classes (class_id);

create table if not exists models
(
    id                serial
        constraint models_pk
            primary key,
    model             varchar(20)                               not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table models
    owner to postgres;

create unique index if not exists models_id_uindex
    on models (id);

create unique index if not exists models_model_uindex
    on models (model);

create table if not exists l_model_class
(
    id                serial
        constraint l_model_class_pk
            primary key,
    model             varchar(20)                               not null
        constraint l_model_class_models_model_fk
            references models (model)
            on update cascade on delete cascade,
    class             varchar(1)                                not null
        constraint l_model_class_classes_class_fk
            references classes (class)
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table l_model_class
    owner to postgres;

create unique index if not exists l_model_class_id_uindex
    on l_model_class (id);

create unique index if not exists l_model_class_model_uindex
    on l_model_class (model);

create table if not exists transmissions
(
    id                serial
        constraint transmissions_pk
            primary key,
    type              varchar(20)                               not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table transmissions
    owner to postgres;

create table if not exists cars
(
    id                bigint           default nextval('cars_rent.cars_car_id_seq'::regclass) not null,
    brand             varchar(20)                                                             not null
        constraint cars_cars_manufactury_brand_fk
            references cars_manufactury (brand)
            on update cascade on delete cascade,
    model             varchar(20)                                                             not null
        constraint cars_models_model_fk
            references models (model)
            on update cascade on delete cascade,
    is_in_stock       boolean          default true                                           not null,
    is_deleted        boolean          default false                                          not null,
    engine_volume     double precision default 2                                              not null,
    year_of_issue     integer,
    number_of_seats   integer          default 5                                              not null,
    air_conditioner   boolean          default true                                           not null,
    cost_per_day      double precision default 50                                             not null,
    creation_date     timestamp(6)     default CURRENT_TIMESTAMP(6)                           not null,
    modification_date timestamp(6)     default CURRENT_TIMESTAMP(6)                           not null,
    transmission_id   integer          default 1                                              not null
        constraint cars_transmissions_id_fk
            references transmissions
            on update cascade on delete cascade
);

alter table cars
    owner to postgres;

alter sequence cars_car_id_seq owned by cars.id;

create unique index if not exists cars_pk
    on cars (id);

create unique index if not exists cars_car_id_uindex
    on cars (id);

create index if not exists cars_is_deleted_index
    on cars (is_deleted);

create index if not exists cars_is_in_stock_index
    on cars (is_in_stock);

create index if not exists cars_cost_per_day_index
    on cars (cost_per_day);

alter table cars
    add constraint cars_pk
        primary key (id);

create table if not exists deal
(
    id                bigint       default nextval('cars_rent.l_users_cars_id_seq'::regclass) not null
        constraint l_users_cars_pk
            primary key,
    user_id           integer                                                                 not null
        constraint l_users_cars_users_id_fk
            references users
            on update cascade on delete cascade,
    car_id            integer                                                                 not null
        constraint l_users_cars_cars_car_id_fk
            references cars (id)
            on update cascade on delete cascade,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)                               not null,
    receiving_date    timestamp(6) default CURRENT_TIMESTAMP(6)                               not null,
    return_date       timestamp(6),
    is_deleted        boolean      default false                                              not null,
    price             double precision                                                        not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                               not null
);

alter table deal
    owner to postgres;

alter sequence l_users_cars_id_seq owned by deal.id;

create unique index if not exists l_users_cars_car_id_uindex
    on deal (car_id);

create unique index if not exists l_users_cars_user_id_uindex
    on deal (user_id);

create unique index if not exists l_users_cars_id_uindex
    on deal (id);

create index if not exists order_creation_date_index
    on deal (creation_date);

create index if not exists order_is_completed_index
    on deal (is_deleted);

create index if not exists order_receiving_date_index
    on deal (receiving_date);

create index if not exists order_return_date_index
    on deal (return_date);

create index if not exists order_price_index
    on deal (price);

create unique index if not exists transmissions_id_uindex
    on transmissions (id);

create unique index if not exists transmissions_transmission_uindex
    on transmissions (type);

create table if not exists roles
(
    id                bigserial
        constraint roles_pk
            primary key,
    role_name         varchar(15)  default 'USER'::character varying not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)      not null,
    is_deleted        boolean      default false                     not null
);

alter table roles
    owner to postgres;

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists driving_licence
(
    id                bigserial
        constraint driving_licence_pk
            primary key,
    date_of_issue     date                                      not null,
    valid_until       date                                      not null,
    category_b        boolean      default true                 not null,
    serial_number     varchar(9)                                not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null,
    user_id           bigint                                    not null
        constraint driving_licence_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table driving_licence
    owner to postgres;

create unique index if not exists driving_licence_id_uindex
    on driving_licence (id);

create unique index if not exists driving_licence_serial_number_uindex
    on driving_licence (serial_number);

create unique index if not exists driving_licence_user_id_uindex
    on driving_licence (user_id);

create table if not exists l_role_user
(
    id                bigserial
        constraint l_role_user_pk
            primary key,
    user_id           bigint                                    not null
        constraint l_role_user_users_id_fk
            references users
            on update cascade on delete cascade,
    role_id           integer                                   not null
        constraint l_role_user_roles_id_fk
            references roles
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table l_role_user
    owner to postgres;

create unique index if not exists l_role_user_id_uindex
    on l_role_user (id);

create index if not exists l_role_user_role_id_user_id_index
    on l_role_user (role_id, user_id);
