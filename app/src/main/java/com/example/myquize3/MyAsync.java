package com.example.myquize3;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://beightlyouch.com/blog/programming/asynctask/
public class MyAsync extends AsyncTask<String,Void, String> {
    private String apiUrlStr = "https://kentuck.net/myquize1.php";
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private MainActivity mainActivity;

    public MyAsync() { }
    public MyAsync(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(apiUrlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection() ;
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            BufferedReader reader = new BufferedReader((new InputStreamReader(con.getInputStream())));
            String line = null;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            Log.i("doInBackground", result.toString());
        } catch (Exception e) {
            Log.e("doInBackground", e.getMessage());
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Log.i("onPostExecute", result.toString());
            JSONObject json = new JSONObject(result);
            JSONArray quizeArray = json.getJSONArray("quizelist");
            Log.i("onPostExecute","quizeArray.length():"+quizeArray.length());
            List<Quize> quizeList = new ArrayList<>();
            for (int i=0; i<quizeArray.length(); i++) {
                JSONObject object = quizeArray.getJSONObject(i);
                String question = object.getString("question");
                JSONArray selectionArray = object.getJSONArray("selections");
                String[] selections = new String[selectionArray.length()];
                for (int j=0; j<selectionArray.length(); j++) {
                    selections[j] = selectionArray.getString(j);
                }
                int correctNumber = object.getInt("correctNumber");
                quizeList.add(new Quize(question, selections, correctNumber));
            }
            mainActivity.setData(quizeList);
        } catch(JSONException e) {
            Log.e("onPostExecute", e.getMessage());
        }
    }
}
