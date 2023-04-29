package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entity.LuyenThi;
import com.example.myapplication.Entity.TuVung;
import com.example.myapplication.My_Interface.InterfaceClickItemLuyenThiListener;
import com.example.myapplication.My_Interface.InterfaceClickItemTuVungListener;
import com.example.myapplication.R;

import java.util.List;

public class TuVungApdapter extends RecyclerView.Adapter<TuVungApdapter.TuVungApdapterHolder>{
    private List<TuVung> tuVungs;
    private InterfaceClickItemTuVungListener interfaceClickItemLuyenThiListener;

    public TuVungApdapter(List<TuVung> tuVungs, InterfaceClickItemTuVungListener interfaceClickItemLuyenThiListener) {
        this.tuVungs = tuVungs;
        this.interfaceClickItemLuyenThiListener = interfaceClickItemLuyenThiListener;
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


        holder.layout_item_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickItemLuyenThiListener.onClickItemTuVung(tuVung);
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

    public class TuVungApdapterHolder extends RecyclerView.ViewHolder{
        private androidx.cardview.widget.CardView layout_item_TV;
        private TextView tvTiengAnh;
        private TextView tvTiengViet;


        public TuVungApdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvTiengAnh = itemView.findViewById(R.id.textTiengAnh);
            tvTiengViet = itemView.findViewById(R.id.textViet);
            layout_item_TV = itemView.findViewById(R.id.layout_item_TV);
        }
    }
}
