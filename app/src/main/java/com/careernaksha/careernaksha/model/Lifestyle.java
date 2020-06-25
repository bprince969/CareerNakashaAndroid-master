package com.careernaksha.careernaksha.model;

public class Lifestyle {
    public String getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(String entertainment) {
        this.entertainment = entertainment;
    }

    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(String miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public Lifestyle(String entertainment, String living, String food, String travel, String miscellaneous) {
        this.entertainment = entertainment;
        this.living = living;
        this.food = food;
        this.travel = travel;
        this.miscellaneous = miscellaneous;
    }

    String entertainment,living,food,travel,miscellaneous;
}
