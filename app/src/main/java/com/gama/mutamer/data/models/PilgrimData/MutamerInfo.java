package com.gama.mutamer.data.models.PilgrimData;

/**
 * Created by mustafa on 11/27/18.
 * Release the GEEK
 */
public class MutamerInfo {

    private Pilgrim mPilgrim;


    private Agent mAgent;


    private UmrahCompany mCompany;


    public void setPilgrim(Pilgrim pilgrim) {
        mPilgrim = pilgrim;
    }

    public void setAgent(Agent agent) {
        mAgent = agent;
    }

    public void setCompany(UmrahCompany company) {
        mCompany = company;
    }

    public Pilgrim getPilgrim() {
        return mPilgrim;
    }

    public Agent getAgent() {
        return mAgent;
    }

    public UmrahCompany getCompany() {
        return mCompany;
    }
}
