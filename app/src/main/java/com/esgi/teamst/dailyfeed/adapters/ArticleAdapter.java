package com.esgi.teamst.dailyfeed.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.models.Article;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sylvainvincent on 30/04/16.
 */
public class ArticleAdapter extends BaseAdapter {

    private ArrayList<Article> mArticleArrayList;
    private Context mContext;

    public ArticleAdapter(Context context, ArrayList<Article> articleArrayList){
        mContext = context;
        mArticleArrayList = articleArrayList;
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
            ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_article,parent);
            viewHolder = new ViewHolder();
        }

        return null;
    }

    private class ViewHolder{
        private TextView mArticleTitle;
        private TextView mArticleContent;
        private ImageView mArticleImage;
    }
}
