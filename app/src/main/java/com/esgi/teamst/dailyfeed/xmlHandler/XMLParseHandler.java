package com.esgi.teamst.dailyfeed.xmlHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.SourceDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;

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
import java.util.List;
import java.util.Map;

/**
 * Created by tracysablon on 05/05/2016.
 */
public class XMLParseHandler {

    private ArrayList<Article> articles = null;
    private Article currentArticle = null;
    private InputStream in_s;

    private void parseXML(InputStream inputStream, int sourceId) throws XmlPullParserException,IOException {
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

                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("item")){
                        currentArticle = new Article();
                        currentArticle.setmSourceId(sourceId);
                    }else if (currentArticle != null) {
                        //check for website title
                        if(name.equals("title")){
                            currentArticle.setmTitle(parser.nextText());
                        }else if(name.equals("description")){
                            currentArticle.setmContent(parser.nextText());
                            Log.d("LOG PARSE CONTENT : ",currentArticle.getmContent());
                        }else if(name.equals("pubDate")){
                            //ex : Wed, 04 May 2016 09:05:25 +0000
                            String inputPattern = "EEE, dd MMM yyyy HH:mm:ss Z";
                            String outputPattern = "dd-MMM-yyyy-h:mm";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                            Date date = null;
                            String str = null;

                            try {
                                date = inputFormat.parse(parser.nextText());
                                str = outputFormat.format(date);
                                Log.d("LOG TEST PARSE : ", String.valueOf(date));
                                Log.d("LOG TEST NEW FORMAT: ", str);
                                currentArticle.setmPublishedDate(str);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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

    public ArrayList<Article> loadXmlFeeds(List<Source> sources) {

        articles = new ArrayList<>();
        if(sources != null){
            for (Source s : sources){
                int id = s.getmId();
                String name = s.getmName();
                String url = "http://"+s.getmUrl();
                Log.d("LOG SOURCE : ",name);
                Log.d("LOG SOURCE ID : ",Integer.toString(id));

                try {
                    in_s = downloadUrl(url);
                    parseXML(in_s,id);
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
            }

        }

        return articles;
    }
}
