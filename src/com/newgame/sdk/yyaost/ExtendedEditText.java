/** 
 * Project Nameï¼šexpandeableListTest 
 * File Nameï¼šExtendedEditText.java 
 * Package Nameï¼šcom.example.expandeablelisttest 
 * Dateï¼?2016å¹?3æœ?3æ—? ä¸‹åˆ9:58:40 
 * Copyright (c) 2016, harris.huang All Rights Reserved. 
 * 
 */
package com.newgame.sdk.yyaost;
import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * @Titleï¼?
 * @Descriptionï¼?
 * @Package com.example.expandeablelisttest
 * @ClassName ExtendedEditText
 * @author harrishuang   
 * @date 2016å¹?3æœ?3æ—? ä¸‹åˆ9:58:40
 * @version 
 */
public class ExtendedEditText extends EditText {

    private ArrayList<TextWatcher> mListeners = null;

    public ExtendedEditText(Context ctx)
    {
        super(ctx);
    }

    public ExtendedEditText(Context ctx, AttributeSet attrs)
    {
        super(ctx, attrs);
    }

    public ExtendedEditText(Context ctx, AttributeSet attrs, int defStyle)
    {
        super(ctx, attrs, defStyle);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher)
    {
        if (mListeners == null)
        {
            mListeners = new ArrayList<TextWatcher>();
        }
        mListeners.add(watcher);

        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher)
    {
        if (mListeners != null)
        {
            int i = mListeners.indexOf(watcher);
            if (i >= 0)
            {
                mListeners.remove(i);
            }
        }

        super.removeTextChangedListener(watcher);
    }

    public void clearTextChangedListeners()
    {
        if(mListeners != null)
        {
            for(TextWatcher watcher : mListeners)
            {
                super.removeTextChangedListener(watcher);
            }

            mListeners.clear();
            mListeners = null;
        }
    }

    
    public int    getTextChangeCounter(){
    	   if (mListeners!=null) {
			return  mListeners.size();
		}
    	   return 0;
    }
     
    

}
