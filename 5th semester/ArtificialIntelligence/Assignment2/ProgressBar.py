import datetime
import time
class ProgressBar:
    """
    A class so that we can know the progresss of each classifier
    when it's training
    """
    def __init__(self):
        self.start = time.time()       
        return
    def reset(self):
        self.start = time.time()       
        return    
    def display(self, percentage):
        """
        Percentage is the amount of work done ,  must be
        withing the interval [0,1]
        """
        interval = time.time()  - self.start  
        done = int(percentage*100)
        #Phase is a number from [0,9]
        phase = int(percentage*10)
        #The two following strings constitute the displayable
        #Progress bar
        progress = "-" * phase + ">"
        padding = " " * (10 - phase)
        #Estimated time of arrival

        # We assumed the the percentage is linear to the time needed 
        # And excetude linear interpolation between the points (start,0)
        # and (now , percentage)
        a = percentage / (interval)
        b = 0

        #So the eta will be the x of the function : time  -> percentage
        #Where percentage = 1
        eta = -1
        if(a != 0): 
            eta =  (1-b) / a   
        printS = "Done: ( %d ) [" % (done)  + progress + padding + "] Est time left : %.2f" %(eta - interval) + " Total Time %.2f"%(interval)
        print(printS)
        
        return  
