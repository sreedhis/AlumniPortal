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

public class mrandmrs extends Activity{
	Button newinvitation;
	String name,name1,venue,date,time,words;
	int code=0;
	String function;
	TextView count;
	 public ProgressDialog pDialog;
	    private String jsonResult;
	    private String url = "http://192.168.43.47/alumni/mrandmrs.php/";
	    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrandmrs);
       count=(TextView)findViewById(R.id.textView1);
        newinvitation=(Button)findViewById(R.id.button1);
       
        newinvitation.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(mrandmrs.this,newinvitation.class);
			startActivity(intent);
		}
	});
        
        listView = (ListView) findViewById(R.id.listView1);
        accessWebService();
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
String fun="Function:" +(i+1);
name = "\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Bride name:"+jsonChildNode.optString("bname");
name1 ="\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Groom name:"+jsonChildNode.optString("gname");
function="\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Type:"+jsonChildNode.optString("funname");
venue="\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Venue:"+jsonChildNode.optString("venue");
date="\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Date:"+jsonChildNode.optString("fundate");
time="\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Time:"+jsonChildNode.optString("funstart");
words="\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"Few Words:"+jsonChildNode.optString("words");
String outPut =fun+"\n"+name+"\n"+name1+"\n"+function+"\n"+venue+"\n"+date+"\n"+time+"\n"+words;
employeeList.add(createEmployee("employees", outPut));
code=code+1;
}
runOnUiThread(new Runnable() {
@Override
public void run() {
   count.setText("Posts(" + code + ")");
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
   
