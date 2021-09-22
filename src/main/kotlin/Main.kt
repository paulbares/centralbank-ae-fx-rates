import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import org.jsoup.Jsoup
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.RuntimeException
import java.math.BigDecimal
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import kotlin.io.path.Path

val nameToCode = hashMapOf(
    "Emirati Dirham" to "AED",
    "Afghan Afghani" to "AFN",
    "Albanian Lek" to "ALL",
    "Armenian Dram" to "AMD",
    "Dutch Guilder" to "ANG",
    "Angolan Kwanza" to "AOA",
    "Argentine Peso" to "ARS",
    "Australian Dollar" to "AUD",
    "Aruban or Dutch Guilder" to "AWG",
    "Azerbaijan Manat" to "AZN",
    "Bosnian Convertible Mark" to "BAM",
    "Barbadian or Bajan Dollar" to "BBD",
    "Bangladesh Taka" to "BDT",
    "Bulgarian Lev" to "BGN",
    "Bahrani Dinar" to "BHD",
    "Burundian Franc" to "BIF",
    "Bermudian Dollar" to "BMD",
    "Brunei Dollar" to "BND",
    "Bolivian Bolíviano" to "BOB",
    "Brazilian Real" to "BRL",
    "Bahamian Dollar" to "BSD",
    "Bhutanese Ngultrum" to "BTN",
    "Botswana Pula" to "BWP",
    "Belarus Rouble" to "BYN",
    "Belizean Dollar" to "BZD",
    "Canadian Dollar" to "CAD",
    "Congolese Franc" to "CDF",
    "Swiss Franc" to "CHF",
    "Chilean Peso" to "CLP",
    "Chinese Yuan" to "CNY",
    "Chinese Yuan - Offshore" to "CNH",
    "Colombian Peso" to "COP",
    "Costa Rican Colon" to "CRC",
    "Cuban Convertible Peso" to "CUC",
    "Cuban Peso" to "CUP",
    "Cape Verdean Escudo" to "CVE",
    "Czech Koruna" to "CZK",
    "Djiboutian Franc" to "DJF",
    "Danish Krone" to "DKK",
    "Dominican Peso" to "DOP",
    "Algerian Dinar" to "DZD",
    "Estonian Kroon" to "EEK",
    "Egypt Pound" to "EGP",
    "Eritrean Nakfa" to "ERN",
    "Ethiopian Birr" to "ETB",
    "Euro" to "EUR",
    "Fijian Dollar" to "FJD",
    "Falkland Island Pound" to "FKP",
    "GB Pound" to "GBP",
    "Georgian Lari" to "GEL",
    "Guernsey Pound" to "GGP",
    "Ghanaian Cedi" to "GHS",
    "Gibraltar Pound" to "GIP",
    "Gambian Dalasi" to "GMD",
    "Guinean Franc" to "GNF",
    "Guatemalan Quetzal" to "GTQ",
    "Guyanese Dollar" to "GYD",
    "HongKong Dollar" to "HKD",
    "Honduran Lempira" to "HNL",
    "Croatian Kuna" to "HRK",
    "Haitian Gourde" to "HTG",
    "Hungarian Forint" to "HUF",
    "Indonesia Rupiah" to "IDR",
    "Israeli new Shekel" to "ILS",
    "Isle of Man Pound" to "IMP",
    "Indian Rupee" to "INR",
    "Iraqi Dinar" to "IQD",
    "Iranian Rial" to "IRR",
    "Iceland Krona" to "ISK",
    "Jersey Pound" to "JEP",
    "Jamaican Dollar" to "JMD",
    "Jordan Dinar" to "JOD",
    "Japanese Yen" to "JPY",
    "Kenya Shilling" to "KES",
    "Kyrgyzstani Som" to "KGS",
    "Cambodian Riel" to "KHR",
    "Comorian Franc" to "KMF",
    "North Korean Won" to "KPW",
    "Korean Won" to "KRW",
    "Kuwaiti Dinar" to "KWD",
    "Caymanian Dollar" to "KYD",
    "Kazakhstan Tenge" to "KZT",
    "Lao Kip" to "LAK",
    "Lebanon Pound" to "LBP",
    "Sri Lanka Rupee" to "LKR",
    "Liberian Dollar" to "LRD",
    "Basotho Loti" to "LSL",
    "Lithuanian Litas" to "LTL",
    "Latvian Lat" to "LVL",
    "Libyan Dinar" to "LYD",
    "Moroccan Dirham" to "MAD",
    "Moldovan Leu" to "MDL",
    "Malagasy Ariary" to "MGA",
    "Macedonia Denar" to "MKD",
    "Burmese Kyat" to "MMK",
    "Mongolian Tughrik" to "MNT",
    "Macau Pataca" to "MOP",
    "Mauritanian Ouguiya" to "MRU",
    "Mauritian Rupee" to "MUR",
    "Maldivian Rufiyaa" to "MVR",
    "Malawian Kwacha" to "MWK",
    "Mexican Peso" to "MXN",
    "Malaysia Ringgit" to "MYR",
    "Mozambican Metical" to "MZN",
    "Namibian Dollar" to "NAD",
    "Nigerian Naira" to "NGN",
    "Nicaraguan Cordoba" to "NIO",
    "Norwegian Krone" to "NOK",
    "Nepalese Rupee" to "NPR",
    "NewZealand Dollar" to "NZD",
    "Omani Rial" to "OMR",
    "Panamanian Balboa" to "PAB",
    "Peru Sol" to "PEN",
    "Papua New Guinean Kina" to "PGK",
    "Philippine Piso" to "PHP",
    "Pakistan Rupee" to "PKR",
    "Polish Zloty" to "PLN",
    "Paraguayan Guarani" to "PYG",
    "Qatari Riyal" to "QAR",
    "Romanian Leu" to "RON",
    "Serbian Dinar" to "RSD",
    "Russia Rouble" to "RUB",
    "Rwandan Franc" to "RWF",
    "Saudi Riyal" to "SAR",
    "Solomon Islander Dollar" to "SBD",
    "Seychellois Rupee" to "SCR",
    "Sudanese Pound" to "SDG",
    "Swedish Krona" to "SEK",
    "Singapore Dollar" to "SGD",
    "Saint Helenian Pound" to "SHP",
    "Sierra Leonean Leone" to "SLL",
    "Somali Shilling" to "SOS",
    "Seborgan Luigino" to "SPL",
    "Surinamese Dollar" to "SRD",
    "Sao Tomean Dobra" to "STN",
    "Salvadoran Colon" to "SVC",
    "Syrian Pound" to "SYP",
    "Swazi Lilangeni" to "SZL",
    "Thai Baht" to "THB",
    "Tajikistani Somoni" to "TJS",
    "Turkmen Manat" to "TMT",
    "Tunisian Dinar" to "TND",
    "Tongan Pa'anga" to "TOP",
    "Turkish Lira" to "TRY",
    "Trin Tob Dollar" to "TTD",
    "Tuvaluan Dollar" to "TVD",
    "Taiwan Dollar" to "TWD",
    "Tanzania Shilling" to "TZS",
    "Ukrainian Hryvnia" to "UAH",
    "Uganda Shilling" to "UGX",
    "US Dollar" to "USD",
    "Uruguayan Peso" to "UYU",
    "Uzbekistani Som" to "UZS",
    "Venezuelan Bolívar" to "VEF",
    "Venezuelan Bolívar" to "VES",
    "Vietnam Dong" to "VND",
    "Ni-Vanuatu Vatu" to "VUV",
    "Samoan Tala" to "WST",
    "Central African CFA Franc BEAC" to "XAF",
    "Silver Ounce" to "XAG",
    "Gold Ounce" to "XAU",
    "Bitcoin" to "XBT",
    "East Caribbean Dollar" to "XCD",
    "IMF Special Drawing Rights" to "XDR",
    "CFA Franc" to "XOF",
    "Palladium Ounce" to "XPD",
    "CFP Franc" to "XPF",
    "Platinum Ounce" to "XPT",
    "Yemen Rial" to "YER",
    "South Africa Rand" to "ZAR",
    "Zambian Kwacha" to "ZMK",
    "Zambian Kwacha" to "ZMW",
    "Zimbabwean Dollar" to "ZWD"
)

data class RatesReport(val lastUpdated: LocalDateTime, val rates: Map<String?, BigDecimal?>)

fun main() {
    val doc = Jsoup.connect("https://www.centralbank.ae/en/fx-rates").get()

    val lastUpdated = doc.select(".pb-4 small span").text()
    val lastUpdatedDateTime = LocalDateTime.from(DateTimeFormatter.ofPattern("dd MMM yyyy h:mma").parse(lastUpdated))

    var count = 0
    val rateByCurrency = doc
        .select("#ratesDateTable tbody tr")
        .associate {
            val row = it.select("td")
            val first = row[0].text()
            val code = nameToCode.firstNotNullOfOrNull { e ->
                if (e.key.equals(first, true)) {
                    e.value
                } else {
                    null
                }
            }

            if (code == null) {
                count++
                Pair(first, null)
            } else {
                Pair(code, BigDecimal(row[1].text()))
            }
        }
    if (count > 0) {
        throw RuntimeException("Could not find mapping for $count currencies: ${rateByCurrency.filter { it.value == null }.keys}")
    } else {
        val now = LocalDate.now()
        val parentDir = Files.createDirectories(Path("rates", "${now.year}"))
        BufferedWriter(FileWriter(File(parentDir.toFile(), "$now.json")))
            .use { it.write(JSON.toJSONString(RatesReport(lastUpdatedDateTime, rateByCurrency))) }
        println("File successfully generated")
    }
}