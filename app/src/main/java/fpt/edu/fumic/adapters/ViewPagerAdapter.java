package fpt.edu.fumic.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import fpt.edu.fumic.fragment.BookFragment;
import fpt.edu.fumic.fragment.HomepageFragment;
import fpt.edu.fumic.fragment.ManagerFragment;
import fpt.edu.fumic.fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new BookFragment();
            case 1: return new ManagerFragment();
            case 2: return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
