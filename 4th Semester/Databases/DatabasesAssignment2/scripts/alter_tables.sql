ALTER TABLE CALENDAR ADD FOREIGN KEY (listing_id) REFERENCES Listings(id);
ALTER TABLE LISTINGS ADD FOREIGN KEY(neighbourhood_cleansed) REFERENCES Neighbourhoods(neighbourhood);
ALTER TABLE REVIEWS ADD FOREIGN KEY(listing_id) REFERENCES Listings(id); 
ALTER TABLE Summary_listings ADD FOREIGN KEY(id) REFERENCES Listings(id); 
ALTER TABLE Summary_reviews ADD FOREIGN KEY(listing_id) REFERENCES Listings(id); 
