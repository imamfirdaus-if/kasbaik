import requests

gender = 1
usia = 31
pinjaman = 1000000
tenor = 16
pemasukan = 5000000
tanggungan = 5
pekerjaan = 2
donasi = 5

# resp = requests.post("https://cobacreditapptoval-bpwlvgokaa-as.a.run.app/", {'input': x})
# resp = requests.post("http://127.0.0.1:5000/", {
#     'gender': gender,
#     'usia': (usia - 20)/80,
#     'pinjaman': (pinjaman - 500000) / 2500000,
#     'tenor': (tenor - 3) / 17,
#     'pemasukan': (pemasukan - 1200000) / 3800000,
#     'tanggungan': (tanggungan / 5),
#     'pekerjaan': (pekerjaan / 4),
#     'donasi': (donasi / 8)
#  })

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
print(resp.json()["prediction"])

