# Course project for Artificial Intelligence

The course project was partitioned in two parts: AI scheduler and Automatic Spam classification.

## AI scheduler

Implemented a Hill Climb algorithm to optimize the schedule of lectures according to given constraints. 
Experimented with both traditional Hill Climb as well as Stochastic Hill Climb. Created a custom heuristic
to optimize based on the hierarchical importance of the heuristics.

## Spame Classifier

Developed an NLP Spam classifier. Created the data processing pipelines:

- Train/Dev/Test split
- Train set shuffling
- Evaluation suite

Experimented with the following models:

- Logistic Regression
- ID5 Tree classifier
- Naive Bayes

Grid search was used at each model hyperparameter tuning.
