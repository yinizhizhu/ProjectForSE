package com.sinaapp.yinizhizhublog.nutshell;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener{
	private SlideMenu slideMenu;
    private TextView login;
    private TextView menu1;
    private TextView menu2;
    private TextView menu3;
    private TextView testTv;

    private ListView lv;
    private MovieAdapter movieAdapter;
    private NewsAdapter newsAdapter;
    public static List<MovieInfo> lists = new ArrayList<MovieInfo>();
    public static List<NewsInfo> lists_ = new ArrayList<NewsInfo>();
//    private static final String TAG = "RawData";
//    private StringBuffer movieDetail;
//    private IntBuffer movieScore;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		menuImg.setOnClickListener(this);

        login = (TextView)findViewById(R.id.login_text);
        login.setOnClickListener(this);

        menu1=(TextView)findViewById(R.id.menu1_tv);
        menu1.setOnClickListener(this);

        menu2=(TextView)findViewById(R.id.menu2_tv);
        menu2.setOnClickListener(this);

        menu3=(TextView)findViewById(R.id.menu3_tv);
        menu3.setOnClickListener(this);

        testTv=(TextView)findViewById(R.id.test_tv);
        testTv.setOnClickListener(this);

//        movieDetail = new StringBuffer();
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_menu_btn:
			if (slideMenu.isMainScreenShowing()) {
				slideMenu.openMenu();
			} else {
				slideMenu.closeMenu();
                Toast.makeText(getApplicationContext(), "HaHa",
                        Toast.LENGTH_SHORT).show();
			}
			break;
            case R.id.menu1_tv:

                slideMenu.closeMenu();
                Toast.makeText(getApplicationContext(), "老炮儿，豆瓣评分9.2",
                        Toast.LENGTH_SHORT).show();
//                getMovie();
                getJSONVolley();
                lv = (ListView)findViewById(R.id.lv);
                movieAdapter = new MovieAdapter(lists, this);
                lv.setAdapter(movieAdapter);
                break;
            case R.id.menu2_tv:
                slideMenu.closeMenu();
                Toast.makeText(getApplicationContext(), "计算接学院炸了",
                        Toast.LENGTH_SHORT).show();
                getJSONVolleyNews();
                lv = (ListView)findViewById(R.id.lv);
                newsAdapter = new NewsAdapter(lists_, this);
                lv.setAdapter(newsAdapter);
                break;
            case R.id.menu3_tv:
                slideMenu.closeMenu();
                Toast.makeText(getApplicationContext(), "脱单进行时，不分时刻都可以！",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_tv:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://today.hit.edu.cn")));
                break;
            case R.id.login_text:
                startActivity(new Intent(MainActivity.this, Login.class));
                break;
		}
	}

    public void getMovie(){
        try {
            InputStream is = getResources().openRawResource(R.raw.movies);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader bfr = new BufferedReader(isr);
            String inString = "";
            while ((inString = bfr.readLine()) != null){
//                Log.i(TAG, inString);
                String temp[] = inString.split("~");
//                movieDetail.append(inString);
                MovieInfo movieInfo = new MovieInfo(temp);
                lists.add(movieInfo);
            }
            bfr.close();
            isr.close();
            is.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void getNews(){
//        try {
//            InputStream is = getResources().openRawResource(R.raw.org);
//            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//            BufferedReader bfr = new BufferedReader(isr);
//            String inString = "";
//            while ((inString = bfr.readLine()) != null){
////                Log.i(TAG, inString);
//                String temp[] = inString.split("~");
////                movieDetail.append(inString);
//                NewsInfo newsInfo = new NewsInfo(temp);
//                lists_.add(newsInfo);
//            }
//            bfr.close();
//            isr.close();
//            is.close();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void getJSONVolley(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDateUrl = "http://10.0.2.2:8000/server/test/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONDateUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
//                        System.out.println("Response: "+ jsonObject);
//                        show.setText(jsonObject.toString());
                        String inString = "";
                        JSONObject jb;
                        try {
                            jb = new JSONObject(jsonObject.toString());
                            inString = jb.getString("test");
                            String temp[] = inString.split("~");
                            MovieInfo movieInfo = new MovieInfo(temp);
                            lists.add(movieInfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    };
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("Sorry, something error is here!");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getJSONVolleyNews(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDateUrl = "http://hitbigdeal.applinzi.com/server/LatestNews/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONDateUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
//                        System.out.println("Response: "+ jsonObject);
//                        show.setText(jsonObject.toString());
                        String inString = "";
                        JSONObject jb;
                        try {
                            jb = new JSONObject(jsonObject.toString());
                            inString = jb.getString("title");
//                            String temp[] = inString.split("~");

                            NewsInfo newsInfo = new NewsInfo(inString);
                            lists_.add(newsInfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    };
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("Sorry, something error is here!");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
