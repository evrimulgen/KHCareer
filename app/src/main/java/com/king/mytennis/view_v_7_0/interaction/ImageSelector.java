package com.king.mytennis.view_v_7_0.interaction;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.king.mytennis.http.bean.ImageUrlBean;
import com.king.mytennis.view.CustomDialog;
import com.king.mytennis.view.R;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/12 0012.
 * 抽象成基类，local浏览和http浏览，区别在于：
 * 1. 初始化的数据不一样
 * 2. 选择的adapter不同
 * 3. 点击确认后事件不同
 */
public abstract class ImageSelector extends CustomDialog {

    protected ImageUrlBean imageUrlBean;
    protected RecyclerView recyclerView;
    protected ImageSelectorAdapter mAdapter;
    protected String imageFlag;

    public ImageSelector(Context context, OnCustomDialogActionListener actionListener) {
        super(context, actionListener);

        HashMap<String, Object> map = new HashMap<>();
        actionListener.onLoadData(map);
        imageUrlBean = (ImageUrlBean) map.get("data");
        setTitle(imageUrlBean.getKey());
        imageFlag = (String) map.get("flag");
        initData(map);
        initAdapter();
        mAdapter.setImageFlag(imageFlag);
    }

    protected abstract void initData(HashMap<String, Object> data);

    protected abstract void initAdapter();

    @Override
    protected View getCustomView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_image_selector, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.dlg_bg_selector_recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, getContext().getResources().getDimensionPixelSize(R.dimen.dlg_loadfrom_list_height)
        );
        view.setLayoutParams(params);

        return view;
    }

    @Override
    protected View getCustomToolbar() {
        return null;
    }

    @Override
    public void onClick(View view) {
        if (view == saveButton || view == deleteButton) {
            onConfirm();
        }
        super.onClick(view);
    }

    protected abstract void onConfirm();
}
