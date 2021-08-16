import random
from sklearn.naive_bayes import GaussianNB
from sklearn.naive_bayes import BernoulliNB
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import Perceptron
from sklearn.linear_model import LogisticRegression

import classifierTesting
import featureExtractor
# NAIVE BAYES FROM Scikit
class sciNaive:
    
    def __init__(self, m):
        #GaussianNb initialized here
        self.model = BernoulliNB()
        self.featureObject = featureExtractor.emailFeatureExtractor(m,5)
        return
    def fit(self,X,Y):
        """
        x iterable over emails (text), 
        y iterable over their respective labels
        """    
        # Resetting the model when we get new examples
        self.model = BernoulliNB()
        #Attributes is a 2d array of 0-1 that represent the presence of a word in an email
        attributes = [self.featureObject.extractArray(x) for x in X]
        #din == len(f.extractArray(x)) so we know how many features are extracted
        self.model.fit(attributes, Y )
        
    def predict(self,X):
        """
        x is one text (email)
        output is it's respective class
        """    
        x_vec = [self.featureObject.extractArray(i) for i in X]
        output = self.model.predict(x_vec)
        #scikits output is a numpy array so we need to convert it to a normal array
        return [int(x) for x in output]
        
import datasets
import classifierTesting
#Testing clf on validation set and calculating best hyperparameters

X , Y = datasets.load_enron()
X , Y = datasets.randomize(X,Y)
X_train , Y_train , X_val , Y_val , X_test , Y_test = datasets.train_validation_test_split(X,Y) 

attributes = [100 , 500 , 1000]

grid = [[a] for a in attributes]
print("Calculating best hyperparameters")

bestParams = None
bestAcc = 0
iterator = 0	
for params in grid:
	clf = sciNaive(params[0])
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
clf = sciNaive(bestParams[0])
classifierTesting.testingSuite(clf , X_test ,Y_test , X_train , Y_train)
    
                         
