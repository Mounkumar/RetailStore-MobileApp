
package java.ac.uk.tees.w9547666.retailstoreapplication.view.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.ac.uk.tees.w9547666.retailstoreapplication.R;
import java.ac.uk.tees.w9547666.retailstoreapplication.model.Repository;
import java.ac.uk.tees.w9547666.retailstoreapplication.model.entities.Money;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.ColorGenerator;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.LabelView;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.TextDrawable;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.customview.TextDrawable.IBuilder;
import java.math.BigDecimal;

/**
 * @author w9547666
 */

public class SimilarProductsPagerAdapter extends PagerAdapter {


    Context mContext;
    LayoutInflater mLayoutInflater;

    private String productCategory;

    private ImageView imageView;

    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    /**
     * Instantiates a new home slides pager adapter.
     *
     * @param context the context
     */
    public SimilarProductsPagerAdapter(Context context, String productCategory) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productCategory = productCategory;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {

        if (null != Repository.getCenterRepository().getMapOfProductsInCategory()
                && null != Repository.getCenterRepository().getMapOfProductsInCategory()
                .get(productCategory)) {
            return Repository.getCenterRepository().getMapOfProductsInCategory()
                    .get(productCategory).size();
        }

        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
     * java.lang.Object)
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup
     * , int)
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_category_list, container,
                false);

        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(
                String.valueOf(Repository.getCenterRepository()
                        .getMapOfProductsInCategory().get(productCategory).get(position)
                        .getItemName().charAt(0)),

                mColorGenerator.getColor(Repository.getCenterRepository()
                        .getMapOfProductsInCategory().get(productCategory).get(position)
                        .getItemName()));

        final String ImageUrl = Repository.getCenterRepository().getMapOfProductsInCategory()
                .get(productCategory).get(position).getImageURL();

        Picasso.with(mContext).load(ImageUrl).placeholder(drawable)
                .error(drawable).fit().centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed

                        Picasso.with(mContext).load(ImageUrl)
                                .placeholder(drawable).error(drawable).fit()
                                .centerCrop().into(imageView);
                    }
                });

        ((TextView) itemView.findViewById(R.id.item_name))
                .setText(Repository.getCenterRepository().getMapOfProductsInCategory()
                        .get(productCategory).get(position).getItemName());

        ((TextView) itemView.findViewById(R.id.item_short_desc))
                .setText(Repository.getCenterRepository().getMapOfProductsInCategory()
                        .get(productCategory).get(position).getItemDetail());

        ((TextView) itemView.findViewById(R.id.category_discount))
                .setText(Money.rupees(
                        BigDecimal.valueOf(Double.valueOf(Repository
                                .getCenterRepository().getMapOfProductsInCategory()
                                .get(productCategory).get(position)
                                .getSellMRP()))).toString());

        LabelView label = new LabelView(mContext);
        label.setText(Repository.getCenterRepository().getMapOfProductsInCategory()
                .get(productCategory).get(position).getDiscount());
        label.setBackgroundColor(0xffE91E63);
        label.setTargetView(itemView.findViewById(R.id.imageView), 10,
                LabelView.Gravity.RIGHT_TOP);

        container.addView(itemView);

        return itemView;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup,
     * int, java.lang.Object)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }


}