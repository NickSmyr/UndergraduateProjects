data <- read.csv("survey_data_2019.txt" , header=TRUE , sep='\t')
data <- data[complete.cases(data[,"prob"]),]
# We are going to use the variable X1bar - X2bar (x1bar avg value of males 
#, x2bar avg value of females)
males <- data[data$sex == 'M',]$prob
females <- data[data$sex == 'F',]$prob

#Examining the data
hist(males)
hist(females)
#No outliers and the distr looks normal enough

#Calculating sample size
nrow(data[data$sex == 'M',]) -> N1
nrow(data[data$sex == 'F',]) -> N2

#Calculating the variances of each male and female data
var(males) -> v1
var(females) -> v2

#sample sd
s <- sqrt(v1/N1 + v2/N2)

mean(males) -> m1
mean(females) -> m2
#Null hypothesis is avg(males) == avg(females)
#Alternative hypothesis is avg(males) > avg(females)

#estimated value
m <- m1-m2
#hypothesized value
h <- 0
# Statistic  t test
t <-  ( m - 0 ) / s
# P - value ( with t test using Student t probabiliy function )
# and one sided significane test ( so input value is t)
pval <- pt(df= N1+N2 , -abs(t))
message("Pvalue = ", pval)
