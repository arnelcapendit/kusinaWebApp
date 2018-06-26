package com.accenture.api.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ElasticSaverUtils {

	 public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, java.text.ParseException {

	        JSONParser parser = new JSONParser();
	        int i = 0,counter=0;;
	        
	        String ts="TIMESTAMP",lsa="LastActionTime",fat= "FirstActionTime",time="LocalTime";
//	        String ts="created_date",lsa="last_update_date";
	        
	        
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	        SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yyyy HH:mm");
	        SimpleDateFormat timeSdf =  new SimpleDateFormat("HH:mm:ss");
	        
	        Date dd;
	        JSONArray obj = (JSONArray) parser.parse(new FileReader("C:\\piwikDummy\\itf.json"));
	        for (Object o : obj) {

	            JSONObject jo = (JSONObject) o; 
	            
	             dd = sdf.parse(jo.get(ts).toString());
	            dateFormat.format(dd);
	            jo.put(ts, dateFormat.format(dd));
	            
	            dd = sdf.parse(jo.get(lsa).toString());
	            dateFormat.format(dd);
	            jo.put(lsa, dateFormat.format(dd));
	            
	            dd = sdf.parse(jo.get(fat).toString());
	            dateFormat.format(dd);
	            jo.put(fat, dateFormat.format(dd));
	            
	            dd = timeSdf.parse(jo.get(time).toString());
	            dateFormat.format(dd);
	            jo.put(time, dateFormat.format(dd));
	            
	            System.out.println(jo);
	            
	            for(int j =0; j<1;j++){
	            URL url = new URL("http://localhost:9200/webmetrics/piwik");
	            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
	            urlConn.setRequestMethod("POST");
	            urlConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	            urlConn.setDoOutput(true);

	            DataOutputStream output = new DataOutputStream(urlConn.getOutputStream());

	            /* Send the request data. */
	            output.writeBytes(jo.toJSONString());
	            output.flush();
	            output.close();
	             
	            int responseCode = urlConn.getResponseCode();
	            
	            
	            System.out.println("Response Code: "+ responseCode);
	            /* Get response data. */
	            StringBuilder result = new StringBuilder();
//	               try{
	   
            	BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            	String line;
	            while ((line = rd.readLine()) != null) {
	                result.append(line);
	            }
	            rd.close();
	              
              }
	            
	        }
	        counter++;
	        System.out.println(counter);
	        
	        JOptionPane.showMessageDialog(null, "ElasticSaver Done.");
	    }

	      
}

//         public static void main(String[] args) {
//
//        MainUtils m = new MainUtils();
//        try {
//
//            m.convertCsvToJson("C:\\Users\\marlon.naraja\\Documents\\Marlon P. Naraja\\IM Project\\AIBS.csv", "C:\\Users\\marlon.naraja\\Documents\\Marlon P. Naraja\\IM Project\\AIBS_copy.json");
//        } catch (IOException ex) {
//            Logger.getLogger(MainUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

//    public static void main(String[] a) throws IOException, Exception, ParseException {
//    	
//    	String url = "https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&ved=0CEEQFjAG&url=https%3A%2F%2Fmysche";
//    	String domainName = null;
//    	int one = url.indexOf('/');
//     	if(url == null || url == "") {
//     		domainName = url;
//     	}else if(one > -1){
//     		String[] urlName = url.split("/");
//     		domainName = urlName[2];
//     	}else {
//     		domainName = url;
//     	}
//    	System.out.println("result: "+ domainName);
//    	
//    
//}

//}
