package cat.udl.eps.etrapp.android.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private AlertDialog alerta;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    HomeAdapter homeAdapter;

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

        EventController.getInstance().getAllEvents()
                .addOnSuccessListener(events -> {
                    List<Event> tmpEvents = new ArrayList<>();
                    List<Event> tmpFeatured = new ArrayList<>();


                    for (Event e: events) {
                        if(e.isFeatured()) tmpFeatured.add(e); else tmpEvents.add(e);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_ITEM_CREATE_EVENT:
                startActivity(CreateOrEditEvent.startCreateMode(getContext()));
                return true;
            case R.id.action_filter:

                CategoryController.getInstance().getAllCategories().addOnSuccessListener(categories -> {
                    System.out.println(categories);
                    List<Category> listCategories = new ArrayList<>();
                    for (Category c: categories) {
                        listCategories.add(c);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Filtro por categoria: ");

                    Map<Integer, Category> categorias = new HashMap<Integer, Category>();
                    List<String> showCategories = new ArrayList<String>();

                    int pos = 0;
                    for (Category c : listCategories) {
                        categorias.put(pos, c);
                        showCategories.add(c.getName());
                        pos++;
                    }

                    System.out.println("Inicio retorno categorias");
                    System.out.println(showCategories.toString());
                    System.out.println("Fim retorno categorias");
                    System.out.println(showCategories.toArray(new String[0]));
                    //String[] categories = {"Deporte", "Social", "Culturales", "Religion"};
                    builder.setItems(showCategories.toArray(new String[0]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            System.out.println("Ooooooooooi" + categorias.get(which).getId());
                            EventController.getInstance().getEventsFromCategory(categorias.get(which).getId()).addOnSuccessListener(events -> {
                                System.out.println("entrei aqui");
                                List<Event> tmpEvents = new ArrayList<>();
                                List<Event> tmpFeatured = new ArrayList<>();


                                for (Event e: events) {
                                    if(e.isFeatured()) tmpFeatured.add(e); else tmpEvents.add(e);
                                }

                                homeAdapter.setBothEvents(tmpFeatured, tmpEvents);
                            });
                        }
                    });

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }).addOnFailureListener(e -> Toaster.show(getContext(), e.getMessage()));



                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
