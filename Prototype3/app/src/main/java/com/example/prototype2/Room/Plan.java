package com.example.prototype2.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plan_table")
public class Plan {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "plan_name")
    private String planName;

    @ColumnInfo(name="category")
    private String category;

    @ColumnInfo(name="year")
    private Integer year;

    @ColumnInfo(name="month")
    private Integer month;

    @ColumnInfo(name="day")
    private Integer day;

    @ColumnInfo(name="hours")
    private Integer hours;

    @ColumnInfo(name="minute")
    private Integer minute;

    @ColumnInfo(name="memo")
    private String memo;
    //コンストラクタ
    public Plan(String planName,String category,Integer year,Integer month,Integer day,Integer hours,Integer minute,String memo){
        this.planName=planName;
        this.category=category;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hours=hours;
        this.minute=minute;
        this.memo=memo;
    }





    //Getter and Setter

    public Integer getId() {
        return id;
    }

    public String getPlanName() {
        return planName;
    }

    public String getCategory() {
        return category;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getMinute() {
        return minute;
    }

    public String getMemo() {
        return memo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlanName(@NonNull String planName) {
        this.planName = planName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
