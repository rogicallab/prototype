package com.example.prototype.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "plan_table")
public class Plan {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "plan_name")
    private String planName;

//    @NonNull
//    @ColumnInfo(name="year")
//    private int year;
//
//    @NonNull
//    @ColumnInfo(name="month")
//    private int month;
//
//    @NonNull
//    @ColumnInfo(name="day")
//    private int day;
//
//    @NonNull
//    @ColumnInfo(name="hours")
//    private int hours;
//
//    @NonNull
//    @ColumnInfo(name="year")
//    private int minute;
//
//    @ColumnInfo(name="memo")
//    private String memo;
//    @Embedded
//    public Category category;

    public Plan(String planName){//引数の名前を一致させる
        this.planName=planName;}


    //Getter and Setter

    public int getId(){
        return id;
    }

    public String getPlanName(){
        return planName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlanName(@NonNull String planName) {
        this.planName = planName;
    }



    //    @ColumnInfo(name = "year")
//    public int year;
//    @ColumnInfo(name = "month")
//    public int month;
//    @ColumnInfo(name = "day")
//    public int day;

}

//    protected String planName; // 予定の名前
//    protected  int year;// 日時
//    protected String category; // To-Doのカテゴリ
//
//    protected String note; // メモ
//
//    //    Plan(String planName,Calendar date, String category, Calendar notification, String note){
//    //        this.planName = planName;
//    //        this.date = date;
//    //        this.category = category;
//    //        this.notification = notification;
//    //        this.note = note;
//    //    }
//    public void setPlanName(String name){
//        this.planName=name;
//    }
//    public String getPlanName(){
//        return this.planName;
//    }
//
//}
//@Entity
//public class Plan {
//    @PrimaryKey
//    public int id;
//
//    @ColumnInfo(name = "plan_name")
//    public String planName;
//    @ColumnInfo(name = "year")
//    public int year;
//    @ColumnInfo(name = "month")
//    public int month;
//    @ColumnInfo(name = "day")
//    public int day;
//
//}
