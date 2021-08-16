UPDATE LISTINGS 
SET street = split_part(street ,',' , 1);

UPDATE LISTINGS 
SET price = replace(price , '$' ,'');
UPDATE LISTINGS 
SET price = replace(price , ',' , '');

UPDATE LISTINGS 
SET weekly_price = replace(weekly_price , '$' ,'');
UPDATE LISTINGS 
SET weekly_price = replace(weekly_price , ',' , '');

UPDATE LISTINGS 
SET monthly_price = replace(monthly_price , '$' ,'');
UPDATE LISTINGS 
SET monthly_price = replace(monthly_price , ',' , '');

UPDATE LISTINGS 
SET security_deposit = replace(security_deposit , '$' ,'');
UPDATE LISTINGS 
SET security_deposit = replace(security_deposit , ',' , '');

UPDATE LISTINGS 
SET cleaning_fee = replace(cleaning_fee , '$' ,'');
UPDATE LISTINGS 
SET cleaning_fee = replace(cleaning_fee , ',' , '');

ALTER TABLE LISTINGS
ALTER COLUMN price TYPE numeric USING  price::numeric,
ALTER COLUMN weekly_price TYPE numeric USING  weekly_price::numeric,
ALTER COLUMN monthly_price TYPE numeric USING  monthly_price::numeric,
ALTER COLUMN security_deposit TYPE numeric USING  security_deposit::numeric,
ALTER COLUMN cleaning_fee TYPE numeric USING cleaning_fee::numeric;

