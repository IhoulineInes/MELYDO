package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorTool {
	
	public static JSONObject servicerefused(String raison,int num){
		JSONObject json=new JSONObject();
		try {
			json.put("code",num);
			json.put("message", raison);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static JSONObject serviceaccepted(String raison,int num){
		JSONObject json=new JSONObject();
		try {
			json.put("code",num);
			json.put("message", raison);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
		
	}
	
}
