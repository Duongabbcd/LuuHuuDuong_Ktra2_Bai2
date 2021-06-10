package com.example.luuhuuduong_ktra2_bai2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.luuhuuduong_ktra2_bai2.control.ExaminationDAO;
import com.example.luuhuuduong_ktra2_bai2.model.Exam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Exam> arrayList = new ArrayList<>();
    ListViewAdapter adapterListView ;
    ListView listView ;
    EditText subname ,amount,dates ;
    CheckBox checkBox ;
    Button addbtn ,updatebtn ,delbtn ,searchbtn ;
    TextView totalamount ;
    int t1h,t1m,pos,idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        subname = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        dates = findViewById(R.id.dates);
        checkBox = findViewById(R.id.checkboxtv);
        totalamount = findViewById(R.id.totalamount);
        addbtn = findViewById(R.id.add);
        updatebtn=findViewById(R.id.update);
        searchbtn=findViewById(R.id.search);
        delbtn=findViewById(R.id.delete);

        DatePickerDialog.OnDateSetListener setListener ;
        Calendar calendar = Calendar.getInstance() ;

        final int year = calendar.get(Calendar.YEAR) ;
        final int month = calendar.get(Calendar.MONTH) ;
        final int day = calendar.get(Calendar.DAY_OF_MONTH) ;

        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1h = hourOfDay ;
                        t1m = minute ;
                        Calendar calendar1  = Calendar.getInstance() ;
                        calendar1.set(0,0,0,t1h,t1m);
                        amount.setText(DateFormat.format("hh:mm:aa",calendar1));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1h,t1m);
                timePickerDialog.show();
            }
        });

        dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1 ;
                        String date = dayOfMonth + "-" + month + "-" + year ;
                        dates.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        final ExaminationDAO database = new ExaminationDAO(this, "LichThi.sqlite" ,null ,1);
        database.queryData("CREATE TABLE IF NOT EXIST " + "Thi(Id INTEGER PRIMARY KEY AUTOINCREMENT ,Subname varchar(200),extime varchar(200) ,exdate varchar(200),ischecked float)");
        final List<Exam> array = database.SearchbyName("");
        int sum = 0;
        for(Exam c : array){
            arrayList.add(c);
            if(c.getIschecked().equals("1")){
                sum++ ;
            }
        }
        totalamount.setText(sum+"");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam c = arrayList.get(position);
                subname.setText(c.getSubname());
                dates.setText(c.getExdate());

                idd = Integer.parseInt(c.getId());
                Toast.makeText(MainActivity.this,c.getId(),Toast.LENGTH_SHORT).show();

                pos =position ;
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten =subname.getText().toString() ;
                String amounts =amount.getText().toString() ;
                String isChecked = 0 +"";
                if(checkBox.isChecked()){
                    isChecked = 1+"";
                }
                String date = dates.getText().toString();
                Exam c = new Exam(String.valueOf(idd),ten ,date ,amounts ,isChecked);
                int result =database.update(c);
                if(result > 0) {
                    Toast.makeText(MainActivity.this,"Upload Successfully !",Toast.LENGTH_SHORT).show();
                    arrayList.set(pos,c);
                }
                adapterListView.notifyDataSetChanged();
            }
        });

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar2 = Calendar.getInstance() ;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy") ;
                String currentDate = simpleDateFormat.format(calendar2.getTime());
                if(currentDate.compareTo(dates.getText().toString()) > 0){
                    int result = database.delete(idd + "");
                    if (result > 0) {

                        Toast.makeText(MainActivity.this,"Deleted Successfully ! ",Toast.LENGTH_SHORT).show();
                        arrayList.remove(pos);
                    }adapterListView.notifyDataSetChanged();
                } else{
                    Toast.makeText(MainActivity.this,"Deleted Failed ! ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapterListView = new ListViewAdapter(arrayList);
        listView.setAdapter(adapterListView);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = subname.getText().toString() ;
                String amounts  = amount.getText().toString() ;
                String isChecked = 0 +"";
                if(checkBox.isChecked()){
                    isChecked = 1+"";
                }
                String date = dates.getText().toString();
                Exam c = new Exam(String.valueOf(idd),ten ,date ,amounts ,isChecked);

                long result = database.Add(c);
                if(result > 0) {
                    Toast.makeText(MainActivity.this ,"Added Successfully !",Toast.LENGTH_SHORT).show();
                    List<Exam> array = database.SearchbyName("") ;
                    for(Exam ex : array){
                        arrayList.add(ex);
                    }
                } adapterListView.notifyDataSetChanged();
            }
        });


       searchbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String ten = subname.getText().toString();
               if(arrayList != null) {
                   arrayList.clear();
               }
               List<Exam> array = database.SearchbyName(ten);
               for(Exam ex :array){
                   arrayList.add(ex);
               }
               adapterListView.notifyDataSetChanged();
           }
       });


    }
}
