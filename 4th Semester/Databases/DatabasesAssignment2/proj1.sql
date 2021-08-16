SELECT date_trunc('month', TIMESTAMP '2017-03-07');

SELECT CASE WHEN calendar.price IS NOT null THEN 
	SELECT calendar.date, listing.zipcode, listing.bedrooms, calendar.price
	FROM calendar
	INNER JOIN listing ON calendar.listing_id=listing.id
	WHERE listing.room_type='Entire home/apt' AND listing.bedrooms>0 AND calendar.date!=null AND listing.zipcode!=null AND listing.bedrooms!=null
	
	WHEN calendar.price IS null THEN
	SELECT calendar.date, listing.zipcode, listing.bedrooms, listing.price
	FROM calendar
	INNER JOIN listing ON calendar.listing_id=listing.id
	WHERE listing.room_type='Entire home/apt' AND listing.bedrooms>0 AND calendar.date!=null AND listing.zipcode!=null AND listing.bedrooms!=null;
	
SELECT DISTINCT date_trunc('month', date), listing_id, price
FROM calendar
WHERE price IS NOT NULL AND (listing_id=1078);