"""
Classes that map emails to feature vectors 
"""

"""

class that transforms emails to a feature vector f = <x1,x2,...,xn>
where xi =  1 if word i of the vocabulary exists in the input email
or 0 otherwise 

the words that the extractor detects are a subset from the given words
and are the top k words with the highest information gain 

"""
import math
import Utils
import os
import datasets

class emailFeatureExtractor:
    def __init__(self, maxNumFeatures , frequencyThreshold):
        """
        vocabulary must be a dict { wi -> {fi , sfi}} 
        mapping words to a tuple [ fi , sfi] where fi is the total
        amount of times the word occured in the corpus and sfi the total
        amount of times the word occured in spam emails
        
        returns vocabulary , din
        (din is the dimension of the vectors output by extractArray)
        
        """
        self.maxNumFeatures = maxNumFeatures
        self.frequencyThreshold = frequencyThreshold
        tmpVoc , totalHams , totalSpams = self.retrieveVocabulary()
        self.voc = self.retainMBest(tmpVoc , totalSpams , totalHams)

    def extractArray(self,x):
        """    
        Extracts an array of numbers that represents
        the given email.
        
        x : String  (the given email from which to extract features)
        """
        emailWords = set(x.split())
        res =  [0] * len(self.voc)
        for word in self.voc:
            if word in emailWords:
                res[self.voc[word]] = 1 
        return res
    def retrieveVocabulary(self):
        """
        Creates a vocabulary containin spam occurences / ham occurences
        From all the dataset
        """
        X,Y = datasets.load_enron()
        tmpVoc , totalSpams , totalHams = self.extractVocabulary(X,Y)         
        return tmpVoc, totalSpams , totalHams
    def reformulateVocabulary(self,X,Y):
        tmpVoc , totalSpams , totalHams = self.extractVocabulary(X,Y)         
        self.voc = self.retainMBest(tmpVoc , totalSpams , totalHams)                        
    def extractVocabulary(self,X , Y):
        """
        Extracts a vocabulary containing information about word frequencies , spam / ham occurences / absenses
        and calculates the information gain for every word
        
        X = list of emails (String types) 
        Y = list of the email classes (Integer types)
        """
        totalHams = 0
        totalSpams = 0
        # Vocabulary containg entries with information,statistics about the word
        # Every entry will be a dictionary containing the fields 
        # "spam_occ" "ham_occ" "spam_abs" "ham_abs" "information_gain" , "frequency"
        tmpVoc = {}    
        for x , y in zip(X,Y):
            words = x.split()
            for word in words:
                #Adding word to the vocabyulary
                if word not in tmpVoc:
                    tmpVoc[word] = {
                        "spam_occurences" : 0 , 
                        "ham_occurences"  : 0 ,
                         }
                #Word occureed in a ham email
                if y == 0:
                    totalHams += 1
                    tmpVoc[word]["ham_occurences"] += 1
                #Word occureed in a spam email
                else :
                    totalSpams += 1
                    tmpVoc[word]["spam_occurences"] += 1
        return tmpVoc , totalSpams , totalHams          
    def retainMBest(self, tmpVoc ,totalSpams , totalHams):
        """
        Creates a vocabulary that contains the m words with
        the highest information gain
        """
        #Calculating frequencies and IG for every word       
        for word in tmpVoc:
            #Saving everything to local variables to avoid lookup times
            
            spam_occurences = tmpVoc[word]["spam_occurences"] 
            ham_occurences = tmpVoc[word]["ham_occurences"]
            tmpVoc[word]["information_gain"] = Utils.information_gain(spam_occurences , ham_occurences , totalSpams , totalHams)
            #print(voc[word]["information_gain"])       
            tmpVoc[word]["frequency"] = spam_occurences + ham_occurences
        #Filtering according to the threshold
        tmpVoc = { x : tmpVoc[x] for x in tmpVoc if tmpVoc[x]["frequency"] > self.frequencyThreshold}
        #Finding the best m words with the best ig
        #Converting to a list so we can sort
        l = [ [x , tmpVoc[x]] for x in tmpVoc ] 
        l = sorted(l , key = lambda x : x[1]["information_gain"])

        """
        k = [ [x , tmpVoc[x]["information_gain"]] for x in tmpVoc ] 
        k = sorted(k , key = lambda x : x[1])
        from pprint import pprint
        pprint(k)
        """
        ##Top words wth highest ig
        #Final voc
        voc = {}
        # Maps from indexes to words
        self.inverseVoc = {}
        for i in range(min(self.maxNumFeatures,len(l))):
            currWord = l[len(l) - i - 1][0]
            voc[currWord] = i
            self.inverseVoc[i] = currWord
        #Returning the size of the feature arrays that will be extracted
        return voc

            
