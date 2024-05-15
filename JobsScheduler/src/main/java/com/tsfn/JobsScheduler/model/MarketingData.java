package com.tsfn.JobsScheduler.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MarketingData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int data_id;

    private String account;
    private String timeStamp;

    private String postid;
    private String content_Type;
    private int impressions;
    private int views;
    private int clicks;
    private double clickThroughRate;
    private int likes;
    private int comments;
    private int shares;
    private double engagementRate;

    public MarketingData(String account, String timeStamp, String postid, String content_Type, int impressions, int views, int clicks, double clickThroughRate, int likes, int comments, int shares, double engagementRate) {
        this.account = account;
        this.timeStamp = timeStamp;
        this.postid = postid;
        this.content_Type = content_Type;
        this.impressions = impressions;
        this.views = views;
        this.clicks = clicks;
        this.clickThroughRate = clickThroughRate;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.engagementRate = engagementRate;
    }
}