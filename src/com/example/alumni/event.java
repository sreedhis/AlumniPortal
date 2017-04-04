package com.example.alumni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class event extends Activity{
	Button add;
	String name;
	int code=0;
	TextView count;
    public ProgressDialog pDialog;
    private String jsonResult;
    private String url = "http://192.168.43.47/alumni/event.php/";
    private ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        			add=(Button)findViewById(R.id.button2);
        			count=(TextView)findViewById(R.id.textView1);
        			//filter=(Button)findViewById(R.id.button1);
        			 add.setOnClickListener(new View.OnClickListener() {
        		    		
        		    		@Override
        		    		public void onClick(View v) {
        		    			// TODO Auto-generated method stub
        		    			Intent intent=new Intent(event.this,newevent.class);
        		    			startActivity(intent);
        		    		}});
        			  listView = (ListView) findViewById(R.id.listView1);
        		        accessWebService();
        		        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        		            @Override
        		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		                int itemPosition = position;
        		                String product = ((TextView) view).getText().toString();
        		               Intent in=new Intent(event.this,listevent.class);
        		               in.putExtra("name", product);
        		               startActivity(in);
        		                //Toast.makeText(getApplicationContext(),product, Toast.LENGTH_LONG).show();
        		             
        		            }
        		        });

}

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
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
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }

    // build hash set for list view
    public void ListDrwaer() {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("emp_info");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                name = jsonChildNode.optString("ename");
                String outPut = name;
                employeeList.add(createEmployee("employees", outPut));
                code=code+1;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   count.setText("Events(" + code + ")");
                }
            });

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();


        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList, android.R.layout.simple_list_item_1, new String[] { "employees" }, new int[] {android.R.id.text1});
        listView.setAdapter(simpleAdapter);
    }

    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }

}
