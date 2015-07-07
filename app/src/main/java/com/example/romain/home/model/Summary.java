package com.example.romain.home.model;

/**
 * Created by romain on 07/07/15.
 */
public class Summary {

    private String lastInsideTemperature;
    private String lastInsidePressure;
    private String lastOutsideTemperature;
    private String lastOutsidePressure;
    private String lastHashRate;
    private String lastErrorRate;
    private String lastAsicTemp1;
    private String lastAsicTemp2;

    public String getLastInsideTemperature() {
        return lastInsideTemperature;
    }

    public void setLastInsideTemperature(String lastInsideTemperature) {
        this.lastInsideTemperature = lastInsideTemperature;
    }

    public String getLastInsidePressure() {
        return lastInsidePressure;
    }

    public void setLastInsidePressure(String lastInsidePressure) {
        this.lastInsidePressure = lastInsidePressure;
    }

    public String getLastOutsideTemperature() {
        return lastOutsideTemperature;
    }

    public void setLastOutsideTemperature(String lastOutsideTemperature) {
        this.lastOutsideTemperature = lastOutsideTemperature;
    }

    public String getLastOutsidePressure() {
        return lastOutsidePressure;
    }

    public void setLastOutsidePressure(String lastOutsidePressure) {
        this.lastOutsidePressure = lastOutsidePressure;
    }

    public String getLastHashRate() {
        return lastHashRate;
    }

    public void setLastHashRate(String lastHashRate) {
        this.lastHashRate = lastHashRate;
    }

    public String getLastErrorRate() {
        return lastErrorRate;
    }

    public void setLastErrorRate(String lastErrorRate) {
        this.lastErrorRate = lastErrorRate;
    }

    public String getLastAsicTemp1() {
        return lastAsicTemp1;
    }

    public void setLastAsicTemp1(String lastAsicTemp1) {
        this.lastAsicTemp1 = lastAsicTemp1;
    }

    public String getLastAsicTemp2() {
        return lastAsicTemp2;
    }

    public void setLastAsicTemp2(String lastAsicTemp2) {
        this.lastAsicTemp2 = lastAsicTemp2;
    }
}
