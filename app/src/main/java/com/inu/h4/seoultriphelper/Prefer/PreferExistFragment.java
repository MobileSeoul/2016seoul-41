package com.inu.h4.seoultriphelper.Prefer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.h4.seoultriphelper.DataBase.TAGForPrefer;
import com.inu.h4.seoultriphelper.InnerDBHelper2;
import com.inu.h4.seoultriphelper.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreferExistFragment extends Fragment {
    public static int synk;
    Bundle bundle = null;
    Button reexamineButton, showRecommendSightButton, deletePreferIndexButton;
    TextView preferTextContent, preferTextTitle;
    String index;
    ArrayList<String> sightIdList;
    String[] tagIdList;
    String[] ES = {"6", "10", "17"};
    String[] EN = {"9", "12", "15"};
    String[] IS = {"7", "11", "16"};
    String[] IN = {"8", "13", "14"};
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("사용자 성향");
        View layout = inflater.inflate(R.layout.prefer_full, container, false);

        final InnerDBHelper2 innerDBHelper2 = new InnerDBHelper2(getContext(), "PREFERDB2.db",null,1);
        synk = 0;

        imageView = (ImageView) layout.findViewById(R.id.prefer_character_image);

        preferTextContent = (TextView) layout.findViewById(R.id.prefer_result_text_content);
        preferTextTitle = (TextView) layout.findViewById(R.id.prefer_result_text_title);
        bundle = getArguments();
        if(bundle != null) {
            if(innerDBHelper2.selectPrefer() == null) {        // 기존의 설문 결과가 없을 경우.
                innerDBHelper2.insertPrefer((String) bundle.get("preferIndex"));        // 방금 완성한 설문의 결과를 저장.
                bundle.remove("preferIndex");       // 번들에 저장해놓은 결과는 삭제. (글로벌 설문 결과 변수가 존재하므로.)
            }
        }
        index = innerDBHelper2.selectPrefer();
        tagIdList = getTagIdListAndImageSet(index);

        setPreferTextValue(index);         // 설문 결과를 화면에 출력.

        // 내 성향에 맞는 추천 여행지 ID 받아옴.
        sightIdList = new ArrayList<>();
        TAGForPrefer.getTagId(tagIdList, sightIdList);

        reexamineButton = (Button) layout.findViewById(R.id.btn_prefer_reexamine);
        reexamineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PreferTestFirstFragment()).addToBackStack(null).commit();
            }
        });

        showRecommendSightButton = (Button) layout.findViewById(R.id.bt_recommend);
        showRecommendSightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(synk == 1) {
                    synk = 0;
                    Fragment fragment = new PreferRecommendSightFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("sightList", sightIdList);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                } else
                    Toast.makeText(getContext(), "잠시 후 다시 눌러주세요.",Toast.LENGTH_SHORT).show();
            }
        });

        deletePreferIndexButton = (Button) layout.findViewById(R.id.prefer_delete_index_btn);
        deletePreferIndexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerDBHelper2.deletePrefer(index);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PreferEmptyFragment()).addToBackStack(null).commit();
                Toast.makeText(getContext(), "설문 결과 삭제 완료",Toast.LENGTH_SHORT).show();
            }
        });


        return layout;
    }
    public String[] getTagIdListAndImageSet(String index) {
        switch (index) {
            case "ES":
                imageView.setImageResource(R.drawable.es);
                return ES;
            case "EN":
                imageView.setImageResource(R.drawable.en);
                return EN;
            case "IS":
                imageView.setImageResource(R.drawable.is);
                return IS;
            case "IN":
                imageView.setImageResource(R.drawable.in);
                return IN;
            default:
                return null;
        }
    }

    // 설문 결과(index)를 바탕으로, raw 폴더에서 적절한 값을 가져와서 화면에 출력함.
    public void setPreferTextValue(String index) {
        InputStream is = getResources().openRawResource(R.raw.prefer_result);
        try {
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is,"UTF-8"));
            while(true){
                String string= bufferedReader.readLine();
                if(string != null){
                    if(string.equals(index)) {
                        preferTextTitle.setText(bufferedReader.readLine());
                        preferTextContent.setText(bufferedReader.readLine());
                    }
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}