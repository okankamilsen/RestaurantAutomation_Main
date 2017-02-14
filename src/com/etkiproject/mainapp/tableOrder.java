package com.etkiproject.mainapp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class tableOrder extends Activity{
	// AdView adView;
	private String[] orders;
	getTableOrder[] mItem;
    private static final String SOAP_ACTION_getTableOrder = "http://tempuri.org/getTableOrderData";
    private static final String SOAP_ACTION_takeReceipt = "http://tempuri.org/takeReceipt";

    private static final String OPERATION_NAME_getTableOrder = "getTableOrderData";
    private static final String OPERATION_NAME_takeReceipt = "takeReceipt";

    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    
    private static final String SOAP_ADDRESS = "http://etkiproject-001-site1.anytempurl.com/mywebservice.asmx";
    //linkler tableID
    int linkler;
	Bundle bundle=new Bundle();
	ListView orderList;
	Bundle select;
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tableorder);
		
		select=getIntent().getExtras();
		//linkler=tableID
		linkler=select.getInt("select");
		orderList=(ListView) findViewById(R.id.orderList);
		TextView table = (TextView) findViewById(R.id.tableNumber);
		table.setText("table: "+linkler);
		AsyncCallGetOrder task = new AsyncCallGetOrder();
        task.execute();
		
        
	}   
	private void takeReceipt(int tableID){
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_takeReceipt);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */
    	request.addProperty("tableID", tableID);

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_takeReceipt, envelope);
           SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
          
           //Toast.makeText(getBaseContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
        Log.i("",response.toString());
         
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
            
    }
	private getTableOrder[] getTableOrder(int tableID){
		//menuItem Objelerimiz array halinde
		getTableOrder[] item=null;
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_getTableOrder);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */
    	request.addProperty("tableID", tableID);

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_getTableOrder, envelope);

        
        SoapObject a = (SoapObject) envelope.bodyIn;
        Object property = a.getProperty(0);
		Log.d("item........ ",property.toString()+"");
		// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
		SoapObject info = (SoapObject) property;
        int RCount=info.getPropertyCount();
        Log.d("property RCount",RCount+"");
        item = new getTableOrder[RCount+1];
            if (property instanceof SoapObject) {
                Object itemName =  info.getProperty(0);
                SoapObject infoItemName = (SoapObject) itemName;
                Log.d("item........ ",infoItemName.toString()+"");
                Object itemQuantity =  info.getProperty(1);
                SoapObject infoItemQuantity = (SoapObject) itemQuantity;
                Object menuItemTotalPrice =  info.getProperty(2);
                SoapObject infoItemTotalPrice = (SoapObject) menuItemTotalPrice;
                
        		
        		int j=0;
                int Count=infoItemName.getPropertyCount();
                Log.d("property Count",Count+"");
                item = new getTableOrder[Count+1];
                for (j = 0; j < Count; j++) {
                    if (property instanceof SoapObject) {
                        int sQuantity = Integer.parseInt(infoItemQuantity.getProperty(j).toString());
                        String sItemName = infoItemName.getProperty(j).toString();
                        double sItemTotalPrice = Double.parseDouble(infoItemTotalPrice.getProperty(j).toString());
                        item[j] = new getTableOrder(sItemName,sQuantity,sItemTotalPrice);
                       Log.d("itemName   ","name: "+item[j].getItemName()+" quantity: "+item[j].getQuantity()+" totalPrice: "+item[j].getTotalPrice());
                    }
                }
            }
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
		
		return item;     
    }
	
	private class AsyncCallGetOrder extends AsyncTask<Void, Void, Void> {
		ProgressDialog progDailog;
        protected Void doInBackground(Void... params) {
            Log.i("", "doInBackground");
            	//responseWaiter(1);
            
            Log.i("2","2");
            mItem = getTableOrder(linkler);
            
            
            runOnUiThread(new Runnable() {// viewde değişiklik için ana thread kullanman lazım bu şekilde kullandık.
			     @SuppressWarnings("deprecation")
				@Override
			     public void run() {
			    	 orders=null;
			    	 orders = new String[mItem.length];
				    	int k=0;
				            while(k<mItem.length-1){
				            	orders[k]=mItem[k].getItemName()+" #:"+mItem[k].getQuantity()+" price: "+mItem[k].getTotalPrice();
				            	k++;
				            }
			    	 ArrayAdapter<String> adapter=new ArrayAdapter<String>(tableOrder.this,R.layout.single_row, R.id.textView1, orders);
			            orderList.setAdapter(adapter);
			            
			            Button receiptBt = (Button) findViewById(R.id.button1);
			            receiptBt.setOnClickListener(new onClickListener() {
			                public void onClick(View v) {
			                    	//garson çağır
			                	AsyncCallReceipt task1 = new AsyncCallReceipt();
			                    task1.execute();
			                	//takeReceipt(linkler);
			                	//getMenuItemList(1);
			                	Toast.makeText(getBaseContext(), "hesap alındı", Toast.LENGTH_SHORT).show();
			                	Intent myintent=new Intent(v.getContext(), MainActivity.class);
			    				startActivityForResult(myintent, 0);
			                	//callWaiterBt.setText("Garson Geliyor");
			                }
			            });
			     }
            });
			return null;
            }
        
        protected void onPreExecute() {
            Log.i("", "onPreExecute");
            progDailog = new ProgressDialog(tableOrder.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        protected void onPostExecute(Void result) {
            Log.i("", "onPostExecute");
            progDailog.dismiss();
        }

        

        protected void onProgressUpdate(Void... values) {
            Log.i("", "onProgressUpdate");
        }


    }

	private class AsyncCallReceipt extends AsyncTask<Void, Void, Void> {
		ProgressDialog progDailog;
        protected Void doInBackground(Void... params) {
            Log.i("", "doInBackground");
            	//responseWaiter(1);
            
            Log.i("2","2");
            takeReceipt(linkler);
            
            

			return null;
            }
        
        protected void onPreExecute() {
            Log.i("", "onPreExecute");
            progDailog = new ProgressDialog(tableOrder.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        protected void onPostExecute(Void result) {
            Log.i("", "onPostExecute");
            progDailog.dismiss();
        }

        

        protected void onProgressUpdate(Void... values) {
            Log.i("", "onProgressUpdate");
        }


    }
}
