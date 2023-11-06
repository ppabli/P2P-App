create table users (
	id serial primary key,
	name varchar(255) not null unique,
	password varchar(255) not null,
	created_at timestamp not null default now(),
);

create table friend_requests (
	id serial primary key,
	user_id integer not null,
	friend_id integer not null,
	created_at timestamp not null default now(),
	foreign key (user_id) references users(id) on delete cascade on update cascade,
	foreign key (friend_id) references users(id) on delete cascade on update cascade
);

create table friends (
	id serial primary key,
	user_id integer not null,
	friend_id integer not null,
	created_at timestamp not null default now(),
	foreign key (user_id) references users(id) on delete cascade on update cascade,
	foreign key (friend_id) references users(id) on delete cascade on update cascade
);