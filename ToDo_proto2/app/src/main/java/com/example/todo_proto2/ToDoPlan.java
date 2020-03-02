package com.example.todo_proto2;

import java.util.ArrayList;
import java.util.List;

public class ToDoPlan extends Plan {

    static int tag_num = 0;// タグの数　
    static List<String> category = new ArrayList<String>(); // カテゴリ


    ToDoPlan(){
        tag_num += 1;
        // テストコード
        category.add("カテゴリ１");
        category.add("カテゴリ２");
        category.add("カテゴリ３");

    }

}
