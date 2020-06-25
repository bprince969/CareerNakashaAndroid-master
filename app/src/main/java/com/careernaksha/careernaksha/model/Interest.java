package com.careernaksha.careernaksha.model;

public class Interest {

    public String getNextmove() {
        return nextmove;
    }

    public void setNextmove(String nextmove) {
        this.nextmove = nextmove;
    }

    public String getLivingexpenses() {
        return livingexpenses;
    }

    public void setLivingexpenses(String livingexpenses) {
        this.livingexpenses = livingexpenses;
    }

    public String getFieldofinterest() {
        return fieldofinterest;
    }

    public void setFieldofinterest(String fieldofinterest) {
        this.fieldofinterest = fieldofinterest;
    }

    public String getNext5years() {
        return next5years;
    }

    public void setNext5years(String next5years) {
        this.next5years = next5years;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDesiredsalary() {
        return desiredsalary;
    }

    public void setDesiredsalary(String desiredsalary) {
        this.desiredsalary = desiredsalary;
    }

    public Interest(String nextmove, String livingexpenses, String fieldofinterest, String next5years, String goal, String desiredsalary) {
        this.nextmove = nextmove;
        this.livingexpenses = livingexpenses;
        this.fieldofinterest = fieldofinterest;
        this.next5years = next5years;
        this.goal = goal;
        this.desiredsalary = desiredsalary;
    }

    String nextmove,livingexpenses,fieldofinterest,next5years,goal,desiredsalary;

}


