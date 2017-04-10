package id.arieridwan.movikoo.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import id.arieridwan.movikoo.R;

/**
 * Created by arieridwan on 10/04/2017.
 */

public class TextSliderViewCustom extends BaseSliderView {

    public TextSliderViewCustom(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }
}
