package
        customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public
class
Button_SF_Pro_Display_Medium
extends
androidx.appcompat.widget.AppCompatButton
{




public
Button_SF_Pro_Display_Medium(Context
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
Button_SF_Pro_Display_Medium(Context
context,
AttributeSet
attrs)
{








super(context,
attrs);








init();




}





public
Button_SF_Pro_Display_Medium(Context
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
