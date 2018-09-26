package com.richard.imoh.collab.Utils;

import java.util.ArrayList;
import java.util.List;

public class PropertyTypeList {
    List<String> purposeList =  new ArrayList<String>();
    List<String> residentialList =  new ArrayList<String>();
    List<String> commercialList =  new ArrayList<String>();

    public List<String> getCommercialList() {
        commercialList.add("Office Space");
        commercialList.add("Co Working Space");
        commercialList.add("Shop");
        commercialList.add("Ware House");
        commercialList.add("Cemetery");
        return commercialList;
    }

   public List<String> getResidential(){
        residentialList.add("Single Room");
        residentialList.add("Self Contain");
        residentialList.add("Mini Flats");
        residentialList.add("1 Bed Room Flats");
        residentialList.add("2 Bed Room Flats");
        residentialList.add("3 Bed Room Flats");
        residentialList.add("4 Bed Room Flats");
        residentialList.add("Bungalow");
        residentialList.add("Duplex");
        residentialList.add("Estate Block");
        residentialList.add("Land");
        return residentialList;
    }

    public List<String>getPurposeList(){
        purposeList.add("Commercial");
        purposeList.add("Residential");
        return purposeList;
    }
}
