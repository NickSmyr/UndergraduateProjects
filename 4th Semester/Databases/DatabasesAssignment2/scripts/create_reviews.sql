create table Reviews(
   listing_id int,
   id int PRIMARY KEY,
   date date,
   reviewer_id int,
   reviewer_name varchar(40),
   comments text
);
