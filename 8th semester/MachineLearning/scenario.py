from sklearn.model_selection import train_test_split

from datasets import DatasetLoader
from functions import Model
from train_loop import train, evaluate


class Scenario:
    def __init__(self, lr, n_epochs, batch_size, l2_reg, hidden_size
                 , dataset, activation, execute_on_dev, verbose):
        self._dict = {}
        self._dict['learning_rate'] = lr
        self._dict['n_epochs'] = n_epochs
        self._dict['batch_size'] = batch_size
        self._dict['hidden_size'] = hidden_size
        self._dict['l2_reg'] = l2_reg
        self._dict['dataset'] = dataset
        self._dict['activation'] = activation
        self._dict['execute_on_dev'] = execute_on_dev
        self._dict['verbose'] = verbose

    def execute(self):
        dataset_str = self['dataset']
        dataset = DatasetLoader.get_dataset(dataset_str)

        n_classes = dataset.test()[1].shape[1]
        n_inputs = dataset.test()[0].shape[1]
        xt, yt = dataset.train()
        self.model = Model(self['l2_reg'], n_classes,
                           self['hidden_size'], n_inputs, self['activation'])
        train(self.model, xt, yt, self['batch_size'],
              self['learning_rate'], self['n_epochs'], with_tqdm=self['verbose'])

        xval, yval = dataset.dev() if self['execute_on_dev'] else dataset.test()
        error = evaluate(self.model, xval, yval, with_tqdm=self['verbose'])
        if self['verbose']:
            print("error is %.2f %%" % (error * 100))
        return error

    def __str__(self):
        return str(self._dict)

    def __getitem__(self, x):
        return self._dict[x]