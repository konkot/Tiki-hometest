package com.tt.hometest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tt.hometest.views.InfinityViewPager;
import com.tt.hometest.views.SearchBar;
import com.tt.hometest.model.BannerItems;
import com.tt.hometest.model.FlashDealItems;
import com.tt.hometest.model.QuickLinkItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    Toast toast;
    ViewPager2 viewPager;
    RecyclerView quickLinkView;
    RecyclerView flashDealView;
    ViewGroup bannerParent, quickLinkParent, flashDealParent, descriptionParent;
    ProgressBar bannerProgressBar, quickLinkProgressBar, flashDealProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageButton buttonChat, buttonCart;
    BannerItems bannerItems;
    QuickLinkItems quickLinkItems;
    FlashDealItems flashDealItems;
    AdapterBanner adapterBanner;
    AdapterQuickLink adapterQuickLink;
    AdapterFlashDeal adapterFlashDeal;
    InfinityViewPager infinityViewPager;

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        transparentStatusBar();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final ImageView logoView = new ImageView(this);
        logoView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo));
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        int logoMarginTop = getResources().getDimensionPixelSize(R.dimen.logo_margin_top);
        int logoMarginBottom = getResources().getDimensionPixelSize(R.dimen.logo_margin_bottom);
        layoutParams.setMargins(0, logoMarginTop, 0, logoMarginBottom);
        toolbar.addView(logoView, layoutParams);
        addActionButtons(toolbar);

        final AppBarLayout appBar = findViewById(R.id.app_bar);
        final SearchBar searchBar = findViewById(R.id.searchBar);
        CollapsingToolbarLayout collapsingToolbarLayout  = appBar.findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(false);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int searchBarMarginRight = toolbar.getWidth() - (int) buttonChat.getX();
                if (searchBarMarginRight <= 0)
                    return;
                float totalScrollRange = appBar.getTotalScrollRange();
                float scrollProgress =  Math.abs(verticalOffset/totalScrollRange);
                int r = (int) (scrollProgress * searchBarMarginRight);
                logoView.setAlpha(1 - scrollProgress);
                searchBar.setEditTextCollapsed(scrollProgress > 0);
                searchBar.setMarginRight(r);

            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        bannerParent = findViewById(R.id.bannerParent);
        quickLinkParent = findViewById(R.id.quickLinkParent);
        flashDealParent = findViewById(R.id.flashDealParent);
        descriptionParent = findViewById(R.id.description);
        viewPager = bannerParent.findViewById(R.id.banner);
        quickLinkView = quickLinkParent.findViewById(R.id.quickLink);
        flashDealView = flashDealParent.findViewById(R.id.flashDeal);
        bannerProgressBar = bannerParent.findViewById(R.id.bannerProgressBar);
        quickLinkProgressBar = quickLinkParent.findViewById(R.id.quickLinkProgressBar);
        flashDealProgressBar = flashDealParent.findViewById(R.id.flashDealProgressBar);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (!isLoading)
                startLoading();
            swipeRefreshLayout.setRefreshing(false);
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewPager.setNestedScrollingEnabled(false);
        }
        viewPager.setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);
        quickLinkView.setHasFixedSize(true);
        quickLinkView.setNestedScrollingEnabled(false);
        flashDealView.setHasFixedSize(true);
        flashDealView.setNestedScrollingEnabled(false);
        infinityViewPager = new InfinityViewPager(viewPager);
        infinityViewPager.enableInifityScroll(true);

        startLoading();

    }

    void addActionButtons(Toolbar toolbar) {
        LayoutInflater inflater = LayoutInflater.from(this);

        buttonCart = (ImageButton)inflater.inflate(R.layout.action_button, toolbar, false);
        buttonCart.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_shopping_cart_black_24dp));
        Toolbar.LayoutParams layoutParams2 = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.RIGHT);
        layoutParams2.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.action_button_margin));
        toolbar.addView(buttonCart, layoutParams2);

        buttonChat = (ImageButton)inflater.inflate(R.layout.action_button, toolbar, false);
        buttonChat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chat_black_24dp));
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.RIGHT);
        toolbar.addView(buttonChat, layoutParams);

        buttonChat.setOnClickListener(v -> showMsgDialog(this, getString(R.string.action_chat)));
        buttonCart.setOnClickListener(v -> showMsgDialog(this, getString(R.string.action_cart)));

        findViewById(R.id.buttonViewMore).setOnClickListener(v -> showMsgDialog(this, getString(R.string.view_more)));
    }

    private boolean requestBannerCompleted, requestQuickLinkCompleted, requestFlashDealCompleted, requestFlashDealStarted;
    private boolean requestBannerOK, requestQuickLinkOK, requestFlashDealOK;
    private boolean renderBannerStarted, renderQuickLinkStarted, renderFlashDealStarted,
            renderBannerCompleted, renderQuickLinkCompleted, renderFlashDealCompleted;
    private boolean isLoading;

    void prepareLoading(){
        bannerParent.setVisibility(View.VISIBLE);
        quickLinkParent.setVisibility(View.INVISIBLE);
        flashDealParent.setVisibility(View.INVISIBLE);
        descriptionParent.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
        quickLinkView.setVisibility(View.INVISIBLE);
        flashDealView.setVisibility(View.INVISIBLE);
        bannerProgressBar.setVisibility(View.VISIBLE);
        quickLinkProgressBar.setVisibility(View.VISIBLE);
        flashDealProgressBar.setVisibility(View.VISIBLE);
        viewPager.setAdapter(null);
        quickLinkView.setAdapter(null);
        flashDealView.setAdapter(null);

        requestBannerCompleted = requestQuickLinkCompleted = requestFlashDealCompleted = requestFlashDealStarted
                = renderBannerStarted = renderQuickLinkStarted = renderFlashDealStarted = renderBannerCompleted
                = renderQuickLinkCompleted = renderFlashDealCompleted = requestBannerOK = requestQuickLinkOK
                = requestFlashDealOK = false;
        bannerItems = null; quickLinkItems = null; flashDealItems = null;

    }

    void startLoading() {
        log(this, "start loading");
        prepareLoading();
        requestBanner();
        requestQuickLink();
        isLoading = true;
    }


    synchronized void checkRequest() {
        log(this, "check request");
        if (requestBannerCompleted && requestQuickLinkCompleted && !requestFlashDealStarted) {
            requestFlashDeal();
        }
        if (requestBannerCompleted && bannerItems == null && requestQuickLinkCompleted && quickLinkItems == null
                && requestFlashDealCompleted && flashDealItems == null) {
            runOnUiThread(this::showConnectionErrorDialog);

        }
    }

    synchronized void checkRender() {
        log(this, "check render");
        if (requestBannerCompleted && !renderBannerStarted) {
            renderBannerStarted = true;
            runOnUiThread(this::renderBanner);

        } else if (requestQuickLinkCompleted && !renderQuickLinkStarted && renderBannerCompleted) {
            renderQuickLinkStarted = true;
            runOnUiThread(this::renderQuickLink);

        } else if (requestFlashDealCompleted && !renderFlashDealStarted && renderQuickLinkCompleted) {
            renderFlashDealStarted = true;
            runOnUiThread(this::renderFlashDeal);
        } else {
            isLoading = false;
        }

        runOnUiThread(() -> {
            if (renderBannerCompleted && !renderQuickLinkStarted)
                quickLinkParent.setVisibility(View.VISIBLE);
            else if (renderQuickLinkCompleted && !renderFlashDealStarted)
                flashDealParent.setVisibility(View.VISIBLE);
        });

    }

    <T> T parseRespond(ResponseBody responseBody, Class<T> modelClass) {
        if (responseBody == null)
            return null;
        Object output = null;
        try {
            output = gson.fromJson(responseBody.string(), modelClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return Primitives.wrap(modelClass).cast(output);
        return output == null ? null : (T) output;
    }

    void requestBanner() {
        log(this, "request banner");
        Request request = new Request.Builder().url("https://api.tiki.vn/v2/home/banners/v2").build();
        //Request request = new Request.Builder().url("https://api.tiki.vn/v2/widget/deals/hot").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                requestBannerCompleted = true;
                requestBannerOK = false;
                checkRequest();
                checkRender();
                log(this, "request banner failed");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                bannerItems = parseRespond(response.body(), BannerItems.class);
                requestBannerCompleted = true;
                requestBannerOK = true;
                checkRequest();
                checkRender();
                log(this, "request banner ok");


            }
        });
    }

    void requestQuickLink() {
        log(this, "request quicklink");
        //Request request = new Request.Builder().url("https://api.tiki.vn/shopping/v2/widgets/quick_link2").build();
        Request request = new Request.Builder().url("https://api.tiki.vn/shopping/v2/widgets/quick_link").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                requestQuickLinkCompleted = true;
                requestQuickLinkOK = false;
                checkRequest();
                checkRender();
                log(this, "request quicklink failed");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                quickLinkItems = parseRespond(response.body(), QuickLinkItems.class);
                requestQuickLinkCompleted = true;
                requestQuickLinkOK = true;
                checkRequest();
                checkRender();
                log(this, "request quicklink ok");
            }
        });
    }

    void requestFlashDeal() {
        log(this, "request flashdeal");
        requestFlashDealStarted = true;
        Request request = new Request.Builder().url("https://api.tiki.vn/v2/widget/deals/hot").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                requestFlashDealCompleted = true;
                requestFlashDealOK = false;
                checkRequest();
                checkRender();
                log(this, "request flashdeal failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                flashDealItems = parseRespond(response.body(), FlashDealItems.class);
                requestFlashDealCompleted = true;
                requestFlashDealOK = true;
                checkRender();
                log(this, "request flashdeal ok");
            }
        });
    }

    void renderBanner() {
        log(this, "render banner");
        renderBannerStarted = true;
        if (bannerItems == null || bannerItems.isNoData()) {
            log(this, "render banner data null");
            bannerParent.setVisibility(View.GONE);
            renderBannerCompleted = true;
            checkRender();
            return;
        }
        float lowestBannerRatio = bannerItems.getLowestBannerRatio();
        int bannerMargin = getResources().getDimensionPixelSize(R.dimen.banner_item_margin);
        ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
        lp.height = (int) ((viewPager.getWidth() - bannerMargin * 2) / lowestBannerRatio) + bannerMargin * 2;
        viewPager.setLayoutParams(lp);
        final AdapterBanner adapterBanner = new AdapterBanner(this, bannerItems, new Adapter.OnAllViewLoaded() {
            @Override
            public void onAllViewLoaded() {
                //renderQuickLink();
                bannerProgressBar.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                renderBannerCompleted = true;
                checkRender();
                log(this, "render banner ok");
            }

            @Override
            public void onAllViewFailedToLoad() {
                //renderQuickLink();s
                bannerParent.setVisibility(View.GONE);
                renderBannerCompleted = true;
                checkRender();
                log(this, "render banner failed");
            }
        });
        viewPager.setAdapter(adapterBanner);
        viewPager.setCurrentItem(adapterBanner.getMiddlePostion(), false);
        infinityViewPager.start();
    }

    void renderQuickLink() {
        log(this, "render quicklink");
        renderQuickLinkStarted = true;
        if (quickLinkItems == null || quickLinkItems.isNoData()) {
            log(this, "render quicklink data null");
            quickLinkParent.setVisibility(View.GONE);
            renderQuickLinkCompleted = true;
            checkRender();
            //
            return;
        }
        quickLinkParent.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        quickLinkView.setLayoutManager(layoutManager);
        AdapterQuickLink adapter = new AdapterQuickLink(this, quickLinkItems, new Adapter.OnAllViewLoaded() {
            @Override
            public void onAllViewLoaded() {
                quickLinkProgressBar.setVisibility(View.GONE);
                quickLinkView.setVisibility(View.VISIBLE);
                renderQuickLinkCompleted = true;
                checkRender();
                log(this, "render quicklink ok");
            }

            @Override
            public void onAllViewFailedToLoad() {
                quickLinkParent.setVisibility(View.GONE);
                renderQuickLinkCompleted = true;
                checkRender();
                log(this, "render quicklink failed");
            }
        });
        quickLinkView.setAdapter(adapter);
    }

    void renderFlashDeal() {
        log(this, "render flashdeal");
        renderFlashDealStarted = true;
        if (flashDealItems == null || flashDealItems.isNoData()) {
            log(this, "render flashdeal data null");
            flashDealParent.setVisibility(View.GONE);
            renderFlashDealCompleted = true;
            checkRender();
            return;
        }
        flashDealParent.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        flashDealView.setLayoutManager(layoutManager2);
        AdapterFlashDeal adapter2 = new AdapterFlashDeal(this, flashDealItems, new Adapter.OnAllViewLoaded() {
            @Override
            public void onAllViewLoaded() {
                flashDealProgressBar.setVisibility(View.GONE);
                flashDealView.setVisibility(View.VISIBLE);
                descriptionParent.setVisibility(View.VISIBLE);
                renderFlashDealCompleted = true;
                checkRender();
                log(this, "render flashdeal ok");

            }

            @Override
            public void onAllViewFailedToLoad() {
                flashDealParent.setVisibility(View.GONE);
                renderFlashDealCompleted = true;
                checkRender();
                log(this, "render flashdeal failed");

            }
        });
        flashDealView.setAdapter(adapter2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        infinityViewPager.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        infinityViewPager.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();
                System.out.println("======= clear");

            }
        }).start();*/

    }

    private void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void showConnectionErrorDialog(){
        AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(R.string.no_connection)
                .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startLoading();
                    }
                }).show();
    }

    public static void showMsgDialog(Context context, CharSequence msg){
        AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(msg)
                .setNegativeButton(R.string.ok, null).show();
    }

    public static void log(Object obj, String msg) {
        //Log.i(obj.getClass().getSimpleName(),msg);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
        //return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chat || id == R.id.action_cart) {
            showMsgDialog(item.getTitle());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
