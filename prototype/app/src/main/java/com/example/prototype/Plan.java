package com.example.prototype;

import java.util.Calendar;

public class Plan {
    
    String planName; // 予定の名前
    Calendar date ; // 日時
    String category; // To-Doのカテゴリ
    Calendar notification; // 通知
    String note; // メモ

    Plan(String planName,Calendar date, String category, Calendar notification, String note){
        this.planName = planName;
        this.date = date;
        this.category = category;
        this.notification = notification;
        this.note = note;
    }

}
