package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.provider.Settings;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.AlertNofication.DailyReminder;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.AlertNofication.UpcomingReminder;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.BuildConfig;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsPrefernces extends AppCompatPreferenceActivity {

    private static final String API_URL = BuildConfig.MOVIE_URL+"/upcoming?api_key="+BuildConfig.API_KEY+"&language=en-US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();

    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        SwitchPreference switchReminder;
        SwitchPreference switchToday;

        DailyReminder dailyReminder = new DailyReminder();

        List<ItemsListMovie>listMovies;
        List<ItemsListMovie>samelistMovies;

        @Override
        public void onCreate(final Bundle savedInstanceState){
           super.onCreate(savedInstanceState);
           addPreferencesFromResource(R.xml.pref_notification);


           listMovies = new ArrayList<>();
           samelistMovies = new ArrayList<>();

           switchReminder = (SwitchPreference)findPreference(getString(R.string.key_today_reminder));
           switchReminder.setOnPreferenceChangeListener(this);
           switchToday = (SwitchPreference)findPreference(getString(R.string.key_release_reminder));
           switchToday.setOnPreferenceChangeListener(this);

           Preference preference = findPreference(getString(R.string.key_lang));
           preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
               @Override
               public boolean onPreferenceClick(Preference preference) {
                   startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                   return true;
               }
           });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean compare = (boolean) newValue;

            if (key.equals(getString(R.string.key_today_reminder))){
                if(compare){
                    dailyReminder.setAlarm(getActivity());
                }else {
                    dailyReminder.CancelAlarm(getActivity());
                }
            }else {
                if (compare){
                    setReleaseAlarm();
                }
            }
            return true;
        }

        private void setReleaseAlarm(){
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    API_URL, new Response.Listener<String>() {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                final String now = dateFormat.format(date);
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray array = jsonObject.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++){

                            ItemsListMovie movies = new ItemsListMovie();

                            JSONObject data = array.getJSONObject(i);
                            movies.setId_movie(data.getInt("id"));
                            movies.setTitle_movie(data.getString("title"));
                            movies.setDescription_movie(data.getString("overview"));
                            movies.setRealese_movie(data.getString("release_date"));
                            movies.setImage_movie(data.getString("poster_path"));
                            movies.setRate_movie(data.getString("vote_average"));
                            listMovies.add(movies);

                            if (movies.getRealese_movie().equals(now)){
                                listMovies.add(movies);
                                Log.v("Is there", ""+samelistMovies.size());
                            }
                        }
                        UpcomingReminder.setAlarm(getActivity(), samelistMovies);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    setReleaseAlarm();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

    }
}


