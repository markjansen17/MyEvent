package eventcoordinator2017.myevent.ui.main.tabs;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import eventcoordinator2017.myevent.R;
import eventcoordinator2017.myevent.app.Constants;
import eventcoordinator2017.myevent.databinding.ItemEventMainBinding;
import eventcoordinator2017.myevent.model.data.Event;

/**
 * Created by Mark Jansen Calderon on 1/12/2017.
 */

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private MainPageView mainView;
    private List<Event> eventList;
    private static final int VIEW_TYPE_MORE = 1;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private boolean loading;

    public MainListAdapter(MainPageView mainView) {
        this.mainView = mainView;
        eventList = new ArrayList<>();

    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventMainBinding itemEventBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_event_main, parent, false);
        return new MainListAdapter.EventViewHolder(itemEventBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainListAdapter.EventViewHolder eventViewHolder = (MainListAdapter.EventViewHolder) holder;
        eventViewHolder.itemEventBinding.setEvent(eventList.get(position));
        eventViewHolder.itemEventBinding.setView(mainView);
        Glide.with(eventViewHolder.itemView.getContext())
                .load(Constants.URL_IMAGE+eventList.get(position).getImageDirectory())
                .centerCrop()
                .dontAnimate()
                .into(eventViewHolder.itemEventBinding.eventImage);

    }

    public void setEvents(List<Event> eventList) {
        this.eventList.clear();
        this.eventList.addAll(eventList);
        notifyDataSetChanged();
    }

    public void clear() {
        eventList.clear();
        notifyDataSetChanged();
    }


    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyDataSetChanged();
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {
        private ItemEventMainBinding itemEventBinding;

        public EventViewHolder(ItemEventMainBinding itemEventBinding) {
            super(itemEventBinding.getRoot());
            this.itemEventBinding = itemEventBinding;
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
