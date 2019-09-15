package com.example.findinshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findinshop.by.parsing.model.entity.results.ResultInfo;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.util.ArrayList;

public class ResultList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        mRecyclerView = findViewById(R.id.resultList);
        backButton = findViewById(R.id.buttonBack);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    backButton.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        ArrayList<ResultInfo> mListResults = intent.getParcelableArrayListExtra("result");
        ArrayList<String> shopPriseStringList = new ArrayList<>();
        ArrayList<String> linksList = new ArrayList<>();


        for (int i = 0; i < mListResults.size(); i++) {
            String st = "";
            String link = "";
            for (int j = 0; j < mListResults.get(i).getShopPriceInfo().size(); j++) {
                st += mListResults.get(i).getShopPriceInfo().get(j) + "\n";

            }
            shopPriseStringList.add(st);
            for (int k = 0; k < mListResults.get(i).getLinksList().size(); k++) {

                link += mListResults.get(i).getLinksList().get(k) + "\n";

            }
            linksList.add(link);
        }

        GridLayoutManager manager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            manager = new GridLayoutManager(this, 2);
        } else {
            manager = new GridLayoutManager(this, 1);
        }
        mRecyclerView.setLayoutManager(manager);
        RVAdapter adapter = new RVAdapter(mListResults, shopPriseStringList, linksList);
        mRecyclerView.setAdapter(adapter);


    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ProductViewHolder> {


        ArrayList<ResultInfo> mResultInfo;
        ArrayList<String> shopPriceInfo;
        ArrayList<String> linkList;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                //Log.d("MyLOG", String.valueOf(itemPosition));

                Intent intentPageProduct = new Intent(ResultList.this
                        , ActivityWithProductInfo.class);
                intentPageProduct.putExtra("photo", mResultInfo.get(itemPosition).getImageUrl());
                intentPageProduct.putExtra("name", mResultInfo.get(itemPosition).getProductName());
                intentPageProduct.putStringArrayListExtra("price", mResultInfo.get(itemPosition).getShopPriceInfo());
                intentPageProduct.putStringArrayListExtra("page", mResultInfo.get(itemPosition).getLinksList());
                //view.setBackgroundColor(R.color.lightGrey);
                startActivity(intentPageProduct);



            }
        };


        public RVAdapter(ArrayList<ResultInfo> mResultInfo, ArrayList<String> shopPriceInfo, ArrayList<String> linkList) {
            this.mResultInfo = mResultInfo;
            this.shopPriceInfo = shopPriceInfo;
            this.linkList = linkList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
            ProductViewHolder productViewHolder = new ProductViewHolder(v);
            v.setOnClickListener(mOnClickListener);
            return productViewHolder;


        }

        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            try {
                Picasso.get().load(mResultInfo.get(position).getImageUrl()).fit().centerInside()
                        .into(holder.productPhoto);

                holder.productName.setText(mResultInfo.get(position).getProductName());

                holder.shopPriceInfo.setText(shopPriceInfo.get(position));
                holder.link.setText(linkList.get(position));


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return mResultInfo.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {

            LinearLayout adapterLiner;
            ImageView productPhoto;
            TextView productName;
            TextView shopPriceInfo;
            TextView link;


            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                adapterLiner = itemView.findViewById(R.id.adapterLiner);
                productPhoto = itemView.findViewById(R.id.imageProd);
                productName = itemView.findViewById(R.id.prodName);
                shopPriceInfo = itemView.findViewById(R.id.recyclerForShopPrice);
                link = itemView.findViewById(R.id.linkInvisible);
            }
        }

    }


}


