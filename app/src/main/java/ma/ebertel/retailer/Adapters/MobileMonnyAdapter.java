package ma.ebertel.retailer.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.jar.Attributes;

import ma.ebertel.retailer.R;

public class MobileMonnyAdapter extends RecyclerView.Adapter<MobileMonnyAdapter.ProductHolder> {

    List<String> Names;
    Context context;
    String[] data = null;
    int role;
    RadioGroup.OnCheckedChangeListener checkListner;

    public MobileMonnyAdapter(Context context, List<String> Names, RadioGroup.OnCheckedChangeListener listener,@Nullable String[] data,int role){
        this.context = context;
        this.Names = Names;
        this.role = role;
        this.checkListner = listener;
        if(data !=null){
            this.data = data;
        }
        if(data != null){
            if(data.length>0){
                String[] test = data[0].split(",");
                if(test.length != 3){
                    this.data = new String[]{};
                }
            }
        }
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mobilemony_recycler_item,parent,false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        if(data != null){
            String[] d = data[position].split(",");
            if(d.length>0){
                holder.title.setText(d[0]);
                holder.propos.setVisibility(View.GONE);
                holder.intress.setVisibility(View.GONE);
                holder.ProposTitle.setVisibility(View.GONE);
                holder.InteressTitle.setVisibility(View.GONE);
            }
        }else {
            // tags interest,propose
            holder.propos.setOnCheckedChangeListener(checkListner);
            holder.intress.setOnCheckedChangeListener(checkListner);
            holder.title.setText(Names.get(position));

            ((TableRow)holder.intress.getParent()).setTag(Names.get(position));
            ((TableRow)holder.propos.getParent()).setTag(Names.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(role == 2){
            return data.length;
        }else {
            return Names.size();
        }
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{

        TextView title,ProposTitle,InteressTitle;
        RadioGroup intress;
        RadioGroup propos;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mobileMonyName);
            ProposTitle = itemView.findViewById(R.id.ProposTitle);
            InteressTitle = itemView.findViewById(R.id.InteressTitle);
            intress = itemView.findViewById(R.id.radioInterests);
            propos = itemView.findViewById(R.id.radioPropose);
        }
    }
}
