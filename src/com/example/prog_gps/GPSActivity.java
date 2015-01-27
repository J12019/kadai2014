
package com.example.prog_gps;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prog_gps.R.id;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class GPSActivity extends FragmentActivity implements LocationListener{
	LocationManager locmanager;
	//Context context;
	TextView lati,longi;
	String lat, lng; //GPS情報送信時に利用
	String p = new String();
	String a = new String();
	private GoogleMap mMap;
	LatLng latlng = new LatLng(35.710065, 139.8107);
	//private static final String url = "http://10.0.2.2/gps.php"; //エミュレータ使用時
	private static final String url = "http://j12024.sangi01.net/cakephp/app/webroot/post.php"; //外部サーバ使用時
	Button b1;
	Text na;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
		setContentView(R.layout.activity_gps);
		locmanager = (LocationManager)getSystemService(LOCATION_SERVICE);
		//lati = (TextView)findViewById(R.id.textView1);
		//longi = (TextView)findViewById(R.id.textView2);
		locmanager.requestLocationUpdates(locmanager.GPS_PROVIDER, 0, 0, this);
		b1 = (Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(id.editText1);
				// エディットテキストのテキストを全選択します
				editText.selectAll();
				// エディットテキストのテキストを取得します
				String text = editText.getText().toString();
				// TODO Auto-generated method stub
				//-----[クライアント設定]
				/*HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				//-----[POST送信するデータを格納]
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
				nameValuePair.add(new BasicNameValuePair("a", text));
				nameValuePair.add(new BasicNameValuePair("b", p));
				nameValuePair.add(new BasicNameValuePair("c", a));
				nameValuePair.add(new BasicNameValuePair("d", lat));
				nameValuePair.add(new BasicNameValuePair("e", lng));*/
				String url="http://j12024.sangi01.net/cakephp/app/webroot/post.php";

				HttpClient httpClient = new DefaultHttpClient();

				HttpPost post = new HttpPost(url);

				//ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
				List<NameValuePair> params = new ArrayList<NameValuePair>(1);

				params.add( new BasicNameValuePair("name", text));
				params.add( new BasicNameValuePair("postalcode", p));
				params.add( new BasicNameValuePair("address", a));
				params.add( new BasicNameValuePair("latitude", lat));
				params.add( new BasicNameValuePair("longitude", lng));

				HttpResponse res = null;

				try {

					post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

					res = httpClient.execute(post);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					res.getEntity().writeTo(byteArrayOutputStream);

					//-----[サーバーからの応答を取得]
					if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
					{
						//TextView tv = (TextView) findViewById(R.id.textView1);
						//tv.setText(byteArrayOutputStream.toString());
						//Toast.makeText(getBaseContext(), "Finish", Toast.LENGTH_SHORT).show();
						// Toastのインスタンスを生成
						Toast toast = Toast.makeText(getBaseContext(), "Finish", Toast.LENGTH_LONG);
						// 表示位置を設定
						toast.setGravity(Gravity.CENTER, 0, 0);
						// メッセージを表示
						toast.show();
					}
					else
					{
						//Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
						// Toastのインスタンスを生成
						Toast toast = Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG);
						// 表示位置を設定
						toast.setGravity(Gravity.CENTER, 0, 0);
						// メッセージを表示
						toast.show();
					}

				} catch (UnsupportedEncodingException e){
					e.printStackTrace();
				}catch (Exception e) {

					e.printStackTrace();

				}

				/*
				try
				{
					//-----[POST送信]
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					HttpResponse response = httpclient.execute(httppost);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					response.getEntity().writeTo(byteArrayOutputStream);

					//-----[サーバーからの応答を取得]
					if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
					{
						//TextView tv = (TextView) findViewById(R.id.textView1);
						//tv.setText(byteArrayOutputStream.toString());
						Toast.makeText(getBaseContext(), "Finish", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
					}
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}*/
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.g, menu);
		return true;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//lati.setText("緯度："+Double.toString(location.getLatitude()));
		//longi.setText("経度："+Double.toString(location.getLongitude()));
		lat = Double.toString(location.getLatitude());
		lng = Double.toString(location.getLongitude());
		latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
		setUpMap();
		String string = new String();
		ReverseGeocode rg = new ReverseGeocode();
		try {
			string = rg.point2address(this,location.getLatitude(), location.getLongitude()) + "付近";
			p=(string.substring(0,9));
			a=(string.substring(10-1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}
	private void setUpMap() {
		mMap.addMarker(new MarkerOptions().position(latlng).title("Check!"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 19));
	}
	public class ReverseGeocode {
		public String point2address(Context context, double latitude, double longitude) throws IOException {
			String string = new String();
			Geocoder geocoder = new Geocoder(context, Locale.JAPAN);
			List<Address> list_address = geocoder.getFromLocation(latitude, longitude, 2);
			if(!list_address.isEmpty()) {
				string = list_address.get(1).getAddressLine(1);
			}else {
				string = "現在地が特定できませんでした。";
			}
			return string;
		}
	}
}
