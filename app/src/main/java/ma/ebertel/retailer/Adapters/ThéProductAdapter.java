package ma.ebertel.retailer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ebertel.retailer.R;

public class ThéProductAdapter extends RecyclerView.Adapter<ThéProductAdapter.ProductHolder> {

    List<String> productNames;
    Context context;
    CompoundButton.OnCheckedChangeListener checkListner;

    public ThéProductAdapter(Context context, List<String> productNames, CompoundButton.OnCheckedChangeListener listener){
        this.context = context;
        this.productNames = productNames;
        this.checkListner = listener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkbox_recycler_item,parent,false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.checkBox.setText(productNames.get(position));
        holder.checkBox.setTag("thé");
        holder.checkBox.setOnCheckedChangeListener(checkListner);
    }

    @Override
    public int getItemCount() {
        return productNames.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.chkProductType);
        }
    }
}
