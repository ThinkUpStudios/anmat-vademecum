package com.thinkupstudios.anmat.vademecum.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.R;

/**
 * Created by FaQ on 29/03/2015.
 */
public class CollapsibleContent extends LinearLayout implements View.OnClickListener{
    private String header = "Default Header";
    private String content ="Default contentent";
    private boolean isCollapsed = true;
    private int openImageId = android.R.drawable.arrow_up_float;
    private int closeImageId = android.R.drawable.arrow_down_float;
    private ViewHolder viewHolder;

    public CollapsibleContent(Context context) {
        this(context, null);
    }

    public CollapsibleContent(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);

    }

    public CollapsibleContent(Context context, AttributeSet attrs) {
        super(context, attrs);

            this.viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.collapsible_content,this,true);

            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.CollapsibleContent,
                    0, 0);

            try

            {
                for (int i = a.getIndexCount(); i >= 0; i--) {
                    int attr = a.getIndex(i);
                    switch (attr) {
                        case R.styleable.CollapsibleContent_content:
                            this.content = a.getString(attr);
                            break;
                        case R.styleable.CollapsibleContent_header:
                            this.header = a.getString(attr);
                            break;
                        case R.styleable.CollapsibleContent_open_icon:
                            this.openImageId = a.getResourceId(attr, this.openImageId);
                            break;
                        case R.styleable.CollapsibleContent_close_icon:
                            this.closeImageId = a.getResourceId(attr,this.closeImageId);
                            break;
                    }
                }
            }

            finally

            {
                a.recycle();
            }
        viewHolder.titulo = (TextView)findViewById(R.id.txt_header);
        viewHolder.titulo.setText(this.header);
        viewHolder.titulo.setOnClickListener(this);
        viewHolder.content = (TextView)findViewById(R.id.txt_content);
        viewHolder.content.setText(this.content);
        viewHolder.content.setOnClickListener(this);
        viewHolder.statIcon = (ImageView) findViewById(R.id.stat_icon);
        this.viewHolder.statIcon.setImageDrawable(getResources().getDrawable(this.closeImageId));
        RelativeLayout lyHeader = (RelativeLayout)findViewById(R.id.ly_header);
        lyHeader.setOnClickListener(this);
        updateState();

    }

    private void updateState() {
        if(!isCollapsed){
            expand();
            this.viewHolder.statIcon.setImageDrawable(getResources().getDrawable(this.openImageId));
        }else{
            collapse();
            this.viewHolder.statIcon.setImageDrawable(getResources().getDrawable(this.closeImageId));
        }
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
        this.viewHolder.titulo.setText(this.header);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.viewHolder.content.setText(this.content);
    }

    public boolean isCollapsed() {
        return this.viewHolder.content.isShown();
    }


    @Override
    public void onClick(View v) {
        toggleCollapsed();

    }

    private void toggleCollapsed() {
        if(!isCollapsed){
            collapse();

        }else{
            expand();
        }
    }

    public void expand() {
        this.viewHolder.content.setVisibility(View.VISIBLE);
        this.viewHolder.statIcon.setImageDrawable(getResources().getDrawable(this.openImageId));
        this.isCollapsed = false;
    }

    public void collapse() {
        this.viewHolder.content.setVisibility(View.GONE);
        this.viewHolder.statIcon.setImageDrawable(getResources().getDrawable(this.closeImageId));
        this.isCollapsed = true;
    }


    /**
     * Created by FaQ on 29/03/2015.
     */
    private class ViewHolder {
        private TextView titulo;
        private TextView content;
        private ImageView statIcon;

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putBoolean("isCollapsed", this.isCollapsed);

        return bundle;
    }
    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.isCollapsed = bundle.getBoolean("isCollapsed");
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
        updateState();

    }
}
