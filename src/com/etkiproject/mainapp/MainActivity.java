package com.etkiproject.mainapp;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
/**
 * 
 * @author manish.s
 *
 */

public class MainActivity extends Activity {
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	 CustomGridViewAdapter customGridAdapter;
	 Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bundle=new Bundle();
		//set grid view item
		Bitmap tableIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.table);
		
		gridArray.add(new Item(tableIcon,"1"));
		gridArray.add(new Item(tableIcon,"2"));
		gridArray.add(new Item(tableIcon,"3"));
		gridArray.add(new Item(tableIcon,"4"));
		gridArray.add(new Item(tableIcon,"5"));
		gridArray.add(new Item(tableIcon,"6"));
		gridArray.add(new Item(tableIcon,"7"));
		gridArray.add(new Item(tableIcon,"8"));
		gridArray.add(new Item(tableIcon,"9"));
		gridArray.add(new Item(tableIcon,"10"));
		gridArray.add(new Item(tableIcon,"11"));
		gridArray.add(new Item(tableIcon,"12"));
		
		
		gridView = (GridView) findViewById(R.id.gridView1);
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		gridView.setAdapter(customGridAdapter);
		gridView.setOnItemClickListener(new onItemClickListener() {
	       	 
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            	position=position+1;
            	Toast.makeText(getBaseContext(), "selam "+ position, Toast.LENGTH_SHORT).show();
            	bundle.putInt("select",position);
            	Intent myintent=new Intent(arg1.getContext(), tableOrder.class);
            	myintent.putExtras(bundle);
				startActivityForResult(myintent, 0);
            }
         });
	}

}


