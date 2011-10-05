package nl.gewis.teun.susos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import nl.gewis.teun.susos.SuSOSUser;

public class SuSOS extends Activity {

	private TextView tvBalance;
	private TextView tvName;
	private TextView tvCharge;
	private Button btnRefresh;
	private SuSOSUser user = null;
	
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.main);
        
        btnRefresh = (Button)this.findViewById(R.id.refresh);
        tvBalance = (TextView)this.findViewById(R.id.balance);
        tvName = (TextView)this.findViewById(R.id.name);
        tvCharge = (TextView)this.findViewById(R.id.charge);
        
        btnRefresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				refreshInfo();
			}
		});
    }
        
    private void refreshInfo()
    {
    	if(user == null){
    		return;
    	}
        tvBalance.setText("Balance: \n\u20AC " + user.getBalance());
        tvName.setText(user.getName());
        tvCharge.setText("Charge: \n\u20AC " + user.getCharge());
    }
    
    @Override
    public void onStart() 
    {
    	super.onStart();          
        SharedPreferences settings = getPreferences(0);
        if(settings.contains("apikey")){
        	String apiKey = settings.getString("apikey", null);
        	if(Helperfunctions.ApiKeyValid(apiKey)){
        		user = new SuSOSUser(apiKey);
        	}else {
    	     	String ID = "5516"; 
    	     	String pin = "1234";
    	     	
    	     	
    	     	
    	     	user = new SuSOSUser(ID, pin);
    	     	Editor editor = settings.edit();
    	     	editor.putString("apikey", user.getApiKey());
        	}
        }else {
	     	String ID = "5516"; 
	     	String pin = "1234";
	     	user = new SuSOSUser(ID, pin);
	     	Editor editor = settings.edit();
	     	editor.putString("apikey", user.getApiKey());
	    }   
        refreshInfo();
    }
}