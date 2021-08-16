/*
Find all reviews that took place at Christmas from 2010-2017 from reviewers named 'Susan'
Output: 2 rows
*/
SELECT date, reviewer_name, reviewer_id
FROM reviews
WHERE reviewer_name='Susan' AND 
(date='2010-12-25' OR date='2011-12-25' OR date='2012-12-25' OR date='2013-12-25' OR date='2014-12-25' OR date='2015-12-25' OR date='2016-12-25' OR date='2017-12-25');



/*
Find the top-10 hosts with the best rating and show their response time
Output: 10 rows
*/
SELECT host_name, review_scores_rating, host_response_time
FROM listings 
ORDER BY review_scores_rating
LIMIT 10;



/*
Find all available places under 200$
Output: 523857 rows
*/
SELECT listings.id, listings.listing_url, calendar.date, calendar.price
FROM listings INNER JOIN calendar
ON listings.id=calendar.listing_id
WHERE calendar.available='t' AND calendar.price<'$200.00';





/*
Find all possible names in tables reviews and listings
Output: 17210 rows
*/
SELECT DISTINCT reviews.reviewer_name
FROM listings FULL OUTER JOIN reviews
ON listings.host_name=reviews.reviewer_name;
