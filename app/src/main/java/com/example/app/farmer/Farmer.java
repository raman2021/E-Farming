package com.example.app.farmer;

public class Farmer {

    private  String Fnm, Lnm, Eid, Ps, Cps;

    public Farmer(String fnm, String lnm, String eid, String ps, String cps) {
        Fnm = fnm;
        Lnm = lnm;
        Eid = eid;
        Ps = ps;
        Cps = cps;
    }
    public  Farmer(){

    }


    public String getFnm() {
        return Fnm;
    }

    public String getLnm() {
        return Lnm;
    }

    public String getEid() {
        return Eid;
    }

    public String getPs() {
        return Ps;
    }

    public String getCps() {
        return Cps;
    }
}
