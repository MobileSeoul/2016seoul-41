package com.inu.h4.seoultriphelper.Prefer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.inu.h4.seoultriphelper.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreferTestFirstFragment extends Fragment {
    Button PreferTestNextButton, PreferTestRestartButton;
    private ListView listView;                  // 출력을 위한 리스트 뷰 객체
    private ArrayList<String> data;             //  파일로부터 설문내용을 받아옴
    private PreferTestListViewAdapter adapter;      // 리스트 뷰 어댑터
    @Override
    public void onStart() {
        super.onStart();

        adapter = new PreferTestListViewAdapter();

        loadTestText();
        setTestText();
        adapter.initSubList();

        listView = (ListView) getActivity().findViewById(R.id.prefer_test_list_1);
        listView.setAdapter(adapter);

        PreferTestNextButton = (Button) getActivity().findViewById(R.id.btn_prefer_test_next);
        PreferTestNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isFilledOut()) {         // radioButton을 모두 선택했는지 확인.
                    Bundle bundle = new Bundle();           // 프래그먼트 간 데이터 전송을 위한 번들 생성.
                    bundle.putString("preferFirstIndex", getMyPreferFirstValue());      // 아직 두번째 설문이 완성되지 않았으므로, 첫번째 설문 결과를 저장.
                    Fragment fragment = new PreferTestSecondFragment();     // 두번째 설문 페이지 생성.
                    fragment.setArguments(bundle);      // 위에서 생성한 번들을 세팅.
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });
        PreferTestRestartButton = (Button) getActivity().findViewById(R.id.btn_prefer_test_restart_1);
        PreferTestRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearRadio();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("사용자 성향");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.prefer_test_1, container, false);
    }

    // raw 폴더에 있는 텍스트 파일의 값들을 읽어 옴.
    public void loadTestText() {
        data = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.prefer_test1);
        try {
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is,"UTF-8"));
            while(true){
                String string= bufferedReader.readLine();
                if(string != null){
                    data.add(string);
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 읽어온 값들을 ListView에 출력하기 위한 복사 작업.
    public void setTestText() {
        for (int i=0;i < data.size();i++) {
            adapter.addItem(data.get(i));
        }
    }

    // 첫번째 설문 결과 값을 계산.
    public String getMyPreferFirstValue() {
        int value = adapter.getRadioValues();
        String preferIndex;

        if(value > 30) {
            preferIndex = "E";
        } else {
            preferIndex = "I";
        }

        return preferIndex;
    }
}
