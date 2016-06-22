package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import contacts.sqlite.app.jsmtech.myapplication.R;
import model.Contact;

/**
 * Created by Sukriti on 6/16/16.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.RV_ViewHolder> {

    private List<Contact> listOfContacts;
    private Context mContext;
    View itemView;

    public ContactAdapter(Context context, ArrayList<Contact> listOfContacts) {
        this.mContext = context;
        this.listOfContacts = listOfContacts;
    }

    @Override
    public RV_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact_view, parent, false); // link to xml
        return new RV_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RV_ViewHolder holder, final int position) {
        Contact c  = listOfContacts.get(holder.getAdapterPosition());
        holder.mName.setText(c.getName());

        // on ClickListener
        // Intent
        // Intent we will pass id to the new activity
//        c.getId();

    }

    @Override
    public int getItemCount() {
        return listOfContacts.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class RV_ViewHolder extends RecyclerView.ViewHolder {

        protected TextView mName;

        public RV_ViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.name);

        }


    }
}
