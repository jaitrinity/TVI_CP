package com.tvi.constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.map.ObjectMapper;

import com.tvi.dto.SystemParamDTO;

public class CommonFunction {
	public static String listToCommaSeperated(List<String> addedListString){
		String seperator = ",";
		int listOfAddedSize = addedListString.size()*seperator.length();
		for(String s:addedListString){
			listOfAddedSize += s.length();
		}
		StringBuilder sb = new StringBuilder(listOfAddedSize);
		for (String s : addedListString) {
		    sb.append(seperator).append(s);
		}
		String result = sb.substring(seperator.length());
		return result;
		
	}
	
	public static String printResponseJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;

	}
	
	public static String generateOtp() {
		String characters = "1234567890123456780";
		int stringLength = 6;
		StringBuilder result = new StringBuilder();
		while (stringLength > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			stringLength--;
		}
		return result.toString();
	}

	public static String getCommaSeperateStringByCode(List<SystemParamDTO> systemParam){
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i<systemParam.size();i++){
			SystemParamDTO s = systemParam.get(i);
		    sb.append(s.getParamCode());
		    if(i != systemParam.size()-1){
		    	sb.append(",");
		    }
		}
		return sb.toString();
	}
	
	public static List<String> convertCommaseparateStringToList(String commaSeparateString){
		String[] elements = commaSeparateString.split(",");
		return Arrays.asList(elements);
	}
	
	public static String getTimestampFormUtilDate(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String strDate = dateFormat.format(date);
		return strDate;
	}
	public static String convertDateFormatAsGiven(Date date,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static Date convertStrDateToUtilDate(String date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dates = new Date();
		try {
			dates = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}
	
	public static String convertBase64ToAllType(String srNumber, String base64Code, String attachType){
		String base64 [] = base64Code.split(",");
		String fileName = srNumber+"_"+attachType;
		String monthYearFolder = getCurrentMonthYearFolder();
		String direcoryPath = Constant.ATTACHED_FOLDER + monthYearFolder + "/";
		File theDir = new File(direcoryPath);
		if (!theDir.exists()){
		    theDir.mkdirs();
		    theDir.setReadable(true, false); // set readable
		    theDir.setWritable(true, false); // set writable
		    theDir.setExecutable(true, false); // set executable
		}
		try {
			String ext = ".";
			String s = base64[0];
			int slashIndex = s.indexOf("/");
			int semiColonIndex = s.indexOf(";");
			ext += s.substring(slashIndex+1, semiColonIndex);
			if(ext.equalsIgnoreCase(".octet-stream")) ext = ".msg";
			else if(ext.equalsIgnoreCase(".rfc822")) ext = ".eml";
			fileName += ext;
			byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64[1]);
			String imgPath = direcoryPath + fileName;
			writeFile(imageBytes, imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = Constant.ATTACHED_URL + monthYearFolder + "/"+ fileName;
		return url;
	}
	
	
	private static void writeFile(byte[] content, String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		Runtime.getRuntime().exec("chmod 664 -R "+filename+"");
		fop.flush();
		fop.close();
	}
	
	public static String readTextFile(String fileName){
		String filePath = Constant.FILE_FOLDER;
		String lines = "";
		try {
			File file = new File(filePath+fileName);
	        FileReader fr = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);
	        //System.out.println("Reading text file using FileReader");
	        String line = "";
	        while((line = br.readLine()) != null){
	            //process the line
	        	lines += line;
	            //System.out.println(line);
	        }
	        
	        br.close();
	        fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return lines;
	}
	
	public static String getCurrentMonthYearFolder(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMyyyy");
		Date date = new Date();
		String strDate = dateFormat.format(date);
		return strDate;
	}
	
	
}
