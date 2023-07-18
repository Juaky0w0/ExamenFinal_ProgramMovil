package xyz.android.finalcalderon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HolidayAdapter extends BaseAdapter {
    private Context context;
    private List<Holiday> holidayList;

    public HolidayAdapter(Context context, List<Holiday> holidayList) {
        this.context = context;
        this.holidayList = holidayList;
    }

    public void updateList(List<Holiday> updatedList) {
        holidayList.clear();
        holidayList.addAll(updatedList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return holidayList.size();
    }

    @Override
    public Object getItem(int position) {
        return holidayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_holiday, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dateTextView = convertView.findViewById(R.id.date_text_view);
            viewHolder.nameTextView = convertView.findViewById(R.id.name_text_view);
            viewHolder.countryTextView = convertView.findViewById(R.id.country_text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Holiday holiday = holidayList.get(position);
        viewHolder.dateTextView.setText(holiday.getDate());
        viewHolder.nameTextView.setText(holiday.getName());
        viewHolder.countryTextView.setText(holiday.getCountry());

        return convertView;
    }

    private static class ViewHolder {
        TextView dateTextView;
        TextView nameTextView;
        TextView countryTextView;
    }
}

