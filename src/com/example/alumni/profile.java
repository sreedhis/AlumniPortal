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
import android.widget.TextView;
import android.widget.Toast;

public class profile extends Activity{
	Button editprofile,ok;
	String uname;
	TextView name,department,dob,email,phone,experience;
	String name1,department1,dob1,email1,phone1,experience1;
	
	 String[] output;
	    HttpPost httppost;
	    InputStream is = null;
	    StringBuffer buffer;
	    String result="";
	    HttpResponse response;
	    HttpClient httpclient;
	    List<NameValuePair> nameValuePairs;
	    String number;
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
        name=(TextView)findViewById(R.id.textView3);
        department=(TextView)findViewById(R.id.textView5);
        dob=(TextView)findViewById(R.id.textView7);
        email=(TextView)findViewById(R.id.textView9);
        phone=(TextView)findViewById(R.id.textView11);
        experience=(TextView)findViewById(R.id.textView13);
        
        
        
        			editprofile=(Button)findViewById(R.id.button2);
        			ok=(Button)findViewById(R.id.button1);
        			Intent i= getIntent();
        	        uname = i.getExtras().getString("pro");
        	        
       			 editprofile.setOnClickListener(new View.OnClickListener() {
        		    		
        		    		@Override
        		    		public void onClick(View v) {
        		    			// TODO Auto-generated method stub
        		    			Intent intent=new Intent(profile.this,editprofile.class);
        		    			intent.putExtra("mmm",email.getText().toString());
        		    			startActivity(intent);
        		    		}});
    	

         	        new Thread(new Runnable() {
         	            public void run() {

         	                login1();
         	            }
         	        }).start();

	}
	
	public String login1(){
        try{

            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://192.168.43.47/alumni/profile.php/");
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
                        Toast.makeText(profile.this, "", Toast.LENGTH_SHORT).show();
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
                name1= String.valueOf(json_data.getString("name"));
                department1= String.valueOf(json_data.getString("department"));
                dob1= String.valueOf(json_data.getString("dob"));
                email1= String.valueOf(json_data.getString("email"));
                phone1= String.valueOf(json_data.getString("phone"));
                experience1= String.valueOf(json_data.getString("working"));
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(name1);
                        department.setText(department1);
                        dob.setText(dob1);
                        email.setText(email1);
                        phone.setText(phone1);
                        experience.setText(experience1);
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

