package com.careernaksha.careernaksha.model;

public class Educational {
    public String getTenth() {
        return tenth;
    }

    public void setTenth(String tenth) {
        this.tenth = tenth;
    }

    public String getTwelth() {
        return twelth;
    }

    public void setTwelth(String twelth) {
        this.twelth = twelth;
    }

    public String getBfieldofstudy() {
        return bfieldofstudy;
    }

    public void setBfieldofstudy(String bfieldofstudy) {
        this.bfieldofstudy = bfieldofstudy;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getToefl() {
        return toefl;
    }

    public void setToefl(String toefl) {
        this.toefl = toefl;
    }

    public String getGre() {
        return gre;
    }

    public void setGre(String gre) {
        this.gre = gre;
    }

    public String getGmat() {
        return gmat;
    }

    public void setGmat(String gmat) {
        this.gmat = gmat;
    }

    public String getIelts() {
        return ielts;
    }

    public void setIelts(String ielts) {
        this.ielts = ielts;
    }

    public String getMfieldofstudy() {
        return mfieldofstudy;
    }

    public void setMfieldofstudy(String mfieldofstudy) {
        this.mfieldofstudy = mfieldofstudy;
    }

    public String getNameofcollege() {
        return nameofcollege;
    }

    public void setNameofcollege(String nameofcollege) {
        this.nameofcollege = nameofcollege;
    }

    public String getAdditinalqualifiction() {
        return additinalqualifiction;
    }

    public void setAdditinalqualifiction(String additinalqualifiction) {
        this.additinalqualifiction = additinalqualifiction;
    }

    public Educational(String tenth, String twelth, String bfieldofstudy, String collegename, String mfieldofstudy, String nameofcollege, String gre, String gmat, String ielts, String toefl, String additinalqualifiction) {
        this.tenth = tenth;
        this.twelth = twelth;
        this.bfieldofstudy = bfieldofstudy;
        this.collegename = collegename;
        this.toefl = toefl;
        this.gre = gre;
        this.gmat = gmat;
        this.ielts = ielts;
        this.mfieldofstudy = mfieldofstudy;
        this.nameofcollege = nameofcollege;
        this.additinalqualifiction = additinalqualifiction;
    }

    String tenth,twelth,bfieldofstudy,collegename,toefl,gre,gmat,ielts,mfieldofstudy,nameofcollege,additinalqualifiction;
}
