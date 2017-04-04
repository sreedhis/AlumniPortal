package com.example.alumni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class chat2 extends Activity {
	  HttpPost httppost;
	    Button seeds;
	    int code;
	      String line = null;
	    public String jsonResult;
	    InputStream is = null;
	    StringBuffer buffer;
	    String result="";
	    Button send;
	    TextView msg;
	    JSONArray jsonMainNode;
	    String outPut;
	    JSONObject jsonResponse;
	    HttpClient httpclient;
	    List<NameValuePair> nameValuePairs;
	    public ProgressDialog pDialog;
	    ListView list;
	    String email,question,message;
	    TextView  qst;
	    SimpleAdapter simpleAdapter;
	    SQLiteDatabase db;
	    Cursor rs;
	    String answers,ansmail,email1;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.chat);
	        send=(Button)findViewById(R.id.button);
	        msg=(EditText)findViewById(R.id.edit_text);
	       list=(ListView)findViewById(R.id.listView13);
	        Intent in = getIntent();
	        email= in.getExtras().getString("pro");
	        Toast.makeText(getApplicationContext(),email, Toast.LENGTH_LONG).show();
	       new JsonReadTask().execute("");
	        send.setOnClickListener(new View.OnClickListener() {
	           @Override
	           public void onClick(View v) {
	               message = msg.getText().toString();
	               Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
	               new insertTask().execute("");
	           if(code>1) {
	        	   Toast.makeText(getApplicationContext(),"submit", Toast.LENGTH_LONG).show();
	               new JsonReadTask().execute("");
	           }}
	       });
	    }
	    private void scrollMyListViewToBottom() {
	        list.post(new Runnable() {
	            @Override
	            public void run() {
	                list.setSelection(simpleAdapter.getCount() - 1);
	            }
	        });
	    }
	    private class JsonReadTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... params) {

	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	  
	            try {
	                HttpClient httpclient = new DefaultHttpClient();
	                HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/answers.php/");
	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                HttpResponse response = httpclient.execute(httppost);
	                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
	                Log.e("Result", jsonResult);
	            }
	            catch (ClientProtocolException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return null;
	        }
	        private StringBuilder inputStreamToString(InputStream is) {
	            String rLine = "";
	            StringBuilder answer = new StringBuilder();
	            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	            try {
	                while ((rLine = rd.readLine()) != null) {
	                    answer.append(rLine);
	                }
	            }
	            catch (IOException e) {
	                Toast.makeText(getApplicationContext(),
	                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
	            }
	            return answer;
	        }
	        @Override
	        protected void onPostExecute(String result) {
	            ListDrwaer();
	        }
	    }
	    public void ListDrwaer() {
	        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
	        try {
	            JSONArray jArray = new JSONArray(jsonResult);
	            for (int i = 0; i < jArray.length() - 1; i++) {
	                JSONObject json_data = jArray.getJSONObject(i);
	                answers = String.valueOf(json_data.getString("message"));
	                ansmail= String.valueOf(json_data.getString("mailid"));
	                outPut = ansmail + "\n" +answers;
	                employeeList.add(createEmployee("employees", outPut));
	                code=code+1;
	            }
	            if(code>1){
	                scrollMyListViewToBottom();
	            }
	        }catch (JSONException e) {
	            Log.e("Failed",e.toString());
	            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
	        }

	      simpleAdapter = new SimpleAdapter(this, employeeList, android.R.layout.simple_list_item_1, new String[] { "employees" }, new int[] {android.R.id.text1});
	        list.setAdapter(simpleAdapter);
	    }
	    private HashMap<String, String> createEmployee(String name, String number) {
	        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
	        employeeNameNo.put(name, number);
	        return employeeNameNo;
	    }
	    class insertTask extends AsyncTask<String, String, String> {
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(chat2.this);
	            pDialog.setMessage("sending message..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	        protected String doInBackground(String... params) {
	            result = insert();
	            return null;
	        }
	        public String insert() {
	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            nameValuePairs.add(new BasicNameValuePair("answer",message));
	            nameValuePairs.add(new BasicNameValuePair("mailid",email));
	            try{
	            }catch(Exception e){
	            }
	            try {
	                HttpClient httpclient = new DefaultHttpClient();
	                HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/submit.php/");
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
	                    if (code == 1) {
	                        Toast.makeText(getApplicationContext(), "Answer submitted", Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(getApplicationContext(), "Sorry failed", Toast.LENGTH_SHORT).show();
	                    }
	                }
	            });
	            return null;
	        }
	        public void onPostExecute(String result) {
	            pDialog.dismiss();}}

}
