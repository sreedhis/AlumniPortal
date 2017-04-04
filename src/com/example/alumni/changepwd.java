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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class changepwd extends Activity{
	Button change;
	EditText old, password, confirm,email;
    public ProgressDialog pDialog;
    InputStream is = null;
    String result;
    String line = null;
    int code = 0;
    String old1, password1, confirm1,email1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);
        change=(Button)findViewById(R.id.button1);
        //signup=(Button)findViewById(R.id.button2);
        old = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        confirm = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);
       change.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			old1 = old.getText().toString();
            password1 = password.getText().toString();
            confirm1 = confirm.getText().toString();
            email1 = email.getText().toString();
            new insertTask().execute("");
	//		Intent intent=new Intent(changepwd.this,MainActivity.class);
		//	startActivity(intent);
		}
	});
    }        	
    class insertTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(changepwd.this);
            pDialog.setMessage("Creating Product..");
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
            nameValuePairs.add(new BasicNameValuePair("old", old1));
            nameValuePairs.add(new BasicNameValuePair("password", password1));
            nameValuePairs.add(new BasicNameValuePair("confirm", confirm1));
            nameValuePairs.add(new BasicNameValuePair("email",email1));
            try {
            } catch (Exception e) {
            }
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/changepwd.php");
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
                return "1";
            } else {
                return "0";
            }
        }
        public void onPostExecute(String result) {
            pDialog.dismiss();
        }
    }

}
