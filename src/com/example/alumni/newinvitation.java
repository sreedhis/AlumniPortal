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

import com.example.alumni.newevent.insertTask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class newinvitation extends Activity {
	Button back,post;
	EditText funname,location,venue,postname,bname,gname,fundate,funstart,funend,words;
	String result;
    String line=null;
    InputStream is=null;
    int code;
    int flag=0;
    public ProgressDialog pDialog;
    String solu;
    int value;
    String funname1,location1,venue1,postname1,bname1,gname1,fundate1,funstart1,funend1,words1;
    static final int DATE_DIALOG_ID = 0;
    public int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newinvitation);
        post=(Button)findViewById(R.id.button2);
        back=(Button)findViewById(R.id.button1);
        funname=(EditText)findViewById(R.id.editText1);
		location=(EditText)findViewById(R.id.editText2);
		venue=(EditText)findViewById(R.id.editText3);
		postname=(EditText)findViewById(R.id.editText4);
		bname=(EditText)findViewById(R.id.editText5);
		gname=(EditText)findViewById(R.id.editText6);
		funstart=(EditText)findViewById(R.id.editText8);
        funend=(EditText)findViewById(R.id.editText9);
        words=(EditText)findViewById(R.id.editText10);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        fundate=(EditText)findViewById(R.id.editText7);
        fundate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                showDialog(DATE_DIALOG_ID);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(newinvitation.this,mrandmrs.class);
			startActivity(intent);
		}
	});
        post.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			//Intent intent=new Intent(newinvitation.this,mrandmrs.class);
    			//startActivity(intent);
    			funname1=funname.getText().toString();
	    			location1=location.getText().toString();
	    			venue1=venue.getText().toString();
	    			postname1=postname.getText().toString();
	    			bname1=bname.getText().toString();
	    			gname1=gname.getText().toString();
	    			fundate1=fundate.getText().toString();
	    			funstart1=funstart.getText().toString();
	    			funend1=funend.getText().toString();
	    			words1=words.getText().toString();
	    			new insertTask().execute("");
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
               fundate.setText(new StringBuilder().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay));
            } 
        }

    };

    class insertTask extends AsyncTask<String,String,String> {
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(newinvitation.this);
        pDialog.setMessage("Creating invitation..");
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
        nameValuePairs.add(new BasicNameValuePair("funname", funname1));
        nameValuePairs.add(new BasicNameValuePair("location", location1));
        nameValuePairs.add(new BasicNameValuePair("venue", venue1));
        nameValuePairs.add(new BasicNameValuePair("postname", postname1));
        nameValuePairs.add(new BasicNameValuePair("bname", bname1));
        nameValuePairs.add(new BasicNameValuePair("gname", gname1));
        nameValuePairs.add(new BasicNameValuePair("fundate", fundate1));
        nameValuePairs.add(new BasicNameValuePair("funstart", funstart1));
        nameValuePairs.add(new BasicNameValuePair("funend", funend1));
        nameValuePairs.add(new BasicNameValuePair("words", words1));
        try{
        }catch(Exception e){
        }
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/newinvitation.php");
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

