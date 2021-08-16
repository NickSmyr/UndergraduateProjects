data <- read.csv("survey_data_2019.txt" , header=TRUE , sep='\t')
data <- data[complete.cases(data[,c("math","prob")]),]
# We are going to use the variable X1bar - X2bar (x1bar avg value of males 
#, x2bar avg value of females)
math <- data[data$sex == 'M',]$math
prob <- data[data$sex == 'F',]$prob

#Examining the data
hist(math)
hist(prob)
#No outliers and the distr looks normal enough

#Calculating sample size
nrow(data) -> N

#Calculating the variances of each data
var(math) -> v1
var(prob) -> v2

#sample sd
s <- sqrt(v1/N + v2/N)

mean(math) -> m1
mean(prob) -> m2
#Null hypothesis is avg(males) == avg(females)
#Alternative hypothesis is avg(males) > avg(females)

#estimated value
m <- m1-m2
#hypothesized value
h <- 0
# Statistic  t test
t <-  ( m - 0 ) / s
# P - value ( with t test using Student t probabiliy function )
# and two sided significance test 
pval <- 2*pt(df= N - 1 , -abs(t))
message("Pvalue = ", pval)
