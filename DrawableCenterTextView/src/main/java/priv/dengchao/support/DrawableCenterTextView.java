package priv.dengchao.support;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Feel easy to place drawables center with text.<br/>
 * The attribute <code>paddingLeft</code> , <code>paddingRight</code> , <code>paddingTop</code> , <code>paddingBottom</code>
 * in xml may not works as expected,<code>padding</code> and <code>drawablePadding</code> still works<br/>
 * Config <code>gravity</code> in xml when necessary.<br/>
 * Created by DengChao on 2017/2/18.<br/>
 */

public class DrawableCenterTextView extends TextView {

    public DrawableCenterTextView(Context context) {
        super(context);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //All these logic should happen before onDraw()
        Drawable[] drawables = getCompoundDrawables();//drawables always not null;
        Drawable drawableLeft = drawables[0],
                drawableTop = drawables[1],
                drawableRight = drawables[2],
                drawableBottom = drawables[3];

        String text = getText().toString();
        float textWidth = getPaint().measureText(text, 0, text.length());
        double textHeight = getLineHeight() * getLineCount();

        int totalDrawablePaddingH = 0;//the total horizontal padding of drawableLeft and drawableRight
        int totalDrawablePaddingV = 0;//the total vertical padding of drawableTop and drawableBottom
        int totalDrawableWidth = 0;//the total width of drawableLeft and drawableRight
        int totalDrawableHeight = 0;//the total height of drawableTop and drawableBottom
        float totalWidth;//the total width of drawableLeft , drawableRight and text
        float totalHeight;//the total height of drawableTop , drawableBottom and text
        int paddingH;//the horizontal padding,used both left and right
        int paddingV;//the vertical padding,used both top and bottom

        // measure width
        if (drawableLeft != null) {
            totalDrawableWidth += drawableLeft.getIntrinsicWidth();
            totalDrawablePaddingH += getCompoundDrawablePadding();//drawablePadding
        }
        if (drawableRight != null) {
            totalDrawableWidth += drawableRight.getIntrinsicWidth();
            totalDrawablePaddingH += getCompoundDrawablePadding();
        }
        totalWidth = textWidth + totalDrawableWidth + totalDrawablePaddingH;
        paddingH = (int) (getWidth() - totalWidth) / 2;

        // measure height
        if (drawableTop != null) {
            totalDrawableHeight += drawableTop.getIntrinsicHeight();
            totalDrawablePaddingV += getCompoundDrawablePadding();
        }
        if (drawableBottom != null) {
            totalDrawableHeight += drawableBottom.getIntrinsicHeight();
            totalDrawablePaddingV += getCompoundDrawablePadding();
        }
        totalHeight = (float) (textHeight + totalDrawableHeight + totalDrawablePaddingV);
        paddingV = (int) (getHeight() - totalHeight) / 2;

        // reset padding.
        setPadding(paddingH, paddingV, paddingH, paddingV);//this method calls invalidate() inside;
    }
}
