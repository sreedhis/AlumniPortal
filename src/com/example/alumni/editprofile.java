package com.example.alumni;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editprofile extends Activity {
	Button cancel,update;
	String mail;
	EditText name,phone,working;
	String name1,phone1,working1,mail1;
    InputStream is = null;
    String result;
    String line = null;
    int code = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        update=(Button)findViewById(R.id.button1);
        name=(EditText)findViewById(R.id.editText1);
        phone=(EditText)findViewById(R.id.editText2);
        working=(EditText)findViewById(R.id.editText3);
      
        Intent i= getIntent();
        mail = i.getExtras().getString("mmm");
        Toast.makeText(getApplicationContext(), mail,Toast.LENGTH_LONG).
        show();
       update.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			name1=name.getText().toString();
			phone1=phone.getText().toString();
			working1=working.getText().toString();
			mail1=mail;
			new insertTask().execute("");
			Toast.makeText(editprofile.this, "Update Success", Toast.LENGTH_SHORT).show();
		//	Intent i=new Intent(editprofile.this,login.class);
			//startActivity(i);
		}
	});
}
    class insertTask extends AsyncTask<String, String, String> {
        /*protected void onPreExecute() {
            super.onPreExecute();
           }*/
        protected String doInBackground(String... params) {
            result = insert();
            return null;
        }
        public String insert() {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name", name1));
            nameValuePairs.add(new BasicNameValuePair("phone", phone1));
            nameValuePairs.add(new BasicNameValuePair("working", working1));
            nameValuePairs.add(new BasicNameValuePair("email",mail1));
            
            try {
            } catch (Exception e) {
            }
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/editprofile.php/");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.e("pass 2", result);
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
            if (code == 1) {
            	  //Toast.makeText(getApplicationContext(), "Update Successful",Toast.LENGTH_LONG).show();
            	
            	return "1";
            } else {
                return "0";
            }
        }
        public void onPostExecute(String result) {
        }
    }

}
