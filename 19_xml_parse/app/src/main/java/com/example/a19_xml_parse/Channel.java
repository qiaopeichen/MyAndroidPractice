package com.example.a19_xml_parse;

public class Channel {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPm250() {
        return pm250;
    }

    public void setPm250(int pm250) {
        this.pm250 = pm250;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Channel [id=" + id +", city=" + city + ", temp=" + temp + ", wind=" + wind + ", pm250=" +pm250 + "]";
    }

    public String temp;
    public String city;
    public int id;
    public int pm250;
    public int wind;
}
