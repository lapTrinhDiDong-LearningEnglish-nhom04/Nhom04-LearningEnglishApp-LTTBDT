package com.example.myapplication.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Adapter.LuyenThiApdapter;
import com.example.myapplication.DB.Database;
import com.example.myapplication.Entity.Book;
import com.example.myapplication.Entity.Category;
import com.example.myapplication.Entity.LuyenThi;
import com.example.myapplication.My_Interface.InterfaceClickItemLuyenThiListener;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LuyenThiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LuyenThiFragment extends Fragment {
    private RecyclerView recyclerView;
    private LuyenThiApdapter luyenThiApdapter;

    Database db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LuyenThiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShortsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LuyenThiFragment newInstance(String param1, String param2) {
        LuyenThiFragment fragment = new LuyenThiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_luyenthi, container, false);
        recyclerView  = view.findViewById(R.id.rcv_categoryLuyenThi);
        db = new Database(getContext());

        luyenThiApdapter = new LuyenThiApdapter(getListLuyenThi(), new InterfaceClickItemLuyenThiListener() {
            @Override
            public void onClickItemLuyenThi(LuyenThi luyenThi) {
                onClickGoToItemLuyenThi(luyenThi);
            }

            @Override
            public void onClickItem(Object object) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(luyenThiApdapter);


        return view;
    }

    private List<LuyenThi> getListLuyenThi() {
        List<LuyenThi> list= new ArrayList<>();
        try {
            Cursor cursor = db.query_hasresult("select * from LuyenThi");
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    String ten = cursor.getString(1);
                    list.add(new LuyenThi(ten,2,2));
                }
            }else{
                list.add(null);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return  list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void onClickGoToItemLuyenThi(LuyenThi luyenThi){
        Intent intent = new Intent(getActivity(), ItemLuyenThiActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("luyenThi_item", luyenThi);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}