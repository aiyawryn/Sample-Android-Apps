package com.example.fragmentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MenuListFragment extends Fragment {

    private boolean _isLayoutXLarge = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // このFragmentが所属するActivity Objectを取得。
        Activity parentActivity = getActivity();
        // Fragmentで表示する画面をXMLファイルからinflate
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        // 画面部品ListViewを取得。
        ListView lvMenu = view.findViewById(R.id.lvMenu);
        // SimpleAdapterで使用するListオブジェクトを用意。
        List<Map<String, String>> menuList = new ArrayList<>();

        // menuList Data生成処理
        Map<String, String> menu = new HashMap<>();
        menu.put("name", "唐揚げ定食");
        menu.put("price", "600円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼き鳥定食");
        menu.put("price", "250円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼きロース豚定食");
        menu.put("price", "400円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼きそば定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "トンカツ定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "チキンカツ定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "野菜炒め定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "広島お好み焼き定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "大阪お好み焼き定食");
        menu.put("price", "1000円");
        menuList.add(menu);


        String[] from = {"name", "price"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter(parentActivity, menuList, android.R.layout.simple_list_item_2, from, to);
        lvMenu.setAdapter(adapter);

        //listenerの登録
        lvMenu.setOnItemClickListener(new ListItemClickListener());

        return view;

    }

    private class ListItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(i);
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            Activity parentActivity = getActivity();

            Bundle bundle = new Bundle();
            bundle.putString("menuName", menuName);
            bundle.putString("menuPrice", menuPrice);

            if (_isLayoutXLarge) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                menuThanksFragment.setArguments(bundle);
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);
                transaction.commit();
            } else {
                Intent intent = new Intent(parentActivity, MenuThanksActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity parentActivity = getActivity();
        View menuThanksFrame = parentActivity.findViewById(R.id.menuThanksFrame);
        if (menuThanksFrame == null) {
            _isLayoutXLarge = false;
        }
    }
}