package nl.hsleiden.imtpmd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import nl.hsleiden.imtpmd.models.Modules;

public class ModulesArrayAdapter extends RecyclerView.Adapter<ModulesArrayAdapter.MyViewHolder> {
    private List<Modules> modulesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, cijfer;
        public CheckBox checkbox;
        public ImageView arrow;
        public ImageView listImage;

        //declare alle views
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            cijfer = (TextView) view.findViewById(R.id.cijfer);
            checkbox = (CheckBox) view.findViewById(R.id.checkBoxList);
            arrow = (ImageView) view.findViewById(R.id.arrow);
            listImage = (ImageView) view.findViewById(R.id.listImage);

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Modules module = modulesList.get(position);
        holder.name.setText(module.getCode());
        holder.listImage.setImageResource(R.drawable.grades);

        //laat het goede cijfer zien
        if(module.getCijfer().equals("null")){
            holder.checkbox.setChecked(false);
            holder.cijfer.setText("...");
        } else {
            holder.cijfer.setText(module.getCijfer());
            if (Double.parseDouble(module.getCijfer()) > 5.5) {
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }
        }
        holder.arrow.setImageResource(R.drawable.pijl);

        //onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cijferIntent = new Intent(view.getContext(), GradesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("module", module);
                cijferIntent.putExtras(bundle);
                cijferIntent.putExtra("position", position);
                view.getContext().startActivity(cijferIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modulesList.size();
    }
}
