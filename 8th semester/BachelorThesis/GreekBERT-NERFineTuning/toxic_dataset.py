import dill
with open('/home/cave-of-time/bak/data/straintek/original_data/train.bin', 'rb') as fr:
    data = dill.load(fr, encoding='utf-8')
counter1 = 0
counter0 = 0
for i, example in enumerate(data):
    print(example['text'])
    if example['rejected'] == 1:
        counter1=counter1+1
    elif example['rejected'] == 0:
        counter0=counter0+1
print(counter1)
print(counter0)

l = len(data)
print(l)
print(counter1/l)
print(counter0/l)

