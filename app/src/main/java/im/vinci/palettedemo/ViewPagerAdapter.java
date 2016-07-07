package im.vinci.palettedemo;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mengxiaofeng on 16/7/7.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"主页", "分享", "收藏", "关注", "微博"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PaletteFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];

    }
}
