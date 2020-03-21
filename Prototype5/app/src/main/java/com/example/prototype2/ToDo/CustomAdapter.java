package com.example.prototype2.ToDo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prototype2.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter  {
    private LayoutInflater inflater;
    private int resourcedId;
    private ArrayList<String> items;
    private Context context;

    static class ViewHolder {
        Button editButton;
        TextView textView;
        Button deleteButton;
    }

    CustomAdapter(Context context, int resourcedId, ArrayList<String> items) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourcedId = resourcedId;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourcedId, parent, false);

            holder = new ViewHolder();
            holder.editButton = convertView.findViewById(R.id.delite);
            holder.textView = convertView.findViewById(R.id.text);

//            if(position==items.size()-1){
//                holder.textView.setEnabled(false);
//            }

            holder.deleteButton = convertView.findViewById(R.id.delite);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(items.get(position).equals("カテゴリなし")){
            holder.textView.setEnabled(false);
        }
        holder.textView.setText(items.get(position));

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.delite);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.delite);
            }
        });

        View finalConvertView = convertView;
        ((EditText) convertView.findViewById(R.id.text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 表示されているデータの更新
                items.set(position,(String)((EditText) finalConvertView.findViewById(R.id.text)).getText().toString());
                System.out.println("items.get(position)"+items.get(position));

                // sharedPreferencesの更新
                Activity activity = (Activity) context;
                SharedPreferences data = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                data.edit().putString("category",gson.toJson(items)).apply();
                notifyDataSetChanged();


            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

