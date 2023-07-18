package xyz.android.finalcalderon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HolidayApiService {
    private static final String BASE_URL = "https://date.nager.at/api/v3/";

    public interface HolidayApiCallback {
        void onSuccess(List<Holiday> holidays);
        void onFailure(String errorMessage);
    }
    public void getHolidays(int year, String country, final HolidayApiCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HolidayService holidayService = retrofit.create(HolidayService.class);
        Call<List<Holiday>> call = holidayService.getHolidays(year, country);
        call.enqueue(new Callback<List<Holiday>>() {
            @Override
            public void onResponse(Call<List<Holiday>> call, Response<List<Holiday>> response) {
                if (response.isSuccessful()) {
                    List<Holiday> holidays = response.body();
                    if (holidays != null) {
                        callback.onSuccess(holidays);
                    }
                } else {
                    callback.onFailure("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Holiday>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }
}

