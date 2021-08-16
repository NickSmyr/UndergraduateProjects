#Initial data
sinergio <- c(500 , 1550, 1250 ,1300, 750, 1000, 1250 ,1300, 800 ,2500)
empeirognomonas <- c(400 , 1500 , 1300 , 1300  
						,800  ,800, 1000 ,  1100 ,  650 ,  2200)


#Examining the data
hist(sinergio)
hist(empeirognomonas)
#No outliers and the distr looks normal enough
#We would like to have more samples though (they are too few)
#Calculating sample size
length(sinergio) -> N

#Calculating the sdeviastions and means of the two datasets
sd(sinergio) -> s1
sd(empeirognomonas) -> s2
mean(sinergio) -> m1
mean(empeirognomonas) -> m2
#sample sd
s <- s1/sqrt(N) + s2/sqrt(N)


#Null hypothesis is avg(mpeirognomonas) == avg(sinergio)
#Alternative hypothesis is avg(sinergio) > avg(females)

#estimated value
m <- m1-m2
#hypothesized value
h <- 0
# Statistic  t test
t <-  ( m - 0 ) / s
# P - value ( with t test using Student t probabiliy function )
# and one sided significane test ( so input value is t)
pval <- pt(df= N-1 , -abs(t))

message("Pvalue = ", pval)
