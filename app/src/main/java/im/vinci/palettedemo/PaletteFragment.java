package im.vinci.palettedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * Created by mengxiaofeng on 16/7/7.
 * 生命周期
 l  onAttach：绑定到activity
 l  onCreate：创建fragment
 l  onCreateView： 创建fragment的布局
 l  onActivityCreated： activity创建完成后
 l  onStart： 可见, 不可交互
 l  onResume： 可见, 可交互
 l  onPause： 部分可见, 不可交互
 l  onStop：不可见
 l  onDestroyView： 销毁fragment的view对象
 l  onDestroy： fragment销毁了
 l  onDetach： 从activity解绑了
 */
public class PaletteFragment extends Fragment {

    private static final int[] drawables = {R.mipmap.one, R.mipmap.two, R.mipmap.four, R.mipmap
            .three, R.mipmap.five};
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
    }

    public static PaletteFragment newInstance(int position) {
        PaletteFragment m = new PaletteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        m.setArguments(bundle);
        return m;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        //图片当做fragment
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(params);
        frameLayout.setBackgroundResource(drawables[position]);




        return frameLayout;
    }

    /**
     * 提供当前ViewPagerItem对应的图片资源
     * @param selectViewPagerItem
     * @return
     */
    public static int getBackgroundBitmapPosition(int selectViewPagerItem){

        return drawables[selectViewPagerItem];
    }


}
