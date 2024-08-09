create table Comment(
    id serial primary key,
    creation_date timestamp,
    note_id bigint,
    owner_id bigint,
    text varchar(300)
);

create table Container_of_notes(
    id serial primary key
);

create table Note(
    id serial primary key,
    author_id bigint,
    container_id bigint,
    description varchar(1000),
    executor_id bigint,
    priority int,
    status int,
    title varchar(100)
);

create table Person(
    id serial primary key,
    email varchar(100),
    password varchar(75)
);