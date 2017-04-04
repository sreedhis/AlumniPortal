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

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alumni.studreg.insertTask;
public class facultyreg extends Activity {
	String result;
    String line=null;
    InputStream is=null;
    int code;
    int flag=0;
    public ProgressDialog pDialog;
    String solu;
    int value;
	String dept,year,gender,name1,dob1,email1,password1,phone1,experience1,designation1;
	Button signup;
	EditText name,dob,email,password,phone,experience,designation;
    private Spinner spinner1;
    private Spinner spinner2;
    static final int DATE_DIALOG_ID = 0;
    public int mYear, mMonth, mDay;
    private static final String[] paths = {"Select department","IT", "CSE", "ECE","MECH","CIVIL"};
    //private static final String[] paths1 = {"Year of Passing","2013","2014", "2015","2016","2017"};
    private static final String[] paths1 = {"Gender","Male","Female"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factultyreg);
        signup=(Button)findViewById(R.id.button1);
        name= (EditText)findViewById(R.id.editText1);
        dob=(EditText)findViewById(R.id.editText2);
        email=(EditText)findViewById(R.id.editText7);
        password=(EditText)findViewById(R.id.editText3);
        phone=(EditText)findViewById(R.id.editText6);
        experience=(EditText)findViewById(R.id.editText5);
        designation=(EditText)findViewById(R.id.editText4);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
      
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                showDialog(DATE_DIALOG_ID);
            }
        });
        
        
        signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent=new Intent(facultyreg.this, login.class);
		        //startActivity(intent);
				name1=name.getText().toString();
				//gender=gender.getText().toString();
				dob1=dob.getText().toString();
				email1=email.getText().toString();
				password1=password.getText().toString();
				//dept=name.getText().toString();
				//year=name.getText().toString();
				phone1=phone.getText().toString();
				experience1=experience.getText().toString();
				designation1=designation.getText().toString();
				new insertTask().execute("");
			
			}
		});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(facultyreg.this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = paths[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(facultyreg.this, android.R.layout.simple_spinner_item, paths1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = paths1[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        

	
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
	                dob.setText(new StringBuilder().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay));
	            } 
	        }

	    };
	class insertTask extends AsyncTask<String,String,String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(facultyreg.this);
            pDialog.setMessage("Creating User..");
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
            nameValuePairs.add(new BasicNameValuePair("name", name1));
            nameValuePairs.add(new BasicNameValuePair("gender", gender));
            nameValuePairs.add(new BasicNameValuePair("dob", dob1));
            nameValuePairs.add(new BasicNameValuePair("email", email1));
            nameValuePairs.add(new BasicNameValuePair("password", password1));
            nameValuePairs.add(new BasicNameValuePair("department", dept));
            nameValuePairs.add(new BasicNameValuePair("year", year));
            nameValuePairs.add(new BasicNameValuePair("phone", phone1));
            nameValuePairs.add(new BasicNameValuePair("experience", experience1));
            nameValuePairs.add(new BasicNameValuePair("designation", designation1));
            
            try{
            }catch(Exception e){
            }
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/facultyreg.php");
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
