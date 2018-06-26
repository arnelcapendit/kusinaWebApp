package com.accenture.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.accenture.api.model.HistoryModel;
import com.accenture.api.model.testModel;

@Component
public class HistoryDaoService {
	
	private static List<HistoryModel> history = new ArrayList<>();
	private static List<testModel> testmodel = new ArrayList<>();
	
	static {
		testmodel.add(new testModel("roks", "28"));
		testmodel.add(new testModel("arn", "30"));
	}

	private static int historyCount = 3;

//	static {
//		history.add(new HistoryModel("1","announcements", "clint.b.quillope", "123", "read", "1525081095000"));
//		history.add(new HistoryModel("2","announcements", "rod.bacolor", "321", "read", "1525081095000"));
//		history.add(new HistoryModel("3","announcements", "eugene.julian", "456", "read", "1525081095000"));
//	}
	
	public List<HistoryModel> findAll() {
		return history;
	}

	
	public HistoryModel save(HistoryModel hm) {
		
		hm.setId(Integer.toString(++historyCount));
		
		history.add(hm);
		return hm;
	}
	
	public testModel addTestModel(testModel tm) {
		testmodel.add(tm);
		System.out.println("THIS IS TESTMODEL: "+ tm.getName());
		
		return tm;
	}
	
	public List<testModel> getTestModel() {	
		return testmodel;
	}
}
