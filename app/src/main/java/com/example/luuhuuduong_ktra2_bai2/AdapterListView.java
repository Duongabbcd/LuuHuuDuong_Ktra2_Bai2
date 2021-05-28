package com.example.luuhuuduong_ktra2_bai2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.luuhuuduong_ktra2_bai2.model.Exam;
import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {
        ArrayList<Exam> arrayList;

        public AdapterListView(ArrayList<Exam> arrayList) {
            this.arrayList = arrayList;
        }
        public class ListViewItem{
            TextView name,location,day,time;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItem listViewItem = null;
            if(convertView == null){
                convertView = View.inflate(parent.getContext(),R.layout.customelistview,null);
                listViewItem = new ListViewItem();
                listViewItem.name = convertView.findViewById(R.id.txtName);
                listViewItem.location = convertView.findViewById(R.id.txtLoca);
                listViewItem.day = convertView.findViewById(R.id.txtDate);
                listViewItem.time = convertView.findViewById(R.id.txtAmount);
                convertView.setTag(listViewItem);
            }
            else {
                listViewItem =(ListViewItem) convertView.getTag();
            }
            Exam ex = arrayList.get(position);
            listViewItem.name.setText(ex.getSubname());
            listViewItem.location.setText(ex.getExdate());
            listViewItem.day.setText(ex.getExtime());
            listViewItem.time.setText(ex.getIschecked());
            return convertView;
        }


}
