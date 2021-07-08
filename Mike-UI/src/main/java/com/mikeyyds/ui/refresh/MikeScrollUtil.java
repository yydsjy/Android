package com.mikeyyds.ui.refresh;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MikeScrollUtil {
    public static View findScrollableChild(@NonNull ViewGroup viewGroup){
        View head = viewGroup.getChildAt(0);
        View child = viewGroup.getChildAt(1);
        if (child instanceof RecyclerView||child instanceof AdapterView){
            return child;
        }
        if (child instanceof ViewGroup){
            View tempChild = ((ViewGroup) child).getChildAt(0);
            if (tempChild instanceof RecyclerView||tempChild instanceof AdapterView){
                child = tempChild;
            }
        }
        return child;
    }

    public static boolean childScrolled(@NonNull View child){
        if (child instanceof AdapterView){
            AdapterView adapterView = (AdapterView) child;
            if (adapterView.getFirstVisiblePosition()!=0
                    ||adapterView.getFirstVisiblePosition()==0
                    &&adapterView.getChildAt(0)!=null
                    &&adapterView.getChildAt(0).getTop()<0){
                return true;
            } else if (child.getScrollY()>0){

                return true;
            }
        }
        if (child instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) child;
            View view = recyclerView.getChildAt(0);
            int firstPosition = recyclerView.getChildAdapterPosition(view);


            return firstPosition != 0 || view.getTop()!=0;

        }
        return false;
    }
}
