package com.thinkupstudios.anmat.vademecum.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

/**
 * Componente de tipo Autocomplete con funcionalidad de limpiar texto.
 *
 * Created by dcamarro on 11/03/2015.
 */
public class ClearableAutoCompliteEditText extends AutoCompleteTextView {

    private static final int MAX_LENGTH = 100;

    public ClearableAutoCompliteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.setBackgroundResource(android.R.drawable.edit_text);
        }


        String value = "";
        final String viewMode = "editing";
        final String viewSide = "right";
        final Drawable x = getResources().getDrawable(android.R.drawable.ic_notification_clear_all); //Imagen de la "X" para borrar
        final Drawable searchIcon = getResources().getDrawable(android.R.drawable.ic_search_category_default); //Icono de lupa en caso de querer agregarlo a la izquierda.


        // The height will be set the same with [X] icon
        //setHeight(x.getBounds().height());

        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        Drawable x2 = viewMode.equals("never") ? null : viewMode
                .equals("always") ? x : viewMode.equals("editing") ? (value
                .equals("") ? searchIcon : x)
                : viewMode.equals("unlessEditing") ? (value.equals("") ? x
                : null) : null;


        searchIcon.setBounds(0, 0, x.getIntrinsicWidth(),x.getIntrinsicHeight());


        setCompoundDrawables(null, null, viewSide.equals("right") ? x2 : null, null);

        //si quisiéramos la lupa del lado izquierdo tendriamos que hacer lo siguiente.
        //setCompoundDrawables(searchIcon, null, viewSide.equals("right") ? x2 : null, null);


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawables()[viewSide.equals("left") ? 0 : 2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                // x pressed
                if ((viewSide.equals("left") && event.getX() < getPaddingLeft()
                        + x.getIntrinsicWidth())
                        || (viewSide.equals("right") && event.getX() > getWidth()
                        - getPaddingRight() - x.getIntrinsicWidth())) {
                    Drawable x3 = viewMode.equals("never") ? null : viewMode
                            .equals("always") ? x
                            : viewMode.equals("editing") ? searchIcon : viewMode
                            .equals("unlessEditing") ? x : null;
                    setText("");

                    /*
                    Si queremos tener el icono de lupa a la izquierda. */
                    setCompoundDrawables(null, null, viewSide.equals("right") ? x3 : null, null);

                    // setCompoundDrawables(searchIcon, null, viewSide.equals("right") ? x3 : null, null);
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                Drawable x4 = viewMode.equals("never") ? null : viewMode
                        .equals("always") ? x
                        : viewMode.equals("editing") ? (getText().toString()
                        .equals("") ? searchIcon : x) : viewMode
                        .equals("unlessEditing") ? (getText()
                        .toString().equals("") ? x : searchIcon) : null;

                setCompoundDrawables(null, null, viewSide.equals("right") ? x4 : null, null);
                // setCompoundDrawables(searchIcon, null, viewSide.equals("right") ? x4 : null, null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > MAX_LENGTH) {
                    setText(s.subSequence(0, MAX_LENGTH));
                    setSelection(MAX_LENGTH);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
    }

}
