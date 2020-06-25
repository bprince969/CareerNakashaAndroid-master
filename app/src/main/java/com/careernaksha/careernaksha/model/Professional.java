package com.careernaksha.careernaksha.model;

public class Professional {
    public String getJ1pos() {
        return j1pos;
    }

    public void setJ1pos(String j1pos) {
        this.j1pos = j1pos;
    }

    public String getJ1loc() {
        return j1loc;
    }

    public void setJ1loc(String j1loc) {
        this.j1loc = j1loc;
    }

    public String getJ1salary() {
        return j1salary;
    }

    public void setJ1salary(String j1salary) {
        this.j1salary = j1salary;
    }

    public String getJ2pos() {
        return j2pos;
    }

    public void setJ2pos(String j2pos) {
        this.j2pos = j2pos;
    }

    public String getJ2loc() {
        return j2loc;
    }

    public void setJ2loc(String j2loc) {
        this.j2loc = j2loc;
    }

    public String getJ2salary() {
        return j2salary;
    }

    public void setJ2salary(String j2salary) {
        this.j2salary = j2salary;
    }

    public Professional(String j1pos, String j1loc, String j1salary, String j2pos, String j2loc, String j2salary) {
        this.j1pos = j1pos;
        this.j1loc = j1loc;
        this.j1salary = j1salary;
        this.j2pos = j2pos;
        this.j2loc = j2loc;
        this.j2salary = j2salary;
    }

    String j1pos,j1loc,j1salary,j2pos,j2loc,j2salary;
}
