package es.unileon.ulebank.googlefinances;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class parses data for Google Finances (https://www.google.com/finance/)
 * to acquire market data in real time. The class implements Singleton pattern.
 */
public class GoogleFinancesApi {

    private static GoogleFinancesApi gfAPI = null;
    private final String baseURL = "https://www.google.com/finance/info?infotype=infoquoteall&q=";
    private final ArrayList<EnterpriseData> enterprises;

    private GoogleFinancesApi() {
        this.enterprises = new ArrayList<EnterpriseData>();
    }

    /**
     * Retrieves the instance of the class.
     *
     * @return The GoogleFinancesApi instance.
     */
    public static GoogleFinancesApi getInstance() {
        if (gfAPI == null) {
            gfAPI = new GoogleFinancesApi();
        }
        return gfAPI;
    }

    public void addEnterprise(EnterpriseData enterprise) {
        if (!this.enterprises.contains(enterprise)) {
            this.enterprises.add(enterprise);
        }
    }

    public void removeEnterprise(EnterpriseData enterprise) {
        if (this.enterprises.contains(enterprise)) {
            this.enterprises.remove(enterprise);
        }
    }
    
    public void callEnterprises() throws IOException, ParseException, ElementNotFoundException {
        for(EnterpriseData enterprise : this.enterprises) {
            enterprise.refresh();
        }
    }

    /**
     * Searches for a specific ticker and retrieves the associated information
     * in JSON format.
     *
     * @param ticker Ticker that identifies the company.
     * @return JSON String.
     * @throws MalformedURLException
     * @throws IOException
     */
    private String searchToJSON(String ticker) throws MalformedURLException, IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(this.baseURL + ticker);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        br.close();
        return result.toString();
    }

    /**
     * Searches for a specific ticker and retrieves the parsed map information.
     *
     * @param ticker Ticker that identifies the company.
     * @return Map Object.
     * @throws IOException
     * @throws ParseException
     */
    public Map searchToParsedMap(String ticker) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray keys = (JSONArray) parser.parse(searchToJSON(ticker).replace("//", ""));
        return (Map) parser.parse(keys.get(0).toString());
    }

    /**
     * Searches for a specific ticker and retrieves the associated information.
     * on a EnterpriseData Object.
     *
     * @param ticker Ticker that identifies the company.
     * @return EnterpriseData Object.
     * @throws IOException
     * @throws ParseException
     */
    public EnterpriseData searchToEnterpriseData(String ticker) throws IOException, ParseException, ElementNotFoundException {
        EnterpriseData enterprise = new EnterpriseData(searchToParsedMap(ticker));
        if(this.enterprises.contains(enterprise)) {
            enterprise = this.enterprises.get(this.enterprises.indexOf(enterprise));
            enterprise.refresh();
        }
        return enterprise;
    }
}
