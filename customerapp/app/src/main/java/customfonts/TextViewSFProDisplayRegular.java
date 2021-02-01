package
        customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TextViewSFProDisplayRegular extends androidx.appcompat.widget.AppCompatTextView {

public TextViewSFProDisplayRegular(Context context, AttributeSet attrs, int defStyle) {

super(context, attrs, defStyle);
init();
}
public TextViewSFProDisplayRegular(Context context, AttributeSet attrs) {
super(context, attrs);
init();
}
public TextViewSFProDisplayRegular(Context context) {
super(context);
init();
}
private void init() {
if (!isInEditMode()) {
setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_regular.ttf"));
}
}
}
