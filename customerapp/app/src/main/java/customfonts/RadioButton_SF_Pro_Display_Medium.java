package
        customfonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

@SuppressLint("AppCompatCustomView")
public
class
RadioButton_SF_Pro_Display_Medium
extends
RadioButton
{




public
RadioButton_SF_Pro_Display_Medium(Context
context,
AttributeSet
attrs,
int
defStyle)
{








super(context,
attrs,
defStyle);








init();




}





public
RadioButton_SF_Pro_Display_Medium(Context
context,
AttributeSet
attrs)
{








super(context,
attrs);








init();




}





public
RadioButton_SF_Pro_Display_Medium(Context
context)
{








super(context);








init();




}





private
void
init()
{








if
(!isInEditMode())
{












setTypeface(Typeface.createFromAsset(getContext().getAssets(),
"fonts/montserrat_bold.ttf"));








}




}
}
