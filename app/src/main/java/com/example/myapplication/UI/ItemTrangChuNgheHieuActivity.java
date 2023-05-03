package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.SoCauAdapter;
import com.example.myapplication.DB.Database;
import com.example.myapplication.EnityDB.DocHieu;
import com.example.myapplication.EnityDB.DocHieuHTCau;
import com.example.myapplication.EnityDB.DocHieuHTDoanVan;
import com.example.myapplication.EnityDB.LuyenNhanh;
import com.example.myapplication.EnityDB.NgheHieu;
import com.example.myapplication.EnityDB.NgheHieuHoiDap;
import com.example.myapplication.EnityDB.NgheHieuMoTaTranh;
import com.example.myapplication.Entity.Book;
import com.example.myapplication.Entity.LuyenThi;
import com.example.myapplication.Entity.SoCau;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemTrangChuNgheHieuActivity extends AppCompatActivity {

    TextView tv_name_user,tvArrowBack, textView;
    Bundle bundle;

    Button btnBatDau;
    private Spinner spinner;
    private SoCauAdapter soCauAdapter;

    Database db;
    int soLuongCauHoi = 0;
    Calendar calendar = Calendar.getInstance();
    Date currentTime = calendar.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String formattedTime = sdf.format(currentTime);
    private ArrayList<NgheHieu> listVoiceMoTaTranh;
    private ArrayList<NgheHieuMoTaTranh> listDapAn;
    private String maLN;
    private ArrayList<NgheHieu> listVoiceHoiDap;
    private ArrayList<NgheHieuHoiDap> listDapAnHoiDap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_trang_chu_doc_hieu);
        tv_name_user = findViewById(R.id.tv_name_user);
        tvArrowBack = findViewById(R.id.tvArrowBack);
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinnerSoCau);
        btnBatDau = findViewById(R.id.btnBatDau);
        db = new Database(ItemTrangChuNgheHieuActivity.this);

        tvArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemTrangChuNgheHieuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bundle  = getIntent().getExtras();
        if (bundle == null){
            return;
        }



        Book book  = (Book) bundle.get("ngheHieu_item");
        Integer id = (Integer) bundle.get("id_item");
        if (id == 2131165414) {
            soCauAdapter = new SoCauAdapter(this, R.layout.item_selected, getListSoCau());
            spinner.setAdapter(soCauAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    soLuongCauHoi = soCauAdapter.getItem(position).getSoCau();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            tv_name_user.setText(book.getTitle());
            textView.setText(" For each question, you will see a picture and you will hear four short staments. The staments will be spoken just one time. They will not be printed in your test book so you must listen carefully to understand what the speaker says. When you hear the four statements, look at the picture and choose the statement that best describes what you see in the picture. Choose the best answer A, B, C or D \n" +
                    " Đối với mỗi câu hỏi, bạn sẽ thấy một bức tranh và bạn sẽ nghe thấy bốn đoạn ngắn. Các nhị hoa sẽ được nói chỉ một lần. Chúng sẽ không được in trong tập kiểm tra của bạn, vì vậy bạn phải lắng nghe cẩn thận để hiểu những gì người nói nói. Khi bạn nghe bốn câu nói, hãy nhìn vào bức tranh và chọn câu mô tả đúng nhất những gì bạn nhìn thấy trong bức tranh. Chọn câu trả lời đúng nhất A, B, C hoặc D" );
            btnBatDau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long diffInMinutes = 0;
                    try {
                        // Tạo một đối tượng Calendar mới
                        Calendar calendar = Calendar.getInstance();
                        // Cộng thêm 10 phút vào đối tượng Calendar
                        calendar.add(Calendar.MINUTE, soLuongCauHoi * 1);
                        // Lấy thời gian mới của đối tượng Calendar
                        Date newTime = calendar.getTime();
                        // Định dạng thời gian mới và in ra
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String formattedTimeNew = sdf.format(newTime);

                        long diffInMillis = newTime.getTime() - currentTime.getTime(); // Khoảng thời gian tính bằng milliseconds
                        diffInMinutes = TimeUnit.MILLISECONDS.toMillis(diffInMillis);

                        try {
                            Cursor cursor2 = db.query_hasresult("SELECT * FROM LuyenNhanh ORDER BY idLN DESC LIMIT 1");
                            maLN = "";
                            if (cursor2.getCount() != 0) {
                                while (cursor2.moveToNext()) {
                                    int id = cursor2.getInt(0);
                                    maLN += id;
                                }
                            } else {
                                maLN = "Không có dữ liệu";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        int idLN = Integer.parseInt(maLN) + 1;
                        String sqlLN = "INSERT INTO LuyenNhanh(idLN, soLuongCauHoiGioiHan, thoiGianBatDau, thoiGianKetThuc, idND) VALUES('" + idLN + "', '" + soLuongCauHoi + "', '" + formattedTime + "', '" + formattedTimeNew + "', '1')";
                        db.query_noresult(sqlLN);

//                    Lấy nghe hiểu:
                        try {
                            listVoiceMoTaTranh = new ArrayList<NgheHieu>();
                            listDapAn = new ArrayList<NgheHieuMoTaTranh>();
                            Cursor cursor = db.query_hasresult("SELECT * FROM NgheHieu WHERE idNH <= 5 ORDER BY RANDOM() LIMIT " + soLuongCauHoi + "");
                            if (cursor.getCount() != 0) {
                                while (cursor.moveToNext()) {
                                    NgheHieu ngheHieu;
                                    int id = cursor.getInt(0);
                                    String voice = cursor.getString(1);
                                    ngheHieu = new NgheHieu(id, voice);
                                    listVoiceMoTaTranh.add(ngheHieu);
                                    listDapAn = layDapAn(listVoiceMoTaTranh);

                                }
                            } else {
                                listVoiceMoTaTranh = null;
                            }

                            for (int i = 0; i < listVoiceMoTaTranh.size(); i++) {
                                String sqlLNNH = "INSERT INTO LuyenNhanhNgheHieu (idLN, idNH) VALUES ('" + idLN + "', '" + listVoiceMoTaTranh.get(i).getIdNH() + "')";
                                db.query_noresult(sqlLNNH);
                            }

                            //IN RA ĐỂ COI
                            for (int i = 0; i < listVoiceMoTaTranh.size(); i++) {
                                Log.d("TAG", "Voice " + (i + 1) + ": " + listVoiceMoTaTranh.get(i).getVoice());
                            }

                            for (int i = 0; i < listDapAn.size(); i++) {
                                Log.d("TAG", "ĐÁP ÁN " + (i + 1) + ": " + listDapAn.get(i).getDapAnA());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Tạo một Intent để chuyển từ Activity hiện tại sang Activity khác
                    Intent intent = new Intent(ItemTrangChuNgheHieuActivity.this, NgheHieuMTTTestNhanhActivity.class);

                    // Tạo một ArrayList mới từ mảng listDe
                    ArrayList<NgheHieuMoTaTranh> arrayListDe = new ArrayList<NgheHieuMoTaTranh>();

                    for (NgheHieuMoTaTranh ngheHieuMoTaTranh : listDapAn) {
                        // create a new DocHieu object using the data from the LuyenNhanh object
                        NgheHieuMoTaTranh ngheHieuMoTaTranh2 = new NgheHieuMoTaTranh(new NgheHieu(ngheHieuMoTaTranh.getIdNH().getIdNH(), ngheHieuMoTaTranh.getIdNH().getVoice()), ngheHieuMoTaTranh.getDeHinhAnh(),ngheHieuMoTaTranh.getDapAnA(), ngheHieuMoTaTranh.getDapAnB(), ngheHieuMoTaTranh.getDapAnC(), ngheHieuMoTaTranh.getDapAnD(), ngheHieuMoTaTranh.getDapAnDung());
                        // add the new DocHieu object to the ArrayList
                        arrayListDe.add(ngheHieuMoTaTranh2);
                    }

                    // Đưa ArrayList vào Intent
                    intent.putParcelableArrayListExtra("list_de", (ArrayList<? extends Parcelable>) arrayListDe);
                    intent.putExtra("myDataThoiGian", diffInMinutes);
                    // Khởi chạy Activity mới
                    startActivity(intent);
                }
            });
        }
        else if (id == 2131165372){
            soCauAdapter = new SoCauAdapter(this, R.layout.item_selected, getListSoCau());
            spinner.setAdapter(soCauAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    soLuongCauHoi = soCauAdapter.getItem(position).getSoCau();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            tv_name_user.setText(book.getTitle());
            textView.setText(" For each question, you will hear a question. The staments will be spoken just one time. They will not be printed in your test book so you must listen carefully to understand what the speaker says. When you hear the four statements, look at the picture and choose the statement that best describes what you see in the picture. Choose the best answer A, B or C \n" +
                    " Đối với mỗi câu hỏi, bạn sẽ nghe thấy một câu hỏi. Các nhị hoa sẽ được nói chỉ một lần. Chúng sẽ không được in trong tập kiểm tra của bạn, vì vậy bạn phải lắng nghe cẩn thận để hiểu những gì người nói nói. Khi bạn nghe bốn câu nói, hãy nhìn vào bức tranh và chọn câu mô tả đúng nhất những gì bạn nhìn thấy trong bức tranh. Chọn câu trả lời đúng nhất A, B hoặc C" );
            btnBatDau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long diffInMinutes = 0;
                    try {
                        // Tạo một đối tượng Calendar mới
                        Calendar calendar = Calendar.getInstance();
                        // Cộng thêm 10 phút vào đối tượng Calendar
                        calendar.add(Calendar.MINUTE, soLuongCauHoi * 1);
                        // Lấy thời gian mới của đối tượng Calendar
                        Date newTime = calendar.getTime();
                        // Định dạng thời gian mới và in ra
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String formattedTimeNew = sdf.format(newTime);

                        long diffInMillis = newTime.getTime() - currentTime.getTime(); // Khoảng thời gian tính bằng milliseconds
                        diffInMinutes = TimeUnit.MILLISECONDS.toMillis(diffInMillis);

                        try {
                            Cursor cursor2 = db.query_hasresult("SELECT * FROM LuyenNhanh ORDER BY idLN DESC LIMIT 1");
                            maLN = "";
                            if (cursor2.getCount() != 0) {
                                while (cursor2.moveToNext()) {
                                    int id = cursor2.getInt(0);
                                    maLN += id;
                                }
                            } else {
                                maLN = "Không có dữ liệu";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        int idLN = Integer.parseInt(maLN) + 1;
                        String sqlLN = "INSERT INTO LuyenNhanh(idLN, soLuongCauHoiGioiHan, thoiGianBatDau, thoiGianKetThuc, idND) VALUES('" + idLN + "', '" + soLuongCauHoi + "', '" + formattedTime + "', '" + formattedTimeNew + "', '1')";
                        db.query_noresult(sqlLN);

//                    Lấy nghe hiểu:
                        try {
                            listVoiceHoiDap = new ArrayList<NgheHieu>();
                            listDapAnHoiDap = new ArrayList<NgheHieuHoiDap>();
                            Cursor cursor = db.query_hasresult("SELECT * FROM NgheHieu WHERE idNH > 5 ORDER BY RANDOM() LIMIT " + soLuongCauHoi + "");
                            if (cursor.getCount() != 0) {
                                while (cursor.moveToNext()) {
                                    NgheHieu ngheHieu;
                                    int id = cursor.getInt(0);
                                    String voice = cursor.getString(1);
                                    ngheHieu = new NgheHieu(id, voice);
                                    listVoiceHoiDap.add(ngheHieu);
                                    listDapAnHoiDap = layDapAnHD(listVoiceHoiDap);

                                }
                            } else {
                                listVoiceHoiDap = null;
                            }

                            for (int i = 0; i < listVoiceHoiDap.size(); i++) {
                                String sqlLNNH = "INSERT INTO LuyenNhanhNgheHieu (idLN, idNH) VALUES ('" + idLN + "', '" + listVoiceHoiDap.get(i).getIdNH() + "')";
                                db.query_noresult(sqlLNNH);
                            }

                            //IN RA ĐỂ COI
                            for (int i = 0; i < listVoiceHoiDap.size(); i++) {
                                Log.d("TAG", "Voice " + (i + 1) + ": " + listVoiceHoiDap.get(i).getVoice());
                            }

                            for (int i = 0; i < listDapAnHoiDap.size(); i++) {
                                Log.d("TAG", "ĐÁP ÁN " + (i + 1) + ": " + listDapAnHoiDap.get(i).getDapAnA());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Tạo một Intent để chuyển từ Activity hiện tại sang Activity khác
                    Intent intent = new Intent(ItemTrangChuNgheHieuActivity.this, NgheHieuHDTestNhanhActivity.class);

                    // Tạo một ArrayList mới từ mảng listDe
                    ArrayList<NgheHieuHoiDap> arrayListDe = new ArrayList<NgheHieuHoiDap>();

                    for (NgheHieuHoiDap ngheHieuHoiDap : listDapAnHoiDap) {
                        // create a new DocHieu object using the data from the LuyenNhanh object
                        NgheHieuHoiDap ngheHieuHoiDap2 = new NgheHieuHoiDap(new NgheHieu(ngheHieuHoiDap.getIdNH().getIdNH(), ngheHieuHoiDap.getIdNH().getVoice()),ngheHieuHoiDap.getDapAnA(), ngheHieuHoiDap.getDapAnB(), ngheHieuHoiDap.getDapAnC(), ngheHieuHoiDap.getDapAnDung());
                        // add the new DocHieu object to the ArrayList
                        arrayListDe.add(ngheHieuHoiDap2);
                    }

                    // Đưa ArrayList vào Intent
                    intent.putParcelableArrayListExtra("list_de", (ArrayList<? extends Parcelable>) arrayListDe);
                    intent.putExtra("myDataThoiGian", diffInMinutes);
                    // Khởi chạy Activity mới
                    startActivity(intent);
                }
            });
        }
//
//            soCauAdapter = new SoCauAdapter(this, R.layout.item_selected, getListSoCauHTCau());
//            spinner.setAdapter(soCauAdapter);
//
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    soLuongCauHoi = soCauAdapter.getItem(position).getSoCau();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//            tv_name_user.setText(book.getTitle());
//            textView.setText(" The test will give you a passage with lots of blanks, then you can find the best answer to fill in the blanks. The total number of questions in this section 4 sentences corresponds to 1 passage, each passage consists of 4 questions.\n" +
//                    " Đề bài sẽ cho bạn một đoạn văn có nhiều chỗ trống, sau đó bạn có thể tìm đáp án phù hợp nhất để điền vào chỗ trống. Tổng số câu hỏi trong phần này 4 câu tương ứng với 1 đoạn văn, mỗi đoạn văn bao gồm 4 câu hỏi. ");
//            btnBatDau.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    long diffInMinutes = 0;
//                    try {
//                        // Tạo một đối tượng Calendar mới
//                        Calendar calendar = Calendar.getInstance();
//                        // Cộng thêm 10 phút vào đối tượng Calendar
//                        calendar.add(Calendar.MINUTE, soLuongCauHoi * 2);
//                        // Lấy thời gian mới của đối tượng Calendar
//                        Date newTime = calendar.getTime();
//                        // Định dạng thời gian mới và in ra
//                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//                        String formattedTimeNew = sdf.format(newTime);
//
//                        long diffInMillis = newTime.getTime() - currentTime.getTime(); // Khoảng thời gian tính bằng milliseconds
//                        diffInMinutes = TimeUnit.MILLISECONDS.toMillis(diffInMillis);
//
//                        try {
//                            Cursor cursor2 = db.query_hasresult("SELECT * FROM LuyenNhanh ORDER BY idLN DESC LIMIT 1");
//                            maLN = "";
//                            if (cursor2.getCount() != 0) {
//                                while (cursor2.moveToNext()) {
//                                    int id = cursor2.getInt(0);
//                                    maLN += id;
//                                }
//                            } else {
//                                maLN = "Không có dữ liệu";
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        int idLN = Integer.parseInt(maLN) + 1;
//                        String sqlLN = "INSERT INTO LuyenNhanh(idLN, soLuongCauHoiGioiHan, thoiGianBatDau, thoiGianKetThuc, idND) VALUES('" + idLN + "', '" + soLuongCauHoi + "', '" + formattedTime + "', '" + formattedTimeNew + "', '1')";
//                        db.query_noresult(sqlLN);
//
////                    Lấy đọc hiểu:
//                        try {
//                            listDeHtDoanVan = new ArrayList<DocHieu>();
//                            listDapAnHtDoanVan = new ArrayList<DocHieuHTDoanVan>();
//                            Log.d("TAG", "Số lượng câu hỏi: " + soLuongCauHoi);
//                            Cursor cursor = db.query_hasresult("SELECT * FROM DocHieu WHERE idDH > 15 ORDER BY RANDOM() LIMIT " + soLuongCauHoi + "");
//                            if (cursor.getCount() != 0) {
//                                while (cursor.moveToNext()) {
//                                    DocHieu docHieu;
//                                    int id = cursor.getInt(0);
//                                    String de = cursor.getString(1);
//                                    docHieu = new DocHieu(id, de);
//                                    listDeHtDoanVan.add(docHieu);
//                                    listDapAnHtDoanVan = layDapAnHTDoanVan(listDeHtDoanVan);
//
//                                }
//                            } else {
//                                listDeHtDoanVan = null;
//                            }
//
//                            for (int i = 0; i < listDeHtDoanVan.size(); i++) {
//                                String sqlLNDH = "INSERT INTO LuyenNhanhDocHieu (idLN, idDH) VALUES ('" + idLN + "', '" + listDeHtDoanVan.get(i).getIdDH() + "')";
//                                db.query_noresult(sqlLNDH);
//                            }
//
////                            //IN RA ĐỂ COI
////                            for (int i = 0; i < listDeHtDoanVan.size(); i++) {
////                                Log.d("TAG", "Đề " + (i + 1) + ": " + listDeHtDoanVan.get(i));
////                            }
////
////                            for (int i = 0; i < listDapAnHtDoanVan.size(); i++) {
////                                Log.d("TAG", "ĐÁP ÁN " + (i + 1) + ": " + listDapAnHtDoanVan.get(i));
////                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // Tạo một Intent để chuyển từ Activity hiện tại sang Activity khác
//                    Intent intent = new Intent(ItemTrangChuDocHieuActivity.this, DocHieuTestNhanhHTCauActivity.class);
//
//                    // Tạo một ArrayList mới từ mảng listDe
//                    ArrayList<DocHieuHTDoanVan> arrayListDeHtDoanVan = new ArrayList<DocHieuHTDoanVan>();
//
//                    for (DocHieuHTDoanVan docHieuHTDoanVan : listDapAnHtDoanVan) {
//                        // create a new DocHieu object using the data from the LuyenNhanh object
//                        DocHieuHTDoanVan docHieuHTDoanVan2 = new DocHieuHTDoanVan(new DocHieu(docHieuHTDoanVan.getIdDH().getIdDH(), docHieuHTDoanVan.getIdDH().getDe()),docHieuHTDoanVan.getIdCau(), docHieuHTDoanVan.getDapAnA(), docHieuHTDoanVan.getDapAnB(), docHieuHTDoanVan.getDapAnC(), docHieuHTDoanVan.getDapAnD(), docHieuHTDoanVan.getDapAnDung());
//                        // add the new DocHieu object to the ArrayList
//                        arrayListDeHtDoanVan.add(docHieuHTDoanVan2);
//                    }
//
//                    // Đưa ArrayList vào Intent
//                    intent.putParcelableArrayListExtra("list_de_ht_cau", (ArrayList<? extends Parcelable>) arrayListDeHtDoanVan);
//                    intent.putExtra("myDataThoiGian", diffInMinutes);
//                    // Khởi chạy Activity mới
//                    startActivity(intent);
//                }
//            });
//        }


    }

//    private ArrayList<DocHieuHTDoanVan> layDapAnHTDoanVan(ArrayList<DocHieu> listDeHtDoanVan) {
//        try {
//            ArrayList<DocHieuHTDoanVan> listDapAnHtDoanVan = new ArrayList<DocHieuHTDoanVan>();
//            DocHieuHTDoanVan docHieuHTDoanVan;
//            for (int i = 0; i < listDeHtDoanVan.size(); i++) {
//                Cursor cursor = db.query_hasresult("select * from DocHieuHTDoanVan WHERE idDH = "+listDeHtDoanVan.get(i).getIdDH()+"");
//                String ds = "";
//                if (cursor.getCount() != 0){
//                    while (cursor.moveToNext()){
//                        int idDH = cursor.getInt(0);
//                        if (idDH == listDeHtDoanVan.get(i).getIdDH()){
//                            int id = cursor.getInt(1);
//                            String dapAnA = cursor.getString(2);
//                            String dapAnB= cursor.getString(3);
//                            String dapAnC = cursor.getString(4);
//                            String dapAnD = cursor.getString(5);
//                            String dapAnDung = cursor.getString(6);
//                            docHieuHTDoanVan = new DocHieuHTDoanVan(new DocHieu(listDeHtDoanVan.get(i).getIdDH(),listDeHtDoanVan.get(i).getDe()),id, dapAnA, dapAnB, dapAnC, dapAnD, dapAnDung);
//
//                            listDapAnHtDoanVan.add(docHieuHTDoanVan);
//                        }else {
//                            Toast.makeText(this, "Không có đáp án", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }else{
//                    listDapAnHtDoanVan = null;
//                }
//            }
//            return listDapAnHtDoanVan;
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private ArrayList<NgheHieuMoTaTranh> layDapAn(ArrayList<NgheHieu> listVoiceMoTaTranh) {
        try {
            ArrayList<NgheHieuMoTaTranh> listDapAn = new ArrayList<NgheHieuMoTaTranh>();
            NgheHieuMoTaTranh ngheHieuMoTaTranh;
            for (int i = 0; i < listVoiceMoTaTranh.size(); i++) {
                Cursor cursor = db.query_hasresult("select * from NgheHieuMoTaTranh WHERE idNH = "+listVoiceMoTaTranh.get(i).getIdNH()+"");
                String ds = "";
                if (cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int idNH = cursor.getInt(0);
                        if (idNH == listVoiceMoTaTranh.get(i).getIdNH()){
                            String deHinhAnh = cursor.getString(1);
                            String dapAnA = cursor.getString(2);
                            String dapAnB= cursor.getString(3);
                            String dapAnC = cursor.getString(4);
                            String dapAnD = cursor.getString(5);
                            String dapAnDung = cursor.getString(6);
                            ngheHieuMoTaTranh = new NgheHieuMoTaTranh(new NgheHieu(listVoiceMoTaTranh.get(i).getIdNH(),listVoiceMoTaTranh.get(i).getVoice()),deHinhAnh,dapAnA,dapAnB,dapAnC,dapAnD,dapAnDung);
                            listDapAn.add(ngheHieuMoTaTranh);
                        }else {
                            Toast.makeText(this, "Không có đáp án", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    listDapAn = null;
                }
            }
            return listDapAn;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<NgheHieuHoiDap> layDapAnHD(ArrayList<NgheHieu> listVoiceHoiDap) {
        try {
            ArrayList<NgheHieuHoiDap> listDapAn = new ArrayList<NgheHieuHoiDap>();
            NgheHieuHoiDap ngheHieuHoiDap;
            for (int i = 0; i < listVoiceHoiDap.size(); i++) {
                Cursor cursor = db.query_hasresult("select * from NgheHieuHoiDap WHERE idNH = "+listVoiceHoiDap.get(i).getIdNH()+"");
                String ds = "";
                if (cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int idNH = cursor.getInt(0);
                        if (idNH == listVoiceHoiDap.get(i).getIdNH()){
                            String dapAnA = cursor.getString(1);
                            String dapAnB= cursor.getString(2);
                            String dapAnC = cursor.getString(3);
                            String dapAnDung = cursor.getString(4);
                            ngheHieuHoiDap = new NgheHieuHoiDap(new NgheHieu(listVoiceHoiDap.get(i).getIdNH(),listVoiceHoiDap.get(i).getVoice()),dapAnA,dapAnB,dapAnC,dapAnDung);
                            listDapAn.add(ngheHieuHoiDap);
                        }else {
                            Toast.makeText(this, "Không có đáp án", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    listDapAn = null;
                }
            }
            return listDapAn;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<SoCau> getListSoCau() {
        List<SoCau> list = new ArrayList<>();
        list.add(new SoCau(1));
        list.add(new SoCau(2));
        list.add(new SoCau(3));
        list.add(new SoCau(4));
        list.add(new SoCau(5));
        return list;
    }
}