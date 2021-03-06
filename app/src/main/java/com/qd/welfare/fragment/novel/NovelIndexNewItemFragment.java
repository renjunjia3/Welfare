package com.qd.welfare.fragment.novel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qd.welfare.App;
import com.qd.welfare.R;
import com.qd.welfare.base.BaseFragment;
import com.qd.welfare.config.PageConfig;
import com.qd.welfare.entity.NovelCateGoryInfo;
import com.qd.welfare.event.StartBrotherEvent;
import com.qd.welfare.utils.DialogUtil;
import com.qd.welfare.widgets.RatioImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import wiki.scene.loadmore.utils.PtrLocalDisplay;

/**
 * 小说分类item
 * Created by scene on 2017/9/22.
 */

public class NovelIndexNewItemFragment extends BaseFragment {
    @BindView(R.id.backImage)
    ImageView backImage;
    @BindView(R.id.image)
    RatioImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.click_number)
    TextView clickNumber;
    @BindView(R.id.update_number)
    TextView updateNumber;
    Unbinder unbinder;
    @BindView(R.id.itemView)
    RelativeLayout itemView;
    private NovelCateGoryInfo info;

    public static NovelIndexNewItemFragment newInstance(NovelCateGoryInfo info) {
        Bundle args = new Bundle();
        args.putSerializable("ARG", info);
        NovelIndexNewItemFragment fragment = new NovelIndexNewItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            info = (NovelCateGoryInfo) getArguments().getSerializable("ARG");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categroy_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        clickNumber.setText(info.getView_times() + "人点击");
        name.setText(info.getTitle());
        updateNumber.setText("更新至" + info.getTotal() + "期");
        try {
            Glide.with(getContext()).load(App.commonInfo.getFile_domain() + info.getIcon()).bitmapTransform(new RoundedCornersTransformation(getContext(), PtrLocalDisplay.dp2px(10), 0)).into(image);
            Glide.with(getContext()).load(App.commonInfo.getFile_domain() + info.getIcon()).bitmapTransform(new BlurTransformation(getContext())).into(backImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (App.userInfo != null && App.userInfo.getRole() > 1) {
                    EventBus.getDefault().post(new StartBrotherEvent(NovelListFragment.newInstance(info.getId(), info.getTitle())));
                } else {
                    DialogUtil.showOpenViewDialog(getContext(), PageConfig.NOVEL_CATEGORY, 0);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
