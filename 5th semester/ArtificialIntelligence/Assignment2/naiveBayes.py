import featureExtractor
import math
class naiveBayes:
    def __init__(self , m):
        self.featureObject=featureExtractor.emailFeatureExtractor(m,5)
        self.arrays = [[0] * len(self.featureObject.voc)] * 4 
        #print(self.featureObject.voc)
        #print(self.featureObject.voc["money"])
    def fit(self, X, Y):
        """
        self.fits += 1
        if(self.fits >=3 ):
            return
        """
        #self.featureObject.reformulateVocabulary(X,Y)
        #Attributes is a 2d array of 0-1 that represent the presence of a word in an email
        attributes = [self.featureObject.extractArray(x) for x in X]
        self.ham = 0
        self.spam = 0
        #for attributes in x_vec:
        #from the class we can take the chosen vocabulary and its dimension
        #din will always be the same
        din=len(attributes[0])
        for c in Y:
            if c==0:
                self.ham=self.ham+1
            else:
                self.spam=self.spam+1
        self.hamP=self.ham/len(Y) # P(Y=0)
        self.spamP=self.spam/len(Y) # P(Y=1)
        #Nice!
        self.postHamP0=[0]*din # P(Xi=0|Y=0) =  hams not containing word  / total hams
        self.postHamP1=[0]*din # P(Xi=1|Y=0) = hams containing word  / total hams
        self.postSpamP0=[0]*din # P(Xi=0|Y=1) = spams not containing word  / total spams
        self.postSpamP1=[0]*din # P(Xi=1|Y=1) = spams containing word / total spams

        #iterate every email and add a word presence to the arrays
        for x,y in zip(attributes, Y):
            if y==0:
                for i in range(din):
                    if x[i]==0:
                        self.postHamP0[i]=self.postHamP0[i]+ 1
                    else:
                        self.postHamP1[i]=self.postHamP1[i]+ 1
            if y==1:
                for i in range(din):
                    if x[i]==0:
                        self.postSpamP0[i]=self.postSpamP0[i]+ 1
                    else:
                        self.postSpamP1[i]=self.postSpamP1[i]+ 1
        """             
        print(self.ham)
        print(self.spam)
        print()
        
        print(self.postHamP0)
        print(self.postHamP1)
        print(self.postSpamP0)
        print(self.postSpamP1)
             
        print("$" * 100)                
        print(sum([x + y for x , y in zip(self.postHamP0 , self.postHamP1)])/din)
        print(sum([x + y for x , y in zip(self.postSpamP0 , self.postSpamP1)])/din)
        """      
        
        #Enforcing laplace regularization
        self.postHamP0 =[(x + 1 )/(self.ham+2) for x in self.postHamP0]
        self.postHamP1 = [1 - x  for x in self.postHamP0]
        #self.postHamP1 = [(x + 1 ) / (self.ham + 2) for x in self.postHamP1]
        self.postSpamP0 =[(x + 1 )/(self.spam+2) for x in self.postSpamP0]
        self.postSpamP1 = [1 - x  for x in self.postSpamP0]
        #self.postSpamP1  = [(x + 1 ) / (self.spam + 2) for x in self.postSpamP1]
        
        newArrays = [ self.postHamP0 , self.postHamP1  , self.postSpamP0 , self.postSpamP1 ] 
        for new , old in zip(newArrays,self.arrays):
            diff = sum([(x - y) / len(new) for x , y in zip(new,old)])
            print("Total diff %.4f" % (diff))
            #print(["%.2f" % (x) for x in a])
            #print(["%.2f" % (x - y) for x , y in zip(new,old)])
        print()    
        self.arrays = newArrays    

    def predict(self, X):
        y_pred = []
        attributes=[self.featureObject.extractArray(x) for x in X ] 
        din=len(attributes[0])
        #Το prevent mathematical errors we are going to compare the logarithms of P(C=1)P(X | C=1)
        #                                                           and P(C=0)P( X | C= 0)
        for attr in attributes:
            pHamIfAttr=math.log(self.hamP) # P(Ham|attributes)  =P(ham)
            pSpamIfAttr=math.log(self.spamP) # P(Spam|attributes)  =P(spam) which is an easy initialization
            
            
            for i in range(din):
                
                if attr[i]==0:
                    
                    pHamIfAttr += math.log(self.postHamP0[i])
                    pSpamIfAttr += math.log(self.postSpamP0[i])
                if attr[i]==1:
                    pHamIfAttr += math.log(self.postHamP1[i])
                    pSpamIfAttr += math.log(self.postSpamP1[i])
            #print("PhamIfAttr %f" % (pHamIfAttr))
            #print("PspamIfAttr %f" % (pSpamIfAttr))
            #print()
            #print(pHamIfAttr) 
            #print(pSpamIfAttr)  
            #print()     
            if (pHamIfAttr>pSpamIfAttr):
                y_pred.append(0)
            else:
                y_pred.append(1)               
        return y_pred        
        #call fit(X,predicted) where X is the last email we got ???????
        #That would mean that we are training the classifier Based on its predictions
        #Which doesn't happen. TRaining happens only when we are given an email
        #and its true label
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
	clf = naiveBayes(params[0])
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
clf = naiveBayes(bestParams[0])
classifierTesting.testingSuite(clf , X_test ,Y_test , X_train , Y_train)
