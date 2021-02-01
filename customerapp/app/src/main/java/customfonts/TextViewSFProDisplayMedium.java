package
        customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public
class
TextViewSFProDisplayMedium
extends
androidx.appcompat.widget.AppCompatTextView
{




public
TextViewSFProDisplayMedium(Context
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
TextViewSFProDisplayMedium(Context
context,
AttributeSet
attrs)
{








super(context,
attrs);








init();




}





public
TextViewSFProDisplayMedium(Context
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
