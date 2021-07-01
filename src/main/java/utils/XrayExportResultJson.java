package utils;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class XrayExportResultJson {
	Logger log = LogManager.getLogger(XrayExportResultJson.class);
	public static void updateXray(String testPlan, String testCaseKey,String remarks, String testResult, String base64File, String filename) throws IOException {
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				ReadPropertiesFile rp =new ReadPropertiesFile();
				String accessKey =rp.readPropertiesFile("accesskey");
		    	MediaType mediaType = MediaType.parse("application/json");
		    	RequestBody body = RequestBody.create(mediaType, "{\r\n "
		    			+ "\"info\": {"
		    			+ "\r\n \"summary\": \"APA Automated Execution (PoC)\","
		    			+ "\r\n  \"description\": \"This execution is automatically created!\","
		    			+ "\r\n   \"testPlanKey\": \""+testPlan+"\","
		    			+ "\r\n    \"testEnvironments\": ["
		    			+ "\r\n      \"SIT\"\r\n]\r\n  },"
		    			+ "\r\n  			\"tests\": [\r\n "
		    			+ "{\r\n      			\"testKey\": \""+testCaseKey+"\","
		    			+ "\r\n      			\"comment\": \""+remarks+"\","
		    			+ "\r\n     			 \"status\": \""+testResult+"\","
		    			+ "\r\n      			  \"evidences\": [\r\n        {"
		    			+ "\r\n          						\"data\": \""+base64File+"\","
		    			+ "\r\n         						 \"filename\": \""+filename+"\","
		    			+ "\r\n          						\"contentType\": \"application/zip\"\r\n }\r\n ]\r\n}"
		    			+ "\r\n							]"
		    			+ "\r\n"
		    			+ "				}");
		    	
			
		Request request = new Request.Builder()
				  .url("https://jira.allianzgi-intra.com:8443/rest/raven/2.0/import/execution")
				  .method("POST", body)
				  .addHeader("Authorization",accessKey)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .build();
		try {
			Response response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
}