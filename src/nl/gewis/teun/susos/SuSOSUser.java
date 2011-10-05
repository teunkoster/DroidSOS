package nl.gewis.teun.susos;

import org.json.JSONException;
import org.json.JSONObject;

public class SuSOSUser {
	
	private String _apiKey = null;
	
	private String _name;
	private int _balance;
	private int _charge;
	
	public SuSOSUser(String pvApiKey)
	{
		_apiKey = pvApiKey;
	}
	
	public SuSOSUser(String pvID, String pvPin)
	{
		reqApiKey(pvID, pvPin);
	}
	
	public String getApiKey()
	{
		refreshInfo();
		return _apiKey;
	}
	
	public String getName()
	{
		refreshInfo();
		return _name;
	}
	
	public int getBalance()
	{
		refreshInfo();
		return _balance;
	}
	
	public int getCharge()
	{
		refreshInfo();
		return _charge;
	}
	
	public void reqApiKey(String pvID, String pvPin)
    {
    	String urlStr = 
    		"https://secure.gewis.nl/susos/api/verifyLogin.php?gebruikersnaam=g" + pvID + "&pincode=" + pvPin;  
    	
    	JSONObject json = Helperfunctions.getWebContent(urlStr);
    	try {
			if(json.getInt("status") != 200){
				//unknown username or pin
			} else {
				try{ 
					_apiKey = json.getString("apikey");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			//vage meuk terug gekregen, API doet raar ofzo
			e.printStackTrace();
		}
    }

	private void refreshInfo() 
	{
		JSONObject info = getUserInfo(_apiKey);
		try {
			_name = info.getString("name");
			_balance = info.getInt("balance");
			_charge = info.getInt("charge");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//gets webcontent and parses it to a JSONObject
	
	
	private static JSONObject getUserInfo(String pvApikey)
	{
		String urlStr = 
	       	"https://secure.gewis.nl/susos/api/getUserInfo.php?apikey=" + pvApikey;
		
		JSONObject json = Helperfunctions.getWebContent(urlStr);
		
		JSONObject userInfo = null;
		try{
			if(json.getInt("status") != 200){
			//unknown username or pin
			} else {
				try{ 
					userInfo = json.getJSONObject("userinfo");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
		//vage meuk terug gekregen, API doet raar ofzo
			e.printStackTrace();
		}
		return userInfo;
	}
	

	
	
	
}
