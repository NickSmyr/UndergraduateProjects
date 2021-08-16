#Initial data
data <- read.csv("ex5data.txt" , header=TRUE , sep=' ')


weights <- data$ΒΑΡΟΣ

hist(weights)
#Data is alright , sample size is large and the distribution looks
# to be normal

N <- length(weights)
s <- sd(weights)
m <- mean(weights)

#Calculating the t for 95 % confidence interval
abs(qt(0.025, df=N-1)) -> t
#Calculating the margin of error
mt <- t * (s / sqrt(N))
#Calculating the final confidence interval
lowerbound <- m - mt
upperbound <- m + mt
message("95% Confidence interval is [ ", lowerbound , " , " , upperbound, " ]"  )


