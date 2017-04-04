package com.example.alumni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class forgetpwd extends Activity{
	Button sendsms,exit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        			sendsms=(Button)findViewById(R.id.button1);
        			exit=(Button)findViewById(R.id.button2);
        			 sendsms.setOnClickListener(new View.OnClickListener() {
        		    		
        		    		@Override
        		    		public void onClick(View v) {
        		    			// TODO Auto-generated method stub
        		    			//Intent intent=new Intent(profile.this,editprofile.class);
        		    			//startActivity(intent);
        		    		}});
        			 exit.setOnClickListener(new View.OnClickListener() {
     		    		
     		    		@Override
     		    		public void onClick(View v) {
     		    			// TODO Auto-generated method stub
     		    			Intent intent=new Intent(forgetpwd.this,login.class);
     		    			startActivity(intent);
     		    		}});
		}
}
