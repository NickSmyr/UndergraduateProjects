import re
import numpy as np
import matplotlib.pyplot as plt
import math

plt.style.use('ggplot')

param_f1 = 4
labels_f1 = ['micro-f1', 'macro-f1']

param_prec = 2
labels_prec = ['micro-prec', 'macro-prec']

param_recall = 3
labels_recall = ['micro-rec', 'macro-rec']


param_thess_type_f1 = -2
param_our_type_f1 = -1

param_thess_type_prec = -4
param_our_type_prec = -5

param_thess_type_recall = -3
param_our_type_recall = -3


def get_final_results_thess_type(thess, metric='f1'):
    index = globals()['param_'+metric]

    lines = thess.split("\n")
    micro_f1 = lines[29].split()[index]
    macro_f1 = lines[30].split()[index]

    return [float(micro_f1), float(macro_f1)]


def get_final_results_our_type(ours, metric='f1', getlabels=False):
    labels = globals()['labels_' + metric]
    steps_away = 2

    clean_results = re.sub(r"[:,{}']", "", ours)
    splitted_results = clean_results.split()
    micro_index = splitted_results.index(labels[0]) + steps_away
    macro_index = splitted_results.index(labels[1]) + steps_away

    print(splitted_results)

    return [float(splitted_results[micro_index]), float(splitted_results[macro_index])]

def get_final_results_our_type_mtask(ours, getLabels=False):
    steps_away = 3

    labels = []
    micros_macros = []
    isMacro = True
    clean_results = re.sub(r"[:,{}']", "", ours)
    splitted_results = clean_results.split()
    for i in range(0, len(splitted_results),steps_away):

        if isMacro:
            labels.append(splitted_results[i])
            micros_macros.append(float(splitted_results[i+2][0:-2]))
            isMacro = False
        else:
            labels.append(splitted_results[i])
            micros_macros.append(float(splitted_results[i+2][0:-2]))
            isMacro=True
    if getLabels:
        return micros_macros,labels
    else:
        return micros_macros

def get_final_results_seperated_our_type_mtask(ours, getLabels=False):
    steps_away = 3

    labels = []
    micros = []
    macros = []
    isMacro = True
    clean_results = re.sub(r"[:,{}']", "", ours)
    splitted_results = clean_results.split()
    for i in range(0, len(splitted_results),steps_away):
        if isMacro:
            labels.append(splitted_results[i][0:splitted_results[i].index('-')])
            macros.append(float(splitted_results[i+2][0:-2]))
            isMacro = False
        else:
            micros.append(float(splitted_results[i+2][0:-2]))
            isMacro=True
    if getLabels:
        return micros,macros,labels
    else:
        return micros,macros

def get_thess_results_per_label(thess, metric='f1', start=10, num_labels=18):
    param = globals()['param_thess_type_' + metric]
    lines = thess.split("\n")
    thess_data = lines[start:start + num_labels]

    labels_to_scores = {}
    for line in thess_data:
        splitted_line = line.split()
        label = splitted_line[0]
        labels_to_scores[label] = [splitted_line[param]]
    return labels_to_scores


def get_final_results_per_label(labels_to_scores, ours, metric='f1', get_labels_and_thess=True):
    paranickm = globals()['param_our_type_' + metric]
    for line in ours:
        splitted_line = line.split()
        label = splitted_line[2]
        labels_to_scores[label].append(splitted_line[param])

    labels = []
    thess_scores = []
    our_scores = []
    for label, scores in labels_to_scores.items():
        if (get_labels_and_thess):
            labels.append(label)
            thess_scores.append(float(scores[0]))
        our_scores.append(float(scores[1]))
    if (get_labels_and_thess):
        return labels, thess_scores, our_scores
    else:
        return our_scores


def plot(scores, labels_per_scores, labels=None, metric=None, y_label="", title="", saved_path='anonymous',
         width=0.2, y_upper_lim=1, figsize=(13, 10),fontsize_label=20,fontsize_per=20):
    if not labels:
        if metric:
            labels = globals()['labels_' + metric]
        else:
            raise Exception('You have to define labels or metric')

    fig, ax = plt.subplots(figsize=figsize)

    l = len(scores)
    x = np.arange(len(labels))
    step = width
    div = l % 2
    f = math.floor(div)
    if div == 0:  # even
        start = x - width / 2 - f * l
    else:  # odd
        start = x - width * f

    all_rects = []
    cur_step = start
    for i in range(l):
        rects = ax.bar(cur_step, scores[i], width, label=labels_per_scores[i])
        all_rects.append(rects)
        cur_step = cur_step + step

    ax.set_ylim([0, y_upper_lim])
    ax.set_ylabel(y_label)
    ax.set_title(title)
    ax.set_xticks(x)
    ax.set_xticklabels(labels,fontsize=fontsize_label)

    ax.legend()

    def autolabel(rects):
        for rect in rects:
            height = rect.get_height()
            ax.annotate('{}'.format(height),
                        xy=(rect.get_x() + rect.get_width() / 2, height),
                        xytext=(0, 3),
                        textcoords="offset points",
                        ha='center', va='bottom',fontsize=fontsize_per)

    for rects in all_rects:
        autolabel(rects)

    fig.tight_layout()
    plt.savefig(saved_path)
    plt.show()

def classification_report_formatted(report):
    formatted = [x.split(' ') for x in report.split('\n')  if len(x) > 0]
    non_zero = [[y for y in x if len(y)> 0] for x in formatted]
    return non_zero[1:][:-3]
def labels(formatted):
    return [x[0] for x in formatted]
def f1_scores(formatted):
    return [float(x[3]) for x in formatted]

def plot_2_cls_reports_per_label(cls_report_1, cls_report_2, labels_common=True, **kwargs):
    formatted_1 = classification_report_formatted(cls_report_1)
    formatted_2 = classification_report_formatted(cls_report_2)

    f1_scores_1 = f1_scores(formatted_1)
    f1_scores_2 = f1_scores(formatted_2)

    labels_per_scores = labels(formatted_1)

    if not labels_common:
        #F1 scores do not have the same order:
        # need to change the order of f1_scores_2
        labels_2 = labels(formatted_2)
        i2l = { i : v for i , v in enumerate(labels_per_scores) }
        l2i = { v : i for i , v in i2l}
        f1_scores_2_new = [ f1_scores_2[l2i[x]] for x in labels_per_scores]
        f1_scores_2 = f1_scores_2_new

    plot([f1_scores_1 , f1_scores_2] , **kwargs)

