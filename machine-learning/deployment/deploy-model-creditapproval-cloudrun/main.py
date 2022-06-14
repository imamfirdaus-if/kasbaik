import os
import io
import tensorflow as tf
from tensorflow import keras
import numpy as np

from flask import Flask, request, jsonify

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model1 = keras.models.load_model("creditapproval_model_v3_02.h5")
model2 = keras.models.load_model("Credit Scoring - V4.h5")


def predict1(x):
    predictions = model1(x)
    pred0 = predictions[0]
    label0 = np.round(pred0)
    return label0

def predict2(x):
    predictions = model2.predict(x)
    # pred0 = predictions[0]
    # label0 = np.round(pred0)
    return predictions


app = Flask(__name__)


@app.route("/credit_approval", methods=["GET", "POST"])
def index1():
    # import pdb; pdb.set_trace()
    if request.method == "POST":
        # test = float(request.form.get('tenor'))
        # print(type(test))
        gender = float(request.form.get("gender"))
        usia = float(request.form.get("usia"))
        pinjaman = float(request.form.get("pinjaman"))
        tenor = float(request.form.get("tenor"))
        pemasukan = float(request.form.get("pemasukan"))
        tanggungan = float(request.form.get("tanggungan"))
        pekerjaan = float(request.form.get("pekerjaan"))
        donasi = float(request.form.get('donasi'))
        # data = [
        #     float(request.form.get('gender')),
        #     float(request.form.get('usia')),
        #     float(request.form.get('pinjaman')),
        #     float(request.form.get('tenor')),
        #     float(request.form.get('pemasukan')),
        #     float(request.form.get('tanggungan')),
        #     float(request.form.get('pekerjaan')),
        #     float(request.form.get('donasi'))
        # ]
        data = [gender, (usia - 20) / 80, (pinjaman - 500000) / 2500000, (tenor - 3) / 17,
                (pemasukan - 1200000) / 3800000, (tanggungan / 5), (pekerjaan / 4), (donasi / 8)]
        file = np.array(data, dtype='float32')
        file = np.expand_dims(file, 0)
        if file is None:
            return jsonify({"error": "no input"})
        try:
            tensor = file
            prediction = predict1(tensor)
            data = {"prediction": int(prediction)}
            print(data)
            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

    return "OK"

@app.route("/credit_score", methods=["GET", "POST"])
def index2():
    # import pdb; pdb.set_trace()
    if request.method == "POST":
        # test = float(request.form.get('tenor'))
        # print(type(test))
        usiakat = float(request.form.get("usiakat"))
        econkat = float(request.form.get("econkat"))
        pekerjaankat = float(request.form.get("pekerjaankat"))
        pinjamankekat = float(request.form.get("pinjamankekat"))
        telatharikat = float(request.form.get("telatharikat"))
        donasikat = float(request.form.get('donasikat'))
        data = [
                (usiakat - 1), (econkat / 5),
                (pekerjaankat - 1), (pinjamankekat - 1) / 2,
                (telatharikat / 3), (donasikat / 3)
                ]
        file = np.array(data, dtype='float32')
        file = np.expand_dims(file, 0)
        if file is None:
            return jsonify({"error": "no input"})
        try:
            tensor = file
            prediction = predict2(tensor)
            if prediction > 850:
                prediction = 850
            elif prediction < 100:
                prediction = 100
            data = {"prediction": int(prediction)}
            print(data)
            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

    return "OK"


if __name__ == "__main__":
    app.run(debug=True)
