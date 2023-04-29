package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.LuyenThiApdapter;
import com.example.myapplication.Adapter.TuVungApdapter;
import com.example.myapplication.Entity.Book;
import com.example.myapplication.Entity.LuyenThi;
import com.example.myapplication.Entity.SoCau;
import com.example.myapplication.Entity.TuVung;
import com.example.myapplication.My_Interface.InterfaceClickItemLuyenThiListener;
import com.example.myapplication.My_Interface.InterfaceClickItemTuVungListener;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ItemTuVungActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TuVungApdapter tuVungApdapter;
    TextView tv_name_user,tvArrowBack;
    TextView tvTitleTuVung;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_tu_vung);
        tv_name_user = findViewById(R.id.tv_name_user);
        tvArrowBack = findViewById(R.id.tvArrowBack);
        tvTitleTuVung = findViewById(R.id.tvTitleTuVung);

        tvArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bundle  = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Book book  = (Book) bundle.get("tuVung_item");
        tvTitleTuVung.setText(book.getTitle());


        recyclerView  = findViewById(R.id.rcv_categoryLuyenThi);
        tuVungApdapter = new TuVungApdapter(getListTuVung(), new InterfaceClickItemTuVungListener() {
            @Override
            public void onClickItemTuVung(TuVung tuVung) {
                Toast.makeText(ItemTuVungActivity.this, "Chưa có", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickItem(Object object) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(tuVungApdapter);

    }

    private List<TuVung> getListTuVung() {
        List<TuVung> list = new ArrayList<>();
        list.add(new TuVung("Apple","Táo"));
        list.add(new TuVung("Apple","Táo"));
        list.add(new TuVung("Apple","Táo"));
        list.add(new TuVung("Apple","Táo"));
        list.add(new TuVung("Apple","Táo"));
        list.add(new TuVung("Apple","Táo"));
        list.add(new TuVung("Apple","Táo"));

        return list;
    }
}