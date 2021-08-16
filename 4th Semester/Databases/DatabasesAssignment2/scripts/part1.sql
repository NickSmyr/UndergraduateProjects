--1
UPDATE host
SET response_rate=replace(response_rate,'%','');
UPDATE host 
SET response_rate=NULL WHERE response_rate='N/A';

ALTER TABLE host 
ALTER COLUMN response_rate TYPE integer USING (trim(response_rate)::integer);
--2
ALTER TABLE host
ADD COLUMN city VARCHAR,
ADD COLUMN state VARCHAR,
ADD COLUMN country VARCHAR;

UPDATE host
SET city=split_part(location,',',1);
UPDATE host
SET state=split_part(location,',',2);
UPDATE host
SET country=split_part(location,',',3);

ALTER TABLE host
DROP COLUMN location;

--3
CREATE TABLE Amenity(amenity,amenity_id) AS(
	SELECT amenity , row_number() OVER (ORDER BY amenity) FROM
	(select distinct regexp_split_to_table(amenities,',|{|}|\"') AS amenity  FROM listing) AS x
);



CREATE TABLE Has_Amenity AS(
	SELECT id AS listing_id, amenity_id FROM Listing,Amenity
	WHERE Amenity.amenity IN (SELECT DISTINCT regexp_split_to_table(amenities,',|{|}|\"'))
);

ALTER TABLE amenity ADD  primary key (amenity_id);
;

ALTER TABLE has_amenity ADD  primary key (amenity_id,listing_id);
;

ALTER TABLE has_amenity ADD foreign key (listing_id) references listing(id),
ADD foreign key (amenity_id) references amenity(amenity_id);



