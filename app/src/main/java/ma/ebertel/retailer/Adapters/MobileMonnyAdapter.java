package ma.ebertel.retailer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ebertel.retailer.R;

public class MobileMonnyAdapter extends RecyclerView.Adapter<MobileMonnyAdapter.ProductHolder> {

    List<String> Names;
    Context context;
    RadioGroup.OnCheckedChangeListener checkListner;

    public MobileMonnyAdapter(Context context, List<String> Names, RadioGroup.OnCheckedChangeListener listener){
        this.context = context;
        this.Names = Names;
        this.checkListner = listener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mobilemony_recycler_item,parent,false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        // tags interest,propose
        holder.propos.setOnCheckedChangeListener(checkListner);
        holder.intress.setOnCheckedChangeListener(checkListner);
        holder.title.setText(Names.get(position));
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{

        TextView title;
        RadioGroup intress;
        RadioGroup propos;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mobileMonyName);
            intress = itemView.findViewById(R.id.radioInterests);
            propos = itemView.findViewById(R.id.radioPropose);
        }
    }
}
