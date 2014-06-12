package es.unileon.ulebank.googlefinances;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class MainExample {

    public static void main(String[] args) throws IOException, ParseException, ElementNotFoundException {
        //Get an instance of Google Api
        GoogleFinancesApi gf = GoogleFinancesApi.getInstance();
        //Lookup Google
        EnterpriseData google = gf.searchToEnterpriseData("GOOG");
        
        //Add listener
        google.addListener(new AboveValueListener(google, 552.5));
        //Every second, basic information(getBasicInfo) is checked and decides the event must be thrown or not(callEnterprises)
        while (true) {
            try {
                gf.callEnterprises();
                getBasicInfo(google);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void getBasicInfo(EnterpriseData enterpriseData) throws ElementNotFoundException {
        System.out.println("Date: " + enterpriseData.getValue("lt")
                + "\tName: " + enterpriseData.getValue("name")
                + "\t\tCurrent: " + enterpriseData.getValue("l_fix")
                + "\t\tLow: " + enterpriseData.getValue("lo")
                + "\t\tHigh: " + enterpriseData.getValue("hi"));
    }
}
