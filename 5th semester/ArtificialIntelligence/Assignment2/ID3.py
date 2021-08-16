import random
import featureExtractor
# STUPID CLASS THAT WORKS AS A CLASSIFIER EXAMPLE
class Tree:
	def __init__(self):
		#A label that indicates which attribute the node is testing
		self.label = None
		#Dictionary that contains mappings from features to id3 trees
		self.values = {}
		#A list containing the examples at the current node
		self.examplesX = None
		self.examplesY = None
		#spams and Hams in the examples of the current node
		self.totalSpams = None
		self.totalHams = None

		return
import Utils
class ID3:
	def id3(self, examplesX, examplesY , attributes , predefined, depth):
		"""
			Runs the id3 algorithm on the specified examples which can
			test the given attributes

			examplesX = list of examples (each example is a list of integers (0 or 1) ) 
			examplesY = list of example labels

			attributes = list of indexes (integers) -> shows which attributes can sill
														be tested by the id3 tree									
		"""
		totalSpams = 0
		totalHams = 0
		for x , y in zip(examplesX , examplesY):
			#Calculating ham, spam occurences for every attribute
			if y == 0:
				totalHams += 1
			else:
				totalSpams += 1
		#Stop conditions
		#There are no examples (spam or ham)
		if((totalSpams == 0 and totalHams == 0)):
			tree = Tree()
			tree.predefined = predefined
			tree.label = None
			tree.examplesX = examplesX
			tree.examplesY = examplesY
			tree.totalHams = totalHams
			tree.totalSpams = totalSpams
			return tree
		#Setting the new predefined category
		if (totalSpams > totalHams):
			predefined = 1
		else:
			predefined = 0
			
		#All examples are either spam of ham
		if((totalHams == 0 and totalSpams > 0) or (totalHams > 0 and totalSpams == 0)):
			tree = Tree()
			#Node returned is a leaf node so there is no attribute to test
			tree.label = None
			tree.predefined = predefined
			tree.examplesX = examplesX
			tree.examplesY = examplesY
			tree.totalHams = totalHams
			tree.totalSpams = totalSpams
			return tree
		#No attributes left
		if(len(attributes) == 0):
			tree = Tree()
			#Node returned is a leaf node so there is no attribute to test
			tree.label = None
			tree.examplesX = examplesX
			tree.examplesY = examplesY
			tree.predefined = predefined
			tree.totalHams = totalHams
			tree.totalSpams = totalSpams
			return tree
		"""
		if(depth > self.max_depth):
			tree = Tree()
			#Node returned is a leaf node so there is no attribute to test
			tree.label = None
			tree.examplesX = examplesX
			tree.examplesY = examplesY
			tree.predefined = predefined
			tree.totalHams = totalHams
			tree.totalSpams = totalSpams
			return tree	
		"""
		spam_occurences = [0] * len(attributes)
		ham_ocurences = [0] * len(attributes)
		information_gains = [0] * len(attributes)	
		#Calculating spam , ham occurences for every attribute
		for x , y in zip(examplesX , examplesY):
			for i in range(len(attributes)):
				if y == 0:
					ham_ocurences[i] += 1
				else:
					spam_occurences[i] += 1

		for i in range(len(attributes)):
			information_gains[i] = Utils.information_gain(
				spam_occurences[i] , ham_ocurences[i] 
				, totalHams , totalSpams
			)
		#Findinf the attribute with the maximum ig
		max_ig = -9999
		max_index = -1
		for i in range(len(attributes)):	
			if(information_gains[i] > max_ig):
				max_ig = information_gains[i]
				max_index = i
		#max_index is the best attribute
		selected_attribute = attributes[max_index]
		#Spliting the input examples depending on whether 
		#They have the attribute
		emailsWithAttrX = []
		emailsWithAttrY = []

		emailsOhneAttrX = []
		emailsOhneAttrY = []
		for x , y in zip(examplesX , examplesY):
			#If attrubute exists in the current email
			if 	x[selected_attribute] == 1:
				emailsWithAttrX.append(x)
				emailsWithAttrY.append(y)
			else:
				emailsOhneAttrX.append(x)
				emailsOhneAttrY.append(y)
		##Creating the tree that id3 will return
		tree = Tree()
		tree.label = selected_attribute
		tree.predefined = predefined
		tree.examplesX = examplesX
		tree.examplesY = examplesY
		tree.totalHams = totalHams
		tree.totalSpams = totalSpams

		#Creating a new list of attributes that will be used by the subtrees
		# (the selected attribute at the current node is not included		)
		newAttributes = []
		for attr in attributes:
			if (attr != selected_attribute):
				newAttributes.append(attr)
		

		#Subtree when selected_attribute has value 0
		tree.values[0] = self.id3(emailsOhneAttrX , emailsOhneAttrY
						, newAttributes , predefined , depth + 1)
		tree.values[1] = self.id3(emailsWithAttrX , emailsWithAttrY
						, newAttributes , predefined , depth + 1)
		return tree				

	def classify_x(self , x):
		"""
		Iterates through the id3 and finds the leaf node where
		input x (one vector of attributes 0 or 1) belongs

		then x is classified as spam if at that node there are more spams than hams
		or else in ham
		"""
		
		currNode = self.root
		#While current node is not a leaf node
		while(len(currNode.values) > 0):
			feature = x[currNode.label]
			#Changing the current Node to point to the
			#Subtree with the specified feature
			currNode = currNode.values[feature]
		#Current node is now a leaf node
		if(currNode.totalSpams == currNode.totalHams):
			return currNode.predefined
		if(currNode.totalSpams > currNode.totalHams):
			return 1
		else:
			return 0	


	def __init__(self, m):
		#Perhaps the architecture of a neural net
		# needs to be specified here
		self.featureObject=featureExtractor.emailFeatureExtractor(m,5)
		#self.max_depth = max_depth
		return 
	def fit(self,X , Y):
		"""
		x iterable over emails (text), 
		y iterable over their respective labels
		"""	
		attributes = [self.featureObject.extractArray(x) for x in X]
		numOfAttr = len(attributes[0])
		self.root = self.id3(attributes,Y, list(range(numOfAttr)) , 0 , 0)
		
		return
	def predict(self,X):
		X = [self.featureObject.extractArray(x) for x in X]
		return [self.classify_x(xi) for xi in X]

#Testing the classifier
import datasets
import classifierTesting
#Testing clf on validation set and calculating best hyperparameters

X , Y = datasets.load_enron()
X , Y = datasets.randomize(X,Y)
X_train , Y_train , X_val , Y_val , X_test , Y_test = datasets.train_validation_test_split(X,Y) 

attributes = [100 , 500 , 900]

grid = [[a] for a in attributes ]
print("Calculating best hyperparameters")

bestParams = None
bestAcc = 0
iterator = 0	
for params in grid:
	clf = ID3(params[0])
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
clf = ID3(bestParams[0])
classifierTesting.testingSuite(clf , X_test ,Y_test , X_train , Y_train)					 				
