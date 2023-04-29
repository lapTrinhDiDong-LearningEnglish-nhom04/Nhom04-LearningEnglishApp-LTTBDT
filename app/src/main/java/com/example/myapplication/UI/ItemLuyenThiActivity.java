package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Entity.LuyenThi;
import com.example.myapplication.R;

public class ItemLuyenThiActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvSoCau;

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_luyen_thi);
        tvTitle = findViewById(R.id.tvTitle);
        tvSoCau = findViewById(R.id.tvSoCau);

        bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        LuyenThi luyenThi = (LuyenThi) bundle.get("luyenThi_item");
        tvTitle.setText(luyenThi.getTitle());
        tvSoCau.setText(luyenThi.getSoCauHoi() + "");
    }
}