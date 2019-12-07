package ma.ebertel.retailer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ma.ebertel.retailer.R;

public class VisPateProductAdapter extends RecyclerView.Adapter<VisPateProductAdapter.ProductHolder> {

    List<String> productNames;
    Context context;

    public VisPateProductAdapter(Context context, List<String> productNames){
        this.context = context;
        this.productNames = productNames;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.textbox_recycler_item,parent,false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.textView.setText(productNames.get(position));
    }

    @Override
    public int getItemCount() {
        return productNames.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtProductName);
        }
    }
}
