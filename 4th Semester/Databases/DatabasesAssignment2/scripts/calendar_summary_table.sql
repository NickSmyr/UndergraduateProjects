CREATE TABLE Calendar_Summary(listing_id, calendar_updated, from_date, availability_30, availability_60, availability_90)
AS SELECT DISTINCT 
id, calendar_updated, calendar_last_scraped, availability_30, availability_60, availability_90
FROM Listings;

ALTER TABLE calendar_summary ADD PRIMARY KEY(listing_id, from_date);
ALTER TABLE calendar_summary ADD FOREIGN KEY(listing_id) REFERENCES Listings;

ALTER TABLE LISTINGS
DROP COLUMN calendar_updated,
DROP COLUMN calendar_last_scraped,
DROP COLUMN availability_30,
DROP COLUMN availability_60,
DROP COLUMN availability_90;