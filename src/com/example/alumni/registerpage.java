package com.example.alumni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registerpage extends Activity{
	Button faculty,student;
@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        faculty=(Button)findViewById(R.id.button1);
        student=(Button)findViewById(R.id.button2);
        faculty.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(registerpage.this, facultyreg.class);
		        startActivity(intent);	
				
			}
		});
        student.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(registerpage.this, studreg.class);
		        startActivity(intent);	
				
			}
		});
}
}