package com.example.prototype2.Setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prototype2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorSetting extends PreferenceFragmentCompat {
    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ColorSetting() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ColorSetting.
//     */
//    // TODO: Rename and change types and number of parameters
    public static ColorSetting newInstance(String rootKey) {
        ColorSetting fragment = new ColorSetting();
        Bundle bundle = new Bundle();
        bundle.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT,rootKey);
        fragment.setArguments(bundle);
        return fragment;
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences,rootKey);
        // クリックされたPreferenceScreen毎にPreferenceのカスタマイズなど
//        switch (rootKey) {
//            case "preference_appearance":
        onCreateAppearancePreferences();
//                break;
//            case "preference_others":
//                break;
//        }
    }
    private void onCreateAppearancePreferences() {
        // テーマ設定の現在の値をSummaryに表示
        ListPreference themePreference = (ListPreference) findPreference("preference_theme");
        themePreference.setSummary(themePreference.getEntry());
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int indexOfValue = themePreference.findIndexOfValue(String.valueOf(newValue));
                themePreference.setSummary(indexOfValue >= 0 ? themePreference.getEntries()[indexOfValue] : null);
                return true;
            }
        });
    }


}