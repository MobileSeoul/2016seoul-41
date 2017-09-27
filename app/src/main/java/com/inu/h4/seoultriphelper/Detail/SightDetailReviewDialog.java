package com.inu.h4.seoultriphelper.Detail;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.inu.h4.seoultriphelper.DataBase.InsertDB_REVIEW1000;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ARRAY;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ForDetailSight;
import com.inu.h4.seoultriphelper.DataBase.SelectDB_REVIEW1000;
import com.inu.h4.seoultriphelper.R;

import java.util.List;

public class SightDetailReviewDialog extends Dialog {

    float rating;
    String writer;
    String content;
    int placeId;
    List<SightDetailReviewListViewItem> reviewList;
    SightDetailItem item;

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public SightDetailReviewDialog(Context context, float rating, int placeId, List<SightDetailReviewListViewItem> reviewList, SightDetailItem item) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.rating = rating;
        this.placeId = placeId;
        this.reviewList = reviewList;
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.7f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.detail_review_dialog);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.detail_review_dialog_rating);
        final EditText writerView = (EditText) findViewById(R.id.detail_review_dialog_writer);
        final EditText contentView = (EditText) findViewById(R.id.detail_review_dialog_content);
        Button submitButton = (Button) findViewById(R.id.btn_detail_review_dialog_submit);

        // 별점 세팅.
        ratingBar.setRating(rating);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력한 값들 받아옴.
                writer = writerView.getText().toString();
                content = contentView.getText().toString();
                rating = ratingBar.getRating();

                // DB에 저장.
                if(writer.equals("") || content.equals("")) {
                    Toast.makeText(getContext(),"빈 칸을 채워주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    InsertDB_REVIEW1000.InsertToDatabase(writer, content, String.valueOf(rating), String.valueOf(placeId));
                    SIGHT1000ForDetailSight.addSumPointAndPeopleCount(String.valueOf(placeId), String.valueOf(rating));
                    SIGHT1000ARRAY.setSumPoint(String.valueOf(placeId), (double)rating);
                    reviewList.clear();
                    SelectDB_REVIEW1000.getData(String.valueOf(placeId), reviewList);
                    SelectDB_REVIEW1000.setAvgRecommendPoint(String.valueOf(placeId), item);
                    Toast.makeText(getContext(),"리뷰 등록 완료!",Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }
}

