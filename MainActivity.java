package com.example.asyncsample;

import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "AsyncSample";
    private static final String WEATHERINFO_URL = "https://api.openweathermap.org/data/2.5/weather?lang=ja";
    private static final String APP_ID = "7ab28496ef022c48120d0f737a965c70";
    private List<Map<String,String>> _List;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _List = createList();

        ListView lvCityList = findViewById(R.id.lvCityList);
        String[] from = {"name"};
        int[] to = {android.R.id.text1};
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),_List,
                android.R.layout.simple_list_item_1,from,to);
        lvCityList.setAdapter(adapter);
        lvCityList.setOnItemClickListener(new ListItemClickListener());
    }

    private List<Map<String, String>> createList() {
        List<Map<String,String>> list = new ArrayList<>();

        Map<String,String> map = new HashMap<>();
        map.put("name","鳥取");
        map.put("q","Tottori");
        list.add(map);

        map = new HashMap<>();
        map.put("name","島根");
        map.put("q","Shimane");
        list.add(map);

        map = new HashMap<>();
        map.put("name","岡山");
        map.put("q","Okayama");
        list.add(map);

        map = new HashMap<>();
        map.put("name","広島");
        map.put("q","Hiroshima");
        list.add(map);

        map = new HashMap<>();
        map.put("name","山口");
        map.put("q","Yamaguchi");
        list.add(map);

        return list;
    }

    private void receiveWeatherInfo(final String urlFull){
        Looper mainLooper = Looper.getMainLooper();
        Handler handler = HandlerCompat.createAsync(mainLooper);
        WeatherInfoBackgroundReceiver backgroundReceiver =
                new WeatherInfoBackgroundReceiver(handler,urlFull);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(backgroundReceiver);
    }
    @UiThread
    private class WeatherInfoBackgroundReceiver implements Runnable{
        private final Handler _handler;
        private final String _urlFull;

        public WeatherInfoBackgroundReceiver(Handler _handler, String _urlFull) {
            this._handler = _handler;
            this._urlFull = _urlFull;
        }

        @WorkerThread
        @Override
        public void run() {
            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try {
                URL url = new URL(_urlFull);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(1000);
                con.setReadTimeout(1000);
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();
                result = is2String(is);
            }
            catch(MalformedURLException ex){
                Log.e(DEBUG_TAG,"URL変換失敗",ex);
            }
            catch (SocketTimeoutException ex){
                Log.w(DEBUG_TAG,"通信タイムアウト",ex);
            }
            catch (IOException ex){
                Log.e(DEBUG_TAG,"通信失敗",ex);
            }
            finally {
                if(con != null){
                    con.disconnect();
                }
                if(is != null){
                    try{
                        is.close();
                    }
                    catch (IOException ex){
                        Log.e(DEBUG_TAG,"InputStream解放失敗",ex);
                    }
                }
            }
            WeatherInfoPostExecutor postExecutor = new WeatherInfoPostExecutor(result);
            _handler.post(postExecutor);
        }
    }
    private String is2String(InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))){
            sb.append(b,0,line);
        }
        return sb.toString();
    }

    private class WeatherInfoPostExecutor implements Runnable{
        private final String _result;
        private WeatherInfoPostExecutor(String result) {
            _result = result;
        }

        @UiThread
        @Override
        public void run() {
            String cityName = "";
            String weather = "";
            String latitude = "";
            String longitude = "";
            try{
                // Create JSON Object
                JSONObject rootJSON = new JSONObject(_result);
                cityName = rootJSON.getString("name");
                JSONObject coordJSON = rootJSON.getJSONObject("coord");
                latitude = coordJSON.getString("lat");
                longitude = coordJSON.getString("lon");
                JSONArray weatherJSONArray = rootJSON.getJSONArray("weather");
                JSONObject weatherJSON = weatherJSONArray.getJSONObject(0);
                weather = weatherJSON.getString("description");

            } catch (JSONException ex) {
                Log.e(DEBUG_TAG,"JSON解放失敗",ex);
            }
            String telop = cityName + "の天気";
            String desc = "現在は" + weather + "です。\n緯度は" + latitude + "度で経度は" +
                    longitude + "度です。";

            TextView tvWeatherTelop = findViewById(R.id.tvWeatherTelop);
            TextView tvWeatherDesc = findViewById(R.id.tvWeatherDesc);

            tvWeatherTelop.setText(telop);
            tvWeatherDesc.setText(desc);

        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Map<String,String> item = _List.get(position);
            String q = item.get("q");
            String urlFull = WEATHERINFO_URL + "&q=" + q + "&appid=" + APP_ID;

            receiveWeatherInfo(urlFull);
        }
    }


}