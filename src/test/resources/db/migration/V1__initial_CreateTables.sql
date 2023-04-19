
DROP TABLE if exists public.pet_services;
DROP TABLE if exists public.vet_services;
DROP TABLE if exists public.owner;
DROP TABLE if exists public.pet;
DROP TABLE if exists public.services;
DROP TABLE if exists public.vet;
DROP TYPE if exists public.gender;
DROP TABLE if exists public.student;

DROP SEQUENCE if exists public.owner_id_seq;
DROP SEQUENCE if exists public.pet_id_seq;
DROP SEQUENCE if exists public.pet_services_id_seq;
DROP SEQUENCE if exists public.services_id_seq;
DROP SEQUENCE if exists public.vet_id_seq;
DROP SEQUENCE if exists public.vet_services_id_seq;

-- SEQUENCE: public.owner_id_seq

-- DROP SEQUENCE public.owner_id_seq;

CREATE SEQUENCE if not exists public.owner_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.owner_id_seq
    OWNER TO postgres;

-- SEQUENCE: public.pet_id_seq

-- DROP SEQUENCE public.pet_id_seq;

CREATE SEQUENCE if not exists public.pet_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.pet_id_seq
    OWNER TO postgres;

-- SEQUENCE: public.pet_services_id_seq

-- DROP SEQUENCE public.pet_services_id_seq;

CREATE SEQUENCE if not exists public.pet_services_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.pet_services_id_seq
    OWNER TO postgres;

-- SEQUENCE: public.services_id_seq

-- DROP SEQUENCE public.services_id_seq;

CREATE SEQUENCE if not exists public.services_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.services_id_seq
    OWNER TO postgres;

-- SEQUENCE: public.vet_id_seq

-- DROP SEQUENCE public.vet_id_seq;

CREATE SEQUENCE if not exists public.vet_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.vet_id_seq
    OWNER TO postgres;

-- SEQUENCE: public.vet_services_id_seq

-- DROP SEQUENCE public.vet_services_id_seq;

CREATE SEQUENCE if not exists public.vet_services_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.vet_services_id_seq
    OWNER TO postgres;

-- Type: gender

-- DROP TYPE public.gender;

CREATE TYPE  public.gender AS ENUM
    ('MALE', 'FEMALE');

ALTER TYPE public.gender
    OWNER TO postgres;

-- Table: public.owner

-- DROP TABLE public.owner;

CREATE TABLE if not exists public.owner
(
    id integer NOT NULL DEFAULT nextval('owner_id_seq'::regclass),
    full_name text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default",
    phone text COLLATE pg_catalog."default",
    birthday date,
    gender gender,
    CONSTRAINT owner_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.owner
    OWNER to postgres;

-- Table: public.pet

-- DROP TABLE public.pet;

CREATE TABLE if not exists public.pet
(
    id integer NOT NULL DEFAULT nextval('pet_id_seq'::regclass),
    kind text COLLATE pg_catalog."default" NOT NULL,
    name text COLLATE pg_catalog."default",
    age integer,
    id_owner integer NOT NULL,
    gender gender,
    CONSTRAINT pet_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.pet
    OWNER to postgres;

-- Table: public.services

-- DROP TABLE public.services;

CREATE TABLE if not exists public.services
(
    id integer NOT NULL DEFAULT nextval('services_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    description text COLLATE pg_catalog."default",
    CONSTRAINT services_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.services
    OWNER to postgres;

-- Table: public.vet

-- DROP TABLE public.vet;

CREATE TABLE if not exists public.vet
(
    id integer NOT NULL DEFAULT nextval('vet_id_seq'::regclass),
    full_name text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default" NOT NULL,
    gender gender NOT NULL,
    phone text COLLATE pg_catalog."default",
    qualification text COLLATE pg_catalog."default",
    birthday date NOT NULL,
    CONSTRAINT vet_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.vet
    OWNER to postgres;

-- Table: public.pet_services

-- DROP TABLE public.pet_services;

CREATE TABLE if not exists public.pet_services
(
    id integer NOT NULL DEFAULT nextval('pet_services_id_seq'::regclass),
    id_pet integer NOT NULL,
    id_services integer NOT NULL,
    CONSTRAINT pet_services_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.pet_services
    OWNER to postgres;

-- Table: public.vet_services

-- DROP TABLE public.vet_services;

CREATE TABLE if not exists public.vet_services
(
    id integer NOT NULL DEFAULT nextval('vet_services_id_seq'::regclass),
    id_services integer NOT NULL,
    id_vet integer NOT NULL,
    CONSTRAINT vet_services_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.vet_services
    OWNER to postgres;