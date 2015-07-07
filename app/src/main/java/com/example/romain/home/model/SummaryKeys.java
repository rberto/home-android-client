package com.example.romain.home.model;

/**
 * Created by romain on 07/07/15.
 */
public enum SummaryKeys {

    LASTINTEMP_KEY("temp"),
    LASTINPRES_KEY("pressure"),
    LASTOUTTEMP_KEY("temp_ext"),
    LASTOUTPRES_KEY("pressure_ext"),
    LASTHASHRATE_KEY("GHS"),
    LASTERRORRATE_KEY("Device Hardware%"),
    LASTASICTEMP1_KEY("temp1"),
    LASTASICTEMP2_KEY("temp2"),
    AVERAGEINTEMP_KEY("avg_temp"),
    AVERAGEOUTTEMP_KEY("avg_temp_ext"),
    AVERAGEINPRES_KEY("avg_pressure"),
    AVERAGEOUTPRES_KEY("avg_pressure_ext"),
    AVERAGEHASHRATE_KEY("GHS av"),
    AVERAGEERRORRATE_KEY("Device Hardware% av"),
    AVERAGEASICTEMP1_KEY("temp1 av"),
    AVERAGEASICTEMP2_KEY("temp2 av");

    private String key;

    SummaryKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
