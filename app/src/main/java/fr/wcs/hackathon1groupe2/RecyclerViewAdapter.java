package fr.wcs.hackathon1groupe2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Gift> giftList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;
        ImageView menu, viewImageGift;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.viewTitle);
            description = view.findViewById(R.id.viewDescription);
            menu = view.findViewById(R.id.menu);
            viewImageGift = view.findViewById(R.id.viewImageGift);
        }
    }

    RecyclerViewAdapter(Context mContext,List<Gift> moviesList) {
        this.mContext = mContext;
        this.giftList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gift_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Gift gift = giftList.get(position);
        holder.title.setText(gift.getTitle());
        holder.description.setText(gift.getDescription());

        // loading album cover using Glide library
        Glide.with(mContext).load(gift.getPicture()).into(holder.viewImageGift);

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.menu);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_gift, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        MyMenuItemClickListener() {}

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_list:
                    Toast.makeText(mContext,"Ajouté à ma liste", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_remove_list:
                    Toast.makeText(mContext, "Retiré de ma liste", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_give:

                    Toast.makeText(mContext, "Cadeau offert", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {return giftList.size();}
}
