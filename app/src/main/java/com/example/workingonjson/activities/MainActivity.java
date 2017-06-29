package com.example.workingonjson.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.workingonjson.R;
import com.example.workingonjson.enumerations.RequestMethod;
import com.example.workingonjson.tasks.JsonAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
//    private final String API = "http://dev.superman-academy.com/api.php";
//    private final String API = "http://10.0.2.2:3306/Android/index.php";
//    private final String API = "http://127.0.0.1/Android/index.php";
private final String API = "http://198.168.129.2:3306/Android/index.php";
    private Context context;
    private String json_feed;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        json_feed = jsonData();

//        AsyncTask asyncTask = new AsyncTask();
//        asyncTask.execute("");

//        AsyncTask asyncTask = new AsyncTask();
//        asyncTask.execute(json_feed);

//        String id_to_delete = "100";
//        AsyncTask asyncTask = new AsyncTask();
//        asyncTask.execute(id_to_delete);

        RequestMethod requestMethod = RequestMethod.GET;
        JsonAsyncTask jsonAsyncTask = new JsonAsyncTask(requestMethod);
        jsonAsyncTask.execute(API,"null");
    }

    private String getData()
    {
        URL url = null;
        HttpURLConnection urlConnection=null;
        InputStream inputStream = null;
        String response = "";
        try
        {
            url = new URL(API);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();

            int byteCharacter;
            while ((byteCharacter = inputStream.read()) != -1)
            {
                response += (char) byteCharacter;
            }

            JSONArray jsonArray = new JSONArray(response);

            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                double price = jsonObject.getDouble("price");

                String resultantData = String.format("Id: %d,Name: %s,Description: %s,Price: %f",id,name,description,price);
                Log.i("Item"+i,resultantData);
            }


            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }




            return response;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                urlConnection.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        return null;
    }

    private String postData(String data) // creates a new record
    {
        URL url = null;
        HttpURLConnection urlConnection=null;
        OutputStream outputStream=null;
        InputStream inputStream = null;
        String response = "";
        try
        {
            url = new URL(API);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            outputStream = urlConnection.getOutputStream();
            outputStream.write(data.getBytes());

            inputStream = urlConnection.getInputStream();

            int byteCharacter;
            while ((byteCharacter = inputStream.read()) != -1)
            {
                response += (char) byteCharacter;
            }
            Log.i("Http Response Status ",urlConnection.getResponseMessage());
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CONFLICT)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }

            return response;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
                urlConnection.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        return null;
    }

    private String putData(String data) // updates a record
    {
        URL url = null;
        HttpURLConnection urlConnection=null;
        OutputStream outputStream=null;
        InputStream inputStream = null;
        String response = "";
        try
        {
            url = new URL(API);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            outputStream = urlConnection.getOutputStream();
            outputStream.write(data.getBytes());

            inputStream = urlConnection.getInputStream();

            int byteCharacter;
            while ((byteCharacter = inputStream.read()) != -1)
            {
                response += (char) byteCharacter;
            }

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CONFLICT)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }

            return response;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
                urlConnection.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        return null;
    }

    private String deleteData(int data)
    {
        URL url = null;
        HttpURLConnection urlConnection=null;
        InputStream inputStream = null;
        String response = "";
        try
        {
            url = new URL(String.format("%s/%d",API,data));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();

            int byteCharacter;
            while ((byteCharacter = inputStream.read()) != -1)
            {
                response += (char) byteCharacter;
            }

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }
            else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
            {
                Log.i("Http Response Status ",urlConnection.getResponseMessage());
            }

            return response;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                urlConnection.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        return null;
    }

    private String jsonData()
    {
        final JSONArray jsonArray = new JSONArray();
        final JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("id",104);
            jsonObject.put("name","One Plus");
            jsonObject.put("price",26.5);
            jsonObject.put("description","This is one of the best android handset");

            jsonArray.put(jsonObject);
            return jsonArray.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private final class AsyncTask extends android.os.AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return getData();
//            return postData(params[0]);
//            return putData(params[0]);
//            return deleteData(Integer.parseInt(params[0]));
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.i(TAG,result);
        }
    }
}
