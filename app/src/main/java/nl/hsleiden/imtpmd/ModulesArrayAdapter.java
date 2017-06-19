package nl.hsleiden.imtpmd;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import nl.hsleiden.imtpmd.models.Modules;

public class ModulesArrayAdapter extends RecyclerView.Adapter<ModulesArrayAdapter.MyViewHolder> {
    private List<Modules> modulesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView checkmark;
        public ImageView arrow;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            checkmark = (ImageView) view.findViewById(R.id.checkmark);
            arrow = (ImageView) view.findViewById(R.id.arrow);
        }
    }


    public ModulesArrayAdapter(List<Modules> modulesList) {
        this.modulesList = modulesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Modules module = modulesList.get(position);
        holder.name.setText(module.getNaam());
        holder.checkmark.setImageResource(R.drawable.noarrow);
        holder.arrow.setImageResource(R.drawable.pijl);
    }

    @Override
    public int getItemCount() {
        return modulesList.size();
    }
}
