create table person (
   id serial primary key not null,
   login varchar(2000),
   password varchar(2000)
);

create table employee (
    id serial primary key not null,
    name varchar (2000),
    surname varchar (2000),
    inn int,
    hiring timestamp
);

create table employee_person_list (
    employee_id int references employee(id),
    person_list_id int references person(id) on delete cascade
)

