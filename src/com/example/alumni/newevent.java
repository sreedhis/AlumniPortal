package com.example.alumni;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.alumni.studreg.insertTask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class newevent extends Activity {
	Button add,cancel;
	EditText ename,elocation,evenue,dept,edate,etime,contactno,description,criteria;
	String result;
    String line=null;
    InputStream is=null;
    int code;
    int flag=0;
    public ProgressDialog pDialog;
    String solu;
    int value;
	String ename1,elocation1,evenue1,dept1,edate1,etime1,contactno1,description1,criteria1;
	
	 static final int DATE_DIALOG_ID = 0;
	    public int mYear, mMonth, mDay;
	    
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newevent);
       			add=(Button)findViewById(R.id.button1);
        			cancel=(Button)findViewById(R.id.button2);
        			ename=(EditText)findViewById(R.id.editText1);
        			elocation=(EditText)findViewById(R.id.editText2);
        			evenue=(EditText)findViewById(R.id.editText3);
        			dept=(EditText)findViewById(R.id.editText4);
        			etime=(EditText)findViewById(R.id.editText6);
        			criteria=(EditText)findViewById(R.id.editText9);
        			description=(EditText)findViewById(R.id.editText8);
        	        contactno=(EditText)findViewById(R.id.editText7);
        	        Calendar c = Calendar.getInstance();
        	        mYear = c.get(Calendar.YEAR);
        	        mMonth = c.get(Calendar.MONTH);
        	        mDay = c.get(Calendar.DAY_OF_MONTH);
        	        edate=(EditText)findViewById(R.id.editText5);
        	        edate.setOnClickListener(new View.OnClickListener() {
        	            @Override
        	            public void onClick(View v) {
        	                flag = 1;
        	                showDialog(DATE_DIALOG_ID);
        	            }
        	        });
        	    
        			 cancel.setOnClickListener(new View.OnClickListener() {
        		    		
        		    		@Override
        		    		public void onClick(View v) {
        		    			// TODO Auto-generated method stub
        		    			Intent intent=new Intent(newevent.this,event.class);
        		    			startActivity(intent);
        		    		}});
        			 add.setOnClickListener(new View.OnClickListener() {
     		    		
     		    		@Override
     		    		public void onClick(View v) {
     		    			// TODO Auto-generated method stub
     		    			//Intent intent=new Intent(newevent.this,event.class);
     		    			//startActivity(intent);
     		    			ename1=ename.getText().toString();
     		    			elocation1=elocation.getText().toString();
     		    			evenue1=evenue.getText().toString();
     		    			dept1=dept.getText().toString();
     		    			edate1=edate.getText().toString();
     		    			etime1=etime.getText().toString();
     		    			contactno1=contactno.getText().toString();
     		    			description1=description.getText().toString();
     		    			criteria1=criteria.getText().toString();
     		    			new insertTask().execute("");
     						//Intent intent=new Intent(newevent.this, event.class);
     				        //startActivity(intent);	
     		    		}});

}

protected Dialog onCreateDialog(int id) {
    switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    }

    return null;

}

private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        if (flag == 1) {
           edate.setText(new StringBuilder().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay));
        } 
    }

};

class insertTask extends AsyncTask<String,String,String> {
protected void onPreExecute() {
    super.onPreExecute();
    pDialog = new ProgressDialog(newevent.this);
    pDialog.setMessage("Creating event..");
    pDialog.setIndeterminate(false);
    pDialog.setCancelable(true);
    pDialog.show();
}
protected String doInBackground(String... params) {
     insert();
    return null;
}
public void insert() {
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("ename", ename1));
    nameValuePairs.add(new BasicNameValuePair("elocation", elocation1));
    nameValuePairs.add(new BasicNameValuePair("evenue", evenue1));
    nameValuePairs.add(new BasicNameValuePair("dept", dept1));
    nameValuePairs.add(new BasicNameValuePair("edate", edate1));
    nameValuePairs.add(new BasicNameValuePair("etime", etime1));
    nameValuePairs.add(new BasicNameValuePair("contactno", contactno1));
    nameValuePairs.add(new BasicNameValuePair("description", description1));
    nameValuePairs.add(new BasicNameValuePair("criteria", criteria1));
    try{
    }catch(Exception e){
    }
    try {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/newevent.php");
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        Log.e("pass 1", "connection success ");
    } catch (Exception e) {
        Log.e("Fail 1", e.toString());
        Toast.makeText(getApplicationContext(), "Invalid IP Address", Toast.LENGTH_LONG).show();
    }
    try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        result = sb.toString();
        Log.e("pass 2", "connection success ");
    } catch (Exception e) {
        Log.e("Fail 2", e.toString());
    }
    try {
        JSONObject json_data = new JSONObject(result);
        code = (json_data.getInt("code"));
    } catch (Exception e) {
        Log.e("Fail 3", e.toString());
    }
    runOnUiThread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			   if (code == 1) {
	          Toast.makeText(getApplicationContext(), "Product Added",Toast.LENGTH_LONG);
	        //name.setText("");	
	            } else {
	            	Toast.makeText(getApplicationContext(), "User creation Failed",Toast.LENGTH_LONG);
	            }
			
		}
	});
    
 
}
public void onPostExecute(String result) {
    pDialog.dismiss();


}}
	}


