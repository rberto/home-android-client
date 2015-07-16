package com.example.romain.home.model.factories;

import com.example.romain.home.model.Summary;
import com.example.romain.home.model.SummaryKeys;
import com.example.romain.home.views.items.SummaryItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by romain on 07/07/15.
 */
public class DataFactory {

    private static final String DEFAULTVALUE = "--";

    public static String getValue(JSONObject json, SummaryKeys key){
        if (json.has(key.getKey())){
            try {
                return json.getString(key.getKey());
            } catch (JSONException e) {
                return DEFAULTVALUE;
            }
        }else {
            return DEFAULTVALUE;
        }
    }

    public static Summary createSummary(JSONObject json){
        Summary sum = new Summary();
        sum.setLastOutsidePressure(getValue(json, SummaryKeys.LASTOUTPRES_KEY));
        sum.setLastOutsideTemperature(getValue(json, SummaryKeys.LASTOUTTEMP_KEY));
        sum.setLastInsideTemperature(getValue(json, SummaryKeys.LASTINTEMP_KEY));
        sum.setLastInsidePressure(getValue(json, SummaryKeys.LASTINPRES_KEY));
        sum.setLastAsicTemp1(getValue(json, SummaryKeys.LASTASICTEMP1_KEY));
        sum.setLastAsicTemp2(getValue(json, SummaryKeys.LASTASICTEMP2_KEY));
        sum.setLastErrorRate(getValue(json, SummaryKeys.LASTERRORRATE_KEY));
        sum.setLastHashRate(getValue(json, SummaryKeys.LASTHASHRATE_KEY));
        sum.setAvgAsicTemp1(getValue(json, SummaryKeys.AVERAGEASICTEMP1_KEY));
        sum.setAvgAsicTemp2(getValue(json, SummaryKeys.AVERAGEASICTEMP2_KEY));
        sum.setAvgErrorRate(getValue(json, SummaryKeys.AVERAGEERRORRATE_KEY));
        sum.setAvgHashRate(getValue(json, SummaryKeys.AVERAGEHASHRATE_KEY));
        sum.setAvgInsidePressure(getValue(json, SummaryKeys.AVERAGEINPRES_KEY));
        sum.setAvgInsideTemperature(getValue(json, SummaryKeys.AVERAGEINTEMP_KEY));
        sum.setAvgOutsidePressure(getValue(json, SummaryKeys.AVERAGEOUTPRES_KEY));
        sum.setAvgOutsideTemperature(getValue(json, SummaryKeys.AVERAGEOUTTEMP_KEY));
        return sum;
    }




}
