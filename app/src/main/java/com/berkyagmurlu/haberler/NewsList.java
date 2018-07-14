package com.berkyagmurlu.haberler;

public class NewsList {

    private String newsTitle;
    private String newsImage;
    private String newsDate;
    private String newsContent;

    public void setNewsTitle(String nTitle)
    {
        this.newsTitle = nTitle;
    }

    public String getNewsTitle()
    {
        return newsTitle;
    }

    public void setNewsImage(String nImage)
    {
        this.newsImage = nImage;
    }

    public String getNewsImage()
    {
        return newsImage;
    }

    public void setNewsContent(String nContent) { this.newsContent = nContent; }

    public String getNewsContent() { return newsContent; }

    public void setNewsDate(String nDate) { this.newsDate = nDate; }

    public String getNewsDate() { return newsDate; }


}
