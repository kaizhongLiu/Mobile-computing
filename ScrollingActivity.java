package com.kai.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    public class ShopData {

         public String name;
         public int imageResource;
         public String description;
         public int cost;
         public boolean purchased;

         public ShopData (String nm, int res, String desc, int cst)
         {
              name = nm;
              imageResource = res;
              description = desc;
              cost = cst;
              purchased = false;
         }
    }

    ShopData data[] = {
            new ShopData("Bone", R.drawable.bone, "Good chew toy.", 1),
            new ShopData("Carrot", R.drawable.carrot, "Good chew.", 1),
            new ShopData("Dog", R.drawable.dog, "Chews toy.", 2),
            new ShopData("Flame", R.drawable.flame, "It burns.", 1),
            new ShopData("Grapes", R.drawable.grapes, "Your eat them.", 1),
            new ShopData("House", R.drawable.house, "As opposed to home.", 100),
            new ShopData("Lamp", R.drawable.lamp, "It lights.", 2),
            new ShopData("Mouse", R.drawable.mouse, "Not a rat.", 1),
            new ShopData("Nail", R.drawable.nail, "Hammer required.", 1),
            new ShopData("Penguin", R.drawable.penguin, "Find Batman.", 10),
            new ShopData("Rocks", R.drawable.rocks, "Rolls.", 1),
            new ShopData("Star", R.drawable.star, "Like the sun but farther away.", 25),
            new ShopData("Toad", R.drawable.toad, "Like a frog.", 1),
            new ShopData("Van", R.drawable.van, "Has four wheels.", 10),
            new ShopData("Wheat", R.drawable.wheat, "Some breads have it.", 1),
            new ShopData("Yak", R.drawable.yak, "Yakity Yak Yak.", 15),
    };

    public ArrayList<Integer> purchasedItem = new ArrayList<Integer>(data.length);

    class CustomDialogClass extends Dialog {

        public int m_id;
        public int balance = 100;
        public View attachView = null;
        public CustomDialogClass(@NonNull Context context) {
            super(context);
        }





        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.shop_dialog);

            Button yes = this.findViewById(R.id.buyButton);
            yes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //removed purchased item
                    balance = balance - data[m_id].cost;
                    UpdateBalanceNumber(balance);

                    if(attachView != null) {
                        attachView.setVisibility(View.GONE);
                    }
                    dismiss();
                }

            });

            Button no = this.findViewById(R.id.noBuyButton);
            no.setOnClickListener(new View.OnClickListener(){
                @Override
                    public void onClick(View view) {
                    //
                    dismiss();
                }
            });
        }

        public void SetDetails(int id, View inputView)
        {
            m_id =id;
            attachView = inputView;

            TextView nameButton = findViewById(R.id.textView);
            nameButton.setText(data[id].name);

            TextView costButton = findViewById(R.id.textView_cost);
            costButton.setText("$" + data[id].cost);

            TextView despButton = findViewById(R.id.textView_despi);
            despButton.setText(data[id].description);

            ImageView imageButton = findViewById(R.id.imageView);
            imageButton.setImageResource(data[id].imageResource);


        }

        public void addMoney(){
            balance = balance +100;
            UpdateBalanceNumber (balance);
        }
    }

    public CustomDialogClass customDialog = null;

    public void ShopClicked(int id, View inputView)
    {
        customDialog.show();
        customDialog.SetDetails(id, inputView);
    }

    public void UpdateBalanceNumber (int Balance){

        TextView balanceText = this.findViewById(R.id.textView_Balance);
        balanceText.setText("Balance: $" + Balance);

    }

    public void InitShopItems()
    {
        LayoutInflater inflator = LayoutInflater.from(this);

        final LinearLayout layout= findViewById(R.id.scrollView);


        for (int i = 0; i < data.length; ++i)
        {

            if (purchasedItem.contains(i))
            {
                break;

            }

            final View myShopItem = inflator. inflate(R.layout.shop_item,null);

            final int tmp_id = i;

            View.OnClickListener click = new View.OnClickListener() {

                public int id = tmp_id;

                @Override
                public void onClick(View view){
                    ShopClicked(id, myShopItem);

                }
            };

            Button nameButton = myShopItem.findViewById(R.id.nameButton);
            nameButton.setText(data[i].name);
            nameButton.setOnClickListener(click);

            Button costButton = myShopItem.findViewById(R.id.costButton);
            costButton.setText("$" + data[i].cost);
            costButton.setOnClickListener(click);

            ImageButton imageButton = myShopItem.findViewById(R.id.imageButton);
            imageButton.setImageResource(data[i].imageResource);
            imageButton.setOnClickListener(click);

            layout.addView(myShopItem);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        customDialog = new CustomDialogClass(this);
        InitShopItems();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        
        if (id == R.id.addMoney) {
              customDialog.addMoney();
              return true;
            }
            return super.onOptionsItemSelected(item);
        }
}