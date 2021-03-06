package com.qd.welfare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haozhang.lib.SlantedTextView;
import com.joooonho.SelectableRoundedImageView;
import com.qd.welfare.App;
import com.qd.welfare.R;
import com.qd.welfare.entity.VideoInfo;
import com.qd.welfare.entity.VideoResultInfo;
import com.qd.welfare.fragment.video.VideoDetailActivity;
import com.qd.welfare.itemDecoration.GridSpacingItemDecoration;
import com.qd.welfare.widgets.drawableratingbar.DrawableRatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import wiki.scene.loadmore.utils.PtrLocalDisplay;

/**
 * 视频
 * Created by scene on 2017/9/1.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_IMAGE_1 = 0;
    private static final int TYPE_IMAGE_2 = 1;

    private Context context;
    private List<VideoResultInfo.VideoIndexInfo> list;
    private LayoutInflater inflater;

    private OnVideoItemClickListener onVideoItemClickListener;
    private GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(2, PtrLocalDisplay.dp2px(5), false);

    public VideoAdapter(Context context, List<VideoResultInfo.VideoIndexInfo> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnVideoItemClickListener(OnVideoItemClickListener onVideoItemClickListener) {
        this.onVideoItemClickListener = onVideoItemClickListener;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE_1) {
            return new ViewHolder1(inflater.inflate(R.layout.fragment_video_item1, parent, false));
        } else {
            return new ViewHolder2(inflater.inflate(R.layout.fragment_video_item2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VideoResultInfo.VideoIndexInfo info = list.get(position);

        if (holder instanceof ViewHolder1) {
            ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.title.setText(info.getTitle());
            if (info.getVideo().size() > 0) {
                holder1.videoName.setText(info.getVideo().get(0).getTitle());
                holder1.videoPlayCount.setText("播放：" + info.getVideo().get(0).getPlay_times());
                holder1.ratingBar.setMax(5);
                holder1.ratingBar.setRating((int) info.getVideo().get(0).getStar());
                holder1.tagText.setText(info.getVideo().get(0).getType() == 1 ? "免费试看" : "会员尊享");
                if (info.getVideo().get(0).getThumb().endsWith("gif")) {
                    Glide.with(context).load(App.commonInfo.getFile_domain() + info.getVideo().get(0).getThumb())
                            .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder1.image);
                } else {
                    Glide.with(context).load(App.commonInfo.getFile_domain() + info.getVideo().get(0).getThumb())
                            .centerCrop().into(holder1.image);
                }
                holder1.tagLayout.setTags(info.getVideo().get(0).getTags());
                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onVideoItemClickListener != null) {
                            onVideoItemClickListener.onVideoItemClick(info.getVideo().get(0));
                        }
                    }
                });
                if (info.getVideo().get(0).getType() > 1) {
                    holder1.tagText.setSlantedBackgroundColor(Color.parseColor("#d462ff"));
                } else {
                    holder1.tagText.setSlantedBackgroundColor(Color.parseColor("#E60012"));
                }
            }
        } else {
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.title.setText(info.getTitle());
            VideoItemAdapter adapter = new VideoItemAdapter(context, info.getVideo());
            try {
                holder2.itemGridView.removeItemDecoration(itemDecoration);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder2.itemGridView.setFocusableInTouchMode(false); //设置不需要焦点
            holder2.itemGridView.requestFocus(); //设置焦点不需要
            holder2.itemGridView.setLayoutManager(new GridLayoutManager(context, 2));
            holder2.itemGridView.addItemDecoration(itemDecoration);
            holder2.itemGridView.setAdapter(adapter);
            adapter.setOnChildItemClickListener(new VideoItemAdapter.OnChildItemClickListener() {
                @Override
                public void onChildItemClick(int position) {
                    if (onVideoItemClickListener != null) {
                        onVideoItemClickListener.onVideoItemClick(info.getVideo().get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getShow_type() == 1 ? TYPE_IMAGE_1 : TYPE_IMAGE_2;
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.videoName)
        TextView videoName;
        @BindView(R.id.videoPlayCount)
        TextView videoPlayCount;
        @BindView(R.id.ratingBar)
        DrawableRatingBar ratingBar;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tag_layout)
        TagContainerLayout tagLayout;
        @BindView(R.id.tag_text)
        SlantedTextView tagText;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.itemGridView)
        RecyclerView itemGridView;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnVideoItemClickListener {
        void onVideoItemClick(VideoInfo info);
    }
}
