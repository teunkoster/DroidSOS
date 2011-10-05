package nl.gewis.teun.susos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class Helperfunctions {
	
	public static JSONObject getWebContent(String pvUrl)
	{
    	URL url = null;
		try {
			url = new URL(pvUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection conn;
		JSONObject ret = null;
		String json = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
			json = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ret = new JSONObject(json);			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
    public static Boolean ApiKeyValid(String pvApiKey)
    {
    	String urlStr = "https://secure.gewis.nl/susos/api/verifyApiKey.php?apikey="+ pvApiKey;
    	JSONObject ret = getWebContent(urlStr);
    	String status = null;
		try {
			status = ret.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return (status == "200");
    }
}
