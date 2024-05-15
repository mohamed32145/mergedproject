package com.tsfn.JobsScheduler.model;

import lombok.Getter;

@Getter
public enum MetricsTable {
    _1(1, "Impressions_20_12", "impressions", 20, 12),
    _2(2, "Impressions_50_12", "impressions", 50, 12),
    _3(3, "Impressions_20_24", "impressions", 20, 24),
    _4(4, "Impressions_50_24", "impressions", 50, 24),
    _5(5, "Views_20_12", "Views", 20, 12),
    _6(6, "Views_50_12", "Views", 50, 12),
    _7(7, "Views_20_24", "Views", 20, 24),
    _8(8, "Views_50_24", "Views", 50, 24),
    _9(9, "Clicks_20_12", "Clicks", 20, 12),
    _10(10, "Clicks_50_12", "Clicks", 50, 12),
    _11(11, "Clicks_20_24", "Clicks", 20, 24),
    _12(12, "Clicks_50_24", "Clicks", 50, 24),
    _13(13, "Likes_20_12", "Likes", 20, 12),
    _14(14, "Likes_50_12", "Likes", 50, 12),
    _15(15, "Likes_20_24", "Likes", 20, 24),
    _16(16, "Likes_50_24", "Likes", 50, 24),
    _17(17, "Comments_20_12", "Comments", 20, 12),
    _18(18, "Comments_50_12", "Comments", 50, 12),
    _19(19, "Comments_20_24", "Comments", 20, 24),
    _20(20, "Comments_50_24", "Comments", 50, 24),
    _21(21, "Shares_20_12", "Shares", 20, 12),
    _22(22, "Shares_50_12", "Shares", 50, 12),
    _23(23, "Shares_20_24", "Shares", 20, 24),
    _24(24, "Shares_50_24", "Shares", 50, 24);

    private final int id;
    private final String name;
    private final String metric;
    private final int threshold;
    private final int timeFrameHours;

    MetricsTable(int id, String name, String metric, int threshold, int timeFrameHours) {
        this.id = id;
        this.name = name;
        this.metric = metric;
        this.threshold = threshold;
        this.timeFrameHours = timeFrameHours;
    }


}
