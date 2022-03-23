package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum Country{
    AFGHANISTAN("af", null),
    ALBANIA("al", null),
    ALGERIA("dz", null),
    AMERICAN_SAMOA("as", null),
    ANDORRA("ad", null),
    ANGOLA("ao", null),
    ANGUILLA("ai", null),
    ANTARCTICA("aq", null),
    ANTIGUA_AND_BARBUDA("ag", null),
    ARGENTINA("ar", Set.of("argentine")),
    ARMENIA("am", null),
    ARUBA("aw", null),
    AUSTRALIA("au", Set.of("australian")),
    AUSTRIA("at", Set.of("austrian")),
    AZERBAIJAN("az", null),
    BAHAMAS("bs", null),
    BAHRAIN("bh", null),
    BANGLADESH("bd", null),
    BARBADOS("bb", null),
    BELARUS("by", null),
    BELGIUM("be", Set.of("belgium", "belgian")),
    BELIZE("bz", null),
    BENIN("bj", null),
    BERMUDA("bm", null),
    BHUTAN("bt", null),
    BOLIVIA("bo", null),
    BOSNIA_AND_HERZEGOVINA("ba", null),
    BOTSWANA("bw", null),
    BRAZIL("br", Set.of("brazilian")),
    BRITISH_INDIAN_OCEAN_TERRITORY("io", null),
    BRITISH_VIRGIN_ISLANDS("vg", null),
    BRUNEI("bn", null),
    BULGARIA("bg", null),
    BURKINA_FASO("bf", null),
    BURUNDI("bi", null),
    CAMBODIA("kh", null),
    CAMEROON("cm", null),
    CANADA("ca", Set.of("canadian")),
    CAPE_VERDE("cv", null),
    CAYMAN_ISLANDS("ky", null),
    CENTRAL_AFRICAN_REPUBLIC("cf", null),
    CHAD("td", null),
    CHILE("cl", Set.of("chilean")),
    CHINA("cn", Set.of("chinese")),
    CHRISTMAS_ISLAND("cx", null),
    COCOS_ISLANDS("cc", null),
    COLOMBIA("co", Set.of("colombian")),
    COMOROS("km", null),
    COOK_ISLANDS("ck", null),
    COSTA_RICA("cr", null),
    CROATIA("hr", null),
    CUBA("cu", null),
    CURACAO("cw", null),
    CYPRUS("cy", null),
    CZECH_REPUBLIC("cz", Set.of("czech")),
    DEMOCRATIC_REPUBLIC_OF_THE_CONGO("cd", null),
    DENMARK("dk", Set.of("danish")),
    DJIBOUTI("dj", null),
    DOMINICA("dm", null),
    DOMINICAN_REPUBLIC("do", null),
    EAST_GERMANY("dd", Set.of("east german")),
    EAST_TIMOR("tl", null),
    ECUADOR("ec", null),
    EGYPT("eg", null),
    EL_SALVADOR("sv", null),
    EQUATORIAL_GUINEA("gq", null),
    ERITREA("er", null),
    ESTONIA("ee", null),
    ETHIOPIA("et", null),
    FALKLAND_ISLANDS("fk", null),
    FAROE_ISLANDS("fo", null),
    FIJI("fj", null),
    FINLAND("fi", Set.of("finnish")),
    FRANCE("fr",  Set.of("french")),
    FRENCH_POLYNESIA("pf", null),
    GABON("ga", null),
    GAMBIA("gm", null),
    GEORGIA("ge", null),
    GERMANY("de",  Set.of("german")),
    GHANA("gh", null),
    GIBRALTAR("gi", null),
    GREECE("gr", null),
    GREENLAND("gl", null),
    GRENADA("gd", null),
    GUAM("gu", null),
    GUATEMALA("gt", null),
    GUERNSEY("gg", null),
    GUINEA("gn", null),
    GUINEA_BISSAU("gw", null),
    GUYANA("gy", null),
    HAITI("ht", null),
    HONDURAS("hn", null),
    HONG_KONG("hk", Set.of("hong kong")),
    HUNGARY("hu", Set.of("hungarian")),
    ICELAND("is", null),
    INDIA("in", Set.of("indian")),
    INDONESIA("id", Set.of("indonesian")),
    IRAN("ir", null),
    IRAQ("iq", null),
    IRELAND("ie", Set.of("irish")),
    ISLE_OF_MAN("im", null),
    ISRAEL("il", null),
    ITALY("it",  Set.of("italian")),
    IVORY_COAST("ci", null),
    JAMAICA("jm", null),
    JAPAN("jp", Set.of("japanese")),
    JERSEY("je", null),
    JORDAN("jo", null),
    KAZAKHSTAN("kz", null),
    KENYA("ke", null),
    KIRIBATI("ki", null),
    KOSOVO("xk", null),
    KUWAIT("kw", null),
    KYRGYZSTAN("kg", null),
    LAOS("la", null),
    LATVIA("lv", null),
    LEBANON("lb", null),
    LESOTHO("ls", null),
    LIBERIA("lr", null),
    LIBYA("ly", null),
    LIECHTENSTEIN("li", Set.of("liechtensteiner")),
    LITHUANIA("lt", null),
    LUXEMBOURG("lu", null),
    MACAU("mo", null),
    MACEDONIA("mk", null),
    MADAGASCAR("mg", null),
    MALAWI("mw", null),
    MALAYSIA("my",  Set.of("malaysian")),
    MALDIVES("mv", null),
    MALI("ml", null),
    MALTA("mt", null),
    MARSHALL_ISLANDS("mh", null),
    MAURITANIA("mr", null),
    MAURITIUS("mu", null),
    MAYOTTE("yt", null),
    MEXICO("mx", Set.of("mexican")),
    MICRONESIA("fm", null),
    MOLDOVA("md", null),
    MONACO("mc", Set.of("monegasque")),
    MONGOLIA("mn", null),
    MONTENEGRO("me", null),
    MONTSERRAT("ms", null),
    MOROCCO("ma", null),
    MOZAMBIQUE("mz", null),
    MYANMAR("mm", null),
    NAMIBIA("na", null),
    NAURU("nr", null),
    NEPAL("np", null),
    NETHERLANDS("nl",  Set.of("dutch")),
    NETHERLANDS_ANTILLES("an", null),
    NEW_CALEDONIA("nc", null),
    NEW_ZEALAND("nz",  Set.of("new zealand", "new zealander")),
    NICARAGUA("ni", null),
    NIGER("ne", null),
    NIGERIA("ng", null),
    NIUE("nu", null),
    NORTH_KOREA("kp", null),
    NORTHERN_MARIANA_ISLANDS("mp", null),
    NORWAY("no", null),
    OMAN("om", null),
    PAKISTAN("pk", null),
    PALAU("pw", null),
    PALESTINE("ps", null),
    PANAMA("pa", null),
    PAPUA_NEW_GUINEA("pg", null),
    PARAGUAY("py", null),
    PERU("pe", null),
    PHILIPPINES("ph", null),
    PITCAIRN("pn", null),
    POLAND("pl", Set.of("polish")),
    PORTUGAL("pt", Set.of("portuguese")),
    PUERTO_RICO("pr", null),
    QATAR("qa", null),
    REPUBLIC_OF_THE_CONGO("cg", null),
    REUNION("re", null),
    RHODESIA("rh", Set.of("rhodesian")),
    ROMANIA("ro", null),
    RUSSIA("ru", Set.of("russian")),
    RWANDA("rw", null),
    SAINT_BARTHELEMY("bl", null),
    SAINT_HELENA("sh", null),
    SAINT_KITTS_AND_NEVIS("kn", null),
    SAINT_LUCIA("lc", null),
    SAINT_MARTIN("mf", null),
    SAINT_PIERRE_AND_MIQUELON("pm", null),
    SAINT_VINCENT_AND_THE_GRENADINES("vc", null),
    SAMOA("ws", null),
    SAN_MARINO("sm", null),
    SAO_TOME_AND_PRINCIPE("st", null),
    SAUDI_ARABIA("sa", null),
    SENEGAL("sn", null),
    SERBIA("rs", null),
    SEYCHELLES("sc", null),
    SIERRA_LEONE("sl", null),
    SINGAPORE("sg", null),
    SINT_MAARTEN("sx", null),
    SLOVAKIA("sk", null),
    SLOVENIA("si", null),
    SOLOMON_ISLANDS("sb", null),
    SOMALIA("so", null),
    SOUTH_AFRICA("za", Set.of("south african")),
    SOUTH_KOREA("kr", null),
    SOUTH_SUDAN("ss", null),
    SPAIN("es", Set.of("spanish")),
    SRI_LANKA("lk", null),
    SUDAN("sd", null),
    SURINAME("sr", null),
    SVALBARD_AND_JAN_MAYEN("sj", null),
    SWAZILAND("sz", null),
    SWEDEN("se", Set.of("swedish")),
    SWITZERLAND("ch",  Set.of("swiss")),
    SYRIA("sy", null),
    TAIWAN("tw", null),
    TAJIKISTAN("tj", null),
    TANZANIA("tz", null),
    THAILAND("th", Set.of("thai")),
    TOGO("tg", null),
    TOKELAU("tk", null),
    TONGA("to", null),
    TRINIDAD_AND_TOBAGO("tt", null),
    TUNISIA("tn", null),
    TURKEY("tr", null),
    TURKMENISTAN("tm", null),
    TURKS_AND_CAICOS_ISLANDS("tc", null),
    TUVALU("tv", null),
    US_VIRGIN_ISLANDS("vi", null),
    UGANDA("ug", null),
    UKRAINE("ua", null),
    UNITED_ARAB_EMIRATES("ae", null),
    UNITED_KINGDOM("gb", Set.of("british")),
    UNITED_STATES("us", Set.of("american")),
    URUGUAY("uy", Set.of("uruguayan")),
    UZBEKISTAN("uz", null),
    VANUATU("vu", null),
    VATICAN("va", null),
    VENEZUELA("ve", Set.of("venezuelan")),
    VIETNAM("vn", null),
    WALLIS_AND_FUTUNA("wf", null),
    WESTERN_SAHARA("eh", null),
    YEMEN("ye", null),
    ZAMBIA("zm", null),
    ZIMBABWE("zw", null);

    private final String code;
    private final Set<String> nationalityKeywords;

    public static Country fromNationality(final String nationality) {
         return Arrays.stream(values())
             .filter(country -> country.nationalityKeywords.contains(nationality))
             .findFirst()
             .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the nationality: %s to a valid country", nationality)));
    }
}
