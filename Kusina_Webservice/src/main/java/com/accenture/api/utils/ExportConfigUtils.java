package com.accenture.api.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.FeedbackService;
import com.accenture.api.service.ITFService;
import com.accenture.api.service.MyTEService;
import com.accenture.api.service.ProfileService;
import com.accenture.api.service.UserService;
import com.accenture.api.service.VisitorService;

/***
 * @author: arnel.m.capendit
 * @created: 3-7-18
 * @comment: Export function logic 
 *
 */
@Component
public class ExportConfigUtils {
	
	@Autowired
    private CustomReportService customReportService;
	
	@Autowired
    private VisitorService visitorService;
	
	@Autowired
    private MyTEService myTEService;
	
	@Autowired
    private ProfileService profileService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private ITFService itfService;
	

	public int mergeCsvFiles(String idFile, int numFiles, String path, String fileType, long sizeLimit) throws IOException {

        // Variables
        ArrayList<File> files = new ArrayList<File>();
        Iterator<File> iterFiles;
        File fileOutput;
        BufferedWriter fileWriter;
        BufferedReader fileReader;
        String csvFile;
        String csvFinal = path + idFile + fileType;	
        String[] headers = null;
        String header = null;
        long size=0;
        int lastIndex=0;

        // Files: Input
        for (int i = 0; i < numFiles; i++) {
            csvFile = path + idFile + i + fileType;
            
            File temp = new File(csvFile);
            size = size + temp.length();
            System.out.println("SIZE OF THE FILE: "+ size);

            if(size <= sizeLimit){
                lastIndex=i;
                files.add(temp);    
            }
            else{
                lastIndex = -1;
            }
        }

        // Files: Output
        System.out.println("deleted: " + csvFinal);
        fileOutput = new File(csvFinal);
        if (fileOutput.exists()) {
            fileOutput.delete();
        }
        try {
            fileOutput.createNewFile();
            // log
            // System.out.println("Output: " + fileOutput);
        } catch (IOException e) {
            // log
        }

        iterFiles = files.iterator();
        fileWriter = new BufferedWriter(new FileWriter(csvFinal, true));

        // Headers
        Scanner scanner = new Scanner(files.get(0));
        if (scanner.hasNextLine())
            header = scanner.nextLine();
        // if (scanner.hasNextLine()) headers = scanner.nextLine().split(";");
        scanner.close();

        fileWriter.write(header);
        fileWriter.newLine();

        while (iterFiles.hasNext()) {
            String line;// = null;
            String[] firstLine;// = null;
            
            File nextFile = iterFiles.next();
            fileReader = new BufferedReader(new FileReader(nextFile));
            
            if ((line = fileReader.readLine()) != null)
                firstLine = line.split(";");
            while ((line = fileReader.readLine()) != null) {
                fileWriter.write(line);
                fileWriter.newLine();
            }
            fileReader.close();
        }
        fileWriter.close();
        return lastIndex;
    }
	
	public JSONObject getCustomReportItem(JSONObject args, String customReporItem) {	
		
		switch(customReporItem) {
		
		case "usageReport":
			return customReportService.getCustomReportUsageV3(args);
		
		case "pagesReport":
			return customReportService.getCustomReportPagesV3(args);
		
		case "visitorLogs":
			return visitorService.getVisitorLogsExport(args);
		
		case "myTE":
			return myTEService.getMyTEReports(args);
		
		case "overviewReport":
			return customReportService.getCustomReportsOverview(args);
					
		case "aibspBviReport":
			return customReportService.getCustomReportsAIBSPBVI(args);
		
		case "eventsReport":
			return customReportService.getCustomReportsEvents(args);
			
		case "users":
			return userservice.getUsers(args);
			
		case "feedbacks":
			return feedbackService.getFeedbacks(args);
			
		case "profile":
			return profileService.getProfileExport(args);
			
		case "itfReport":
			return itfService.getCustomReportsITF(args);

		default:
			break;
		}
		
		return null;
	}
}

