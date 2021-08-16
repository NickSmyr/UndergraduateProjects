# Spam Classification Alforithms
Algorithms implemented to perform spam classification for the Artificial Intelligence class 2019-2020 @ AUEB

# Create a classifier!
To create a classifier and use the training / test / graph methods implement the classifier must at least implement these two methods 
```python
def fit(self,X,Y):
    """
    X is an iterable over strings (the input emails)
    X is an iterable over ints (their respective labels , 1 for spam , 0 for ham)
    """
def predict(self,X):
    """
    X is an iterable over strings (the input emails)
    return Y an iterable over int (the output of the classifier for every input)
    """    
```
The rest of the work lik accuracy calculation , graphing be done by the other modules here
# Brief explanation of dataset
To enforce uniformity, the testing methods and scripts will only handle data that is in the form
```text
allExamples
|
-- examples
        |
        1.txt
        2.txt
|
-- labels  
        |
        1.txt
        2.txt
```
That means that all examples, whether ham or spam, must be located at the folder allExamples and there for every file in the allExamples folder there must exist a file that contains its label (1 if spam , 0 if ham)  and is located at the labels/ folder
    
# Brief explanation of modules
processEnron.py - Preprocessing and ETL for the enron dataset
featureExtractor.py - Feature extraction classes 
classifierTesting.py - Methods for training and testing of classifiers 

# Brief explanation of scripts






