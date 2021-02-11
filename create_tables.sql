create table film (
id integer generated always as identity not null,
name varchar(255),
duration integer,
description varchar(5000),
primary key (id)
);

create table seance  (
id integer generated always as identity not null,
film_id integer,
hall_id integer,
start_date timestamp,
price integer,
foreign key (film_id) references film(id) on update restrict on delete cascade,
foreign key (hall_id) references hall(id) on update restrict on delete cascade,
primary key(id)
);

create table hall  (
id integer generated always as identity not null,
name varchar(255),
primary key(id)
);

create table place  (
id integer generated always as identity not null,
number integer,
row_id integer,
foreign key (row_id) references row(id) on update restrict on delete cascade,
primary key(id)
);

create table row  (
id integer generated always as identity not null,
number integer,
hall_id integer,
foreign key (hall_id) references hall(id) on update restrict on delete cascade,
primary key(id)
);

create table reservation  (
id integer generated always as identity not null,
seance_id integer,
place_id integer,
user_id integer foreign key (user_id) references user(id) on update restrict on delete cascade,
foreign key (seance_id) references seance(id) on update restrict on delete cascade,
foreign key (place_id) references place(id) on update restrict on delete cascade,
primary key(id)
);