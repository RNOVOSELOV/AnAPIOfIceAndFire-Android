package xyz.rnovoselov.enterprise.aniceandfire.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.05.17.
 */

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseListViewHolder> {

    public static final String TAG = Constants.TAG_PREFIX + HouseListAdapter.class.getSimpleName();

    private List<HouseDataDto> houses;
    private Context context;
    private HouseListViewHolder.HouseClickListener houseClickListener;

    public HouseListAdapter(Context context, List<HouseDataDto> houses, HouseListViewHolder.HouseClickListener houseClickListener) {
        this.houses = houses;
        this.houseClickListener = houseClickListener;
        this.context = context;
    }

    @Override
    public HouseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_house_rv, parent, false);
        return new HouseListViewHolder(view, houseClickListener);
    }

    @Override
    public void onBindViewHolder(HouseListViewHolder holder, int position) {
        final HouseDataDto house = houses.get(position);
        holder.houseTitleTv.setText(house.getName());
        holder.houseAdditionalTv.setText(house.getCoatOfArms());
    }

    @Override
    public int getItemCount() {
        return houses.size();

    }

    public void setHousesList(List<HouseDataDto> houses) {
        this.houses = houses;
        notifyDataSetChanged();
    }

    public static class HouseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.house_title)
        TextView houseTitleTv;
        @BindView(R.id.house_additional)
        TextView houseAdditionalTv;
        @BindView(R.id.house_show_characters_btn)
        Button houseShowCharactersBtn;
        @BindView(R.id.house_show_more_btn)
        Button houseShowMoreBtn;

        private HouseClickListener listener;

        public HouseListViewHolder(View itemView, HouseClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = clickListener;

            houseShowMoreBtn.setOnClickListener(this);
            houseShowCharactersBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                switch (v.getId()) {
                    case R.id.house_show_characters_btn:
                        listener.onShowCharactersClick(getAdapterPosition());
                        break;
                    case R.id.house_show_more_btn:
                        listener.onShowMoreClick(getAdapterPosition());
                        break;
                }
            }
        }

        public interface HouseClickListener {
            void onShowMoreClick(int position);

            void onShowCharactersClick(int position);
        }
    }
}
