package fpt.edu.fumic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import fpt.edu.fumic.adapters.BookMainAdapter;
import fpt.edu.fumic.adapters.ViewPagerAdapter;
import fpt.edu.fumic.ui.AddBookActivity;
import fpt.edu.fumic.ui.CategoryListActivity;
import fpt.edu.fumic.ui.SearchActivity;
import fpt.edu.fumic.R;
import fpt.edu.fumic.utils.UserInformation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation_button);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Menu bottomMenu = navigationView.getMenu();

        // Assume you have access to the user's role (isAdmin)
        boolean isAdmin = UserInformation.getInstance().getUser().getRole() == 0; // Change this to your logic to determine the user's role

        // Find the nav_manage item and hide it if the user is not admin
        MenuItem manageItem = bottomMenu.findItem(R.id.nav_manage);
        if (!isAdmin && manageItem != null) {
            manageItem.setVisible(false);
        }
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (isAdmin){
                    switch (position){
                        case 0:
                            navigationView.setSelectedItemId(R.id.nav_home);
                            break;
                        case 1:
                            navigationView.setSelectedItemId(R.id.nav_manage);
                            break;
                        case 2:
                            navigationView.setSelectedItemId(R.id.nav_profile);
                            break;
                    }
                } else {
                    switch (position){
                        case 0:
                            navigationView.setSelectedItemId(R.id.nav_home);
                            break;
                        case 1:
                            navigationView.setSelectedItemId(R.id.nav_profile);
                            break;
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(isAdmin){
                    if(id==R.id.nav_home){
                        viewPager.setCurrentItem(0);
                    } else if (id == R.id.nav_manage) {
                        viewPager.setCurrentItem(1);
                    } else if (id == R.id.nav_profile) {
                        viewPager.setCurrentItem(2);
                    }
                } else {
                    if(id==R.id.nav_home){
                        viewPager.setCurrentItem(0);
                    } else if (id == R.id.nav_profile) {
                        viewPager.setCurrentItem(1);
                    }
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.m_add){
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.m_category){
            Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
            startActivity(intent);
        } else if (id == R.id.m_search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}