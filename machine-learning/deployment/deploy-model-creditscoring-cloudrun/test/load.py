import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import tensorflow as tf
from tensorflow import keras
import numpy as np


# model = keras.models.load_model("../nn.h5")
model = keras.models.load_model("../Credit Scoring - V4.h5")

# standartize data


# transform image, same as for training!
# gender = int(input("Enter your gender value: "))
# usia = int(input("Enter your usia value: "))
# pinjaman = int(input("Enter your pinjaman value: "))
# tenor = int(input("Enter your tenor value: "))
# pemasukan = int(input("Enter your pemasukan value: "))
# tanggungan = int(input("Enter your tanggungan value: "))
# pekerjaan = int(input("Enter your pekerjaan value: "))
# donasi = int(input("Enter your donasi value: "))

usiakat = 2
pinjaman = 500000
tenor = 20
pemasukan = 4800000
tanggungan = 0
pekerjaankat = 2
pinjamankekat = 3
telatbayar = 0
donasi = 10

# data = [(usiakat - 1.556400) / 0.496834, (pinjaman - 1.471570e+06) / 6.758450e+05,
#                 (tenor - 8.885900) / 5.184146, (pemasukan - 3.199450e+06) / 1.296915e+06,
#                 (tanggungan - 2.47 / 1.705518), (pekerjaankat - 1.3289 / 0.469837),
#                 (pinjamankekat - 2.216300) / 0.743216, (telatbayar - 1.853400) / 0.989549,
#                 (donasi - 4.998000 / 3.165659)]

## expected output: 849.969
data = [0.89289874, -1.43763531,  2.14397047,  1.23418254, -1.44831297,
        1.42843895,  1.05452456, -1.87306894,  1.58016066]

## expected output: 110.833
# data = [-1.11994783,  2.26162288, -0.94251674, -1.31044424,  1.48349466,
#         -0.70006492, -1.63661889,  1.15876813, -1.57889704]
inputs = np.array(data, dtype='float32')
inputs = np.expand_dims(inputs, 0)
print(inputs)

# predict
predictions = model(inputs)
pred0 = predictions[0]
print(model.predict(inputs))
# label0 = np.round(pred0)
# print(label0)
