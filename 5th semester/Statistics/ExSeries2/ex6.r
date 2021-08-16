#Initial data
data <- c(5.7 ,4.6 ,6.4 ,6.3 ,6.9 ,5.2 ,4.9 ,5.4 ,4.9, 5.6,
5.4, 5.3 ,4.9 ,5.1, 5.0 ,6.0,6.3, 5.4, 5.3 , 5.4)
hist(data)
#Data is alright , sample size is large and the distribution looks
# to be normal
N <- length(data)
s <- sd(data)
m <- mean(data)

#Calculating the t for 95 % confidence interval
abs(qt(0.025, df=N-1)) -> t
#Calculating the margin of error
mt <- t * (s / sqrt(N))
#Calculating the final confidence interval
lowerbound <- m - mt
upperbound <- m + mt
message( "Mean = " , m , " Standard deviation = " , s 
, "95% Confidence interval is [ ", lowerbound , " , " , upperbound, " ]"  )


