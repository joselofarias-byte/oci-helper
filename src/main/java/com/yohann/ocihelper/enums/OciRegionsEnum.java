package com.yohann.ocihelper.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yohann
 */

public enum OciRegionsEnum {

    // Asia Pacific
    AP_SYDNEY_1("ap-sydney-1", "Australia este (Sídney)", "Sydney, Australia", "SYD"),
    AP_MELBOURNE_1("ap-melbourne-1", "Australia sureste (Melbourne)", "Melbourne, Australia", "MEL"),
    AP_HYDERABAD_1("ap-hyderabad-1", "India sur (Hyderabad)", "Hyderabad, India", "HYD"),
    AP_MUMBAI_1("ap-mumbai-1", "India oeste (Bombay)", "Mumbai, India", "BOM"),
    AP_BATAM_1("ap-batam-1", "Indonesia norte (Batam)", "Batam, Indonesia", "HSG"),
    AP_OSAKA_1("ap-osaka-1", "Japón central (Osaka)", "Osaka, Japan", "KIX"),
    AP_TOKYO_1("ap-tokyo-1", "Japón este (Tokio)", "Tokyo, Japan", "NRT"),
    AP_SINGAPORE_1("ap-singapore-1", "Singapur", "Singapore", "SIN"),
    AP_SINGAPORE_2("ap-singapore-2", "Singapur oeste", "Singapore", "XSP"),
    AP_SEOUL_1("ap-seoul-1", "Corea del Sur central (Seúl)", "Seoul, South Korea", "ICN"),
    AP_CHUNCHEON_1("ap-chuncheon-1", "Corea del Sur norte (Chuncheon)", "Chuncheon, South Korea", "YNY"),
    AP_KULAI_2("ap-kulai-2", "Malasia oeste (Kulai)", "Kulai, Malaya", "JBP"),

    // Americas
    SA_SAOPAULO_1("sa-saopaulo-1", "Brasil este (São Paulo)", "Sao Paulo, Brazil", "GRU"),
    SA_VINHEDO_1("sa-vinhedo-1", "Brasil sureste (Vinhedo)", "Vinhedo, Brazil", "VCP"),
    CA_MONTREAL_1("ca-montreal-1", "Canadá sureste (Montreal)", "Montreal, Canada", "YUL"),
    CA_TORONTO_1("ca-toronto-1", "Canadá sureste (Toronto)", "Toronto, Canada", "YYZ"),
    SA_SANTIAGO_1("sa-santiago-1", "Chile central (Santiago)", "Santiago, Chile", "SCL"),
    SA_VALPARAISO_1("sa-valparaiso-1", "Chile oeste (Valparaíso)", "Valparaiso, Chile", "VAP"),
    SA_BOGOTA_1("sa-bogota-1", "Colombia central (Bogotá)", "Bogota, Colombia", "BOG"),
    MX_QUERETARO_1("mx-queretaro-1", "México central (Querétaro)", "Queretaro, Mexico", "QRO"),
    MX_MONTERREY_1("mx-monterrey-1", "México noreste (Monterrey)", "Monterrey, Mexico", "MTY"),
    US_ASHBURN_1("us-ashburn-1", "Estados Unidos este (Ashburn)", "Ashburn, VA", "IAD"),
    US_CHICAGO_1("us-chicago-1", "Estados Unidos central (Chicago)", "Chicago, IL", "ORD"),
    US_PHOENIX_1("us-phoenix-1", "Estados Unidos oeste (Phoenix)", "Phoenix, AZ", "PHX"),
    US_SANJOSE_1("us-sanjose-1", "Estados Unidos oeste (San José)", "San Jose, CA", "SJC"),

    // Europe
    EU_PARIS_1("eu-paris-1", "Francia central (París)", "Paris, France", "CDG"),
    EU_MARSEILLE_1("eu-marseille-1", "Francia sur (Marsella)", "Marseille, France", "MRS"),
    EU_FRANKFURT_1("eu-frankfurt-1", "Alemania central (Fráncfort)", "Frankfurt, Germany", "FRA"),
    EU_MILAN_1("eu-milan-1", "Italia noroeste (Milán)", "Milan, Italy", "LIN"),
    EU_TURIN_1("eu-turin-1", "Italia norte (Turín)", "Turin, Italy", "NRQ"),
    EU_AMSTERDAM_1("eu-amsterdam-1", "Países Bajos noroeste (Ámsterdam)", "Amsterdam, Netherlands", "AMS"),
    EU_JOVANOVAC_1("eu-jovanovac-1", "Serbia central (Jovanovac)", "Jovanovac, Serbia", "BEG"),
    EU_MADRID_1("eu-madrid-1", "España central (Madrid)", "Madrid, Spain", "MAD"),
    EU_MADRID_3("eu-madrid-3", "España central (Madrid 3)", "Madrid, Spain", "ORF"),
    EU_STOCKHOLM_1("eu-stockholm-1", "Suecia central (Estocolmo)", "Stockholm, Sweden", "ARN"),
    EU_ZURICH_1("eu-zurich-1", "Suiza norte (Zúrich)", "Zurich, Switzerland", "ZRH"),
    UK_LONDON_1("uk-london-1", "Reino Unido sur (Londres)", "London, United Kingdom", "LHR"),
    UK_CARDIFF_1("uk-cardiff-1", "Reino Unido oeste (Newport)", "Newport, United Kingdom", "CWL"),

    // Middle East & Africa
    IL_JERUSALEM_1("il-jerusalem-1", "Israel central (Jerusalén)", "Jerusalem, Israel", "MTZ"),
    ME_RIYADH_1("me-riyadh-1", "Arabia Saudita central (Riad)", "Riyadh, Saudi Arabia", "RUH"),
    ME_JEDDAH_1("me-jeddah-1", "Arabia Saudita oeste (Yeda)", "Jeddah, Saudi Arabia", "JED"),
    AF_JOHANNESBURG_1("af-johannesburg-1", "Sudáfrica central (Johannesburgo)", "Johannesburg, South Africa", "JNB"),
    ME_ABUDHABI_1("me-abudhabi-1", "Emiratos Árabes Unidos central (Abu Dabi)", "Abu Dhabi, UAE", "AUH"),
    ME_DUBAI_1("me-dubai-1", "Emiratos Árabes Unidos este (Dubái)", "Dubai, UAE", "DXB"),
    AF_CASABLANCA_1("af-casablanca-1", "Marruecos oeste (Casablanca)", "Casablanca, Morocco", "LEJ");

    private final String id;
    private final String name;
    private final String location;
    private final String key;

    OciRegionsEnum(String id, String name, String location, String key) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getKey() {
        return key;
    }

    /**
     * 根据 region id 获取 key
     */
    public static Optional<String> getKeyById(String id) {
        return Arrays.stream(values())
                .filter(r -> r.id.equalsIgnoreCase(id))
                .map(OciRegionsEnum::getKey)
                .findFirst();
    }

    /**
     * 根据 region id 获取 name
     */
    public static Optional<String> getNameById(String id) {
        return Arrays.stream(values())
                .filter(r -> r.id.equalsIgnoreCase(id))
                .map(OciRegionsEnum::getName)
                .findFirst();
    }

    public static List<String> getBroadcastAmdRegions() {
        return Arrays.asList(
                AP_SINGAPORE_1,
                EU_MADRID_1,
                EU_MADRID_3,
                AF_JOHANNESBURG_1,
                MX_QUERETARO_1,
                EU_PARIS_1
        ).parallelStream().map(OciRegionsEnum::getId).collect(Collectors.toList());
    }
}
