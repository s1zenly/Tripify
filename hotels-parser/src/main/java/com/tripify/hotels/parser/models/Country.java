package com.tripify.hotels.parser.models;

import lombok.Getter;

@Getter
public enum Country {

    AFGHANISTAN("AF", "AFG", 4),
    ALBANIA("AL", "ALB", 8),
    ALGERIA("DZ", "DZA", 12),
    ANDORRA("AD", "AND", 20),
    ANGOLA("AO", "AGO", 24),
    ARGENTINA("AR", "ARG", 32),
    ARMENIA("AM", "ARM", 51),
    AUSTRALIA("AU", "AUS", 36),
    AUSTRIA("AT", "AUT", 40),
    AZERBAIJAN("AZ", "AZE", 31),

    BAHAMAS("BS", "BHS", 44),
    BAHRAIN("BH", "BHR", 48),
    BANGLADESH("BD", "BGD", 50),
    BELARUS("BY", "BLR", 112),
    BELGIUM("BE", "BEL", 56),
    BELIZE("BZ", "BLZ", 84),
    BENIN("BJ", "BEN", 204),
    BHUTAN("BT", "BTN", 64),
    BOLIVIA("BO", "BOL", 68),
    BOSNIA_AND_HERZEGOVINA("BA", "BIH", 70),
    BOTSWANA("BW", "BWA", 72),
    BRAZIL("BR", "BRA", 76),
    BRUNEI("BN", "BRN", 96),
    BULGARIA("BG", "BGR", 100),
    BURKINA_FASO("BF", "BFA", 854),
    BURUNDI("BI", "BDI", 108),

    CAMBODIA("KH", "KHM", 116),
    CAMEROON("CM", "CMR", 120),
    CANADA("CA", "CAN", 124),
    CHAD("TD", "TCD", 148),
    CHILE("CL", "CHL", 152),
    CHINA("CN", "CHN", 156),
    COLOMBIA("CO", "COL", 170),
    COSTA_RICA("CR", "CRI", 188),
    CROATIA("HR", "HRV", 191),
    CUBA("CU", "CUB", 192),
    CYPRUS("CY", "CYP", 196),
    CZECHIA("CZ", "CZE", 203),

    DENMARK("DK", "DNK", 208),
    DJIBOUTI("DJ", "DJI", 262),
    DOMINICAN_REPUBLIC("DO", "DOM", 214),

    ECUADOR("EC", "ECU", 218),
    EGYPT("EG", "EGY", 818),
    EL_SALVADOR("SV", "SLV", 222),
    ESTONIA("EE", "EST", 233),
    ETHIOPIA("ET", "ETH", 231),

    FINLAND("FI", "FIN", 246),
    FRANCE("FR", "FRA", 250),

    GEORGIA("GE", "GEO", 268),
    GERMANY("DE", "DEU", 276),
    GHANA("GH", "GHA", 288),
    GREECE("GR", "GRC", 300),

    HUNGARY("HU", "HUN", 348),

    ICELAND("IS", "ISL", 352),
    INDIA("IN", "IND", 356),
    INDONESIA("ID", "IDN", 360),
    IRAN("IR", "IRN", 364),
    IRAQ("IQ", "IRQ", 368),
    IRELAND("IE", "IRL", 372),
    ISRAEL("IL", "ISR", 376),
    ITALY("IT", "ITA", 380),

    JAPAN("JP", "JPN", 392),
    JORDAN("JO", "JOR", 400),

    KAZAKHSTAN("KZ", "KAZ", 398),
    KENYA("KE", "KEN", 404),
    KUWAIT("KW", "KWT", 414),
    KYRGYZSTAN("KG", "KGZ", 417),

    LATVIA("LV", "LVA", 428),
    LEBANON("LB", "LBN", 422),
    LIBYA("LY", "LBY", 434),
    LITHUANIA("LT", "LTU", 440),
    LUXEMBOURG("LU", "LUX", 442),

    MALAYSIA("MY", "MYS", 458),
    MALDIVES("MV", "MDV", 462),
    MALTA("MT", "MLT", 470),
    MEXICO("MX", "MEX", 484),
    MOLDOVA("MD", "MDA", 498),
    MONGOLIA("MN", "MNG", 496),
    MONTENEGRO("ME", "MNE", 499),
    MOROCCO("MA", "MAR", 504),

    NEPAL("NP", "NPL", 524),
    NETHERLANDS("NL", "NLD", 528),
    NEW_ZEALAND("NZ", "NZL", 554),
    NIGERIA("NG", "NGA", 566),
    NORTH_MACEDONIA("MK", "MKD", 807),
    NORWAY("NO", "NOR", 578),

    OMAN("OM", "OMN", 512),

    PAKISTAN("PK", "PAK", 586),
    PANAMA("PA", "PAN", 591),
    PARAGUAY("PY", "PRY", 600),
    PERU("PE", "PER", 604),
    PHILIPPINES("PH", "PHL", 608),
    POLAND("PL", "POL", 616),
    PORTUGAL("PT", "PRT", 620),

    QATAR("QA", "QAT", 634),

    ROMANIA("RO", "ROU", 642),
    RUSSIA("RU", "RUS", 643),

    SAUDI_ARABIA("SA", "SAU", 682),
    SERBIA("RS", "SRB", 688),
    SINGAPORE("SG", "SGP", 702),
    SLOVAKIA("SK", "SVK", 703),
    SLOVENIA("SI", "SVN", 705),
    SOUTH_AFRICA("ZA", "ZAF", 710),
    SOUTH_KOREA("KR", "KOR", 410),
    SPAIN("ES", "ESP", 724),
    SRI_LANKA("LK", "LKA", 144),
    SWEDEN("SE", "SWE", 752),
    SWITZERLAND("CH", "CHE", 756),

    THAILAND("TH", "THA", 764),
    TUNISIA("TN", "TUN", 788),
    TURKEY("TR", "TUR", 792),

    UKRAINE("UA", "UKR", 804),
    UNITED_ARAB_EMIRATES("AE", "ARE", 784),
    UNITED_KINGDOM("GB", "GBR", 826),
    UNITED_STATES("US", "USA", 840),

    UZBEKISTAN("UZ", "UZB", 860),

    VIETNAM("VN", "VNM", 704),

    YEMEN("YE", "YEM", 887),

    ZAMBIA("ZM", "ZMB", 894),
    ZIMBABWE("ZW", "ZWE", 716);

    private final String alpha2;
    private final String alpha3;
    private final int numeric;

    Country(String alpha2, String alpha3, int numeric) {
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.numeric = numeric;
    }

    public static Country fromAlpha2(String code) {
        for (Country c : values()) {
            if (c.alpha2.equalsIgnoreCase(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown country code: " + code);
    }
}
