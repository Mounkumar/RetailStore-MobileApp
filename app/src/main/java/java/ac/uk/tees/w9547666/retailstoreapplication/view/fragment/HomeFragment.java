package java.ac.uk.tees.w9547666.retailstoreapplication.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.ac.uk.tees.w9547666.retailstoreapplication.R;
import java.ac.uk.tees.w9547666.retailstoreapplication.domain.api.ProductCategoryLoader;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.Utils;
import java.ac.uk.tees.w9547666.retailstoreapplication.util.Utils.AnimationType;
import java.ac.uk.tees.w9547666.retailstoreapplication.view.activities.HomeActivity;

/**
 * @author w9547666
 */

public class HomeFragment extends Fragment {
    int mutedColor = R.color.primary;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    /**
     * The double back to exit pressed once.
     */
    private boolean doubleBackToExitPressedOnce;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product_category, container, false);

        view.findViewById(R.id.search_item).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Utils.switchFragmentWithAnimation(R.id.frag_container,
                                new SearchProductFragment(),
                                ((HomeActivity) getActivity()), null,
                                AnimationType.SLIDE_UP);

                    }
                });

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.anim_toolbar);
        ((HomeActivity) getActivity()).setSupportActionBar(toolbar);
        ((HomeActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        collapsingToolbar = (CollapsingToolbarLayout) view
                .findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Categories");

        ImageView header = (ImageView) view.findViewById(R.id.header);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                mutedColor = palette.getMutedColor(R.color.primary_500);
                collapsingToolbar.setContentScrimColor(mutedColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        new ProductCategoryLoader(recyclerView, getActivity()).execute();



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (doubleBackToExitPressedOnce) {
                        // super.onBackPressed();

                        if (mHandler != null) {
                            mHandler.removeCallbacks(mRunnable);
                        }

                        getActivity().finish();

                        return true;
                    }

                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(getActivity(),
                            "Please click BACK again to exit",
                            Toast.LENGTH_SHORT).show();

                    mHandler.postDelayed(mRunnable, 2000);

                }
                return true;
            }
        });

        return view;

    }

}
