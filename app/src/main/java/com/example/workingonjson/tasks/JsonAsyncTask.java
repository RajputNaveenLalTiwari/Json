package com.example.workingonjson.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.workingonjson.enumerations.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 2114 on 21-04-2017.
 */

public class JsonAsyncTask extends AsyncTask<String,Void,String>
{
    private static final String TAG = "JsonAsyncTask";
    private final RequestMethod requestMethod;
    private String requestMethodName;
    private String API;

    public JsonAsyncTask(RequestMethod requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    @Override
    protected String doInBackground(String... input)
    {
        URL url = null;
        HttpURLConnection urlConnection=null;
        OutputStream outputStream=null;
        InputStream inputStream = null;
        String response = "";
        String data=null;

        switch (requestMethod)
        {
            case GET:
                requestMethodName = "GET";
                API = input[0];
                if(input[1] != null)
                {

                }
                break;
            case POST:
                requestMethodName = "POST";
                API = input[0];
                if(input[1] != null)
                {
                    data = input[1];
                }
                break;
            case PUT:
                requestMethodName = "PUT";
                API = input[0];
                if(input[1] != null)
                {
                    data = input[1];
                }
                break;
            case DELETE:
                requestMethodName = "DELETE";
                API = input[0];
                if(input[1] != null)
                {
                    Integer id_to_delete = Integer.parseInt(input[1]);
                    String  api_to_delete = String.format("%s/%d",API,id_to_delete);
                    API = api_to_delete;
                }
                break;
        }


        try
        {
            url = new URL(API);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethodName);
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
            urlConnection.connect();

            if(requestMethodName.equals("GET") || requestMethodName.equals("DELETE"))
            {
                inputStream = urlConnection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = inputStream.read()) != -1)
                {
                    response += (char) byteCharacter;
                }

            }
            else if(requestMethodName.equals("POST") || requestMethodName.equals("PUT"))
            {
                outputStream = urlConnection.getOutputStream();
                outputStream.write(data.getBytes());

                inputStream = urlConnection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = inputStream.read()) != -1)
                {
                    response += (char) byteCharacter;
                }
            }

            return response;//urlConnection.getResponseMessage();
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
                if(inputStream!=null)
                    inputStream.close();

                if(outputStream!=null)
                    outputStream.close();

                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onPostExecute(String output)
    {
        Log.i(TAG,output);
    }
}
