# Exchange Rates against UAE Dirham for VAT related obligations

This repository provides the exchange rates against UAE Dirham for VAT related obligations. Values are fetched daily from https://www.centralbank.ae/en/fx-rates and stored in a json file to be easily consumed by third-party application via a simple http call (see below). 

A new file is generated each day at 06:00 AM UTC (10:00 PM GST) and put in [rates/ directory](https://github.com/paulbares/centralbank-ae-fx-rates/tree/main/rates). The name of the file is the date at which it is generated in [ISO 8601](https://www.iso.org/iso-8601-date-and-time-format.html) format suffixed by `.json` (e.g `2021-09-17.json`).

# Format

- Current date and time expressed according to ISO 8601
- Currencies are in [ISO 4217 format](https://en.wikipedia.org/wiki/ISO_4217)

# Rest API

To fetch the rates of the current day:

```bash
curl https://raw.githubusercontent.com/paulbares/centralbank-ae-fx-rates/main/rates/$(date +'%Y')/$(date +'%Y-%m-%d').json
```

To access historical data, simply replace `$(date +'%Y')/$(date +'%Y-%m-%d')` any hardcoded value.

Result:

```json
{
   "lastUpdated":"2021-09-17T18:00:00",
   "rates":{
      "USD":3.672500,
      "ARS":0.037343,
      "AUD":2.677530,
      "BDT":0.043099,
      "BHD":9.743447,
      "BND":2.725822,
      "BRL":0.687554,
      "BWP":0.330154,
      "BYN":1.478224,
      "CAD":2.894467,
      "CHF":3.952750,
      "CLP":0.004687,
      "CNH":0.568560,
      "CNY":0.568410,
      "COP":0.000959,
      "CZK":0.170410,
      "DKK":0.580550,
      "DZD":0.026816,
      "EGP":0.233620,
      "EUR":4.317033,
      "GBP":5.058540,
      "HKD":0.471898,
      "HUF":0.012240,
      "IDR":0.000258,
      "INR":0.049921,
      "ISK":0.028513,
      "JOD":5.179831,
      "JPY":0.033380,
      "KES":0.033341,
      "KRW":0.003116,
      "KWD":12.202213,
      "KZT":0.008632,
      "LBP":0.002429,
      "LKR":0.018409,
      "MAD":0.409124,
      "MKD":0.070287,
      "MXN":0.184131,
      "MYR":0.880379,
      "NGN":0.008925,
      "NOK":0.425225,
      "NZD":2.589915,
      "OMR":9.538961,
      "PEN":0.894837,
      "PHP":0.073524,
      "PKR":0.021910,
      "PLN":0.941691,
      "QAR":1.008651,
      "RSD":0.036721,
      "RUB":0.050539,
      "SAR":0.979412,
      "SDG":0.008332,
      "SEK":0.424542,
      "SGD":2.726227,
      "THB":0.110318,
      "TND":1.317205,
      "TRY":0.426921,
      "TTD":0.540853,
      "TWD":0.132419,
      "TZS":0.001584,
      "UGX":0.001041,
      "VND":0.000161,
      "YER":0.014675,
      "ZAR":0.250508,
      "ZMW":0.223855,
      "AZN":2.161566,
      "BGN":2.207297,
      "HRK":0.575149,
      "ETB":0.079431,
      "IQD":0.002515,
      "ILS":1.145759,
      "LYD":0.813454,
      "MUR":0.086007,
      "RON":0.872369,
      "SYP":0.001462,
      "TMT":1.050787,
      "UZS":0.000344
   }
}
```

# Disclaimer (from the [Central Bank of the UAE](https://www.centralbank.ae/en/fx-rates))

> The listed foreign currency rates against AED (Arab Emirates Dirham) are published by the Central Bank of UAE only for the calculation of the VAT obligation of UAE business entities to the UAE Federal Tax Authority as per the requirement of Union Law No. 8 on Value Added Tax. The rates are provided by Thomson Reuters and converted into AED by the Central Bank of UAE. These rates are updated Monday to Friday and are based on FX rates prevailing at 6pm UAE time each day. In instances where specific markets are closed due to local holiday, then the relevant rate will be the prevailing rate of the previous day at 6pm. All rates used are the mid rates of the concerned currency pairs.

> Neither The Central Bank of UAE or Thomson Reuters, nor any person or persons acting on their behalf, may be held responsible with respect to any direct (or indirect) loss that may arise from the use of, or reliance on, the published rates.
