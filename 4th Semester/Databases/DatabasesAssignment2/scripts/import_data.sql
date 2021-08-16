set client_encoding to 'utf8';
\copy listings from 'airbnb/listings.csv' csv header;
\copy reviews from 'airbnb/reviews.csv' csv header;
\copy neighbourhoods from 'airbnb/neighbourhoods.csv' csv header;
\copy calendar from 'airbnb/calendar.csv' csv header;
\copy summary_listings from 'airbnb/summary_listings.csv' csv header;
\copy summary_reviews from 'airbnb/summary_reviews.csv' csv header;
