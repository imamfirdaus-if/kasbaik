import requests

## expected output: 0
# gender = 1
# usia = 53
# pinjaman = 1800000
# tenor = 6
# pemasukan = 1500000
# tanggungan = 1
# pekerjaan = 0
# donasi = 0

## expected output: 1
gender = 1
usia = 31
pinjaman = 1000000
tenor = 16
pemasukan = 5000000
tanggungan = 5
pekerjaan = 2
donasi = 5

# resp = requests.post("https://cobacreditapptoval-bpwlvgokaa-as.a.run.app/", {'input': x})

resp = requests.post("http://127.0.0.1:5000/", {
    'gender': gender,
    'usia': usia,
    'pinjaman': pinjaman,
    'tenor': tenor,
    'pemasukan': pemasukan,
    'tanggungan': tanggungan,
    'pekerjaan': pekerjaan,
    'donasi': donasi
 })

print(resp.json())

