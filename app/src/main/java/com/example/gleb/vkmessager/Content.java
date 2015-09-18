package com.example.gleb.vkmessager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gleb.vkmessager.user.User;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gleb on 24.08.15.
 */
public class Content extends ActionBarActivity {
    public static final String TAG = "Tag";
    public static final String REQUEST = "Request";
    public static final String RESPONSE = "Response";
    public static final String FRAGMENT_TAG = "response_view";
    public VKRequest vkRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ResponseFragment(), FRAGMENT_TAG).commit();
        }

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(Content.REQUEST)){
            long requestId = getIntent().getExtras().getLong(Content.REQUEST);
            VKRequest request = null;
            request = VKRequest.getRegisteredRequest(requestId);

            if (request == null)
                return;
            //для сохранения id request при переходе
            vkRequest = request;
            request.executeWithListener(requestListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vkRequest.cancel();
    }

    /*
     * Сохранение ответа в RESPONSE и id request в REQUEST при переходе
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(Content.RESPONSE, getFragment().responseTextView.getText());
        if (vkRequest != null){
            outState.putLong(Content.REQUEST, vkRequest.registerObject());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CharSequence response = savedInstanceState.getCharSequence(Content.RESPONSE);
        if (response != null){
            getFragment().responseTextView.setText(response);
        }

        long requestId = savedInstanceState.getLong(Content.REQUEST);
        VKRequest request = VKRequest.getRegisteredRequest(requestId);
        if (request != null){
            request.unregisterObject();
            request.setRequestListener(requestListener);
        }
    }

    private ResponseFragment getFragment(){
        return (ResponseFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

    }

    public void setResponse(String response){
        ResponseFragment responseFragment = getFragment();
        if (responseFragment != null && responseFragment.responseTextView != null){
            try {
                JSONObject jsonObject= new JSONObject(response);

                JSONArray array = jsonObject.getJSONArray("response");
                for (int i = 0; i < array.length(); i++) {
                    //parse of array
                    JSONObject jObject = array.getJSONObject(i);
                    String firstName = jObject.getString("first_name");
                    String lastName = jObject.getString("last_name");
                    int id = jObject.getInt("id");
                    int sex = jObject.getInt("sex");
                    String bDate = jObject.getString("bdate");
                    JSONObject jCity = jObject.getJSONObject("city");
//                    JSONObject jCountry = jObject.getJSONObject("country");
                    String city = jCity.getString("title");
//                    String country = jCountry.getString("title");
//                    String university = jObject.getString("university_name");
//                    String faculty = jObject.getString("faculty_name");

//                    User user = new User(id, firstName, lastName, sex, bDate, city, country, university, faculty);

//                    Log.d(TAG, String.valueOf(user.getId()));
//                    Log.d(TAG, user.getFirstName());
//                    Log.d(TAG, user.getLastName());
//                    Log.d(TAG, String.valueOf(user.getSex()));
//                    Log.d(TAG, user.getbDate());
//                    Log.d(TAG, user.getCity());
//                    Log.d(TAG, user.getCountry());
//                    Log.d(TAG, user.getUniversity());
//                    Log.d(TAG, user.getFaculty());

                    Log.d(TAG, String.valueOf(id));
                    Log.d(TAG, firstName);
                    Log.d(TAG, lastName);
                    Log.d(TAG, String.valueOf(sex));
                    Log.d(TAG, bDate);
                    Log.d(TAG, city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            responseFragment.responseTextView.setText(response);
        }

    }

    VKRequest.VKRequestListener requestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.d(TAG, "onComplete response");
            setResponse(response.json.toString());
        }

        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            super.attemptFailed(request, attemptNumber, totalAttempts);
        }

        @Override
        public void onError(VKError error) {
            setResponse(error.toString());
        }

        @Override
        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
            super.onProgress(progressType, bytesLoaded, bytesTotal);
        }
    };

    public static class ResponseFragment extends Fragment {
        public TextView responseTextView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_response, container, false);
            responseTextView = (TextView) v.findViewById(R.id.response);
            return v;
        }
    }
}
