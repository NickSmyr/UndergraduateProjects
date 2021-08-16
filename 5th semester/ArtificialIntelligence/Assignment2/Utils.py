import math
def H(probabilityDistr):
    """
    Calculates the entropy for the random variable with probability distr
    prbabilityDistr : a list of probabilities [0 , 1] (must sum to 1)
    """
    result = 0
    for px in probabilityDistr:
        if px <= 0 :
            continue
        result += - math.log2(px) * px
    return result
        
def information_gain(spam_occurences , ham_occurences, totalSpams , totalHams):
            """
            Calculates the information gain of a word that appeared for the given
            amount of times in hams and spams
            """           
            spam_absences = totalSpams - spam_occurences
            ham_absences = totalHams - ham_occurences

            #Adding 1 to occurences and absences for normalization
            spam_occurences += 1
            ham_occurences += 1
            spam_absences += 1
            ham_absences += 1

            pham = totalHams / (totalSpams + totalHams)
            pspam = 1 - pham
            
            oldEntropy = H([pham,pspam])
            
            pExists = (spam_occurences + ham_occurences ) / (totalSpams + totalHams)
            pNExists = (spam_absences + ham_absences) / (totalSpams + totalHams)
            
            phamIfExists = ham_occurences / (ham_occurences + spam_occurences)
            pspamIfExists = 1 -  phamIfExists
            
            phamIfNExists = ham_absences / ( ham_absences + spam_absences )
            pspamIfNExists = 1 -  phamIfNExists
            
            EntropyIfExists = H([phamIfExists , pspamIfExists])
            EntropyIfNExists = H([phamIfNExists , pspamIfNExists])
            
            
            
            newExpectedEntropy = pExists * EntropyIfExists + pNExists * EntropyIfNExists
            return  oldEntropy - newExpectedEntropy        
            
