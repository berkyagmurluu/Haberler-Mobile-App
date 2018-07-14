package com.berkyagmurlu.haberler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<NewsList> newsLists;

    public MainAdapter(Context context, List nList) {
        this.context = context;
        this.newsLists = nList;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(newsLists.get(position));

        NewsList n = newsLists.get(position);

        holder.pTitle.setText(n.getNewsTitle());
        holder.pImage.setImageUrl(n.getNewsImage(), News.getImageLoader(context));
    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pTitle;
        public NetworkImageView pImage;

        public ViewHolder(View itemView) {
            super(itemView);

            pTitle = (TextView) itemView.findViewById(R.id.textTitle);
            pImage = (NetworkImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsList cpu = (NewsList) view.getTag();
                    Intent myHaberDetay = new Intent(view.getContext(), MainDetails.class);
                    myHaberDetay.putExtra("titleN", cpu.getNewsTitle());
                    myHaberDetay.putExtra("dateN", cpu.getNewsDate());
                    myHaberDetay.putExtra("contentN", cpu.getNewsContent());
                    myHaberDetay.putExtra("imageN", cpu.getNewsImage());
                    view.getContext().startActivity(myHaberDetay);
                }
            });

        }
    }

}