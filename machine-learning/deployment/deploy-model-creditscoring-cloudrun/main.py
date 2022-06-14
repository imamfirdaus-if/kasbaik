import os
import io
import tensorflow as tf
from tensorflow import keras
import numpy as np

from flask import Flask, request, jsonify

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model = keras.models.load_model("Credit Scoring - V4.h5")


def predict(x):
    predictions = model.predict(x)
    # pred0 = predictions[0]
    # label0 = np.round(pred0)
    return predictions


app = Flask(__name__)


@app.route("/", methods=["GET", "POST"])
def index():
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
            prediction = predict(tensor)
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
