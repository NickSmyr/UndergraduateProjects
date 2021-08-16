from tqdm.auto import tqdm
import numpy as np

# iteration methods
def train(model, x, y, batch_size, learning_rate, n_epochs, with_tqdm=True):
    # Batched stochastic gradient ascent
    pbar = range(n_epochs)
    if with_tqdm:
        pbar = tqdm(pbar)
    for i in pbar:
        for xb, yb in BatchSampler(x, y, batch_size, with_tqdm=False):
            loss = model.score(xb, yb)
            params = model.parameters()
            grads = model.grads()
            new_params = [None] * len(params)
            for i in range(len(params)):
                new_params[i] = params[i] + (learning_rate * grads[i])
            model.set_parameters(new_params)
            if with_tqdm:
                pbar.set_postfix_str("loss: %.4f" % (loss))


from sklearn.metrics import accuracy_score


def evaluate(model, x, y, with_tqdm=True):
    loss_sum = 0
    y_true = []
    y_pred = []
    for xb, yb in BatchSampler(x, y, 200, with_tqdm=with_tqdm):
        curr_y_true = yb.argmax(axis=-1)
        curr_y_pred = model.predict(xb)

        y_true.append(curr_y_true)
        y_pred.append(curr_y_pred)

    y_true = np.concatenate(y_true)
    y_pred = np.concatenate(y_pred)

    return 1 - accuracy_score(y_true, y_pred)


def BatchSampler(x, y, batch_size, with_tqdm=True):
    num_examples = x.shape[0]
    generator = range(0, num_examples, batch_size)

    if with_tqdm:
        generator = tqdm(generator)

    for start_idx in generator:
        end_idx = min(num_examples - 1, (start_idx + batch_size) - 1)
        yield x[start_idx:end_idx, :], y[start_idx:end_idx, :]