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
import android.widget.TextView;
import android.widget.Toast;

public class listinvitation extends Activity {
	String uname;
	TextView funname,location,venue,postname,bname,gname,fundate,funstart,funend,words;
	String funname1,location1,venue1,postname1,bname1,gname1,fundate1,funstart1,funend1,words1;
	
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
	        setContentView(R.layout.profile);
	       Intent in=getIntent();
	        name=in.getStringExtra("name"); 
	        Toast.makeText(getApplicationContext(),name, Toast.LENGTH_LONG).show();
	        funname=(TextView)findViewById(R.id.textView1);
		       location=(TextView)findViewById(R.id.textView2);
		       venue=(TextView)findViewById(R.id.textView3);
		       postname=(TextView)findViewById(R.id.textView4);
		       bname=(TextView)findViewById(R.id.textView5);
		       gname=(TextView)findViewById(R.id.textView6);
		       fundate=(TextView)findViewById(R.id.textView7);
		       funstart=(TextView)findViewById(R.id.textView8);
		       funend=(TextView)findViewById(R.id.textView9);
		       words=(TextView)findViewById(R.id.textView19);
		            
		       
	            
	        			Intent i= getIntent();
	        	        uname = i.getExtras().getString("pro");
	     	new Thread(new Runnable() {
	         	            public void run() {

	         	                login1();
	         	            }
	         	        }).start();

		}
		
		public String login1(){
	        try{

	            httpclient=new DefaultHttpClient();
	            httppost= new HttpPost("http://192.168.43.47/alumni/listinvitation.php/");
	            nameValuePairs = new ArrayList<NameValuePair>(1);
	            nameValuePairs.add(new BasicNameValuePair("email",uname));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            Log.e("log_tag", "connection success");
	            if (response.equals("student")) {
	                runOnUiThread(new Runnable() {
	                    public void run() {
	                        Toast.makeText(listinvitation.this, "", Toast.LENGTH_SHORT).show();
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
	                funname1= String.valueOf(json_data.getString("funname"));
	                location1= String.valueOf(json_data.getString("location"));
	                venue1= String.valueOf(json_data.getString("venue"));
	                postname1= String.valueOf(json_data.getString("postname"));
	                bname1= String.valueOf(json_data.getString("bname"));
	                gname1= String.valueOf(json_data.getString("gname"));
	                fundate1= String.valueOf(json_data.getString("fundate"));
	                funstart1= String.valueOf(json_data.getString("funstart"));
	                funend1= String.valueOf(json_data.getString("funend"));
	                words1= String.valueOf(json_data.getString("words"));
	              
	                
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                    	funname.setText(funname1);
	                    	location.setText(location1);
	                    	venue.setText(venue1);
	                    	postname.setText(postname1);
	                    	bname.setText(bname1);
	                    	gname.setText( gname1);
	                    	fundate.setText(fundate1);
	                    	funstart.setText(funstart1);
	                    	funend.setText(funend1);
	                    	words.setText(words1);
	        
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
