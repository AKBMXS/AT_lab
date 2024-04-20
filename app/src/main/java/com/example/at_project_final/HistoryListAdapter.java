package com.example.at_project_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HistoryListAdapter extends ArrayAdapter<HistoryItem> {

    private final LayoutInflater inflater;
    private final DBHelper dbHelper;

    public HistoryListAdapter(Context context, int resource, List<HistoryItem> objects, DBHelper dbHelper) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.history_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewText = convertView.findViewById(R.id.textViewText);
            viewHolder.textViewTime = convertView.findViewById(R.id.textViewTime);
            viewHolder.deleteButton = convertView.findViewById(R.id.buttonDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final HistoryItem historyItem = getItem(position);
        if (historyItem != null) {
            viewHolder.textViewText.setText(historyItem.getExtractedText());
            viewHolder.textViewTime.setText(historyItem.getExtractionTime());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete the entry from the database
                    dbHelper.deleteEntry((int) historyItem.getId());
                    // Remove the deleted item from the adapter
                    remove(historyItem);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewText;
        TextView textViewTime;
        Button deleteButton;
    }
}
