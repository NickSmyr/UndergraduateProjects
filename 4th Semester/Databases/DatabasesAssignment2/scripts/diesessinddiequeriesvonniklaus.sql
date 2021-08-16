/*
Find all cases where the reviewer has the same name with the host
Output: 242 rows
*/
SELECT reviewer_name,listing_id , host_name
FROM reviews 
JOIN listings
ON  reviews.listing_id = listings.id
WHERE listing_id = listings.id AND host_name = reviewer_name;



/*
Count all listings per neighbourhood
Output: 44 rows
*/

SELECT neighbourhood_cleansed, COUNT(id) x 
FROM Listings 
GROUP BY  neighbourhood_cleansed 
ORDER BY x DESC;

/*
List all the people who have reviewed the most listings and the average of all the lisiting ratings they have reviewed
Output: 100 rows
*/
	
SELECT reviewer_id , COUNT( DISTINCT listings.id) x , AVG(CAST (listings.review_scores_rating AS float)) average_rating_score 
FROM Reviews 
FULL OUTER JOIN Listings 
ON listings.id = reviews.listing_id 
GROUP BY reviewer_id ORDER BY x DESC LIMIT 100; 

/*
Lists the latest review on every listing that has at least one review
Output: 5999 rows 
*/
SELECT listing_id , reviews.comments 
FROM listings 
JOIN reviews 
ON reviews.listing_id = listings.id 
WHERE to_char(reviews.date, 'YYYY-MM-DD') = listings.last_review;

/*
Lists the ids and host_names of the listings reviewed where the reviewer mentioned something about being uncomfortable
Output: 354 rows
*/
SELECT listing_id,host_name,reviewer_name,comments 
FROM summary_listings 
JOIN reviews 
ON summary_listings.id = reviews.listing_id 
WHERE reviews.comments LIKE '%uncomfortable%';

/*
Count all reviews per neighbourhood
Output: 44 rows
*/
SELECT neighbourhood_cleansed , count(reviewer_id) total_reviews 
FROM listings 
JOIN reviews 
ON reviews.listing_id = listings.id 
GROUP BY neighbourhood_cleansed 
ORDER BY total_reviews desc;

/*
Shows each reviewer id and the average price of the listings each one has reviewd
Output: 117591 rows
*/
SELECT reviewer_id ,avg(summary_listings.price) average_reviewer_listing_price 
FROM summary_listings 
JOIN reviews 
ON reviews.listing_id = summary_listings.id 
GROUP BY reviewer_id 
ORDER BY average_reviewer_listing_price desc ;
