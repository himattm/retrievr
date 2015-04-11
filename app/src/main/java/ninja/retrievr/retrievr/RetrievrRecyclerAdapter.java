package ninja.retrievr.retrievr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mattmckenna on 4/11/15.
 */
public class RetrievrRecyclerAdapter extends RecyclerView.Adapter<RetrievrItemViewHolder> {

    private List<RetrievrItem> items;

    public RetrievrRecyclerAdapter(List<RetrievrItem> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    @Override
    public RetrievrItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view, parent, false);

        return new RetrievrItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RetrievrItemViewHolder holder, int position) {
        RetrievrItem item = items.get(position);
        holder.titleText.setText(item.getName());
        //holder.contentText.setText(...)
        //holder.card.setCardBackgroundColor(...)
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
