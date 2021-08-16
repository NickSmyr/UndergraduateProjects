
/* Find listings that are weelchair accesible
Output: 701 rows
*/

SELECT listing_id 
FROM has_amenity
WHERE amenity_id=51;


/* Find the number hosts in every city 
Output: 284 rows 
*/
SELECT COUNT(id), city
FROM host 
GROUP BY city 


/* Find the cities that have 10 or more hosts
Output: 11 rows
*/
SELECT COUNT(id), city
FROM host 
GROUP BY city
HAVING COUNT(id) > 10
ORDER BY count(id);
/*
	Shows all the listings which
	have more than 30 amenities
	Output: 47 rows
*/
SELECT listing_id , count(amenity_id)
FROM Listing 
JOIN Has_Amenity
ON Listing.id = Has_Amenity.listing_id
GROUP BY Listing_id
HAVING count(amenity_id) > 30
ORDER BY count(amenity_id) DESC


/*
	Shows the verified hosts and the total
	amount of money their listings cost
	Output: 4637 rows
*/
SELECT Host.id ,MAX(Host.name) ,sum(listing.price)
FROM Host
JOIN Listing
ON Host.id = Listing.host_id
WHERE identity_verified
GROUP BY Host.id
ORDER BY sum(listing.price) DESC;


/*
	Shows all cities and the average listing
	price in all the years of the calendar
	Output: 21 rows
*/
SELECT city , avg(Calendar.price) 
FROM Listing
JOIN Calendar
ON Calendar.listing_id = Listing.id
WHERE available = 't'
GROUP By city
ORDER BY avg(Calendar.price) DESC



