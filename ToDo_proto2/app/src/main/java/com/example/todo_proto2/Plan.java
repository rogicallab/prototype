package com.example.todo_proto2;

import java.util.Calendar;

public class Plan {


    String planName; // 予定の名前
    Calendar date ; // 日時
    String category; // To-Doのカテゴリ
    Calendar notification; // 通知
    String note; // メモ

    Plan(){//これはサブクラスでエラーが起きないようにつけただけ

    }

    Plan(String planName,String category, String note){// 日時/通知を除いたPlan
        this.planName = planName;
        this.category = category;
        this.note = note;
    }

    Plan(String planName,Calendar date, String category, Calendar notification, String note){
        this.planName = planName;
        this.date = date;
        this.category = category;
        this.notification = notification;
        this.note = note;
    }

}
