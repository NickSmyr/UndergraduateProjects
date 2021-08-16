import os
import time
from sklearn.metrics import f1_score
from sklearn.metrics import accuracy_score
from sklearn.metrics import precision_score
from sklearn.metrics import recall_score
import matplotlib.pyplot as plt
import datasets
def train_and_test(clf, X_train , Y_train , X_test , Y_test):
	"""
	TRains the clf on the given training set
	and returns the accuracy on the test set
	(needs the be the validation set if we are opting to 
	tune hyperparameters)
	"""
	clf.fit(X_train,Y_train)
	y_pred = clf.predict(X_test)
	return accuracy_score(Y_test,y_pred)

def calcMetrics(y_true , y_pred):
	return f1_score(y_true,y_pred) , accuracy_score(y_true,y_pred) , recall_score(y_true,y_pred) , precision_score(y_true,y_pred)
def validationSuite(classifier):
	"""
	Trains the classifier on the training dataset and runs in on the development dataset
	
	This method is faster that testingSuite and is designed to set hyperparameters of classifiers
	
	"""
	X , Y = datasets.load_enron()
	X , Y = datasets.randomize(X,Y)
	X_train , Y_train , X_val , Y_val , X_test , Y_test = datasets.train_validation_test_split(X,Y)

	classifier.fit(X_train,Y_train)
	y_pred = classifier.predict(X_train)
	print("Training set results :")
	print(str(calcMetrics(Y_train,y_pred)))

	y_pred = classifier.predict(X_val)
	print("Validation set results :")
	print(str(calcMetrics(Y_val,y_pred)))
	return
from ProgressBar import ProgressBar
import csv
def testingSuite(classifier , X_test , Y_test , X_train , Y_train):
	"""
	Trains and tests the classifier and outputs time elapsed 
	also needs to create graphs for precision , accuracy , recall , f1 score
								for training set , and test set 
								
	"""
	start = time.time()
	

	#Runs the model through the sets to calc
	pb = ProgressBar()
	totalX = len(X_train)
	trainingExamples = 0
	#Arrays where we store the results from the test
	testResults = []
	trainResults = []
	i = 0 
	#At each step we are training the classifier with 10 % more of the total examples
	for i in range(10):
		trainingExamples = (i+1) * (totalX // 10)
		print("Training examples seen %d" % (trainingExamples))
		classifier.fit(X_train[0:trainingExamples],Y_train[0:trainingExamples])
		#Calculating classifiers perfomance on the test set		
		y_test_pred = classifier.predict(X_test)
		res = calcMetrics(Y_test,y_test_pred)
		#Adding a tuple (n , f1 , acc , rec , prec) to the array holding the results
		testResults.append((trainingExamples,) + res)


		y_train_pred = classifier.predict(X_train)
		res = calcMetrics(Y_train,y_train_pred)
		trainResults.append((trainingExamples,) + res)
		pb.display(sum(range(i+2))/sum(range(11	)))
	end = time.time()
	print("Execution time =  " + str(end-start))
	#Plots
	n = [i[0] for i in testResults]
	
	f1_test = [i[1] for i in testResults]
	acc_test = [i[2] for i in testResults]
	rec_test = [i[3] for i in testResults]
	pre_test = [i[4] for i in testResults]
	
	f1_train = [i[1] for i in trainResults]
	acc_train = [i[2] for i in trainResults]
	rec_train = [i[3] for i in trainResults]
	pre_train = [i[4] for i in trainResults]
	
	plt.title("F1-scores")
	plt.plot(n,f1_test , label="test")
	plt.plot(n,f1_train , label="train")
	plt.legend(loc="upper left")
	plt.xlabel("Amount of training examples")
	plt.savefig("results/F1.png")
	plt.close('all')
	
	plt.title("Accuracies")
	plt.plot(n,acc_test , label="test")
	plt.plot(n,acc_train , label="train")
	plt.legend(loc="upper left")
	plt.xlabel("Amount of training examples")
	plt.savefig("results/Acc.png")
	plt.close('all')
	
	plt.title("Precisions")
	plt.plot(n,pre_test , label="test")
	plt.plot(n,pre_train , label="train")
	plt.legend(loc="upper left")
	plt.xlabel("Amount of training examples")
	plt.savefig("results/Pre.png")
	plt.close('all')
	
	plt.title("Recalls")
	plt.plot(n,rec_test , label="test")
	plt.plot(n,rec_train , label="train")
	plt.legend(loc="upper left")
	plt.xlabel("Amount of training examples")
	plt.savefig("results/Rec.png")
	plt.close('all')
	
	#plt.ylabel("")
	#Saving results as tables
	with open('results/trainResults.csv', mode='w') as train_file:
		writer = csv.writer(train_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
		writer.writerow(["n" , "f1-score" , "accuracy" , "recall" , "precision"])
		for row in trainResults:
			writer.writerow(row)

	with open('results/testResults.csv', mode='w') as test_file:
		writer = csv.writer(test_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
		writer.writerow(["n" , "f1-score" , "accuracy" , "recall" , "precision"])
		for row in testResults:
			writer.writerow(row)		

