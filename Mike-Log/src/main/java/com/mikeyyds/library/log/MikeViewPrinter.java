package com.mikeyyds.library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikeyyds.library.R;


import java.util.ArrayList;
import java.util.List;

public class MikeViewPrinter implements MikeLogPrinter {
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private MikeViewPrinterProvider viewProvider;

    public MikeViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new MikeViewPrinterProvider(rootView, recyclerView);
    }

    @NonNull
    public MikeViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull MikeLogConfig config, int level, String tag, @NonNull String printString) {
        adapter.addItem(new MikeLogMo(System.currentTimeMillis(), level, tag, printString));
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);

    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private LayoutInflater inflater;
        private List<MikeLogMo> logs = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        void addItem(MikeLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case MikeLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case MikeLogType.D:
                    highlight = 0xffffffff;
                    break;
                case MikeLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case MikeLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case MikeLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.mikelog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            MikeLogMo logItem = logs.get(position);
            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);

            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tagView;
        TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
