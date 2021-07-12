package utils;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class CreateTestExecution {
	private static char[] key;
	Logger log = LogManager.getLogger(CreateTestExecution.class);
	public static void creatExecution() throws IOException {
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				ReadPropertiesFile rp =new ReadPropertiesFile();
				String accessKey =rp.readPropertiesFile("accesskey");
		    	MediaType mediaType = MediaType.parse("application/json");
		    	RequestBody body = RequestBody.create(mediaType, "{\r\n "
		    			+ "\"fields\": {"
		    			+ "\r\n \"project\": {"
		    			+ "\r\n  \"key\": \"AMSEG\"\r\n },"
		    			+ "\r\n   \"summary\": \"Execution of automated tests\","
		    			+ "\r\n    \"description\": \"This execution is automatically created\","						
		    			+ "\r\n     \"issuetype\": {"
		    			+ "\r\n      \"name\": \"Test Execution\"\r\n  },"						
		    			+ "\r\n       \"customfield_13930\": ["
		    			+ "\r\n         \"SIT\"\r\n]\r\n  }"
		    			+ "				}");
		
		//System.out.println(body);
		//https://jira.allianzgi-intra.com:8443 - Prod Url
		//https://jira-test.allianzgi-intra.com:8443 -- Test Url
		Request request = new Request.Builder()
				  .url("https://jira.allianzgi-intra.com:8443/rest/api/2/issue")
				  .method("POST", body)
				  .addHeader("Authorization",accessKey)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .build();
		try {
			Response response = client.newCall(request).execute();
			String stringResponse = response.body().string();
			JSONObject jj = new JSONObject(stringResponse);
			rp.setVariable(9, "testexecutionkey="+jj.getString("key"));
			//rp.write(jj.getString("key"));
			System.out.println(jj.getString("key"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
}