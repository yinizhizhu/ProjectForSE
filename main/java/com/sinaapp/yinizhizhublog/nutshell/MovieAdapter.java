package com.sinaapp.yinizhizhublog.nutshell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private List<MovieInfo> lists;
    private Context context;
    private LinearLayout layout;
    public String name;  //the name of movie
    public String score;         //the score of the movie
    public String content;    //the brief details of the movie
    public String date; //the date of the onscreen.
    public String picture;     //the picture of the movie

    public MovieAdapter(List<MovieInfo> lists, Context context){
        this.lists = lists;
        this.context = context;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return lists.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String imageUrl = lists.get(position).getMoviePicture();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s, bitmap);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.call_movie, null);
            holder = new ViewHolder();
            holder.movieDate = (TextView)convertView.findViewById(R.id.movieDate);
            holder.movieName = (TextView)convertView.findViewById(R.id.movieName);
            holder.movieScore = (TextView)convertView.findViewById(R.id.movieScore);
            holder.moviePicture = (NetworkImageView)convertView.findViewById(R.id.moviePicture);

            name = lists.get(position).getMovieName();
            date = lists.get(position).getMovieDate();
            score = lists.get(position).getMovieScore();
            content = lists.get(position).getMovieContent();
            picture = lists.get(position).getMoviePicture();
            holder.movieDate.setText(""+lists.get(position).getMovieDate());
            holder.movieName.setText(lists.get(position).getMovieName());
            holder.movieScore.setText(lists.get(position).getMovieScore());
            holder.moviePicture.setTag("url");
            holder.moviePicture.setImageUrl(imageUrl, imageLoader);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ShowMovie.class);
                    Bundle b = new Bundle();
                    b.putString("name", name);
                    b.putString("score", score);
                    b.putString("date", date);
                    b.putString("content", content);
                    b.putString("picture", picture);
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
            holder.movieDate.setText(""+lists.get(position).getMovieDate());
            holder.movieName.setText(lists.get(position).getMovieName());
            holder.movieScore.setText(lists.get(position).getMovieScore());
            holder.moviePicture.setTag("url");
            holder.moviePicture.setImageUrl(imageUrl, imageLoader);
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView movieDate;
        TextView movieName;
        TextView movieScore;
        NetworkImageView moviePicture;
    }
}
