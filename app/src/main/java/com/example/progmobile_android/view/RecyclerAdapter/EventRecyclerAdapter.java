package com.example.progmobile_android.view.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progmobile_android.R;
import com.example.progmobile_android.model.entities.Event;
import com.example.progmobile_android.view.EventDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder> {

    private List<Event> events;
    private Context context;

    public EventRecyclerAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.component_event,
                viewGroup, false);
        return new EventViewHolder(view, events, context);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder viewHolder, int position) {
        Event event = events.get(position);
        String url = event.getImageURL();
        Picasso.get().load(url).into(viewHolder.eventImage);
        viewHolder.eventName.setText(event.getName());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView eventImage;
        private TextView eventName;
        private List<Event> events;
        private Context context;

        EventViewHolder(@NonNull View itemView, List<Event> events, Context context) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_name);
            itemView.setOnClickListener(this);
            this.events = events;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EventDetails.class);
            intent.putExtra("event_id", events.get(getAdapterPosition()).getId());
            context.startActivity(intent);
        }
    }
}
