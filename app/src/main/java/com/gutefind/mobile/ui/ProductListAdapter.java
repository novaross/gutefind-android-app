package com.gutefind.mobile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gutefind.mobile.R;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> productList;
    private Fragment parentFragment;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public Button navigateButton;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            navigateButton = (Button) itemView.findViewById(R.id.navigateButton);
        }
    }

    public ProductListAdapter(List<Product> productList, Fragment parentFragment) {
        this.productList = productList;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View productView = layoutInflater.inflate(R.layout.fragment_product_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        TextView productName = holder.productName;
        productName.setText(product.getName());

        Button navigateButton = holder.navigateButton;

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(parentFragment).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
