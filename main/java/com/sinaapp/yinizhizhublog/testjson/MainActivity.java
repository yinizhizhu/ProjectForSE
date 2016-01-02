package com.sinaapp.yinizhizhublog.testjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends ActionBarActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>(){

                    /**
                     * Override this method to perform a computation on a background thread. The
                     * specified parameters are the parameters passed to {@link #execute}
                     * by the caller of this task.
                     * <p/>
                     * This method can call {@link #publishProgress} to publish updates
                     * on the UI thread.
                     *
                     * @param params The parameters of the task.
                     * @return A result, defined by the subclass of this task.
                     * @see #onPreExecute()
                     * @see #onPostExecute
                     * @see #publishProgress
                     */
                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            System.out.println("Haha");
                            URLConnection connection = url.openConnection();
                            System.out.println("Haha");
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is, "utf-8");
                            BufferedReader br = new BufferedReader(isr);
                            System.out.println("Haha");
                            String line;
                            while ((line = br.readLine()) != null){
                                JSONObject jb = new JSONObject(line);
                                System.out.println(jb.getString("second"));
                                System.out.println("Haha");
                                System.out.println(line);
                            }
                            br.close();
                            isr.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute("http://10.0.2.2:8000/server/test/");
//                execute("http://192.168.191.1:8000/bigdeals/ {"second": "fuck you again!!!!!!!", "first": "fuck you"}download/");
            }
        });
    }
}
