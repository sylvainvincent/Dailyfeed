package com.esgi.teamst.dailyfeed.xmlHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.models.Article;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by tracysablon on 05/05/2016.
 */
public class XMLParseHandler extends AsyncTask<String, Void, ArrayList<Article>> {

    //Param entrée/progress/Result
    private ArrayList<Article> articles = null;
    private Article currentArticle = null;
    private Date articleDate;
    private InputStream in_s;
    ListView mlistViewArticles;
    Context context;

    public XMLParseHandler() {}
    public XMLParseHandler(ListView mlistViewArticles,Context context) {
        this.mlistViewArticles = mlistViewArticles;
        this.context  = context;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        //Perform loadXmlFeeds on a background thread
        return loadXmlFeeds(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleResult) {
        //Run on the UI thread after doBackground
        Log.d("Articles size onPost: ", String.valueOf(articleResult.size()));
        mlistViewArticles.setAdapter(new ArticleAdapter(context,articleResult));
    }

    private void parseXML(InputStream inputStream) throws XmlPullParserException,IOException {
        //Get XMLPullParserFactory reference
        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
        //Send new Parser
        XmlPullParser parser = pullParserFactory.newPullParser();
        parser.setInput(inputStream, "UTF-8");
        //Download input stream from URL
        //Get entity type parser is currently pointed at
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    articles = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("item")){
                        currentArticle = new Article();
                    }else if (currentArticle != null) {
                        //check for website title
                        if(name.equals("title")){
                            currentArticle.setmTitle(parser.nextText());
                        }else if(name.equals("link")){
                            currentArticle.setmArticleLink(parser.nextText());
                        }else if(name.equals("pubDate")){
                            //ex : Wed, 04 May 2016 09:05:25 +0000
                           /* SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
                            try {
                                articleDate = dateFormat.parse(parser.nextText());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            */
                            currentArticle.setmPublishedDate(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("item") && currentArticle != null) {
                        articles.add(currentArticle);
                    }
            }
            eventType = parser.next();
        }
    }

    private InputStream downloadUrl(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();
        return conn.getInputStream();
    }

    public ArrayList<Article> loadXmlFeeds(String urlString) {

        try {
            in_s = downloadUrl(urlString);
            parseXML(in_s);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in_s != null) try {
                in_s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }
}
