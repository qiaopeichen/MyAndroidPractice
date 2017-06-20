package com.example.a19_xml_parse;

public class Channel {

    public String temp;
    public String city;
    public String id;
    public String pm250;
    public String wind;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPm250() {
        return pm250;
    }

    public void setPm250(String pm250) {
        this.pm250 = pm250;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Channel [id=" + id +", city=" + city + ", temp=" + temp + ", wind=" + wind + ", pm250=" +pm250 + "]";
    }

}
