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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class find extends Activity {
	Button search;
	EditText name1;
	String name,name2;
	String user,mobile,department;
	int code=0;
	//TextView count;
    public ProgressDialog pDialog;
    private String jsonResult;
    private String url = "http://192.168.43.47/alumni/find.php/";
    private ListView listView;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
       search=(Button)findViewById(R.id.button1);
       name1=(EditText)findViewById(R.id.editText1);
      // count=(TextView)findViewById(R.id.textView1);
        search.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			name2=name1.getText().toString();
    			new JsonReadTask().execute("");
    			//Intent intent=new Intent(find.this,MainActivity.class);
    			//startActivity(intent);
    		}});
		  listView = (ListView) findViewById(R.id.listView1);
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                int itemPosition = position;
	                String product = ((TextView) view).getText().toString();
	               
	                Toast.makeText(getApplicationContext(),product, Toast.LENGTH_LONG).show();
	              //  Intent intent  = new Intent(shoplist.this,shopdetails.class);
	               // intent.putExtra("name",product);
	               // intent.putExtra("email",n1);
	                //startActivity(intent);
	            }
	        });

}

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("name",name2));
        
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/find.php/");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                Log.e("Result",jsonResult);
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
                     user= String.valueOf(json_data.getString("name"));
                    department = String.valueOf(json_data.getString("department"));
               mobile= String.valueOf(json_data.getString("phone"));
               String output=user+"\n"+department+"\n"+mobile;     
               employeeList.add(createEmployee("employees", output));
                    code=code+1;
                }
        }catch (JSONException e) {
            Log.e("Failed",e.toString());
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
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

