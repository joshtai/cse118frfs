package com.hw.frsecurity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.hw.frsecurity.AddEmployeeActivity.TAG;

// Probably should extract this to handle more listviews instead of just employee

@SuppressLint("SetTextI18n")
public class ListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Employee> A = new ArrayList<>();


    // Experimenting
    public ListAdapter(Context context, ArrayList<Employee> arr) {
        this.context = context;
        this.A = (ArrayList<Employee>) arr.clone();
    }

    @Override
    public int getCount() {
        return A.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_employee_list_item, parent, false);
            viewHolder.employeeId = convertView.findViewById(R.id.employee_item_id_tv);
            viewHolder.employeeName = convertView.findViewById(R.id.employee_item_name_tv);
            viewHolder.employeeTimeSeen = convertView.findViewById(R.id.employee_item_time_tv);
            viewHolder.employeeDateSeen = convertView.findViewById(R.id.employee_item_date_tv);
            viewHolder.employeeImg = convertView.findViewById(R.id.employee_item_img);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        // String employeeId = String.valueOf(A.get(position).getId());
        viewHolder.employeeId.setText("ID: " + String.valueOf(A.get(position).getId()));
        viewHolder.employeeName.setText("Name: " + A.get(position).getName());

        // Date related stuff
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm");

        Date convertedDate = new Date();

        try {
            convertedDate = formatter.parse(A.get(position).getLastSeen());
        } catch (Exception e) {
            //e.printStackTrace();
        }
        Log.d(TAG, "Continuing");

        if (convertedDate != null) {
            Log.d(TAG, "in here");
            String dateOnly = dateFmt.format(convertedDate);
            String timeOnly = timeFmt.format(convertedDate);

            viewHolder.employeeTimeSeen.setText("Time seen: " + timeOnly);
            viewHolder.employeeDateSeen.setText("Date seen: " + dateOnly);
        }
        Log.d(TAG, "made it out");

        // decodes byte array into image
        Bitmap img = BitmapFactory.decodeByteArray(A.get(position).getImg(), 0, A.get(position).getImg().length);
        viewHolder.employeeImg.setImageBitmap(img);
        // viewHolder.employeeImg.setImageResource(A.get(position).getImg());

        return convertView;
    }

    public void swapItems(ArrayList<Employee> items) {
        this.A = items;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView employeeId;
        TextView employeeName;
        TextView employeeTimeSeen;
        TextView employeeDateSeen;
        ImageView employeeImg;
    }

}