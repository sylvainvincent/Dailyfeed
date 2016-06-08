package com.esgi.teamst.dailyfeed.xmlHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.models.Article;
/**
 * Created by tracysablon on 08/06/2016.
 */
public class XMLDatabaseHandler extends AsyncTask<Void, Void, Void> {
    Context context;

    public XMLDatabaseHandler() {}
    public XMLDatabaseHandler(Context context) {
        this.context  = context;
    }
    /*ArticleDAO articleDAO = new ArticleDAO(context);
    articleDAO.open();
    boolean find = articleDAO.add(user);
    articleDAO.close();
    if(find) {

    }else{

    }*/

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
