import random

import classifierTesting
import featureExtractor
import math
from ProgressBar import ProgressBar
def sigmoid(x):
		return 1 / ( 1 + math.exp(-x))
# Logistic regression
class logisticRegression:
	
		
	def __init__(self, learning_rate , max_iter , regularization_term , m):
		##Initializing model's features
		self.f = featureExtractor.emailFeatureExtractor(m,5)
		self.din = len(self.f.voc)
		#Initializing weights with random values
		#The weights will be the numer of features the feature extractor extracts
		#plus the bias
		
		self.max_iter = max_iter
		self.eta = learning_rate
		self.lamda = regularization_term
		self.progressBar = ProgressBar()
		return
	def fit(self,X , Y):
		print("Fitting array of size " , len(X))
		#Reinitializing weights
		din = len(self.f.voc)

		self.w = [random.random() for i in range(self.din + 1)]
		"""
		x iterable over emails (text), 
		y iterable over their respective labels
		"""	
		#Vector containing the all the examples after the features have been
		# extracted
		feature_vectors = [[1] + self.f.extractArray(i) for i in X]
		
		#Doing the run through training examples for max_iter epochs
		for n in range(self.max_iter):
			#self.progressBar.display(n / self.max_iter)
			print("Epoch %d out of %d" % (n + 1 , self.max_iter))
			#Iterating through training examples
			for x_i , y_i in zip(feature_vectors,Y):
				#Calculating the classifiers output for input x_i
				logit = sum([xij * wij for xij , wij in zip(x_i , self.w)])
				pspam = sigmoid(logit)
				pham = 1 - pspam
				#Updating the weights based on the loss function and gradients etc
				for i in range(len(self.w)):
					self.w[i] = (1 - 2 * self.lamda * self.eta) * self.w[i] + self.eta * (y_i - pspam) * x_i[i]	
		#self.progressBar.display(1)
		#self.progressBar.reset()
		"""
		l = [ [ self.w[i + 1] , i ] for i in range(len(self.w) - 1)]
		l = sorted(l , key = lambda x : x[0])
		k = [[ x[0] , self.f.inverseVoc[x[1]] ]for x in l ]
		"""
		return			
					

	def predict(self,X):
		"""
		x is a list of texts (email)
		output is it's respective class
		"""	
		#Adding a feature that is always 1 for the bias
		feature_vectors = [[1] + self.f.extractArray(i) for i in X]
		
		#Results
		y = []
		for in_i in feature_vectors:
			#Calculating the dot product of weights and input features x
			w_sum = sum([xi * wi for xi , wi in zip(in_i , self.w)])
			
			#Calculating the probability that the input email is 1 (spam) and ham
			pspam = sigmoid(w_sum)
			pham = 1 - pspam 
			#Finally returning the result
			#print("Wsum %.2f Pspam is %.2f Pham is %.2f" % (w_sum , pspam , pham))
			if pspam > pham :
				y.append(1)
			else:
				y.append(0)
		return y
import datasets
#Testing clf on validation set and calculating best hyperparameters

X , Y = datasets.load_enron()
X , Y = datasets.randomize(X,Y)
X_train , Y_train , X_val , Y_val , X_test , Y_test = datasets.train_validation_test_split(X,Y) 

learning_rates = [0.1 , 0.01 , 0.001]
max_iters = [1 , 3  , 5 , 10 ]
regularization_terms = [0.1 , 0.001 , 0.0001]
attributes = [100 , 500 , 1000]

grid = [[ l , i , r , a ] for l in learning_rates for i in max_iters for r in regularization_terms for a in attributes]
print("Calculating best hyperparameters")

bestParams = None
bestAcc = 0
iterator = 0	
for params in grid:
	clf = logisticRegression(params[0] , params[1] , params[2] , params[3])
	#Testing the classifier on the validation set
	acc = classifierTesting.train_and_test(clf,X_train, Y_train, X_val , Y_val)
	if acc > bestAcc:
		bestAcc = acc
		bestParams = params
	print("%.2f done" %(iterator / len(grid)))	
	iterator += 1	
print("Best parameters : " , bestParams)
#Creating the classifier with the best hyperparameters and running the testing suite
#To output the graphs
clf = logisticRegression(bestParams[0] , bestParams[1] , bestParams[2] , bestParams[3])
classifierTesting.testingSuite(clf , X_test ,Y_test , X_train , Y_train)

		 				
