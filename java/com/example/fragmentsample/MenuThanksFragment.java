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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuThanksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuThanksFragment extends Fragment {

    private boolean _isLayoutXLarge = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager manager = getFragmentManager();
        MenuListFragment menuListFragment = (MenuListFragment) manager.findFragmentById(R.id.fragmentMenuList);
        if (menuListFragment == null) {
            _isLayoutXLarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // このFragmentが所属するActivity Objectを取得
        Activity parentActivity = getActivity();
        // Fragmentで表示するがめんをXMLファイルからinflateする
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);

        Bundle extras;
        if (_isLayoutXLarge) {
            extras = getArguments();
        } else {
            // intentを取得
            Intent intent = parentActivity.getIntent();
            // intentから引継ぎDataをまとめてたもの(Bundle Object) を取得
            extras = intent.getExtras();
        }

        // 注文した定食名と金額変数を用意。引継ぎdataが無かった時のために””で初期化
        String menuName = "";
        String menuPrice = "";
        // 引継ぎdata(Bundleオブジェクト)が存在すれば・・・
        if (extras != null) {
            // 定食名と金額を取得
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }
        // 定食名と金額を表示させるTextViewを取得
        TextView tvMenuName = view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = view.findViewById(R.id.tvMenuPrice);

        // TextViewに定食名と金額を表示。
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);

        //戻るボタンを取得
        Button btBackButton = view.findViewById(R.id.btBackButton);
        // 戻るボタンにリストを登録
        btBackButton.setOnClickListener(new ButtonClickListener());

        //inflateされた画面を戻り値として返す。
        return view;
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (_isLayoutXLarge) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(MenuThanksFragment.this);
                transaction.commit();
            } else {
                // このFragmentが所属するActivity Objectを取得
                Activity parentActivity = getActivity();
                // 自分が所属するActivityを修了
                parentActivity.finish();
            }

        }
    }


}