package com.gutefind.mobile.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gutefind.mobile.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Logger log = LoggerFactory.getLogger(ProductListAdapter.class);
    private List<Product> productList;
    private Fragment parentFragment;

    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public Button navigateButton;

        public ImageView productImageView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            navigateButton = (Button) itemView.findViewById(R.id.navigateButton);
            productImageView = (ImageView) itemView.findViewById(R.id.productImageView);
        }
    }

    public ProductListAdapter(List<Product> productList, Fragment parentFragment, Context context) {
        this.productList = productList;
        this.parentFragment = parentFragment;
        this.context = context;
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
        holder.getAdapterPosition();
        Product product = productList.get(position);

        ImageView productImageView = holder.productImageView;
        // Drawable res = context.getResources().getDrawable(product.getDrawableId());
        // Drawable res = ContextCompat.getDrawable(context, product.getDrawableId());
        productImageView.setImageResource(product.getDrawableId());

        TextView productName = holder.productName;
        productName.setText(product.getName());

        Button navigateButton = holder.navigateButton;

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is an example of a more elegant way to pass information to the target fragment
                // https://stackoverflow.com/questions/59452243/android-navigation-component-pass-value-arguments-in-fragments
                // use holder.getAdapterPosition() instead of position when not using immediately
                int productId = productList.get(holder.getAdapterPosition()).getId();
                // prepare navigation action with the product id
                com.gutefind.mobile.ui.products.FragmentProductListDirections.ActionStartNavigation actionStartNavigation =
                        FragmentProductListDirections.actionStartNavigation(productId);
                // pass the product id with the action to the navigation fragment
                NavHostFragment.findNavController(parentFragment).navigate(actionStartNavigation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
