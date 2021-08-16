from itertools import product
import pandas as pd
from tqdm.auto import tqdm

from functions import Model
from scenario import Scenario
import numpy as np


def tune():
    lrs = [0.01, 0.001, 0.0001]
    n_epochs = [50]
    batch_sizes = [200]
    hidden_sizes = [100, 200, 300]
    l2_regs = [0.0001, 0.00001, 0.000001]
    datasets = ["mnist", "cifar"]
    activations = ["logexp", "tanh", "cos"]
    execute_on_dev = [True]
    verbose = [False]
    generator = tqdm(
        list(product(
            lrs, n_epochs, batch_sizes, l2_regs, hidden_sizes,
            datasets, activations, execute_on_dev, verbose)))
    results = []
    for x in generator:
        a = Scenario(*x)
        result = a.execute()
        results.append([result, a])

    df = pd.DataFrame(data=results)
    df.to_csv("tune_res")

    print("min scenario error ", min(results, key=lambda x: x[0])[0])
    print("min error scenario ", min(results, key=lambda x: x[0])[1])

def run(lr, n_epochs, batch_size, hidden_size, l2_reg, dataset, activation):
    a = Scenario(
        lr=lr,
        n_epochs=n_epochs,
        batch_size=batch_size,
        hidden_size=hidden_size,
        l2_reg=l2_reg,
        dataset=dataset,
        activation=activation,
        execute_on_dev=False,
        verbose=True
    )
    a.execute()


import random


def _gradcheck(model, tol=10e-4, epsilon=10e-6, batch_size=150):
    """
    Performs a numerical gradient check of the model
    """
    test_input = np.random.rand(batch_size, model.n_inputs)
    test_target = np.zeros((batch_size, model.n_classes))
    # Set each examples class randomly
    for i in range(0, batch_size):
        test_target[i][random.randrange(0, model.n_classes)] = 1

    test_loss = model.score(test_input, test_target)
    test_grad = model.grads()
    # Perform gradient check
    count = 0
    all_ok = True

    for param, grad in zip(model.parameters(), test_grad):
        it = np.nditer(param, flags=['multi_index'], op_flags=['writeonly'])
        for x in tqdm(it):
            previous_value = x
            target_grad = grad[it.multi_index]

            # Evalutaing at w + e
            param[it.multi_index] = x + epsilon
            wpe = model.score(test_input, test_target)

            # Evaluating at w - e
            param[it.multi_index] = x - 2 * epsilon
            wme = model.score(test_input, test_target)

            # Resetting the model param
            param[it.multi_index] = previous_value

            num_grad = (wpe - wme) / (2 * epsilon)
            if abs(num_grad - target_grad) > tol:
                all_ok = False
                print("num grad ", num_grad)
                print("target grad ", target_grad)
                diff = abs(num_grad - target_grad)
                print("GRADIENT CHECK ERROR " +
                      "%.2f paramorder %d coordinate %s dif %.2f" % (x, count, it.multi_index, diff))
        count += 1

    if all_ok:
        print("Gradient checks passed!")
    else:
        print("Gradient checks failed")


def gradcheck(tol=1e-4, eps=1e-6):
    model = Model(0.01, n_classes=8, hidden_size=10, n_inputs=5, activation="logexp")
    _gradcheck(model, batch_size=3, epsilon=eps, tol=tol)

