import os
import io
import tensorflow as tf
from tensorflow import keras
import numpy as np

from flask import Flask, request, jsonify

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model = keras.models.load_model("creditapproval_model_v3_02.h5")


def predict(x):
    predictions = model(x)
    pred0 = predictions[0]
    label0 = np.round(pred0)
    return label0


app = Flask(__name__)


@app.route("/", methods=["GET", "POST"])
def index():
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
            prediction = predict(tensor)
            data = {"prediction": int(prediction)}
            print(data)
            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

    return "OK"


if __name__ == "__main__":
    app.run(debug=True)
