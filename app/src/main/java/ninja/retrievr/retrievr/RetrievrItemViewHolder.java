package ninja.retrievr.retrievr;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mattmckenna on 4/11/15.
 */
public class RetrievrItemViewHolder extends RecyclerView.ViewHolder {

    protected TextView titleText;
    protected TextView contentText; // currently unused
    protected CardView card;        // currently unused

    public RetrievrItemViewHolder(View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.item_name);
        //contentText = (TextView) itemView.findViewById(R.id.content);
        //card = (CardView) itemView;
    }
}
