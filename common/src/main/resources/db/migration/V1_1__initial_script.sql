create sequence if not exists cars_manufactury_brand_id_seq
    as integer;

alter sequence cars_manufactury_brand_id_seq owner to postgres;

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
    user_name         varchar(20)                               not null,
    surname           varchar(20)                               not null,
    is_deleted        boolean      default false                not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    user_login        varchar(100),
    user_password     varchar(200)                              not null
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
    id                integer      default nextval('cars_rent.cars_manufactury_brand_id_seq'::regclass) not null
        constraint cars_manufactury_pk
            primary key,
    brand             varchar(20)                                                                       not null,
    country_of_origin varchar(20)                                                                       not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                                         not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)                                         not null,
    is_deleted        boolean      default false                                                        not null
);

alter table cars_manufactury
    owner to postgres;

alter sequence cars_manufactury_brand_id_seq owned by cars_manufactury.id;

create unique index if not exists cars_manufactury_brand_uindex
    on cars_manufactury (brand);

create unique index if not exists cars_manufactury_pk
    on cars_manufactury (id);

create unique index if not exists cars_manufactury_brand_id_uindex
    on cars_manufactury (id);

create table if not exists classification
(
    id                    integer      default nextval('cars_rent.class_class_id_seq'::regclass) not null
        constraint class_pk
            primary key,
    classification_letter varchar(1)                                                             not null,
    description           varchar(20)                                                            not null,
    creation_date         timestamp(6) default CURRENT_TIMESTAMP(6)                              not null,
    modification_date     timestamp(6) default CURRENT_TIMESTAMP(6)                              not null,
    is_deleted            boolean      default false                                             not null
);

alter table classification
    owner to postgres;

alter sequence class_class_id_seq owned by classification.id;

create unique index if not exists class_description_uindex
    on classification (description);

create unique index if not exists classes_class_uindex
    on classification (classification_letter);

create unique index if not exists class_class_id_uindex
    on classification (id);

create table if not exists cars
(
    id                bigint       default nextval('cars_rent.cars_car_id_seq'::regclass) not null
        constraint cars_pk
            primary key,
    is_in_stock       boolean      default true                                           not null,
    is_deleted        boolean      default false                                          not null,
    engine_volume     double precision                                                    not null,
    year_of_issue     integer,
    number_of_seats   integer      default 5                                              not null,
    air_conditioner   boolean      default true                                           not null,
    cost_per_day      double precision                                                    not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                           not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)                           not null,
    transmission      varchar(15)                                                         not null
);

alter table cars
    owner to postgres;

alter sequence cars_car_id_seq owned by cars.id;

create unique index if not exists cars_car_id_uindex
    on cars (id);

create index if not exists cars_is_deleted_index
    on cars (is_deleted);

create index if not exists cars_is_in_stock_index
    on cars (is_in_stock);

create index if not exists cars_cost_per_day_index
    on cars (cost_per_day);

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
            references cars
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

create table if not exists models
(
    id                serial
        constraint models_pk
            primary key,
    model_name        varchar(20)                               not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table models
    owner to postgres;

create unique index if not exists models_id_uindex
    on models (id);

create unique index if not exists models_model_uindex
    on models (model_name);

create table if not exists roles
(
    id                bigserial
        constraint roles_pk
            primary key,
    role_name         varchar(15)                               not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
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

create table if not exists l_brand_model_class
(
    id                bigserial
        constraint l_brand_model_class_pk
            primary key,
    brand_id          integer                                   not null
        constraint l_brand_model_class_cars_manufactury_id_fk
            references cars_manufactury
            on update cascade on delete cascade,
    model_id          integer                                   not null
        constraint l_brand_model_class_models_id_fk
            references models
            on update cascade on delete cascade,
    classification_id integer                                   not null
        constraint l_brand_model_class_classes_id_fk
            references classification
            on update cascade on delete cascade,
    car_id            bigint                                    not null
        constraint l_brand_model_class_cars_id_fk
            references cars
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    is_deleted        boolean      default false                not null
);

alter table l_brand_model_class
    owner to postgres;

create unique index if not exists l_brand_model_class_id_uindex
    on l_brand_model_class (id);

create unique index if not exists l_brand_model_class_car_id_uindex
    on l_brand_model_class (car_id);