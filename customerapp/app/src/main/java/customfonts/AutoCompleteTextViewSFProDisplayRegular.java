package
        customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public
class
AutoCompleteTextViewSFProDisplayRegular
extends
androidx.appcompat.widget.AppCompatAutoCompleteTextView
{




public
AutoCompleteTextViewSFProDisplayRegular(Context
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
AutoCompleteTextViewSFProDisplayRegular(Context
context,
AttributeSet
attrs)
{








super(context,
attrs);








init();




}





public
AutoCompleteTextViewSFProDisplayRegular(Context
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
"fonts/montserrat_regular.ttf"));








}




}
}
