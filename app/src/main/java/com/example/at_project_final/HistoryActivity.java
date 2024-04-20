package com.example.at_project_final;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private HistoryListAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DBHelper(this);
        List<HistoryItem> historyItems = dbHelper.getAllTexts();

        ListView listViewHistory = findViewById(R.id.listViewHistory);
        historyListAdapter = new HistoryListAdapter(this, R.layout.history_list_item, historyItems, dbHelper);
        listViewHistory.setAdapter(historyListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear_history) {
            showClearHistoryDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearHistoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear History");
        builder.setMessage("Are you sure you want to clear the entire history?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            dbHelper.clearHistory();
            historyListAdapter.clear();
            historyListAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

