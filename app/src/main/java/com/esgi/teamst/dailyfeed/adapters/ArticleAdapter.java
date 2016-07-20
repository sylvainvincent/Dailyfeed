package com.esgi.teamst.dailyfeed.adapters;

import android.app.Activity;
import android.content.Context;
import android.transition.ArcMotion;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;
import com.esgi.teamst.dailyfeed.util.Util;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sylvainvincent on 30/04/16.
 */
public class ArticleAdapter extends BaseAdapter {

    private static final String TAG = ArticleAdapter.class.getSimpleName();
    private List<Article> mArticleArrayList;
    private Context mContext;
    private  List<Source> msources;

    public ArticleAdapter(Context context, List<Article> articleArrayList, List<Source> sources){
        mContext = context;
        mArticleArrayList = articleArrayList;
        msources = sources;
    }

    @Override
    public int getCount() {
        return mArticleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_article,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mArticleTitle = (TextView) convertView.findViewById(R.id.text_article_title);
            viewHolder.mArticleSource = (TextView) convertView.findViewById(R.id.text_article_source);
            viewHolder.mArticleImage = (ImageView) convertView.findViewById(R.id.image_article_thumb);
            viewHolder.mArticleDate = (TextView) convertView.findViewById(R.id.text_article_date);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Article article = mArticleArrayList.get(position);
        if(article != null){
            //Source source;
            viewHolder.mArticleTitle.setText(article.getTitle());
            for(Source source : msources){
                if(source.getId() == article.getSourceId()){
                    viewHolder.mArticleSource.setText(source.getName());
                }
            }
            viewHolder.mArticleDate.setText(new Util().dateDiff(article.getPublishedDate()));
            if(article.getThumbnailLink() != null){
                Log.i(TAG, "getView: " + article.getThumbnailLink());
                Picasso.with(mContext).load(article.getThumbnailLink())
                        .placeholder(R.drawable.article_picture)
                        .into(viewHolder.mArticleImage);
            }else{
                Picasso.with(mContext).load(R.drawable.article_picture).into(viewHolder.mArticleImage);
            }
        }

        return convertView;
    }

    public void refreshList(List<Article> articleArrayList){
        mArticleArrayList = articleArrayList;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        private TextView mArticleTitle;
        private TextView mArticleSource;
        private TextView mArticleDate;
        private ImageView mArticleImage;
    }
}

