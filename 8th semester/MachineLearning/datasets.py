import os
import numpy as np
from sklearn.model_selection import train_test_split


class MnistDataset():

    def _parse_mnist_file(self, filepath, label):
        x = None
        y = None
        with open(filepath, 'r') as f:
            lines = f.readlines()
            lines = " ".join(lines)
            x = np.fromstring(lines, dtype=int, sep=' ').reshape(-1, 28 * 28)
            y = np.zeros((x.shape[0], 10))
            y[:, label] = 1

        return x, y

    def train(self):
        return self.train_x, self.train_y

    def dev(self):
        return self.dev_x, self.dev_y

    def test(self):
        return self.test_x, self.test_y

    def __init__(self, path):
        """
        loads the mnist dataset from the path
        """
        self.train_x = []
        self.train_y = []

        self.test_x = []
        self.test_y = []
        print("Loading MNIST dataset")
        s = os.listdir(path)
        for filename in s:
            curr_label = int(filename[-5])
            x, y = self._parse_mnist_file(path + "/" + filename, curr_label)
            if 'train' in filename:
                self.train_x.append(x)
                self.train_y.append(y)
            elif 'test' in filename:
                self.test_x.append(x)
                self.test_y.append(y)
            else:
                raise Exception(f"Unexpected mnist file {filename}")
        self.train_x = np.concatenate(self.train_x, axis=0) / 255
        self.train_y = np.concatenate(self.train_y, axis=0)

        self.test_x = np.concatenate(self.test_x, axis=0) / 255
        self.test_y = np.concatenate(self.test_y, axis=0)

        # Creating dev subsets
        self.train_x, self.dev_x, self.train_y, self.dev_y \
            = train_test_split(self.train_x, self.train_y, test_size=0.3)


import pickle
from itertools import chain


class CifarDataset():
    def _parse_cifar_file(self, filename):
        data = None
        labels = None
        with open(filename, 'rb') as f:
            x = pickle.load(f, encoding='bytes')
            labels = np.array(x[b'labels'])
            data = x[b'data'].astype(np.float)

        return data, labels

    def train(self):
        return self.train_x, self.train_y

    def dev(self):
        return self.dev_x, self.dev_y

    def test(self):
        return self.test_x, self.test_y

    def __init__(self, path):
        print("Loading CIFAR-10 dataset")
        self.train_x = []
        self.train_y = []

        self.test_x = []
        self.test_y = []

        s = os.listdir(path)
        for filename in s:
            if 'data_batch' in filename:
                x, y = self._parse_cifar_file(path + "/" + filename)
                self.train_x.append(x)
                self.train_y.append(y)
            elif 'test_batch' in filename:
                x, y = self._parse_cifar_file(path + "/" + filename)
                self.test_x.append(x)
                self.test_y.append(y)

        self.train_x = np.concatenate(self.train_x, axis=0) / 255
        self.train_y = np.concatenate(self.train_y, axis=0)

        self.test_x = np.concatenate(self.test_x, axis=0) / 255
        self.test_y = np.concatenate(self.test_y, axis=0)

        # Need to convert labels to the target array ( one hot vectors)
        max_label = max(self.train_y.max(), self.train_y.max())
        one_hot_vectors = np.eye(max_label + 1)

        self.train_y = one_hot_vectors[self.train_y]
        self.test_y = one_hot_vectors[self.test_y]

        # Creating dev subsets
        self.train_x, self.dev_x, self.train_y, self.dev_y \
            = train_test_split(self.train_x, self.train_y, test_size=0.3)


class DatasetLoader:
    loaded_dsets = {}

    def get_dataset(self, ds):
        if ds == "mnist":
            if ds not in DatasetLoader.loaded_dsets:
                DatasetLoader.loaded_dsets['mnist'] = MnistDataset("./data/mnist/")
            return DatasetLoader.loaded_dsets['mnist']

        elif ds == "cifar":
            if ds not in DatasetLoader.loaded_dsets:
                DatasetLoader.loaded_dsets['cifar'] = CifarDataset("./data/cifar/")
            return DatasetLoader.loaded_dsets['cifar']
        else:
            raise Exception("invalid dataset provided")


DatasetLoader.get_dataset = classmethod(DatasetLoader.get_dataset)