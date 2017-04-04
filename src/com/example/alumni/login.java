package com.example.alumni;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class login extends Activity {
	Button signup,login;
	Button forget;
	EditText email,password;
	String email1,password1;
	ProgressDialog dialog = null;
	Boolean val;
    HttpPost httppost;
    InputStream is = null;
    String result = null;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login=(Button)findViewById(R.id.button1);
        signup=(Button)findViewById(R.id.button2);
        forget=(Button)findViewById(R.id.button3);
        email=(EditText)findViewById(R.id.editText1);
        password=(EditText)findViewById(R.id.editText2);
       login.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
            dialog = ProgressDialog.show(login.this, "", "Validating user...", true);

             new Thread(new Runnable() {
                 public void run() {
                     login();
                 }
             }).start();
         }

/*		public void onClick(View v) {
			// TODO Auto-generated method stub
			 if(email.getText().toString().trim().length()==0){
                 email.setError("Email is not entered");
                 email.requestFocus();
           }
           if(password.getText().toString().trim().length()==0){
                 password.setError("Password is not entered");
                 password.requestFocus();
           }
           else{
                 Intent it=new Intent(getApplicationContext(), MainActivity.class);
                 it.putExtra("mmm", email.getText().toString());
                 startActivity(it);  
           }
			//Intent intent=new Intent(login.this,MainActivity.class);
			//startActivity(intent);
		}*/
	});
        signup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(login.this, registerpage.class);
		        startActivity(intent);	
			}
		});
        forget.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(login.this, forgetpwd.class);
		        startActivity(intent);	
			}
		});
	
	
}
    void login() {
        try {

            httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.47/alumni/login.php/");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);
            Log.e("msg", response);
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

                if(response.equalsIgnoreCase("User Found")){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent in=new Intent(login.this,MainActivity.class);
                     in.putExtra("mmm",email.getText().toString());
                     startActivity(in);
            } else {
                showAlert();
            }
        } catch (Exception e) {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert() {
        login.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }






}
