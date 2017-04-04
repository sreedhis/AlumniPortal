package com.example.alumni;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends Activity {
	Button profile,changepwd,event,find,mrandmrs,chat,logout;

	String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profile=(Button)findViewById(R.id.button1);
        changepwd=(Button)findViewById(R.id.button5);
        event=(Button)findViewById(R.id.button2);
        find=(Button)findViewById(R.id.button3);
        mrandmrs=(Button)findViewById(R.id.button4);
        chat=(Button)findViewById(R.id.button6);
        logout=(Button)findViewById(R.id.button7);
        Intent i= getIntent();
        uname = i.getExtras().getString("mmm");

        profile.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent=new Intent(MainActivity.this,profile.class);
                intent.putExtra("pro",uname);
                startActivity(intent);
    		}});
changepwd.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent=new Intent(MainActivity.this,changepwd.class);
    			startActivity(intent);
    		}});
event.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this,event.class);
		startActivity(intent);
	}});
find.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this,find.class);
		startActivity(intent);
	}});
mrandmrs.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(MainActivity.this,mrandmrs.class);
		startActivity(i);
	}});
      chat.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this,chat2.class);
        intent.putExtra("pro",uname);
        startActivity(intent);
	}});
      logout.setOnClickListener(new View.OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent intent=new Intent(MainActivity.this,login.class);
  			startActivity(intent);
  		}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
