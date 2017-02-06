package eventcoordinator2017.myevent.ui.events.add;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eventcoordinator2017.myevent.R;
import eventcoordinator2017.myevent.databinding.ItemPackageBinding;
import eventcoordinator2017.myevent.model.data.Package;

/**
 * Created by Sen on 1/26/2017.
 */

public class PackagesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private EventAddView eventsView;
    private List<Package> list;
    private boolean loading;

    public PackagesListAdapter(EventAddView eventsView) {
        this.eventsView = eventsView;
        list = new ArrayList<>();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPackageBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_package, parent, false);
        return new PackageViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PackageViewHolder packageViewHolder = (PackageViewHolder) holder;
        packageViewHolder.itemPackageBinding.setAPackage(list.get(position));
        packageViewHolder.itemPackageBinding.setView(eventsView);

    }

    public class PackageViewHolder extends RecyclerView.ViewHolder {
        private ItemPackageBinding itemPackageBinding;

        public PackageViewHolder(ItemPackageBinding itemPackageBinding) {
            super(itemPackageBinding.getRoot());
            this.itemPackageBinding = itemPackageBinding;
        }
    }


    public void setPackages(List<Package> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }


    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
