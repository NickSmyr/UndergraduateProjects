/*
Removing the existing constraint from listings to
the old table  neighbourhoods 
*/
ALTER TABLE Listings DROP CONSTRAINT "listings_neighbourhood_cleansed_fkey";

CREATE TABLE Neighborhood(neighbourhood, neighbourhood_cleansed)
AS SELECT DISTINCT neighbourhood, neighbourhood_cleansed
FROM Listings;

ALTER TABLE Neighborhood
RENAME neighbourhood TO neighborhood_name;

ALTER TABLE Neighborhood
RENAME neighbourhood_cleansed TO zip_code;

DELETE FROM Neighborhood
WHERE neighborhood_name IS NULL OR zip_code IS NULL;

ALTER TABLE Listings
DROP COLUMN neighbourhood_group_cleansed;

DROP TABLE Neighbourhoods;

ALTER TABLE Listings
RENAME neighbourhood TO neighborhood;

ALTER TABLE neighborhood
ADD PRIMARY KEY(neighborhood_name, zip_code);

ALTER TABLE Listings ADD FOREIGN KEY(neighborhood, neighbourhood_cleansed) REFERENCES Neighborhood(neighborhood_name, zip_code);

ALTER TABLE Summary_Listings
RENAME neighbourhood TO zip_code;

ALTER TABLE Summary_Listings
DROP COLUMN neighbourhood_group;
