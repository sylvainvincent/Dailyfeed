package com.esgi.teamst.dailyfeed.xmlHandler;

import android.os.AsyncTask;

import com.esgi.teamst.dailyfeed.models.Article;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by tracysablon on 05/05/2016.
 */
public class XMLParseHandler {

    private ArrayList<Article> articles = null;
    private Article currentArticle = null;
    private Date articleDate;
    private InputStream in_s;

    public XMLParseHandler() {}

    private ArrayList<Article> loadXmlFeeds(String urlString) throws IOException {

        try {
            InputStream in_s = downloadUrl(urlString);
            parseXML(in_s);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (in_s != null) {
                in_s.close();
            }
        }
        return articles;
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
                    if (name.equals("channel")){
                        currentArticle = new Article();
                    }else if (currentArticle != null) {
                        //check for website title
                        if(name.equals("title")){
                            currentArticle.setTitle(parser.nextText());
                        }else if(name.equals("link")){
                            currentArticle.setLink(parser.nextText());
                        }else if(name.equals("pubDate")){
                            //ex : Wed, 04 May 2016 09:05:25 +0000
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
                            try {
                                articleDate = dateFormat.parse(parser.nextText());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            currentArticle.setDate(articleDate);
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
}
