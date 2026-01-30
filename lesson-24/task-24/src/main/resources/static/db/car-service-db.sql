create table if not exists carBrand
(
    id             integer generated always as identity
        constraint brand_pkey
            primary key,
    name           varchar(255) not null
        constraint brand_name_key
            unique,
    country        varchar(255),
    founded_year   integer,
    trigger_column varchar(200)
);

create table if not exists carModel
(
    id        integer generated always as identity
        constraint model_pkey
            primary key,
    brand_id  integer
        constraint fk_model_brand
            references carBrand,
    name      varchar(255) not null,
    category  varchar(255) not null,
    year_from integer,
    year_to   integer
);

create table if not exists car
(
    id                  integer generated always as identity
        constraint car_pkey
            primary key,
    model_id            integer     not null
        constraint fk_car_model
            references carModel,
    vin                 varchar(20) not null,
    registration_number varchar(10) not null,
    mileage_km          integer     not null,
    base_car_id         integer
        constraint fk_car_base
            references car,
    production_year     integer
);

create table if not exists cardetails
(
    id                integer generated always as identity
        constraint cardetails_pkey
            primary key,
    car_id            integer      not null
        constraint uc_registration_code
            unique
        constraint fk_car_details_car
            references car,
    engine_number     varchar(20)  not null,
    registration_code varchar(20)  not null,
    fuel_type         varchar(20)  not null,
    engine_capacity   varchar(200) not null,
    color             varchar(255),
    insurance_number  varchar(255)
);

create table if not exists service_visit
(
    id           integer generated always as identity
        constraint service_visit_pkey
            primary key,
    car_id       integer      not null
        constraint fk_service_visit
            references car,
    service_date timestamp    not null,
    odometer_km  integer      not null,
    service_type varchar(255) not null,
    notes        varchar(255)
);

create table if not exists part
(
    id          serial
        constraint part_pkey
            primary key,
    name        varchar(255),
    part_code   varchar(255),
    description varchar(255),
    unit_price  numeric(10, 2),
    carBrand       varchar(255)
);

create table if not exists service_part
(
    id         integer generated always as identity
        constraint service_part_pkey
            primary key,
    service_id integer
        constraint service_part_service_id_fkey
            references service_visit
        constraint fk_service_part_service_visit
            references service_visit,
    part_id    integer
        constraint service_part_part_id_fkey
            references part
        constraint fk_service_part_part
            references part,
    quantity   integer
);

create table if not exists feature
(
    id          integer generated always as identity
        constraint feature_pkey
            primary key,
    feature_id  integer
        constraint feature_feature_id_key
            unique,
    name        varchar(255),
    description varchar(255),
    category    varchar(255)
);

create table if not exists car_feature
(
    car_id     integer not null
        constraint fk_car_feature_car
            references car,
    feature_id integer not null
        constraint fk_car_feature_feature
            references feature,
    constraint pk_car_feature
        primary key (car_id, feature_id)
);

create or replace function brand_update_trigger_fn() returns trigger
    language plpgsql
as
$$
BEGIN
    -- Set trigger_column whenever row is updated
    NEW.trigger_column :=
            'UPDATED_AT_' || to_char(CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS');

    RETURN NEW;
END;
$$;

create trigger brand_before_update_trigger
    before update
    on carBrand
    for each row
execute procedure brand_update_trigger_fn();


INSERT INTO carBrand (name, country, founded_year, trigger_column)
SELECT 'Brand_' || i,
       CASE
           WHEN i % 5 = 0 THEN 'Germany'
           WHEN i % 5 = 1 THEN 'USA'
           WHEN i % 5 = 2 THEN 'Japan'
           WHEN i % 5 = 3 THEN 'France'
           ELSE 'Italy' END,
       1920 + (i % 100),
       NULL
FROM generate_series(1, 100) AS s(i);

INSERT INTO carModel (brand_id, name, category, year_from, year_to)
SELECT (i % 100) + 1,
       'Model_' || i,
       CASE
           WHEN i % 4 = 0 THEN 'Sedan'
           WHEN i % 4 = 1 THEN 'SUV'
           WHEN i % 4 = 2 THEN 'Truck'
           ELSE 'Hatchback' END,
       1990 + (i % 30),
       2020 + (i % 5)
FROM generate_series(1, 300) AS s(i);


INSERT INTO car (model_id, vin, registration_number, mileage_km, base_car_id, production_year)
SELECT (i % 300) + 1,
       LEFT('VIN' || md5(i::text), 17) || LPAD(i::text, 3, '0'), -- total 20 chars
       'REG' || LPAD(i::text, 7, '0'),                           -- total 10 chars
       10000 + (i * 7 % 200000),
       NULL,
       2000 + (i % 25)
FROM generate_series(1, 500) AS s(i);


INSERT INTO cardetails (car_id, engine_number, registration_code, fuel_type, engine_capacity, color, insurance_number)
SELECT id,

       -- ENGINE NUMBER: 20-char based on hash
       LEFT('ENG' || md5(id::text), 20),

       -- REGISTRATION CODE: padded to 20 chars
       RPAD('REG' || id, 20, 'X'),

       -- FUEL TYPE: make realistic but padded to 20 chars
       RPAD(
               CASE
                   WHEN id % 3 = 0 THEN 'Petrol'
                   WHEN id % 3 = 1 THEN 'Diesel'
                   ELSE 'Electric'
                   END,
               20, '_'
       ),

       -- ENGINE CAPACITY (as text)
       (1000 + (id % 2500)) || 'cc',

       -- COLOR: pick from a few
       CASE
           WHEN id % 4 = 0 THEN 'Red'
           WHEN id % 4 = 1 THEN 'Black'
           WHEN id % 4 = 2 THEN 'Blue'
           ELSE 'White'
           END,

       -- INSURANCE NUMBER: synthetic
       'INS' || id || '-' || LEFT(md5(id::text), 10)

FROM car;

INSERT INTO service_visit (car_id, service_date, odometer_km, service_type, notes)
SELECT (i % 500) + 1,
       NOW() - (i || ' days')::interval,
       5000 + ((i * 100) % 200000),
       CASE
           WHEN i % 3 = 0 THEN 'Maintenance'
           WHEN i % 3 = 1 THEN 'Repair'
           ELSE 'Inspection' END,
       'Note for service ' || i
FROM generate_series(1, 1000) AS s(i);

INSERT INTO part (name, part_code, description, unit_price, carBrand)
SELECT 'Part_' || i,
       'PCODE' || i,
       'Description of part ' || i,
       round((10 + random() * 490)::numeric, 2),
       CASE
           WHEN i % 5 = 0 THEN 'Bosch'
           WHEN i % 5 = 1 THEN 'Delphi'
           WHEN i % 5 = 2 THEN 'Valeo'
           WHEN i % 5 = 3 THEN 'Denso'
           ELSE 'Magneti Marelli'
           END
FROM generate_series(1, 200) AS s(i);

INSERT INTO service_part (service_id, part_id, quantity)
SELECT (i % 1000) + 1,
       (i % 200) + 1,
       1 + (i % 5)
FROM generate_series(1, 1500) AS s(i);

INSERT INTO feature (feature_id, name, description, category)
SELECT i,
       'Feature_' || i,
       'This is feature number ' || i,
       CASE
           WHEN i % 3 = 0 THEN 'Safety'
           WHEN i % 3 = 1 THEN 'Comfort'
           ELSE 'Entertainment' END
FROM generate_series(1, 100) AS s(i);


INSERT INTO car_feature (car_id, feature_id)
SELECT DISTINCT ((random() * 499 + 1)::int),
                ((random() * 99 + 1)::int)
FROM generate_series(1, 2000);


CREATE TABLE IF NOT EXISTS app_user
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(50) NOT NULL UNIQUE,
    full_name     VARCHAR(100),
    email         VARCHAR(100) UNIQUE,
    password_hash TEXT        NOT NULL,
    is_active     BOOLEAN   DEFAULT TRUE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS role
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);


CREATE TABLE IF NOT EXISTS user_role
(
    user_id INTEGER NOT NULL REFERENCES app_user (id) ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


CREATE TABLE IF NOT EXISTS permission
(
    id              SERIAL PRIMARY KEY,
    http_method     VARCHAR(10)  NOT NULL,
    path_pattern    VARCHAR(255) NOT NULL, -- e.g. '/cars', '/users', '/services'
    permission_code VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS role_permission
(
    role_id       INTEGER NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    permission_id INTEGER NOT NULL REFERENCES permission (id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);


-- Insert roles
INSERT INTO role (name, description)
VALUES ('Admin', 'Full access to all modules'),
       ('Mechanic', 'Limited to service and car updates'),
       ('Viewer', 'Read-only access');


-- Insert permissions
INSERT INTO permission (http_method, path_pattern, permission_code)
VALUES ('GET', '/cars', 'car:read'),
       ('POST', '/cars', 'car:update'),
       ('GET', '/users', 'user:manage'),
       ('POST', '/services', 'service:create');
