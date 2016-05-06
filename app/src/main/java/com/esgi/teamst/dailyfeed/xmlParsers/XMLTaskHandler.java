package com.esgi.teamst.dailyfeed.xmlParsers;

import android.os.AsyncTask;

import com.esgi.teamst.dailyfeed.models.Article;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.List;

/**
 * Created by tracysablon on 05/05/2016.
 */
public class XMLTaskHandler extends AsyncTask<String, Void, String> {

    private ArrayList<String> urlsList;
    private ArrayList<Article> articles = null;
    private Article currentArticle = null;
    public String urlFeed;

    public XMLTaskHandler(ArrayList<String> urlsList) {
        this.urlsList = urlsList;
    }


    private InputStream downloadUrl(String urlString) throws IOException {

       /* urlsList = new ArrayList<String>();
        urlsList.add("http://feeds.feedburner.com/topito/tip-top?format=xml");
        urlsList.add("http://feeds.feedburner.com/AndroidMtApplication?format=xml");
        urlsList.add("http://feeds.feedburner.com/Phonandroid?format=xml");
        */

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();
        return conn.getInputStream();
    }

    protected ArrayList<Article> loadXmlFeeds(String urlString) throws XmlPullParserException, IOException {

        //Get XMLPullParserFactory reference
        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
        //Send new Parser
        XmlPullParser parser = pullParserFactory.newPullParser();
        InputStream in_s = downloadUrl(urlString);
        parser.setInput(in_s,"UTF-8");
        //Get entity type parser is currently pointed at
        int eventType = parser.getEventType();

        List<Article> article = null;
        String url = null;
        StringBuilder htmlString = new StringBuilder();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    articles = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("channel")){
                        currentArticle = new Article();
                    }else if (currentArticle != null) {
                        //check for website title
                        if(name.equals("title")){
                            currentArticle.setTitle(parser.nextText());
                        }else{
                            if(name.equals("title")){
                                currentArticle.setTitle(parser.nextText());
                            }else if(name.equals("pubDate")){
                                currentArticle.setDesciption(parser.nextText());
                            }else if(name.equals("description")){
                                currentArticle.setDesciption(parser.nextText());
                            }else if(name.equals("content")){
                                currentArticle.setContent(parser.nextText());
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("channel") && currentArticle != null) {
                        articles.add(currentArticle);
                    }
            }
            eventType = parser.next();
        }
        return articles;
    }

    @Override
    protected String doInBackground(String... params) {
       /* try {
            return loadXmlFeeds();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
