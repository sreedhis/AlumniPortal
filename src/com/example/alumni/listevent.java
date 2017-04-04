package com.example.alumni;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class listevent extends Activity{
	String uname;
	TextView ename,elocation,evenue,dept,edate,etime,contactno,description,criteria;
	String ename1,elocation1,evenue1,dept1,edate1,etime1,contactno1,description1,criteria1;
	
	 String[] output;
	    HttpPost httppost;
	    InputStream is = null;
	    StringBuffer buffer;
	    String result="";
	    HttpResponse response;
	    HttpClient httpclient;
	    List<NameValuePair> nameValuePairs;
	    String number;
String name;
	    
	    @Override
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listevent);
	        Intent in=getIntent();
	        name=in.getStringExtra("name"); 
	        Toast.makeText(getApplicationContext(),name, Toast.LENGTH_LONG).show();
	        ename=(TextView)findViewById(R.id.textView10);
			elocation=(TextView)findViewById(R.id.textView11);
			evenue=(TextView)findViewById(R.id.textView12);
			dept=(TextView)findViewById(R.id.textView13);
			edate=(TextView)findViewById(R.id.textView14);
			etime=(TextView)findViewById(R.id.textView15);
			criteria=(TextView)findViewById(R.id.textView16);
			description=(TextView)findViewById(R.id.textView17);
	        contactno=(TextView)findViewById(R.id.textView18);
	            
	     	new Thread(new Runnable() {
	         	            public void run() {

	         	                login1();
	         	            }
	         	        }).start();

		}
		
		public String login1(){
	        try{

	            httpclient=new DefaultHttpClient();
	            httppost= new HttpPost("http://192.168.43.47/alumni/listevent.php/");
	            nameValuePairs = new ArrayList<NameValuePair>(1);
	            nameValuePairs.add(new BasicNameValuePair("email",name));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            Log.e("log_tag", "connection success");
	            if (response.equals("student")) {
	                runOnUiThread(new Runnable() {
	                    public void run() {
	                        Toast.makeText(listevent.this, "", Toast.LENGTH_SHORT).show();
	                        }
	                });
	            }
	            else {
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                    }
	                });
	            }

	        } catch (Exception e) {
	            Log.e("log_tag", "Error in http connection"+ e.toString());
	            Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();
	        }

	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();

	            result = sb.toString();
	            Log.e("log_tag", result);

	        } catch (Exception e) {
	            Log.e("log_tag", "Error converting result" + e.toString());
	            Toast.makeText(getApplicationContext(), "Input reading fail", Toast.LENGTH_SHORT).show();

	        }
	       
	        try {
	            JSONArray jArray = new JSONArray(result);
	            for (int i = 0; i < jArray.length() - 1; i++) {
	                JSONObject json_data = jArray.getJSONObject(i);
	                ename1= String.valueOf(json_data.getString("ename"));
	                elocation1= String.valueOf(json_data.getString("elocation"));
	                evenue1= String.valueOf(json_data.getString("evenue"));
	                dept1= String.valueOf(json_data.getString("dept"));
	                edate1= String.valueOf(json_data.getString("edate"));
	                etime1= String.valueOf(json_data.getString("etime"));
	                contactno1= String.valueOf(json_data.getString("contactno"));
	                description1= String.valueOf(json_data.getString("description"));
	                criteria1= String.valueOf(json_data.getString("criteria"));
	              
	                
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                    	ename.setText(ename1);
	                    	elocation.setText(elocation1);
	                    	evenue.setText(evenue1);
	                    	dept.setText(dept1);
	                    	edate.setText(edate1);
	                    	etime.setText(etime1);
	                    	contactno.setText(contactno1);
	                    	description.setText(description1);
	                    	criteria.setText(criteria1);
	                        }
	                });
	            }
	        }
	        catch (JSONException e) {
	            Log.e("log_tag", "Error parsing data" + e.toString());
	            Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
	        }
	        return null;
	    }
}
