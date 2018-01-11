package cat.udl.eps.etrapp.android.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnCloseClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.CategoryController;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Category;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.activities.CreateOrEditEvent;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.adapters.HomeAdapter;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;
import cat.udl.eps.etrapp.android.utils.Toaster;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_CREATE_EVENT;

public class HomeFragment extends ScrollableFragment {


    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.chip_container) ViewGroup chipContainer;
//    @BindView(R.id.chip) Chip chip;

    private HomeAdapter homeAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void configView(View fragmentView) {
        setHasOptionsMenu(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        homeAdapter = new HomeAdapter(this);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnClickListener(v -> {
            startActivity(EventActivity.start(getContext(), (long) v.getTag()));
        });

//        chip.setOnCloseClickListener(v -> {
//            loadAllEvents();
//            chipContainer.setVisibility(View.GONE);
//        });

        loadAllEvents();
    }

    private void loadAllEvents() {
        EventController.getInstance().getAllEvents()
                .addOnSuccessListener(events -> {
                    List<Event> tmpEvents = new ArrayList<>();
                    List<Event> tmpFeatured = new ArrayList<>();

                    for (Event e : events) {
                        if (e.isFeatured()) tmpFeatured.add(e);
                        else tmpEvents.add(e);
                    }

                    homeAdapter.setBothEvents(tmpFeatured, tmpEvents);
                })
                .addOnFailureListener(e -> Toaster.show(getContext(), e.getMessage()));
    }

    @Override
    public void scroll() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onPause() {
        super.onPause();

        for (Fragment f : getChildFragmentManager().getFragments()) {
            if (f instanceof FeaturedEventFragment) {
                Timber.d("Tag %s", f.getTag());
                getChildFragmentManager().beginTransaction().remove(f).commit();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_home, menu);

        if (UserController.getInstance().isUserLoggedIn()) {
            menu.add(0, ID_MENU_ITEM_CREATE_EVENT, 50, R.string.add)
                    .setIcon(R.drawable.ic_plus_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 345) {
            if (resultCode == Activity.RESULT_OK) {
                loadAllEvents();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_ITEM_CREATE_EVENT:
                startActivityForResult(CreateOrEditEvent.startCreateMode(getContext()), 345);
                return true;
            case R.id.action_filter:

                CategoryController.getInstance()
                        .getAllCategories()
                        .addOnSuccessListener(categories -> {
                            System.out.println(categories);
                            List<Category> listCategories = new ArrayList<>();
                            listCategories.addAll(categories);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(getString(R.string.filter));

                            List<String> showCategories = new ArrayList<>();

                            for (Category c : listCategories) {
                                showCategories.add(c.getName());
                            }

                            builder.setItems(showCategories.toArray(new String[0]), (dialog, which) -> {
                                EventController.getInstance()
                                        .getEventsFromCategory(categories.get(which).getId())
                                        .addOnSuccessListener(events -> {


                                            Chip chip = new Chip(getContext());
                                            chip.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT)
                                            );
                                            chip.setChipText(categories.get(which).getName());
                                            chip.setClosable(true);
                                            chip.changeBackgroundColor(R.color.md_amber400);
                                            chip.setCloseColor(R.color.md_amber900);
                                            chip.changeSelectedBackgroundColor(R.color.md_amber400);
                                            chip.setSelectedCloseColor(R.color.md_pink200);
                                            chip.setOnCloseClickListener(v -> {
                                                ((ViewGroup)chipContainer.findViewById(R.id.real_chip_container)).removeAllViews();
                                                chipContainer.setVisibility(View.GONE);
                                                loadAllEvents();
                                            });
                                            ((ViewGroup)chipContainer.findViewById(R.id.real_chip_container)).addView(chip);
                                            chipContainer.setVisibility(View.VISIBLE);

                                            List<Event> tmpEvents = new ArrayList<>();
                                            List<Event> tmpFeatured = new ArrayList<>();

                                            for (Event e : events) {
                                                if (e.isFeatured()) tmpFeatured.add(e);
                                                else tmpEvents.add(e);
                                            }

                                            homeAdapter.setBothEvents(tmpFeatured, tmpEvents);

                                        })
                                        .addOnFailureListener(e -> Toaster.show(getContext(), "No events in this category"));

                            });

                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        })
                        .addOnFailureListener(e -> Toaster.show(getContext(), e.getMessage()));


                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
