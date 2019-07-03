package rocks.marcelgross.passbooky

object TestsPasses {
    fun eventTicket(): String = "{\n" +
            "   \"formatVersion\":1,\n" +
            "   \"serialNumber\":\"23500957\",\n" +
            "   \"passTypeIdentifier\":\"passContent.de.cineplex.booking\",\n" +
            "   \"description\":\"Buchungsbestätigung: Glass (31.01.2019 19:45)\",\n" +
            "   \"teamIdentifier\":\"YAUN25EF7Q\",\n" +
            "   \"organizationName\":\"Cineplex Deutschland GmbH & Co. KG\",\n" +
            "   \"foregroundColor\":\"rgb(255,255,255)\",\n" +
            "   \"labelColor\":\"rgb(255,255,255)\",\n" +
            "   \"barcode\":{\n" +
            "      \"format\":\"PKBarcodeFormatQR\",\n" +
            "      \"altText\":\"988475258779\",\n" +
            "      \"message\":\"988475258779\",\n" +
            "      \"messageEncoding\":\"UTF-8\"\n" +
            "   },\n" +
            "   \"barcodes\":[\n" +
            "      {\n" +
            "         \"format\":\"PKBarcodeFormatQR\",\n" +
            "         \"altText\":\"988475258779\",\n" +
            "         \"message\":\"988475258779\",\n" +
            "         \"messageEncoding\":\"UTF-8\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"eventTicket\":{\n" +
            "      \"headerFields\":[\n" +
            "         {\n" +
            "            \"key\":\"performance_date\",\n" +
            "            \"label\":\"31.01.2019\",\n" +
            "            \"value\":\"19:45\"\n" +
            "         }\n" +
            "      ],\n" +
            "      \"primaryFields\":[\n" +
            "         {\n" +
            "            \"key\":\"performance_title\",\n" +
            "            \"label\":\"2 x Ticket\",\n" +
            "            \"value\":\"Glass\"\n" +
            "         }\n" +
            "      ],\n" +
            "      \"secondaryFields\":[\n" +
            "         {\n" +
            "            \"key\":\"auditorium_name\",\n" +
            "            \"label\":\"Admiral Filmpalast Nürnberg\",\n" +
            "            \"value\":\"Admiral 3\"\n" +
            "         }\n" +
            "      ],\n" +
            "      \"auxiliaryFields\":[\n" +
            "         {\n" +
            "            \"key\":\"seat_info\",\n" +
            "            \"label\":\"Reihe/Sitz\",\n" +
            "            \"value\":\"F/12,F/11\"\n" +
            "         }\n" +
            "      ]\n" +
            "   },\n" +
            "   \"relevantDate\":\"2019-01-31T18:45:00Z\"\n" +
            "}"

    fun storeCard() = "{\n" +
            "\t\"formatVersion\" : 1,\n" +
            "\t\"passTypeIdentifier\" : \"passContent.com.kurzdigital.businesscards\",\n" +
            "\t\"serialNumber\" : \"cbr\",\n" +
            "\t\"teamIdentifier\" : \"25MXZ8DDDM\",\n" +
            "\t\"barcodes\" : [\n" +
            "\t\t{\n" +
            "\t\t    \"message\" : \"https://businesscards.kurzdigital.com/cbr-businesscard.pkpass\",\n" +
            "\t\t    \"format\" : \"PKBarcodeFormatQR\",\n" +
            "\t\t    \"messageEncoding\" : \"iso-8859-1\"\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"organizationName\" : \"kurzdigital\",\n" +
            "\t\"description\" : \"Christian Braun\",\n" +
            "\t\"foregroundColor\" : \"rgb(255, 255, 255)\",\n" +
            "\t\"backgroundColor\" : \"rgb(30, 30, 30)\",\n" +
            "\t\"labelColor\" : \"rgb(208, 223, 0)\",\n" +
            "\t\"storeCard\" : {\n" +
            "\t\t\"headerFields\" : [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"header-name\",\n" +
            "\t\t\t\t\"textAlignment\" : \"PKTextAlignmentRight\",\n" +
            "\t\t\t\t\"label\" : \"profession\",\n" +
            "\t\t\t\t\"value\" : \"iOS Developer\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"primaryFields\" : [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"front-academic\",\n" +
            "\t\t\t\t\"label\" : \"\",\n" +
            "\t\t\t\t\"value\" : \"Christian Braun\",\n" +
            "\t\t\t\t\"textAlignment\" : \"PKTextAlignmentLeft\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"secondaryFields\" : [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"front-focus\",\n" +
            "\t\t\t\t\"label\" : \"FOCUS\",\n" +
            "\t\t\t\t\"value\" : \"Swift, VoIP\",\n" +
            "\t\t\t\t\"textAlignment\" : \"PKTextAlignmentLeft\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"backFields\" : [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-name\",\n" +
            "\t\t\t\t\"label\" : \"NAME\",\n" +
            "\t\t\t\t\"value\" : \"Christian Braun\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-role\",\n" +
            "\t\t\t\t\"label\" : \"PROFESSION\",\n" +
            "\t\t\t\t\"value\" : \"iOS Developer\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-website\",\n" +
            "\t\t\t\t\"label\" : \"WEBSITE\",\n" +
            "\t\t\t\t\"attributedValue\" : \"<a href='https://www.kurzdigital.com'>www.kurzdigital.com</a>\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-email\",\n" +
            "\t\t\t\t\"label\" : \"EMAIL\",\n" +
            "\t\t\t\t\"attributedValue\" : \"<a href='mailto:christian.braun@kurzdigital.com'>christian.braun@kurzdigital.com</a>\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-address\",\n" +
            "\t\t\t\t\"label\" : \"ADDRESS\",\n" +
            "\t\t\t\t\"attributedValue\" : \"KURZ Digital Solutions GmbH & Co. KG\\nSchwabacher Str. 106\\nD-90763 Fürth\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-telephone\",\n" +
            "\t\t\t\t\"label\" : \"TELEPHONE\",\n" +
            "\t\t\t\t\"attributedValue\" : \"<a href='tel:+491721502741'>(+49) 172 15 02 741</a>\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"key\" : \"back-twitter\",\n" +
            "\t\t\t\t\"label\" : \"TWITTER\",\n" +
            "\t\t\t\t\"attributedValue\" : \"<a href='https://twitter.com/NorbiBraun'>@NorbiBraun</a>\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}\n"
}