package com.dreaming.drilling.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.dreaming.drilling.activity.DrillSettingsActivity;
import com.dreaming.drilling.bean.HoleDeployments;
import com.dreaming.drilling.bean.HoleDetail;
import com.dreaming.drilling.bean.SpinnerData;

public class RestClient {
	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
 
    /* This is a test function which will connects to a given
     * rest service and prints it's response to Android Log with
     * labels "Praeda".
     */
    public static void connect(String url)
    {
 
        HttpClient httpclient = new DefaultHttpClient();
 
        // Prepare a request object
        HttpGet httpget = new HttpGet(url); 
 
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("Praeda",response.getStatusLine().toString());
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
 
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("Praeda",result);
 
                // A Simple JSONObject Creation
                JSONObject json=new JSONObject(result);
                Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
 
                // A Simple JSONObject Parsing
                JSONArray nameArray=json.names();
                JSONArray valArray=json.toJSONArray(nameArray);
                for(int i=0;i<valArray.length();i++)
                {
                    Log.i("Praeda","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
                            +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
                }
 
                // A Simple JSONObject Value Pushing
                json.put("sample key", "sample value");
                Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
 
                // Closing the input stream will trigger connection release
                instream.close();
            }
 
 
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    // 配置界面返回钻孔列表的方法
    public static ArrayList<SpinnerData> getHolelist(String httpurl) {
    	Log.d("RestClient", "url: "+httpurl);
    	
    	//Toast.makeText(null, "in the url" + httpurl
    	//		, Toast.LENGTH_LONG).show();
    	
        ArrayList<SpinnerData> items = new ArrayList<SpinnerData>();
        SpinnerData data;
     
        try {
            URL url = new URL(httpurl);
            HttpURLConnection urlConnection = 
                (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // gets the server json data
            BufferedReader bufferedReader = 
                new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
            String next;
            while ((next = bufferedReader.readLine()) != null){
                JSONArray ja = new JSONArray(next);
 
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
//                    data = new SpinnerData(jo.getString("id"), jo.getString("name"));
                    data = new SpinnerData(jo.getString("id"), jo.getString("contractname") + "|" +jo.getString("holenumber"));
                    data.setOuterflag(jo.getInt("outerflag"));
                    Log.i("RestClient", jo.getString("id")+";" + "name:" + jo.getString("holenumber")+";outerflag(是否外协):"+jo.getInt("outerflag"));
                    items.add(data);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return items;
    }
    
    // 返回钻孔配置的请求
    public static List<HoleDeployments> populate(String httpurl) {
    	Log.d("RestClient-HoleDeployments", "url: "+httpurl);
    	
    	List<HoleDeployments> items = new ArrayList<HoleDeployments>();
    	HoleDeployments data;
    	
    	 try {
             URL url = new URL(httpurl);
             HttpURLConnection urlConnection = 
                 (HttpURLConnection) url.openConnection();
             urlConnection.setReadTimeout(10000 /* milliseconds */);
             urlConnection.setConnectTimeout(15000 /* milliseconds */);
             urlConnection.setRequestMethod("GET");
             urlConnection.connect();
             // gets the server json data
             BufferedReader bufferedReader = 
                 new BufferedReader(new InputStreamReader(
                         urlConnection.getInputStream()));
             String next;
             while ((next = bufferedReader.readLine()) != null){
                 JSONArray ja = new JSONArray(next);
  
                 for (int i = 0; i < ja.length(); i++) {
                     JSONObject jo = (JSONObject) ja.get(i);
                     data = new HoleDeployments(jo.getString("id"), jo.getString("name"), jo.getString("type"));
                     Log.i("RestClient-[id:", jo.getString("id")+";name-:" + jo.getString("name") + ";type:"+jo.getString("type")+"]");
                     items.add(data);
                 }
             }
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (JSONException e) {
             e.printStackTrace();
         }
    	
    	return items;
    }
    
    // 返回钻孔详细信息的restclient request
    public static HoleDetail populateHoleDetail(String httpurl) {
    	//Log.d("RestClient-HoleDetail", "url: "+httpurl);
    	
    	HoleDetail result = new HoleDetail();
    	
    	 try {
             URL url = new URL(httpurl);
             HttpURLConnection urlConnection = 
                 (HttpURLConnection) url.openConnection();
             urlConnection.setReadTimeout(10000 /* milliseconds */);
             urlConnection.setConnectTimeout(15000 /* milliseconds */);
             urlConnection.setRequestMethod("GET");
             urlConnection.connect();
             // gets the server json data
             BufferedReader bufferedReader = 
                 new BufferedReader(new InputStreamReader(
                         urlConnection.getInputStream()));
             String next;
             while ((next = bufferedReader.readLine()) != null){
            	 
            	 //Log.d("the json ", next);
                 //JSONArray ja = new JSONArray(next);
                 JSONObject jo = new JSONObject(next);
                 Log.d("minearea",jo.getString("minearea"));
                 result = new HoleDetail(jo.getString("minearea"),jo.getString("actualdeep"),jo.getString("holenumber"),jo.getString("geologysituation"));
                 //for (int i = 0; i < ja.length(); i++) {
                 //    JSONObject jo = (JSONObject) ja.get(i);
                 //    data = new HoleDeployments(jo.getString("id"), jo.getString("name"), jo.getString("type"));
                 //    Log.i("RestClient-[id:", jo.getString("id")+";name-:" + jo.getString("name") + ";type:"+jo.getString("type")+"]");
                 //   items.add(data);
                // }
                 
             }
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (JSONException e) {
             e.printStackTrace();
         }
    	
    	return result;
    }
    
    
    /**
     * 
     * 验证用户登录，验证用户是否可以登录
     * 
     * **/
    public static String validate_user(String httpurl) {
    	String result=null;
    	 try {
             URL url = new URL(httpurl);
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setReadTimeout(10000 /* milliseconds */);
             urlConnection.setConnectTimeout(15000 /* milliseconds */);
             urlConnection.setRequestMethod("GET");
             
             //urlConnection.connect();
             
             InputStream is = urlConnection.getInputStream();//得到网络返回的输入流
             String return_string = readData(is,"utf-8");

        	 if(return_string.equalsIgnoreCase("false"))
        	 {
        		result = "false";
        	 }
        	 else
        	 {
        		 //String[] arr = return_string.split("_");
        		 result = return_string;
        	 }
        	 
        	 
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    }
    
  //第一个参数为输入流,第二个参数为字符集编码
    private static String readData(InputStream inSream, String charsetName) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, charsetName);
    }

}
