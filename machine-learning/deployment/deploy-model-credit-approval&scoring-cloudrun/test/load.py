import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import tensorflow as tf
from tensorflow import keras
import numpy as np


# model = keras.models.load_model("../nn.h5")
model = keras.models.load_model("../creditapproval_model_v3_02.h5")

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

## 1
# gender = 1
# usia = 31
# pinjaman = 1000000
# tenor = 16
# pemasukan = 5000000
# tanggungan = 5
# pekerjaan = 2
# donasi = 5

# 0
gender = 1
usia = 53
pinjaman = 1800000
tenor = 6
pemasukan = 1500000
tanggungan = 1
pekerjaan = 0
donasi = 0

# data = np.array([1, 61, 500000, 4, 3500000, 0, 1, 3], dtype='float32')
data = [gender, (usia-20)/(80), (pinjaman-500000)/2500000, (tenor-3)/17, (pemasukan-1200000)/3800000 ,(tanggungan/5), (pekerjaan/4), (donasi/8)]
# --> [1, x, y, 1]
inputs = np.array(data, dtype='float32')
inputs = np.expand_dims(inputs, 0)
print(inputs)

# predict
predictions = model(inputs)
# predictions = tf.nn.softmax(predictions)
pred0 = predictions[0]
print(model.predict(inputs))
print(pred0)
label0 = np.round(pred0)
print(label0)
