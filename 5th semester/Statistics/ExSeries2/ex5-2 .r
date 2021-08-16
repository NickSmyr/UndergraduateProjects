#Initial data
data <- read.csv("ex5data.txt" , header=TRUE , sep=' ')

males <- data[data$ΦΥΛΟ == 'Α',]$ΒΑΡΟΣ
females <- data[data$ΦΥΛΟ == 'Γ',]$ΒΑΡΟΣ

hist(males)
hist(females)
#Data is alright , sample size is large enough and the distribution looks
# to be normal

N1 <- length(males)
N2 <- length(females)

m1 <- mean(males)
m2 <- mean(females)

s1 <- sd(males)
s2 <- sd(females)

#Calculating the t for 80 % confidence interval
abs(qt(0.1, df=min(N1-1,N2-1))) -> t
#Calculating the margin of error
mt <- t * ( (s1 / sqrt(N1)) + (s2 / sqrt(N2)))
#Calculating the final confidence interval
lowerbound <- (m1 - m2) - mt
upperbound <- (m1 - m2) + mt
message("80% Confidence interval is [ ", lowerbound , " , " , upperbound, " ]"  )


