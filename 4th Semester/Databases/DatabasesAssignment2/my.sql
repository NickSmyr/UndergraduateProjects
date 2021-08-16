--select  percentile_cont(0.5) within group (order by date) from (
--	select price from listing where id > 123
--)
--as t;

select neighbourhood_cleansed, id , price ,
	CASE
		WHEN bedrooms = 1 THEN 1
		WHEN bedrooms = 2 THEN 2
		WHEN bedrooms = 3 THEN 3
		WHEN bedrooms = 4 THEN 4
		WHEN bedrooms >= 5 THEN 5
	END	as beds
 from listing where listing.room_type = 'Entire home/apt' order by beds desc;

select count(id) from listing where bedrooms IS NULL;


select zipcode , bedrooms ,date_trunc(), percentile_cont(0.5) within group (order by price) 
from listing
inner join calendar
on id = calendar.listing_id 
group by (zipcode,bedrooms) order by bedrooms asc;
