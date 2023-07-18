package xyz.android.finalcalderon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    private EditText yearEditText, countryEditText;
    private Button searchButton;
    private ListView holidayListView;
    private HolidayAdapter holidayAdapter;
    private HolidayDatabase holidayDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yearEditText = findViewById(R.id.year_edit_text);
        countryEditText = findViewById(R.id.country_edit_text);
        searchButton = findViewById(R.id.search_button);
        holidayListView = findViewById(R.id.holiday_list_view);

        holidayAdapter = new HolidayAdapter(this, new ArrayList<>());
        holidayListView.setAdapter(holidayAdapter);

        holidayDatabase = new HolidayDatabase(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = yearEditText.getText().toString();
                String country = countryEditText.getText().toString();
                if (!year.isEmpty() && !country.isEmpty()) {
                    getHolidays(Integer.parseInt(year), country);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingresa el año y el país", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getHolidays(int year, String country) {
        HolidayApiService apiService = new HolidayApiService();

        apiService.getHolidays(year, country, new HolidayApiService.HolidayApiCallback() {
            @Override
            public void onSuccess(List<Holiday> holidays) {
                saveHolidaysToDatabase(holidays);
                displayHolidaysFromDatabase();
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void saveHolidaysToDatabase(List<Holiday> holidays) {
        holidayDatabase.deleteAllHolidays();

        for (Holiday holiday : holidays) {
            holidayDatabase.insertHoliday(holiday);
        }
    }

    private void displayHolidaysFromDatabase() {
        List<Holiday> holidays = holidayDatabase.getAllHolidays();
        holidayAdapter.updateList(holidays);
    }
}