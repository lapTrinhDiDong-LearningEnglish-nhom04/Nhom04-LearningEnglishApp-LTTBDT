package com.example.myapplication.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entity.Book;
import com.example.myapplication.Entity.LuyenThi;
import com.example.myapplication.Entity.TuVung;
import com.example.myapplication.My_Interface.InterfaceClickItemLuyenThiListener;
import com.example.myapplication.My_Interface.InterfaceClickItemTuVungListener;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TuVungApdapter extends RecyclerView.Adapter<TuVungApdapter.TuVungApdapterHolder>{
    private List<TuVung> tuVungs;

    SQLiteDatabase database;
    private InterfaceClickItemTuVungListener interfaceClickItemTuVungListener;

    public TuVungApdapter(List<TuVung> tuVungs, InterfaceClickItemTuVungListener interfaceClickItemTuVungListener) {
        this.tuVungs = tuVungs;
        this.interfaceClickItemTuVungListener = interfaceClickItemTuVungListener;
    }

    @NonNull
    @Override
    public TuVungApdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuvung, parent, false);
        return new TuVungApdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuVungApdapterHolder holder, int position) {
        final TuVung tuVung = tuVungs.get(position);
        if (tuVung == null){
            return;
        }
        holder.tvTiengAnh.setText(tuVung.getTiengAnh());
        holder.tvTiengViet.setText(tuVung.getTiengViet());
        holder.chkLuuTuVung.setChecked(checkTuVungTrongSoTay(tuVung.getTiengAnh(),1));


        holder.layout_item_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickItemTuVungListener.onClickItemTuVung(tuVung);
            }
        });

        holder.chkLuuTuVung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    luuTuVungVaoSoTay(holder.chkLuuTuVung.getContext(), 1,getIdTheoTiengAnh(tuVung.getTiengAnh()));
                } else{
                    xoaTuVungKhoiSoTay(holder.chkLuuTuVung.getContext(), 1,getIdTheoTiengAnh(tuVung.getTiengAnh()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tuVungs != null) {
            return tuVungs.size();
        }
        return 0;
    }

    private void luuTuVungVaoSoTay(Context context, int idND, int idTV) {
        try {
            database = SQLiteDatabase.openDatabase("/data/data/com.example.myapplication/files/LearningEnglish.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            ContentValues cv = new ContentValues();
            cv.put("idND",idND);
            cv.put("idTuVung",idTV);
            long kq= database.insert("ChiTietTuVung",null,cv);
            if(kq!=-1)
                Toast.makeText(context, "Đã lưu vào sổ tay", Toast.LENGTH_SHORT).show();
            database.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void xoaTuVungKhoiSoTay(Context context, int idND, int idTV) {
        try {
            database = SQLiteDatabase.openDatabase("/data/data/com.example.myapplication/files/LearningEnglish.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            String [] deleteArgs = {String.valueOf(idND),String.valueOf(idTV)};
            long kq= database.delete("ChiTietTuVung","idND=? and idTuVung=?",deleteArgs);
            if(kq!=-1)
                Toast.makeText(context, "Đã xóa khỏi sổ tay", Toast.LENGTH_SHORT).show();
            database.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkTuVungTrongSoTay(String tiengAnh, int idND) {
        try {
            database = SQLiteDatabase.openDatabase("/data/data/com.example.myapplication/files/LearningEnglish.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            String[] args = {String.valueOf(idND)};
            Cursor c = database.rawQuery("SELECT tiengAnh FROM ChiTietTuVung c join TuVung t on c.idTuVung = t.idTuVung  WHERE idND = ?",args);
            while(c.moveToNext()){
                String a = c.getString(0);
                if (a.equalsIgnoreCase(tiengAnh))
                    return true;
            }
            database.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private int getIdTheoTiengAnh(String tiengAnh){
        int id=0;
        try {
            database = SQLiteDatabase.openDatabase("/data/data/com.example.myapplication/files/LearningEnglish.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            String[] args = {tiengAnh};
            Cursor c = database.rawQuery("SELECT idTuVung FROM TuVung WHERE tiengAnh = ?",args);
            while(c.moveToNext()){
                id = c.getInt(0);
            }
            database.close();
            return id;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public class TuVungApdapterHolder extends RecyclerView.ViewHolder{
        private androidx.cardview.widget.CardView layout_item_TV;
        private TextView tvTiengAnh;
        private TextView tvTiengViet;
        private CheckBox chkLuuTuVung;


        public TuVungApdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvTiengAnh = itemView.findViewById(R.id.textTiengAnh);
            tvTiengViet = itemView.findViewById(R.id.textViet);
            layout_item_TV = itemView.findViewById(R.id.layout_item_TV);
            chkLuuTuVung = itemView.findViewById(R.id.chkLuuTuVung);
        }
    }
}
