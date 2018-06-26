package com.accenture.api.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaElasticUtils {

    @Autowired
    private KusinaConfigUtils kusinaConfigUtils;

    @Autowired
    private KusinaPayloadUtils kusinaPayloadUtils;

    @Autowired
    private KusinaExtractionUtils kusinaExtractionUtils;

    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;

    public JSONObject sendRequest(JSONObject o) throws MalformedURLException, ProtocolException, IOException, ParseException {

        String url = kusinaConfigUtils.generateURI(o.get("uri").toString());

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        System.out.println("URI: " + url);
		
        con.setRequestMethod(o.get("method").toString());      
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        
        if (!StringUtils.isEmpty(kusinaConfigUtils.getAppClientEnvironment())) {
            String loginUsernamePassword = kusinaConfigUtils.getEnvironmentCredentials();
            con.setRequestProperty("Authorization", "Basic " + new String(Base64.getEncoder().encode(loginUsernamePassword.getBytes())));
        }

        if ((boolean) o.get("hasPayload")) {
            String payload = kusinaPayloadUtils.generatePayload(o);
            System.out.println("PAYLOAD: " + payload);
            con.setDoOutput(true);
            try (DataOutputStream output = new DataOutputStream(con.getOutputStream())) {
                output.writeBytes(payload);
                int bytesWritten = output.size();
                System.out.println("Total " + bytesWritten + " bytes are written to stream.");
                output.flush();
            }
            
        }
        
    
        int responseCode = con.getResponseCode();
        System.out.println("\nSending '" + o.get("method").toString() + "' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        StringBuilder response = null;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        // Parsing request into JSON
        JSONObject result = new JSONObject();
        if ((boolean) o.get("isKusina")) {
            if (Arrays.asList("s_session", "d_session",  "d_user", "insert", "update", "addFeedback", "addAnnouncement", "editAnnouncement", "deleteAnnouncement", "insertHistory", "postHistory", "deleteAnnouncementHistories", "addProfile", "editProfile", "deleteProfile").contains(o.get("payload").toString())) {
                result.put("status", kusinaExtractionUtils.getResult(response.toString()));
            } else if ("sessionTypeId".equalsIgnoreCase(o.get("payload").toString())) {
                result.put("_id", kusinaExtractionUtils.getDocumentId(response.toString()));
            } else if ("userTypeId".equalsIgnoreCase(o.get("payload").toString())) {
                result.put("_id", kusinaExtractionUtils.getDocumentId(response.toString()));
            } else if ("allusers".equalsIgnoreCase(o.get("payload").toString()) || "users".equalsIgnoreCase(o.get("payload").toString())) {
                result = kusinaExtractionUtils.getAllUsersList(response.toString());
            }  else if ("feedbacks".equalsIgnoreCase(o.get("payload").toString())) {
                result = kusinaExtractionUtils.getFeedBacks(response.toString());
            }  else {
                System.out.println("PAYLOAD TERM: " + o.get("payload"));
                result = kusinaExtractionUtils.getHitsDocument(response.toString());
            }
        } else {
            if (o.get("payload").toString().equalsIgnoreCase("reports")) {

                JSONObject jo = (JSONObject) o.get("reportObj");
                String reportType = jo.get("reportType").toString();

                if (kusinaValidationUtils.isAggregation(reportType)) {
                    result = kusinaExtractionUtils.getAggregationDocument(response.toString());
                } else {
                    result = kusinaExtractionUtils.getReportHitsDocument(response.toString());
                }
            } else if (o.get("payload").toString().equalsIgnoreCase("AIBSPBVIreports")) {

                JSONObject jo = (JSONObject) o.get("reportObj");
                String reportType = jo.get("reportType").toString();

                if (kusinaValidationUtils.isAggregation(reportType)) {
                    result = kusinaExtractionUtils.getAggregationDocument(response.toString());
                } else {
                    result = kusinaExtractionUtils.getReportHitsDocument(response.toString());
                }
            } else {
                result = kusinaExtractionUtils.getAggregationDocument(response.toString());
            }
        }

        con.disconnect();
        return result;

    }
}
