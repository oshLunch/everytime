package com.example.myeverytime.fragment.frag_notification;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.myeverytime.R;


public class NotificationFragment extends Fragment {

    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;

    private String courseUniversity = "";
    private String courseYear = "";
    private String courseTerm = "";
    private String courseArea = "";
    private String courseMajor = "";

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final RadioGroup courseUniversityGroup = (RadioGroup)getView().findViewById(R.id.courseUniversityGroup);
        yearSpinner = (Spinner)getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner)getView().findViewById(R.id.termSpinner);
        areaSpinner = (Spinner)getView().findViewById(R.id.areaSpinner);
        majorSpinner = (Spinner)getView().findViewById(R.id.majorSpinner);


        //라디오 버튼에 따라서 달라짐
        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                //라디오 버튼 그룹 선언
                RadioButton courseButton = (RadioButton)getView().findViewById(i);

                //현재 라디오 버튼이 눌린 값의 text를 가져옴
                courseUniversity = courseButton.getText().toString();

                //arrays.xml의 내용을 이용해서 단순한 스피너를 만드는 부분
                yearAdapter = ArrayAdapter.createFromResource(getContext(), R.array.yearArea, android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);//여기서 스피너뷰에 어댑터패턴을 이용해서 데이터를 연결해줌

                //위와 동일
                termAdapter = ArrayAdapter.createFromResource(getContext(), R.array.termArea, android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);

                //라디오버튼의 상태에 따라서 학부와 대학원으로 나눔
                if(courseUniversity.equals("학부")){
                    areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.universityArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);

                    majorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);

                }else{
                    areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.graduateArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);

                    majorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }
}