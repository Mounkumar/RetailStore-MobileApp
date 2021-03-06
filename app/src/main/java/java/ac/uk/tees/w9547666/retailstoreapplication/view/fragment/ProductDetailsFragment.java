package java.ac.uk.tees.w9547666.retailstoreapplication.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.ac.uk.tees.w9547666.retailstoreapplication.R;
import java.ac.uk.tees.w9547666.retailstoreapplication.model.Repository;
import java.ac.uk.tees.w9547666.retailstoreapplication.model.entities.Money;
import java.ac.uk.tees.w9547666.retailstoreapplication.model.entities.Product;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.ColorGenerator;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.Utils;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.Utils.AnimationType;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.activities.HomeActivity;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.adapter.SimilarProductsPagerAdapter;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.ClickableViewPager;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.ClickableViewPager.OnItemClickListener;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.LabelView;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.TextDrawable;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.TextDrawable.IBuilder;
import java.math.BigDecimal;

/**
 * @author w9547666
 */

public class ProductDetailsFragment extends Fragment {

    private int productListNumber;
    private ImageView itemImage;
    private TextView itemSellPrice;
    private TextView itemName;
    private TextView quanitity;
    private TextView itemdescription;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private String subcategoryKey;
    private boolean isFromCart;
    private ClickableViewPager similarProductsPager;
    private ClickableViewPager topSellingPager;
    private Toolbar mToolbar;

    /**
     * Instantiates a new product details fragment.
     */
    public ProductDetailsFragment(String subcategoryKey, int productNumber,
                                  boolean isFromCart) {

        this.subcategoryKey = subcategoryKey;
        this.productListNumber = productNumber;
        this.isFromCart = isFromCart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_product_detail,
                container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.htab_toolbar);
        if (mToolbar != null) {
            ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        if (mToolbar != null) {
            ((HomeActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);

        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        ((HomeActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        similarProductsPager = (ClickableViewPager) rootView
                .findViewById(R.id.similar_products_pager);

        topSellingPager = (ClickableViewPager) rootView
                .findViewById(R.id.top_selleing_pager);

        itemSellPrice = ((TextView) rootView
                .findViewById(R.id.category_discount));

        quanitity = ((TextView) rootView.findViewById(R.id.iteam_amount));

        itemName = ((TextView) rootView.findViewById(R.id.product_name));

        itemdescription = ((TextView) rootView
                .findViewById(R.id.product_description));

        itemImage = (ImageView) rootView.findViewById(R.id.product_image);

        fillProductData();

        rootView.findViewById(R.id.add_item).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isFromCart) {

                            //Update Quantity on shopping List
                            Repository
                                    .getCenterRepository()
                                    .getListOfProductsInShoppingList()
                                    .get(productListNumber)
                                    .setQuantity(
                                            String.valueOf(

                                                    Integer.valueOf(Repository
                                                            .getCenterRepository()
                                                            .getListOfProductsInShoppingList()
                                                            .get(productListNumber)
                                                            .getQuantity()) + 1));


                            //Update Ui
                            quanitity.setText(Repository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity());

                            Utils.vibrate(getActivity());

                            //Update checkout amount on screen
                            ((HomeActivity) getActivity()).updateCheckOutAmount(
                                    BigDecimal.valueOf(Long
                                            .valueOf(Repository
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList()
                                                    .get(productListNumber)
                                                    .getSellMRP())), true);

                        } else {

                            // current object
                            Product tempObj = Repository
                                    .getCenterRepository().getMapOfProductsInCategory()
                                    .get(subcategoryKey).get(productListNumber);

                            // if current object is lready in shopping list
                            if (Repository.getCenterRepository()
                                    .getListOfProductsInShoppingList().contains(tempObj)) {

                                // get position of current item in shopping list
                                int indexOfTempInShopingList = Repository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .indexOf(tempObj);

                                // increase quantity of current item in shopping
                                // list
                                if (Integer.parseInt(tempObj.getQuantity()) == 0) {

                                    ((HomeActivity) getContext())
                                            .updateItemCount(true);

                                }

                                // update quanity in shopping list
                                Repository
                                        .getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .get(indexOfTempInShopingList)
                                        .setQuantity(
                                                String.valueOf(Integer
                                                        .valueOf(tempObj
                                                                .getQuantity()) + 1));

                                // update checkout amount
                                ((HomeActivity) getContext()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(Repository
                                                        .getCenterRepository()
                                                        .getMapOfProductsInCategory()
                                                        .get(subcategoryKey)
                                                        .get(productListNumber)
                                                        .getSellMRP())), true);

                                // update current item quanitity
                                quanitity.setText(tempObj.getQuantity());

                            } else {

                                ((HomeActivity) getContext())
                                        .updateItemCount(true);

                                tempObj.setQuantity(String.valueOf(1));

                                quanitity.setText(tempObj.getQuantity());

                                Repository.getCenterRepository()
                                        .getListOfProductsInShoppingList().add(tempObj);

                                ((HomeActivity) getContext()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Double
                                                .valueOf(Repository
                                                        .getCenterRepository()
                                                        .getMapOfProductsInCategory()
                                                        .get(subcategoryKey)
                                                        .get(productListNumber)
                                                        .getSellMRP())), true);

                            }

                            Utils.vibrate(getContext());

                        }
                    }

                });

        rootView.findViewById(R.id.remove_item).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isFromCart)

                        {

                            if (Integer.valueOf(Repository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity()) > 2) {

                                Repository
                                        .getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .get(productListNumber)
                                        .setQuantity(
                                                String.valueOf(

                                                        Integer.valueOf(Repository
                                                                .getCenterRepository()
                                                                .getListOfProductsInShoppingList()
                                                                .get(productListNumber)
                                                                .getQuantity()) - 1));

                                quanitity.setText(Repository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(productListNumber).getQuantity());

                                ((HomeActivity) getActivity()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(Repository
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getSellMRP())), false);

                                Utils.vibrate(getActivity());
                            } else if (Integer.valueOf(Repository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity()) == 1) {
                                ((HomeActivity) getActivity())
                                        .updateItemCount(false);

                                ((HomeActivity) getActivity()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(Repository
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getSellMRP())), false);

                                Repository.getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .remove(productListNumber);

                                if (Integer
                                        .valueOf(((HomeActivity) getActivity())
                                                .getItemCount()) == 0) {

                                    MyCartFragment.updateMyCartFragment(false);

                                }

                                Utils.vibrate(getActivity());

                            }

                        } else {

                            Product tempObj = Repository
                                    .getCenterRepository().getMapOfProductsInCategory()
                                    .get(subcategoryKey).get(productListNumber);

                            if (Repository.getCenterRepository()
                                    .getListOfProductsInShoppingList().contains(tempObj)) {

                                int indexOfTempInShopingList = Repository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .indexOf(tempObj);

                                if (Integer.valueOf(tempObj.getQuantity()) != 0) {

                                    Repository
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .setQuantity(
                                                    String.valueOf(Integer.valueOf(tempObj
                                                            .getQuantity()) - 1));

                                    ((HomeActivity) getContext()).updateCheckOutAmount(
                                            BigDecimal.valueOf(Long
                                                    .valueOf(Repository
                                                            .getCenterRepository()
                                                            .getMapOfProductsInCategory()
                                                            .get(subcategoryKey)
                                                            .get(productListNumber)
                                                            .getSellMRP())),
                                            false);

                                    quanitity.setText(Repository
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .getQuantity());

                                    Utils.vibrate(getContext());

                                    if (Integer.valueOf(Repository
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .getQuantity()) == 0) {

                                        Repository
                                                .getCenterRepository()
                                                .getListOfProductsInShoppingList()
                                                .remove(indexOfTempInShopingList);

                                        ((HomeActivity) getContext())
                                                .updateItemCount(false);

                                    }

                                }

                            } else {

                            }

                        }

                    }

                });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (isFromCart) {

                        Utils.switchContent(R.id.frag_container,
                                Utils.SHOPPING_LIST_TAG,
                                ((HomeActivity) (getActivity())),
                                AnimationType.SLIDE_UP);
                    } else {

                        Utils.switchContent(R.id.frag_container,
                                Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
                                ((HomeActivity) (getActivity())),
                                AnimationType.SLIDE_RIGHT);
                    }

                }
                return true;
            }
        });

        if (isFromCart) {

            similarProductsPager.setVisibility(View.GONE);

            topSellingPager.setVisibility(View.GONE);

        } else {
            showRecomondation();
        }

        return rootView;
    }

    private void showRecomondation() {

        SimilarProductsPagerAdapter mCustomPagerAdapter = new SimilarProductsPagerAdapter(
                getActivity(), subcategoryKey);

        similarProductsPager.setAdapter(mCustomPagerAdapter);

        similarProductsPager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                productListNumber = position;

                fillProductData();

                Utils.vibrate(getActivity());

            }
        });

        topSellingPager.setAdapter(mCustomPagerAdapter);

        topSellingPager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                productListNumber = position;

                fillProductData();

                Utils.vibrate(getActivity());

            }
        });
    }

    public void fillProductData() {

        if (!isFromCart) {


            //Fetch and display item from Gloabl Data Model

            itemName.setText(Repository.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getItemName());

            quanitity.setText(Repository.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getQuantity());

            itemdescription.setText(Repository.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getItemDetail());

            String sellCostString = Money.rupees(
                    BigDecimal.valueOf(Double.valueOf(Repository
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getSellMRP()))).toString()
                    + "  ";

            String buyMRP = Money.rupees(
                    BigDecimal.valueOf(Double.valueOf(Repository
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getMRP()))).toString();

            String costString = sellCostString + buyMRP;

            itemSellPrice.setText(costString, BufferType.SPANNABLE);

            Spannable spannable = (Spannable) itemSellPrice.getText();

            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(Repository.getCenterRepository()
                            .getMapOfProductsInCategory().get(subcategoryKey)
                            .get(productListNumber).getItemName().charAt(0)),
                    mColorGenerator.getColor(Repository
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getItemName()));

            Picasso.with(getActivity())
                    .load(Repository.getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getImageURL()).placeholder(drawable)
                    .error(drawable).fit().centerCrop()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed

                            Picasso.with(getActivity())
                                    .load(Repository.getCenterRepository()
                                            .getMapOfProductsInCategory()
                                            .get(subcategoryKey)
                                            .get(productListNumber)
                                            .getImageURL())
                                    .placeholder(drawable).error(drawable)
                                    .fit().centerCrop().into(itemImage);
                        }
                    });

            LabelView label = new LabelView(getActivity());


            label.setText(Repository.getCenterRepository().getMapOfProductsInCategory()
                    .get(subcategoryKey).get(productListNumber).getDiscount());
            label.setBackgroundColor(0xffE91E63);

            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);
        } else {


            //Fetch and display products from Shopping list

            itemName.setText(Repository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getItemName());

            quanitity.setText(Repository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getQuantity());

            itemdescription.setText(Repository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getItemDetail());

            String sellCostString = Money.rupees(
                    BigDecimal.valueOf(Long.valueOf(Repository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getSellMRP()))).toString()
                    + "  ";

            String buyMRP = Money.rupees(
                    BigDecimal.valueOf(Long.valueOf(Repository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getMRP()))).toString();

            String costString = sellCostString + buyMRP;

            itemSellPrice.setText(costString, BufferType.SPANNABLE);

            Spannable spannable = (Spannable) itemSellPrice.getText();

            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(Repository.getCenterRepository()
                            .getListOfProductsInShoppingList().get(productListNumber)
                            .getItemName().charAt(0)),
                    mColorGenerator.getColor(Repository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getItemName()));

            Picasso.with(getActivity())
                    .load(Repository.getCenterRepository()
                            .getListOfProductsInShoppingList().get(productListNumber)
                            .getImageURL()).placeholder(drawable)
                    .error(drawable).fit().centerCrop()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed

                            Picasso.with(getActivity())
                                    .load(Repository.getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(productListNumber)
                                            .getImageURL())
                                    .placeholder(drawable).error(drawable)
                                    .fit().centerCrop().into(itemImage);
                        }
                    });

            LabelView label = new LabelView(getActivity());

            label.setText(Repository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getDiscount());
            label.setBackgroundColor(0xffE91E63);

            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);

        }
    }


}
