import random
from sklearn.naive_bayes import GaussianNB
from sklearn.naive_bayes import BernoulliNB
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import Perceptron
from sklearn.linear_model import LogisticRegression
from sklearn.linear_model import SGDClassifier

import classifierTesting
import featureExtractor
# Logistic from Scikit
class sciLogistic:
    def __init__(self , learning_rate , max_iter , regularization_term , m):
        self.learning_rate = learning_rate
        self.max_iter = max_iter
        self.regularization_term = regularization_term

        self.model = SGDClassifier(
            loss="log" ,
            alpha=regularization_term ,
            max_iter=max_iter ,
            learning_rate="constant",
            eta0=learning_rate
            )
        self.featureObject = featureExtractor.emailFeatureExtractor(m,5)
        return
    def fit(self,X,Y):
        """
        x iterable over emails (text), 
        y iterable over their respective labels
        """    
        # Resetting the model when we get new examples
        self.model = SGDClassifier(
            loss="log" ,
            alpha=self.regularization_term ,
            max_iter=self.max_iter ,
            learning_rate="constant",
            eta0=self.learning_rate
            )
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
import classifierTesting  
import datasets
X , Y = datasets.load_enron()
X , Y = datasets.randomize(X,Y)
X_train , Y_train , X_val , Y_val , X_test , Y_test = datasets.train_validation_test_split(X,Y) 

#Testing the classifier
clf = sciLogistic(0.1 , 3 , 0.0001, 1000) 
#classifierTesting.validationSuite(classifier)
classifierTesting.testingSuite(clf , X_test ,Y_test , X_train , Y_train)

    
                         
