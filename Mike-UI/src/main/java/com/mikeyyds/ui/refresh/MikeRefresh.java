package com.mikeyyds.ui.refresh;

public interface MikeRefresh {
    void setDisableRefreshScroll(boolean disableRefreshScroll);
    void refreshFinished();
    void setRefreshListener(MikeRefreshListener mikeRefreshListener);
    void setRefreshOverView(MikeOverView mikeOverView);
    interface MikeRefreshListener{
        void onRefresh();
        boolean enableRefresh();
    }

}
