package com.example.myapplication.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.BookAdapter;
import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Entity.Book;
import com.example.myapplication.Entity.Category;
import com.example.myapplication.My_Interface.InterfaceClickItemListener;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    private RecyclerView recyclerViewTV;
    private BookAdapter categoryAdapterTV;

    private RecyclerView recyclerViewDH;
    private BookAdapter categoryAdapterDH;

    private RecyclerView recyclerViewNH;
    private BookAdapter categoryAdapterNH;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //TỪ VỰNG
        recyclerViewTV  = view.findViewById(R.id.rcv_categoryTV);
        categoryAdapterTV = new BookAdapter(getListCategoryTV(), new InterfaceClickItemListener() {
            @Override
            public void onClickItem(Book book) {
                onClickGoToDetail(book);
            }

            @Override
            public void onClickItem(Object object) {

            }
        });
        LinearLayoutManager linearLayoutManagerTV = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewTV.setLayoutManager(linearLayoutManagerTV);
        categoryAdapterTV.setData(getListCategoryTV());
        recyclerViewTV.setAdapter(categoryAdapterTV);

        //NGHE HIỂU
        recyclerViewNH  = view.findViewById(R.id.rcv_categoryNH);
        categoryAdapterNH = new BookAdapter(getListCategoryNH(), new InterfaceClickItemListener() {
            @Override
            public void onClickItem(Book book) {
                onClickGoToDetailNH(book);
            }

            @Override
            public void onClickItem(Object object) {

            }
        });
        LinearLayoutManager linearLayoutManagerNH = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewNH.setLayoutManager(linearLayoutManagerNH);
        categoryAdapterNH.setData(getListCategoryNH());
        recyclerViewNH.setAdapter(categoryAdapterNH);

        // ĐỌC HIỂU
        recyclerViewDH  = view.findViewById(R.id.rcv_categoryDH);
        categoryAdapterDH = new BookAdapter(getListCategoryDH(), new InterfaceClickItemListener() {
            @Override
            public void onClickItem(Book book) {
                onClickGoToDetailDH(book);
            }

            @Override
            public void onClickItem(Object object) {

            }
        });
        LinearLayoutManager linearLayoutManagerDH = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewDH.setLayoutManager(linearLayoutManagerDH);
        categoryAdapterDH.setData(getListCategoryDH());
        recyclerViewDH.setAdapter(categoryAdapterDH);
        return view;
    }

    private List<Book> getListCategoryDH() {
        List<Book> bookListDocHieu = new ArrayList<>();
        bookListDocHieu.add(new Book( R.drawable.dien_khuyet," Hoàn thành câu"));
        bookListDocHieu.add(new Book( R.drawable.doc_hieu, "Hoàn thành đoạn văn"));
        return bookListDocHieu;
    }

    private List<Book> getListCategoryNH() {

        List<Book> bookListNgheHieu = new ArrayList<>();
        bookListNgheHieu.add(new Book( R.drawable.mo_ta_anh,"Mô Tả Tranh"));
        bookListNgheHieu.add(new Book( R.drawable.hoi_dap,"Hỏi Đáp"));
        return bookListNgheHieu;
    }

    private List<Book> getListCategoryTV() {
        List<Book> bookListTuVung = new ArrayList<>();
        bookListTuVung.add(new Book( R.drawable.giao_thong,"Giao thông"));
        bookListTuVung.add(new Book( R.drawable.thoi_tiet,"Bản tin \nthời tiết"));
        bookListTuVung.add(new Book( R.drawable.meo3, "Xây dựng"));
        bookListTuVung.add(new Book( R.drawable.xuat_nhap_khau,"Xuất nhập \nkhẩu"));
        bookListTuVung.add(new Book( R.drawable.dong_vat, "Động vật"));
        bookListTuVung.add(new Book( R.drawable.thoi_tiet,"Thời tiết"));
        bookListTuVung.add(new Book( R.drawable.giai_tri, "Giải trí"));
        bookListTuVung.add(new Book( R.drawable.du_lich,"Di lịch"));

        return bookListTuVung;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onClickGoToDetail(Book book){
        Intent intent = new Intent(getContext(), ItemTuVungActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tuVung_item", book);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void onClickGoToDetailNH(Book book){
        Intent intent = new Intent(getContext(), ItemTrangChuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book_item", book);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onClickGoToDetailDH(Book book){
        Intent intent = new Intent(getContext(), ItemTrangChuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book_item", book);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}