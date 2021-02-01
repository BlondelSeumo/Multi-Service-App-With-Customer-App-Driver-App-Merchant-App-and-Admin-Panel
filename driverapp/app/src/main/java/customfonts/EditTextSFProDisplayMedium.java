package
        customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public
class
EditTextSFProDisplayMedium
extends
androidx.appcompat.widget.AppCompatEditText
{




public
EditTextSFProDisplayMedium(Context
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
EditTextSFProDisplayMedium(Context
context,
AttributeSet
attrs)
{








super(context,
attrs);








init();




}





public
EditTextSFProDisplayMedium(Context
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
