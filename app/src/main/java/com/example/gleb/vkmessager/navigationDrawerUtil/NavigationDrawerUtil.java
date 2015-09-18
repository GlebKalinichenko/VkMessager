package com.example.gleb.vkmessager.navigationDrawerUtil;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import com.example.gleb.vkmessager.R;

/**
 * Created by gleb on 25.08.15.
 */
public class NavigationDrawerUtil {
    public static ActionBarDrawerToggle configNavigationDrawer(
            AppCompatActivity appCompatActivity,
            DrawerLayout drawerLayout,
            final DrawerCallback drawerCallback) {

        if (appCompatActivity == null || drawerLayout == null) {
            return null;
        }

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.LEFT);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                appCompatActivity, drawerLayout, R.string.app_name, R.string.app_name) {

            @Override public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (drawerCallback != null) {
                    drawerCallback.onDrawerOpened();
                }
            }

            @Override public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (drawerCallback != null) {
                    drawerCallback.onDrawerClosed();
                }
            }

            @Override public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (drawerCallback != null) {
                    drawerCallback.onDrawerSlide(drawerView, slideOffset);
                }
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        return actionBarDrawerToggle;
    }

    public interface DrawerCallback {
        void onDrawerOpened();
        void onDrawerClosed();
        void onDrawerSlide(View drawerView, float slideOffset);
    }

}
