package com.example.bo.datepick_p8;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView txtDate,txtTime;
    EditText editCv,editNd;
    Button btnDate,btnTime,btnAdd;
    ArrayList<Job> arrJob = new ArrayList<Job>();
    ArrayAdapter<Job> adapter = null;
    ListView listCv;
    Calendar cal;
    Date dateFinish;
    Date timeFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDate = (TextView) findViewById(R.id.txtdate);
        txtTime = (TextView) findViewById(R.id.txttime);
        editCv = (EditText) findViewById(R.id.editcongviec);
        editNd = (EditText) findViewById(R.id.editnoidung);
        btnDate = (Button) findViewById(R.id.btndate);
        btnTime = (Button) findViewById(R.id.btntime);
        btnAdd = (Button) findViewById(R.id.btncongviec);
        listCv = (ListView) findViewById(R.id.lvcongviec);

        adapter=new ArrayAdapter<Job> (this, android.R.layout.simple_list_item_1, arrJob);
        listCv.setAdapter(adapter);
        getDefaultInfor();
        addEventFormWidgets();
    }
    public void getDefaultInfor()
    {
        cal=Calendar.getInstance();
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = sdf.format(cal.getTime());
        //hiển thị lên giao diện
        txtDate.setText(strDate);
        //Định dạng giờ phút am/pm
        sdf = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime = sdf.format(cal.getTime());
        //đưa lên giao diện
        txtTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(sdf.format(cal.getTime()));

        editCv.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish=cal.getTime();
        timeFinish=cal.getTime();
    }
    public void addEventFormWidgets()
    {
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
        btnAdd.setOnClickListener(new MyButtonEvent());
        listCv.setOnItemClickListener(new MyListViewEvent());
        listCv.setOnItemLongClickListener(new MyListViewEvent());
    }
    private class MyButtonEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btndate:
                    showDatePickerDialog();
                    break;
                case R.id.btntime:
                    showTimePickerDialog();
                    break;
                case R.id.btncongviec:
                    processAddJob();
                    break;
            }
        }

    }
    private class MyListViewEvent implements
            AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener
    {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            //Xóa vị trí thứ arg2
            arrJob.remove(arg2);
            adapter.notifyDataSetChanged();
            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            //Hiển thị nội dung công việc tại vị trí thứ arg2
            Toast.makeText(MainActivity.this,
                    arrJob.get(arg2).getDescription(),
                    Toast.LENGTH_LONG).show();
        }

    }
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                txtDate.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtDate.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                MainActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }
    public void showTimePickerDialog()
    {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s=hourOfDay +":"+minute;
                int hourTam=hourOfDay;
                if(hourTam>12)
                    hourTam=hourTam-12;
                txtTime.setText
                        (hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
                //lưu giờ thực vào tag
                txtTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                timeFinish =cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                MainActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
    public void processAddJob()
    {
        String title=editCv.getText()+"";
        String description=editNd.getText()+"";
        Job job=new Job(title, description, dateFinish, timeFinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        editCv.setText("");
        editNd.setText("");
        editCv.requestFocus();
    }
}
