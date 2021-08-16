CREATE TABLE Host(
	id , url , name , since , location , 
	about , response_time , response_rate , 
	acceptance_rate ,  is_superhost , 
	thumbnail_url  , picture_url , 
	neighbourhood , listings_count , 
	total_listings_count , verifications , 
	has_profile_pic , identity_verified ,
	calculated_host_listings_count
	 ) AS
SELECT DISTINCT 
host_id, host_url, host_name, host_since, host_location, host_about,
host_response_time, host_response_rate, host_acceptance_rate, host_is_superhost,
host_thumbnail_url,
host_picture_url,
host_neighbourhood,
host_listings_count,
host_total_listings_count, host_verifications, host_has_profile_pic, host_identity_verified,
calculated_host_listings_count
FROM Listings;

ALTER TABLE Host ADD PRIMARY KEY(id);
ALTER TABLE Listings ADD FOREIGN KEY(host_id) REFERENCES Host;
ALTER TABLE Listings 
DROP COLUMN
host_url,DROP COLUMN host_name,DROP COLUMN host_since,DROP COLUMN host_location,
DROP COLUMN host_about,
DROP COLUMN host_response_time,DROP COLUMN host_response_rate,DROP COLUMN host_acceptance_rate,
DROP COLUMN host_is_superhost,
DROP COLUMN host_thumbnail_url,
DROP COLUMN host_picture_url,
DROP COLUMN host_neighbourhood,
DROP COLUMN host_listings_count,
DROP COLUMN host_total_listings_count,DROP COLUMN host_verifications,
DROP COLUMN host_has_profile_pic,
DROP COLUMN host_identity_verified,
DROP COLUMN calculated_host_listings_count;

