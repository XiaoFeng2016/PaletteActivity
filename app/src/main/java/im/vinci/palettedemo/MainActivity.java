package im.vinci.palettedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @Bind(R.id.main_vp_container)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        //Tab和ViewPager联动
        toolbarTab.setupWithViewPager(viewPager);

        updateColor(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
     */
    private void updateColor(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), PaletteFragment.getBackgroundBitmapPosition(position));

        Palette.Builder builder = new Palette.Builder(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                /*各种色调的颜色*/
//                Palette.Swatch swatch2 = palette.getMutedSwatch();
                Palette.Swatch swatch1 = palette.getVibrantSwatch();//其他会发生空指针
//                Palette.Swatch swatch4 = palette.getDarkMutedSwatch();
//                Palette.Swatch swatch5 = palette.getLightMutedSwatch();
//                Palette.Swatch swatch3 = palette.getLightVibrantSwatch();

                toolbarTab.setBackgroundColor(swatch1.getRgb());
                toolbarTab.setSelectedTabIndicatorColor(colorBurn(swatch1.getRgb()));
                toolbar.setBackgroundColor(swatch1.getRgb());

                if (Build.VERSION.SDK_INT >= 21) {//如果5。0以上，
                    Window window = getWindow();
                    //沉浸式状态栏
                    window.setStatusBarColor(colorBurn(swatch1.getRgb()));
                    //底部选项栏（有些机型没有）
                    window.setNavigationBarColor(colorBurn(swatch1.getRgb()));
                }
            }
        });

    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}

