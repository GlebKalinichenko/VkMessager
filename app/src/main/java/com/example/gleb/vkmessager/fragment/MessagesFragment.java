package com.example.gleb.vkmessager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gleb.vkmessager.R;
import com.example.gleb.vkmessager.adapter.DialogAdapter;
import com.example.gleb.vkmessager.adapter.FriendAdapter;
import com.example.gleb.vkmessager.user.Dialog;
import com.example.gleb.vkmessager.user.Friend;
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
 * Created by gleb on 27.08.15.
 */
public class MessagesFragment extends Fragment {

    public static final String TAG = "Tag";
    public TextView responseTextView;
    public RecyclerView rv;
    public int idSender;
    public int readState;
    public String title;
    public String body;
    public DialogAdapter adapter;
    public List<Dialog> dialogs;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_cult, container, false);
//        ButterKnife.inject(this, view);
//        return view;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        ButterKnife.inject(this, v);
        dialogs = new ArrayList<>();

        VKRequest request = VKApi.messages().get();
        request.executeWithListener(requestListener);
        return v;
    }

    public void setResponse(String response){
        try {
            JSONObject o = new JSONObject(response);
            Log.d(TAG, response);

            JSONObject jObject = o.getJSONObject("response");
            Log.d(TAG, jObject.toString());

            int count = jObject.getInt("count");
            Log.d(TAG, String.valueOf(count));

            JSONArray array = jObject.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemFriends = array.getJSONObject(i);

                idSender = itemFriends.getInt("user_id");
                title = itemFriends.getString("title");
                body = itemFriends.getString("body");

                Dialog dialog = new Dialog(idSender, title, body);
                dialogs.add(dialog);

                Log.d(TAG, idSender + " " + title + " " + body);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new DialogAdapter(dialogs);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

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
