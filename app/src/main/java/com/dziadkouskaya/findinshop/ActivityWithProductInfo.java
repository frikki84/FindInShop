package com.dziadkouskaya.findinshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityWithProductInfo extends AppCompatActivity {

    String photoURL;
    String name;
    ArrayList<String > mPriceLIst;
    ArrayList<String> mLinkList;

    String url;

    ImageView photo;
    TextView namePriduct;
    RecyclerView recyclerView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_mini);

        Intent intent = getIntent();
        photoURL = intent.getStringExtra("photo");
        name = intent.getStringExtra("name");


        mPriceLIst = intent.getStringArrayListExtra("price");
        mLinkList = intent.getStringArrayListExtra("page");



        photo = findViewById(R.id.imagePhoto);
        namePriduct = findViewById(R.id.textName);
        recyclerView = findViewById(R.id.recyclerForPrLi);
        back = findViewById(R.id.buttonBackForSearch);



        Picasso.get().load(photoURL).fit().centerInside()
                .into(photo);

        namePriduct.setText(name);

        GridLayoutManager manager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(manager);

        LinkAdapter adapter = new LinkAdapter(mPriceLIst, mLinkList);

        recyclerView.setAdapter(adapter);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        back.setOnClickListener(onClickListener);



    }



    class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.PriceLink> {

        ArrayList<String> priceList;
        ArrayList<String> linkList;
        Uri uriUrl;




        public LinkAdapter(ArrayList<String> priceList, ArrayList<String> linkList) {
            this.priceList = priceList;
            this.linkList = linkList;
        }

        @NonNull
        @Override
        public PriceLink onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_with_product_info, parent, false);
            final PriceLink priceLink = new PriceLink(v);
            return priceLink;

        }

        @Override
        public void onBindViewHolder(@NonNull PriceLink holder, int position) {
            try {
                holder.price.setText(priceList.get(position));
                holder.link.setText(linkList.get(position));

                final String url = holder.link.getText().toString();

                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToUrl(url);
                    }
                };
                holder.price.setOnClickListener(onClickListener);


            } catch (ActivityNotFoundException e) {
                    e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return priceList.size();
        }

        public class PriceLink extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;
            TextView price;
            TextView link;

            public PriceLink(@NonNull View itemView) {
                super(itemView);
                linearLayout = itemView.findViewById(R.id.price_link);
                price = itemView.findViewById(R.id.textViewForPrice);
                link = itemView.findViewById(R.id.textViewForLink);

            }

        }

        private void goToUrl (String url) {
            try {
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
