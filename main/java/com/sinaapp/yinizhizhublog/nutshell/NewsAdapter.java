package com.sinaapp.yinizhizhublog.nutshell;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class NewsAdapter extends BaseAdapter {
    private List<NewsInfo> lists;
    private Context context;
    private LinearLayout layout;
    public String url;

    public NewsAdapter(List<NewsInfo> lists, Context context){
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.call_news, null);
            holder = new ViewHolder();
            holder.newsTitle = (TextView)convertView.findViewById(R.id.newsTitle);
            holder.newsNumber = (TextView)convertView.findViewById(R.id.newsNumber);
            holder.newsOrg = (TextView)convertView.findViewById(R.id.newsOrg);

            holder.newsTitle.setText(""+lists.get(position).getTitle());
            holder.newsNumber.setText(lists.get(position).getNumber());
            holder.newsOrg.setText(lists.get(position).getOrg());
            url = lists.get(position).getUrl().replace(" ", "");
            System.out.println(url);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
            holder.newsTitle.setText(""+lists.get(position).getTitle());
            holder.newsNumber.setText(lists.get(position).getNumber());
            holder.newsOrg.setText(lists.get(position).getOrg());
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView newsTitle;
        TextView newsNumber;
        TextView newsOrg;
    }
}
