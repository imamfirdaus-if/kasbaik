import requests

## Higest Input
# usiakat = 2
# econkat = 5
# pekerjaankat = 2
# pinjamankekat = 3
# telatharikat = 0
# donasikat = 3

## Lowest Input
# usiakat = 1
# econkat = 0
# pekerjaankat = 1
# pinjamankekat = 1
# telatharikat = 3
# donasikat = 0
## Expected Output: 657.896
usiakat = 2
econkat = 5
pekerjaankat = 1
pinjamankekat = 2
telatharikat = 1
donasikat = 2


resp = requests.post("http://127.0.0.1:5000/", {
    'usiakat': usiakat,
    'econkat': econkat,
    'pekerjaankat': pekerjaankat,
    'pinjamankekat': pinjamankekat,
    'telatharikat': telatharikat,
    'donasikat': donasikat,
 })

print(resp.json())
print(resp.json()["prediction"])

