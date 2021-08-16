import numpy as np
from math import sqrt

def softmax(x, ax=1):
    m = np.max(x, axis=ax, keepdims=True)  # max per row
    p = np.exp(x - m)
    return (p / np.sum(p, axis=ax, keepdims=True))


def logexp(x):
    return np.log(1 + np.exp(x))


def logexp_d(x):
    a = np.exp(x)
    return a / (1 + a)


def tanh(x):
    a = np.exp(x)
    b = np.exp(-1 * x)
    return (a - b) / (a + b)


def tanh_d(x):
    return 1 - (tanh(x) ** 2)


def cos(x):
    return np.cos(x)


def cos_d(x):
    return -np.sin(x)


def identity(x):
    return x


def identity_d(x):
    return np.ones(x.shape)


class Model:
    def __init__(self, l2_reg, n_classes, hidden_size
                 , n_inputs, activation="logexp"):

        self.n_inputs = n_inputs
        self.n_classes = n_classes

        # We add a bias to n_inputs so D needs to be == n_inputs
        self.K = n_classes
        self.M = hidden_size
        self.D = n_inputs
        w1_fan_in = self.D + 1
        w2_fan_in = self.M + 1
        # TODO proper init
        self.W2 = (np.random.rand(self.K, self.M + 1) * 2 - 1) * 1 / sqrt(w2_fan_in)
        self.W1 = (np.random.rand(self.M, self.D + 1) * 2 - 1) * 1 / sqrt(w1_fan_in)

        if activation == "logexp":
            self.activation = logexp
            self.activation_d = logexp_d
        elif activation == "tanh":
            self.activation = tanh
            self.activation_d = tanh_d
        elif activation == "cos":
            self.activation = cos
            self.activation_d = cos_d
        else:
            self.activation = identity
            self.activation_d = identity_d
            print("Invalid activation provided, using identity")

        self.l2 = l2_reg
        self.h = self.activation

    def parameters(self):
        return [self.W1, self.W2]

    def set_parameters(self, params):
        self.W1 = params[0]
        self.W2 = params[1]

    def __call__(self, _input):
        """
        Does the models forward pass
        """
        pass

    def score(self, _input, _target):
        """
        Returns the loss of the model at _input with target _target.
        """
        # input (Nb, D + 1)
        # target (Nb, K)
        Nb = _input.shape[0]
        # Add ones for bias
        _input = np.concatenate((np.ones((Nb, 1), dtype=np.float), _input), axis=-1)
        zetas = self.h(_input @ self.W1.T)  # (Nb, M)
        # Add ones for bias
        zetas = np.concatenate((np.ones((Nb, 1), dtype=np.float), zetas), axis=-1)  # (Nb, M+1)
        logits = zetas @ self.W2.T
        probabilities = softmax(logits)

        # Saving these arrays for gradient computation
        self.T = _target
        self.X = _input
        self.Y = probabilities
        self.Z = zetas

        log_likelihood = np.sum(_target * np.log(probabilities))
        reg_term = - (self.l2 / 2) * (np.sum(self.W1 * self.W1) + np.sum(self.W2 * self.W2))

        loss = log_likelihood + reg_term
        return loss

    def predict(self, _input):
        """
        Returns the predicted classes
        """
        # input (Nb, D + 1)
        # target (Nb, K)
        Nb = _input.shape[0]
        _input = np.concatenate((np.ones((Nb, 1), dtype=np.float), _input), axis=-1)
        zetas = self.h(_input @ self.W1.T)  # (Nb, M)
        zetas = np.concatenate((np.ones((Nb, 1), dtype=np.float), zetas), axis=-1)  # (Nb, M+1)
        logits = zetas @ self.W2.T
        probabilities: np.ndarray = softmax(logits)
        return probabilities.argmax(axis=-1)

    def grads(self):
        """
        REturns the gradients for the model's parameters.
        The output of this method and the output of the @parameters
        method should be both listswith the same length and each
        element must be a numpy array with the same shapes
        """
        TminusY = self.T - self.Y
        gradW2 = TminusY.T @ self.Z - (self.l2 * self.W2)
        A = self.activation_d(self.X @ self.W1.T)
        # gradW1 = (((TminusY @ self.W2[:,1:]).T * A.T) @ self.X)
        gradW1 = (((TminusY @ self.W2[:, 1:]).T) * A.T @ self.X) - (self.l2 * self.W1)
        return [gradW1, gradW2]
