import os
import random
import math

def load_enron():
    DATA_DIR = "data/"
    X = []
    Y = []
    enron_datasets = ["enron" + str(i + 1) for i in range(6) ]
    for ds in enron_datasets:
        #Looping through every enron subdataset
        if not (os.path.isdir(DATA_DIR + ds)):
            continue
        for f in os.listdir(DATA_DIR + ds + "/ham/"):
            try:
                with open(DATA_DIR + ds + "/ham/" + f) as hamF:
                    X.append(hamF.read())
                    Y.append(0)
            except:
                #Ignore faulty files
                continue
        for f in os.listdir(DATA_DIR + ds + "/spam/"):
            try:
                with open(DATA_DIR + ds + "/spam/" + f) as spamF:
                    X.append(spamF.read())
                    Y.append(1)
            except:
                #Ignore faulty files
                continue
    return X , Y            
def randomize(X,Y):
    """
        Shuffles the dataset X, Y
    """
    combined = [ [x,y] for x ,y in zip(X,Y) ]
    random.shuffle(combined)
    return [x[0] for x in combined] , [x[1] for x in combined]
def train_test_split(X , Y , ptest = 0.1):
    """
    Splits the input dataset X,Y into test and train
    with test being ptest * 100 % of the dataset size

    returns the train dataset and the test dataset 
    """    
    split_point = math.floor((1 - ptest) * len(X))

    X_train = X[0:split_point]
    Y_train = Y[0:split_point]

    X_test = X[split_point :]
    Y_test = Y[split_point :]

    return X_train , Y_train , X_test , Y_test

def train_validation_test_split(X , Y , ptest = 0.1 , pval = 0.1):
    """
    Splits the input dataset X,Y into test and train and validation
    with test being ptest * 100 % of the dataset size
    and validation being pval * 100 % oof the dataset size

    returns the train dataset , the validation dataset , and the test dataset 
    """    
    split_point1 = math.floor((1 - (ptest + pval)) * len(X))
    split_point2 = math.floor((1 - (ptest)) * len(X))

    X_train = X[0:split_point1]
    Y_train = Y[0:split_point1]

    X_val = X[split_point1 : split_point2]
    Y_val = Y[split_point1 : split_point2]

    X_test = X[split_point2 :]
    Y_test = Y[split_point2 :]

    return X_train , Y_train ,X_val , Y_val, X_test , Y_test    