package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Getter
public enum Country{
    AFGHANISTAN("af", List.of("afghanistan"), emptyList()),
    ALBANIA("al", List.of("albania"), emptyList()),
    ALGERIA("dz", List.of("algeria"), emptyList()),
    AMERICAN_SAMOA("as", List.of("american_samoa"), emptyList()),
    ANDORRA("ad", List.of("andorra"), emptyList()),
    ANGOLA("ao", List.of("angola"), emptyList()),
    ANGUILLA("ai", List.of("anguilla"), emptyList()),
    ANTARCTICA("aq", List.of("antarctica"), emptyList()),
    ANTIGUA_AND_BARBUDA("ag", List.of("antigua_and_barbuda"), emptyList()),
    ARGENTINA("ar", List.of("argentina"), List.of("argentine")),
    ARMENIA("am", List.of("armenia"), emptyList()),
    ARUBA("aw", List.of("aruba"), emptyList()),
    AUSTRALIA("au", List.of("australia"), List.of("australian")),
    AUSTRIA("at", List.of("austria"), List.of("austrian")),
    AZERBAIJAN("az", List.of("azerbaijan"), emptyList()),
    BAHAMAS("bs", List.of("bahamas"), emptyList()),
    BAHRAIN("bh", List.of("bahrain"), emptyList()),
    BANGLADESH("bd", List.of("bangladesh"), emptyList()),
    BARBADOS("bb", List.of("barbados"), emptyList()),
    BELARUS("by", List.of("belarus"), emptyList()),
    BELGIUM("be", List.of("belgium"), List.of("belgium", "belgian")),
    BELIZE("bz", List.of("belize"), emptyList()),
    BENIN("bj", List.of("benin"), emptyList()),
    BERMUDA("bm", List.of("bermuda"), emptyList()),
    BHUTAN("bt", List.of("bhutan"), emptyList()),
    BOLIVIA("bo", List.of("bolivia"), emptyList()),
    BOSNIA_AND_HERZEGOVINA("ba", List.of("bosnia_and_herzegovina"), emptyList()),
    BOTSWANA("bw", List.of("botswana"), emptyList()),
    BRAZIL("br", List.of("brazil"), List.of("brazilian")),
    BRITISH_INDIAN_OCEAN_TERRITORY("io", List.of("british_indian_ocean_territory"), emptyList()),
    BRITISH_VIRGIN_ISLANDS("vg", List.of("british_virgin_islands"), emptyList()),
    BRUNEI("bn", List.of("brunei"), emptyList()),
    BULGARIA("bg", List.of("bulgaria"), emptyList()),
    BURKINA_FASO("bf", List.of("burkina_faso"), emptyList()),
    BURUNDI("bi", List.of("burundi"), emptyList()),
    CAMBODIA("kh", List.of("cambodia"), emptyList()),
    CAMEROON("cm", List.of("cameroon"), emptyList()),
    CANADA("ca", List.of("canada"), List.of("canadian")),
    CAPE_VERDE("cv", List.of("cape_verde"), emptyList()),
    CAYMAN_ISLANDS("ky", List.of("cayman_islands"), emptyList()),
    CENTRAL_AFRICAN_REPUBLIC("cf", List.of("central_african_republic"), emptyList()),
    CHAD("td", List.of("chad"), emptyList()),
    CHILE("cl", List.of("chile"), List.of("chilean")),
    CHINA("cn", List.of("china"), List.of("chinese")),
    CHRISTMAS_ISLAND("cx", List.of("christmas_island"), emptyList()),
    COCOS_ISLANDS("cc", List.of("cocos_islands"), emptyList()),
    COLOMBIA("co", List.of("colombia"), List.of("colombian")),
    COMOROS("km", List.of("comoros"), emptyList()),
    COOK_ISLANDS("ck", List.of("cook_islands"), emptyList()),
    COSTA_RICA("cr", List.of("costa_rica"), emptyList()),
    CROATIA("hr", List.of("croatia"), emptyList()),
    CUBA("cu", List.of("cuba"), emptyList()),
    CURACAO("cw", List.of("curacao"), emptyList()),
    CYPRUS("cy", List.of("cyprus"), emptyList()),
    CZECH_REPUBLIC("cz", List.of("czech_republic"), List.of("czech")),
    DEMOCRATIC_REPUBLIC_OF_THE_CONGO("cd", List.of("democratic_republic_of_the_congo"), emptyList()),
    DENMARK("dk", List.of("denmark"), List.of("danish")),
    DJIBOUTI("dj", List.of("djibouti"), emptyList()),
    DOMINICA("dm", List.of("dominica"), emptyList()),
    DOMINICAN_REPUBLIC("do", List.of("dominican_republic"), emptyList()),
    EAST_GERMANY("dd", List.of("east_germany"), List.of("east german")),
    EAST_TIMOR("tl", List.of("east_timor"), emptyList()),
    ECUADOR("ec", List.of("ecuador"), emptyList()),
    EGYPT("eg", List.of("egypt"), emptyList()),
    EL_SALVADOR("sv", List.of("el_salvador"), emptyList()),
    EQUATORIAL_GUINEA("gq", List.of("equatorial_guinea"), emptyList()),
    ERITREA("er", List.of("eritrea"), emptyList()),
    ESTONIA("ee", List.of("estonia"), emptyList()),
    ETHIOPIA("et", List.of("ethiopia"), emptyList()),
    FALKLAND_ISLANDS("fk", List.of("falkland_islands"), emptyList()),
    FAROE_ISLANDS("fo", List.of("faroe_islands"), emptyList()),
    FIJI("fj", List.of("fiji"), emptyList()),
    FINLAND("fi", List.of("finland"), List.of("finnish")),
    FRANCE("fr", List.of("france"),  List.of("french")),
    FRENCH_POLYNESIA("pf", List.of("french_polynesia"), emptyList()),
    GABON("ga", List.of("gabon"), emptyList()),
    GAMBIA("gm", List.of("gambia"), emptyList()),
    GEORGIA("ge", List.of("georgia"), emptyList()),
    GERMANY("de", List.of("germany"),  List.of("german")),
    GHANA("gh", List.of("ghana"), emptyList()),
    GIBRALTAR("gi", List.of("gibraltar"), emptyList()),
    GREECE("gr", List.of("greece"), emptyList()),
    GREENLAND("gl", List.of("greenland"), emptyList()),
    GRENADA("gd", List.of("grenada"), emptyList()),
    GUAM("gu", List.of("guam"), emptyList()),
    GUATEMALA("gt", List.of("guatemala"), emptyList()),
    GUERNSEY("gg", List.of("guernsey"), emptyList()),
    GUINEA("gn", List.of("guinea"), emptyList()),
    GUINEA_BISSAU("gw", List.of("guinea_bissau"), emptyList()),
    GUYANA("gy", List.of("guyana"), emptyList()),
    HAITI("ht", List.of("haiti"), emptyList()),
    HONDURAS("hn", List.of("honduras"), emptyList()),
    HONG_KONG("hk", List.of("hong_kong"), List.of("hong kong")),
    HUNGARY("hu", List.of("hungary"), List.of("hungarian")),
    ICELAND("is", List.of("iceland"), emptyList()),
    INDIA("in", List.of("india"), List.of("indian")),
    INDONESIA("id", List.of("indonesia"), List.of("indonesian")),
    IRAN("ir", List.of("iran"), emptyList()),
    IRAQ("iq", List.of("iraq"), emptyList()),
    IRELAND("ie", List.of("ireland"), List.of("irish")),
    ISLE_OF_MAN("im", List.of("isle_of_man"), emptyList()),
    ISRAEL("il", List.of("israel"), emptyList()),
    ITALY("it", List.of("italy"),  List.of("italian")),
    IVORY_COAST("ci", List.of("ivory_coast"), emptyList()),
    JAMAICA("jm", List.of("jamaica"), emptyList()),
    JAPAN("jp", List.of("japan"), List.of("japanese")),
    JERSEY("je", List.of("jersey"), emptyList()),
    JORDAN("jo", List.of("jordan"), emptyList()),
    KAZAKHSTAN("kz", List.of("kazakhstan"), emptyList()),
    KENYA("ke", List.of("kenya"), emptyList()),
    KIRIBATI("ki", List.of("kiribati"), emptyList()),
    KOSOVO("xk", List.of("kosovo"), emptyList()),
    KUWAIT("kw", List.of("kuwait"), emptyList()),
    KYRGYZSTAN("kg", List.of("kyrgyzstan"), emptyList()),
    LAOS("la", List.of("laos"), emptyList()),
    LATVIA("lv", List.of("latvia"), emptyList()),
    LEBANON("lb", List.of("lebanon"), emptyList()),
    LESOTHO("ls", List.of("lesotho"), emptyList()),
    LIBERIA("lr", List.of("liberia"), emptyList()),
    LIBYA("ly", List.of("libya"), emptyList()),
    LIECHTENSTEIN("li", List.of("liechtenstein"), List.of("liechtensteiner")),
    LITHUANIA("lt", List.of("lithuania"), emptyList()),
    LUXEMBOURG("lu", List.of("luxembourg"), emptyList()),
    MACAU("mo", List.of("macau"), emptyList()),
    MACEDONIA("mk", List.of("macedonia"), emptyList()),
    MADAGASCAR("mg", List.of("madagascar"), emptyList()),
    MALAWI("mw", List.of("malawi"), emptyList()),
    MALAYSIA("my", List.of("malaysia"),  List.of("malaysian")),
    MALDIVES("mv", List.of("maldives"), emptyList()),
    MALI("ml", List.of("mali"), emptyList()),
    MALTA("mt", List.of("malta"), emptyList()),
    MARSHALL_ISLANDS("mh", List.of("marshall_islands"), emptyList()),
    MAURITANIA("mr", List.of("mauritania"), emptyList()),
    MAURITIUS("mu", List.of("mauritius"), emptyList()),
    MAYOTTE("yt", List.of("mayotte"), emptyList()),
    MEXICO("mx", List.of("mexico"), List.of("mexican")),
    MICRONESIA("fm", List.of("micronesia"), emptyList()),
    MOLDOVA("md", List.of("moldova"), emptyList()),
    MONACO("mc", List.of("monaco"), List.of("monegasque")),
    MONGOLIA("mn", List.of("mongolia"), emptyList()),
    MONTENEGRO("me", List.of("montenegro"), emptyList()),
    MONTSERRAT("ms", List.of("montserrat"), emptyList()),
    MOROCCO("ma", List.of("morocco"), emptyList()),
    MOZAMBIQUE("mz", List.of("mozambique"), emptyList()),
    MYANMAR("mm", List.of("myanmar"), emptyList()),
    NAMIBIA("na", List.of("namibia"), emptyList()),
    NAURU("nr", List.of("nauru"), emptyList()),
    NEPAL("np", List.of("nepal"), emptyList()),
    NETHERLANDS("nl", List.of("netherlands"),  List.of("dutch")),
    NETHERLANDS_ANTILLES("an", List.of("netherlands_antilles"), emptyList()),
    NEW_CALEDONIA("nc", List.of("new_caledonia"), emptyList()),
    NEW_ZEALAND("nz", List.of("new_zealand"),  List.of("new zealand", "new zealander")),
    NICARAGUA("ni", List.of("nicaragua"), emptyList()),
    NIGER("ne", List.of("niger"), emptyList()),
    NIGERIA("ng", List.of("nigeria"), emptyList()),
    NIUE("nu", List.of("niue"), emptyList()),
    NORTH_KOREA("kp", List.of("north_korea"), emptyList()),
    NORTHERN_MARIANA_ISLANDS("mp", List.of("northern_mariana_islands"), emptyList()),
    NORWAY("no", List.of("norway"), emptyList()),
    OMAN("om", List.of("oman"), emptyList()),
    PAKISTAN("pk", List.of("pakistan"), emptyList()),
    PALAU("pw", List.of("palau"), emptyList()),
    PALESTINE("ps", List.of("palestine"), emptyList()),
    PANAMA("pa", List.of("panama"), emptyList()),
    PAPUA_NEW_GUINEA("pg", List.of("papua_new_guinea"), emptyList()),
    PARAGUAY("py", List.of("paraguay"), emptyList()),
    PERU("pe", List.of("peru"), emptyList()),
    PHILIPPINES("ph", List.of("philippines"), emptyList()),
    PITCAIRN("pn", List.of("pitcairn"), emptyList()),
    POLAND("pl", List.of("poland"), List.of("polish")),
    PORTUGAL("pt", List.of("portugal"), List.of("portuguese")),
    PUERTO_RICO("pr", List.of("puerto_rico"), emptyList()),
    QATAR("qa", List.of("qatar"), emptyList()),
    REPUBLIC_OF_THE_CONGO("cg", List.of("republic_of_the_congo"), emptyList()),
    REUNION("re", List.of("reunion"), emptyList()),
    RHODESIA("rh", List.of("rhodesia"), List.of("rhodesian")),
    ROMANIA("ro", List.of("romania"), emptyList()),
    RUSSIA("ru", List.of("russia"), List.of("russian")),
    RWANDA("rw", List.of("rwanda"), emptyList()),
    SAINT_BARTHELEMY("bl", List.of("saint_barthelemy"), emptyList()),
    SAINT_HELENA("sh", List.of("saint_helena"), emptyList()),
    SAINT_KITTS_AND_NEVIS("kn", List.of("saint_kitts_and_nevis"), emptyList()),
    SAINT_LUCIA("lc", List.of("saint_lucia"), emptyList()),
    SAINT_MARTIN("mf", List.of("saint_martin"), emptyList()),
    SAINT_PIERRE_AND_MIQUELON("pm", List.of("saint_pierre_and_miquelon"), emptyList()),
    SAINT_VINCENT_AND_THE_GRENADINES("vc", List.of("saint_vincent_and_the_grenadines"), emptyList()),
    SAMOA("ws", List.of("samoa"), emptyList()),
    SAN_MARINO("sm", List.of("san_marino"), emptyList()),
    SAO_TOME_AND_PRINCIPE("st", List.of("sao_tome_and_principe"), emptyList()),
    SAUDI_ARABIA("sa", List.of("saudi arabia"), emptyList()),
    SENEGAL("sn", List.of("senegal"), emptyList()),
    SERBIA("rs", List.of("serbia"), emptyList()),
    SEYCHELLES("sc", List.of("seychelles"), emptyList()),
    SIERRA_LEONE("sl", List.of("sierra_leone"), emptyList()),
    SINGAPORE("sg", List.of("singapore"), emptyList()),
    SINT_MAARTEN("sx", List.of("sint_maarten"), emptyList()),
    SLOVAKIA("sk", List.of("slovakia"), emptyList()),
    SLOVENIA("si", List.of("slovenia"), emptyList()),
    SOLOMON_ISLANDS("sb", List.of("solomon_islands"), emptyList()),
    SOMALIA("so", List.of("somalia"), emptyList()),
    SOUTH_AFRICA("za", List.of("south africa"), List.of("south african")),
    SOUTH_KOREA("kr", List.of("korea"), emptyList()),
    SOUTH_SUDAN("ss", List.of("south_sudan"), emptyList()),
    SPAIN("es", List.of("spain"), List.of("spanish")),
    SRI_LANKA("lk", List.of("sri_lanka"), emptyList()),
    SUDAN("sd", List.of("sudan"), emptyList()),
    SURINAME("sr", List.of("suriname"), emptyList()),
    SVALBARD_AND_JAN_MAYEN("sj", List.of("svalbard_and_jan_mayen"), emptyList()),
    SWAZILAND("sz", List.of("swaziland"), emptyList()),
    SWEDEN("se", List.of("sweden"), List.of("swedish")),
    SWITZERLAND("ch", List.of("switzerland"),  List.of("swiss")),
    SYRIA("sy", List.of("syria"), emptyList()),
    TAIWAN("tw", List.of("taiwan"), emptyList()),
    TAJIKISTAN("tj", List.of("tajikistan"), emptyList()),
    TANZANIA("tz", List.of("tanzania"), emptyList()),
    THAILAND("th", List.of("thailand"), List.of("thai")),
    TOGO("tg", List.of("togo"), emptyList()),
    TOKELAU("tk", List.of("tokelau"), emptyList()),
    TONGA("to", List.of("tonga"), emptyList()),
    TRINIDAD_AND_TOBAGO("tt", List.of("trinidad_and_tobago"), emptyList()),
    TUNISIA("tn", List.of("tunisia"), emptyList()),
    TURKEY("tr", List.of("turkey"), emptyList()),
    TURKMENISTAN("tm", List.of("turkmenistan"), emptyList()),
    TURKS_AND_CAICOS_ISLANDS("tc", List.of("turks_and_caicos_islands"), emptyList()),
    TUVALU("tv", List.of("tuvalu"), emptyList()),
    US_VIRGIN_ISLANDS("vi", List.of("us_virgin_islands"), emptyList()),
    UGANDA("ug", List.of("uganda"), emptyList()),
    UKRAINE("ua", List.of("ukraine"), emptyList()),
    UNITED_ARAB_EMIRATES("ae", List.of("uae"), emptyList()),
    UNITED_KINGDOM("gb", List.of("uk"), List.of("british")),
    UNITED_STATES("us", List.of("usa", "united states"), List.of("american")),
    URUGUAY("uy", List.of("uruguay"), List.of("uruguayan")),
    UZBEKISTAN("uz", List.of("uzbekistan"), emptyList()),
    VANUATU("vu", List.of("vanuatu"), emptyList()),
    VATICAN("va", List.of("vatican"), emptyList()),
    VENEZUELA("ve", List.of("venezuela"), List.of("venezuelan")),
    VIETNAM("vn", List.of("vietnam"), emptyList()),
    WALLIS_AND_FUTUNA("wf", List.of("wallis_and_futuna"), emptyList()),
    WESTERN_SAHARA("eh", List.of("western_sahara"), emptyList()),
    YEMEN("ye", List.of("yemen"), emptyList()),
    ZAMBIA("zm", List.of("zambia"), emptyList()),
    ZIMBABWE("zw", List.of("zimbabwe"), emptyList());

    private final String code;
    private final List<String> names;
    private final List<String> nationalityKeywords;

    public static Country fromName(final String name) {
        return Arrays.stream(values())
            .filter(country -> country.names.contains(name.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the name: %s to a valid country", name)));
    }

    public static Country fromNationality(final String nationality) {
         return Arrays.stream(values())
             .filter(country -> country.nationalityKeywords.contains(nationality.toLowerCase()))
             .findFirst()
             .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the nationality: %s to a valid country", nationality)));
    }
}
