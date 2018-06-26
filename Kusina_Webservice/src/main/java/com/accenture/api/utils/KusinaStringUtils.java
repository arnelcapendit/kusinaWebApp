package com.accenture.api.utils;

import com.accenture.api.controller.KusinaController;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaStringUtils {

    public static final String ACTION_MYTE_NONE = "none";
    public static final String ACTION_MYTE_ALL = "all";
    
    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;
    
    private String cleanStringAdvice(String text) {
        return Jsoup.clean(text, Whitelist.simpleText());
    }

    private String addSlashes(String str) {

        str = str.replace("'", "\\'");
        str = str.replace("\"", "\\\"");
        return str;
    }

    public String sanitizeString(String s) {
        return addSlashes(cleanStringAdvice(s));
    }

    public String getModStr(Object obj) {

        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }
    
   

    public String getPageUrlParam(String pageUrl, String action) {

        StringBuilder sb = new StringBuilder();
        sb.append(pageUrl);
        if (StringUtils.hasText(action)) {
            // added modified by rocky.chucas 12/4/2017-----
            if (action.equalsIgnoreCase(ACTION_MYTE_ALL)) {
                sb.append("*");
            } else if (action.equalsIgnoreCase(ACTION_MYTE_NONE)) {

            } else {
                sb.append("?");
                sb.append("action");
                sb.append("=");
                sb.append(action);
                sb.append("*");
            }
        }
        System.out.println("pageUrlParam: " + sb.toString());

        return sb.toString();
    }
    
    public boolean isValidEid(String eid) {
        if (!StringUtils.isEmpty(eid)
                && !StringUtils.containsWhitespace(eid)
                && !eid.equalsIgnoreCase("nosession")
                && !eid.equalsIgnoreCase("undefined")
                && !eid.equalsIgnoreCase("-")) {
            return true;
        } else {
            return false;
        }
    }
    
    public String convertStringtoElasticQuery(String fieldNames) {
    	String convertResultFilter = fieldNames.replace("[", "").replace("]", "").replace("\"", "");
    	String[] names = convertResultFilter.split(",");
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<names.length; i++) {
    		sb.append("\\\""+names[i]+"\\\"");
    		if(i != names.length -1) {
    			sb.append(" OR ");
    		}
    	}
    	return sb.toString();
    }
    
    public String convertStringToElasticFieldQuery(String fields) {
    	String[] namesList = fields.split(",");
    	List<String> newNameList = new ArrayList<String>();
    	for(String names: namesList ) {
    		newNameList.add("\"" + names + "\"");
    	}
    	
    	return newNameList.toString();
    }
    
    public String convertStringToElasticQueryNames(String fields) {
    	String[] names = fields.split(" ");
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<names.length; i++) {
    		if(names[i].equalsIgnoreCase("&amp;")) {
    			String newName = names[i].replace("&amp;", "&");
    			sb.append("*"+newName+"*");
    			if(i != names.length -1) {
        			sb.append(" AND ");
        		}
    		}else {
    			sb.append("*"+names[i]+"*");
        		if(i != names.length -1) {
        			sb.append(" AND ");
        		}
    		}
    	}
    	return sb.toString();
    }
    
    public static String convertFilterToElasticQueryNames(String fields) {
    	String[] names = fields.split(" ");
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<names.length; i++) {
    		if(names[i].equalsIgnoreCase("&amp;")) {
    			String newName = names[i].replace("&amp;", "&");
    			sb.append(newName);
    			sb.append(" ");
    		}else {
    			sb.append(names[i]);
        		if(i != names.length -1) {
        			sb.append(" ");
        		}
    		}	
    	}
    	return sb.toString();
    }
    
    public String convertSearchToELKQueryParam(String search, String filter) {
    	String word = null;
    	if(!filter.equalsIgnoreCase("user")) {
    		word = toCamelCase(search);
    		if(countWords(word) > 1) {
        		return convertStringToElasticQueryNames(word);
        	} else {
        		return "*"+word+"*";
        	}
    	}else {
    		word = search.toLowerCase();
    		return "*"+word+"*";
    	}	
    }
    
    public String convertSearchtoLowerCase(String search) {
    	String word = null;
    	
    	word = search.toLowerCase();
    	return "*"+word+"*";
    }
    
    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }
    
    public int countWords(String input) {
    	StringTokenizer st = new StringTokenizer(input);
    	return st.countTokens();
    }
    
    //Converting two words to search params
    public String convertSearchToParams(String input) {
		if(countWords(input) > 1) {
    		return convertStringToElasticQueryNames(input);
    	} else {
    		return "*"+input+"*";
    	}
    }
    
    //Exact searching
    public String searchQuery(String reportType, String input) {
    	if(kusinaValidationUtils.isValidSearchQueryType(reportType)) {
    		String word = toCamelCase(input);
    		return convertSearchToParams(word);
    	}else {
    		return convertSearchToParams(input);
    	}	
    }
    
}
