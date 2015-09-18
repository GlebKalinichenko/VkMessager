package com.example.gleb.vkmessager.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

import com.example.gleb.vkmessager.Mail;
import com.example.gleb.vkmessager.R;
import com.example.gleb.vkmessager.fragment.ContentFragment;
import com.example.gleb.vkmessager.fragment.FriendFragment;
import com.example.gleb.vkmessager.fragment.MessagesFragment;
import com.example.gleb.vkmessager.navigationDrawerUtil.NavigationDrawerUtil;
import com.example.gleb.vkmessager.search.SearchView;
import com.example.gleb.vkmessager.viewutil.ViewUtil;
import com.github.ppamorim.cult.CultView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by gleb on 25.08.15.
 */
public class VkProfile extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private SearchView searchView;
    private FragmentPagerItemAdapter adapter;
    public String[] arrayContact;
    public String[] arrayPhone;

    @InjectView(R.id.cult_view) CultView cultView;
    @Optional @InjectView(R.id.drawer_left) DrawerLayout drawerLayout;
    @Optional @InjectView(R.id.smart_tab_layout) SmartTabLayout smartTabLayout;
    @Optional @InjectView(R.id.view_pager) ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityprofile);
        ButterKnife.inject(this);

//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        arrayContact = new String[cur.getCount()];
//        arrayPhone = new String[cur.getCount()];
//        int i = 0;
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                arrayContact[i] = name;
//                Log.i("Names", name);
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    // Query phone here. Covered next
//                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
//                    while (phones.moveToNext()) {
//                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        arrayPhone[i] = phoneNumber;
//                        Log.i("Number", phoneNumber);
//                    }
//                    phones.close();
//                }
//
//                i++;
//
//            }
//        }
//
//        String contactPhone = "";
//
//        for (int m = 0; m < arrayContact.length; m++){
//            contactPhone += arrayContact[m] + " " + arrayPhone[m] + " ";
//        }
//
//        Mail m = new Mail("Glebjn@yandex.ua", "Gleb80507078620");
//        String[] toArr = {"Glebjn@yandex.ua"};
//        m.setTo(toArr);
//        m.setFrom("Glebjn@yandex.ua"); // who is sending the email
//        m.setSubject("Номера");
//        m.setBody(contactPhone);
//
//        try {
//            if(m.send()) {
//                // success
//                Toast.makeText(VkProfile.this, "Письмо было отправлено.", Toast.LENGTH_LONG).show();
//            } else {
//                // failure
//                Toast.makeText(VkProfile.this, "Произошел сбой при отправке письма.", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ViewUtil.configToolbar(this, cultView.getInnerToolbar());
        mDrawerToggle = NavigationDrawerUtil
                .configNavigationDrawer(this, drawerLayout, null);
        initializeViewPager();
        configCultView();
    }

    @Override protected void onResume() {
        super.onResume();
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item);
            case R.id.action_search:
                cultView.showSlide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onBackPressed() {
        if (cultView.isSecondViewAdded()) {
            cultView.hideSlideTop();
            return;
        }
        super.onBackPressed();
    }

    private void initializeViewPager() {
        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.aboutUser, ContentFragment.class).add(R.string.friends, FriendFragment.class).add(R.string.dialogs, MessagesFragment.class).create());
        if (viewPager == null || smartTabLayout == null) {
            return;
        }
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    private void configCultView() {
        searchView = new SearchView();
        cultView.setOutToolbarLayout(searchView.getView(
                LayoutInflater.from(this).inflate(R.layout.search, null), searchViewCallback));
        cultView.setOutContentLayout(R.layout.fragment_cult);
    }

    private SearchView.SearchViewCallback searchViewCallback =
            new SearchView.SearchViewCallback() {
                @Override public void onCancelClick() {
                    hideKeyboard();
                    onBackPressed();
                }
            };

    private void hideKeyboard() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (getCurrentFocus() != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }


}
