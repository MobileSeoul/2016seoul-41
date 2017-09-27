package com.inu.h4.seoultriphelper.Prefer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.inu.h4.seoultriphelper.InnerDBHelper2;
import com.inu.h4.seoultriphelper.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreferTestSecondFragment extends Fragment {
    Button PreferTestOkButton, PreferTestRestartButton;
    private ListView listView;                  // 출력을 위한 리스트 뷰 객체
    private ArrayList<String> data;             //  파일로부터 설문내용을 받아옴
    private PreferTestListViewAdapter adapter;      // 리스트 뷰 어댑터

    @Override
    public void onStart() {
        super.onStart();

        final InnerDBHelper2 innerDBHelper2 = new InnerDBHelper2(getContext(), "PREFERDB2.db",null,1);

        adapter = new PreferTestListViewAdapter();

        loadTestText();
        setTestText();
        adapter.initSubList();

        listView = (ListView) getActivity().findViewById(R.id.prefer_test_list_2);
        listView.setAdapter(adapter);

        PreferTestOkButton = (Button) getActivity().findViewById(R.id.btn_prefer_test_ok);
        PreferTestOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isFilledOut()) {
                    if(innerDBHelper2.selectPrefer() != null)          // 기존의 설문 결과가 있는지 확인.
                        innerDBHelper2.deletePrefer(innerDBHelper2.selectPrefer()); // 있을 경우 초기화.

                    Bundle bundle = getArguments();         // 이전 페이지에서 세팅한 번틀을 가져옴.
                    String index = ((String) bundle.get("preferFirstIndex")).concat(getMyPreferSecondValue());    // 첫번째 설문의 결과와 두번째 설문의 결과를 합침.
                    bundle.remove("preferFirstIndex");          // 첫번째 설문 결과 삭제.
                    bundle.putString("preferIndex",index);          // 설문 결과 저장.

                    Fragment fragment = new PreferExistFragment();
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });
        PreferTestRestartButton = (Button) getActivity().findViewById(R.id.btn_prefer_test_restart_2);
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
        return inflater.inflate(R.layout.prefer_test_2, container, false);
    }


    // raw 폴더에 있는 텍스트 파일의 값들을 읽어 옴.
    public void loadTestText() {
        data = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.prefer_test2);
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

    // 두번째 설문 결과 값을 계산.
    public String getMyPreferSecondValue() {
        int value = adapter.getRadioValues();
        String preferIndex;

        if(value > 27) {
            preferIndex = "N";
        } else {
            preferIndex = "S";
        }

        return preferIndex;
    }
}
