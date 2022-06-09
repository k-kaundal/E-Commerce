package com.whlinks.e_commerce.ui.fragments.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.adapter.ItemAdapter;
import com.whlinks.e_commerce.adapter.LatestItemAdapter;
import com.whlinks.e_commerce.adapter.The_Slide_items_Pager_Adapter;
import com.whlinks.e_commerce.adapter.TopItemAdapter;
import com.whlinks.e_commerce.models.Item;
import com.whlinks.e_commerce.models.The_Slide_Items_Model_Class;
import com.whlinks.e_commerce.service.CommonDBCall;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerViewlatest, recyclerViewall,recyclerViewTop;
    CommonDBCall commonDBCall = new CommonDBCall();
    List<Item> itemList1;
    List<Item> itemList2;
    List<Item> topItem;
    List<DocumentSnapshot> itemList;
    List<DocumentSnapshot> itemList3;
    List<DocumentSnapshot> topItemDoc;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Item item;
    private List<The_Slide_Items_Model_Class> listItems;
    private ViewPager page;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {
            try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem() < listItems.size() - 1) {
                        page.setCurrentItem(page.getCurrentItem() + 1);
                    } else
                        page.setCurrentItem(0);
                }
            });
        }catch (Exception e){
                System.out.println(e);
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerViewlatest = view.findViewById(R.id.latestitemrecyc);
        recyclerViewall = view.findViewById(R.id.allitemsrecyc);
        recyclerViewTop = view.findViewById(R.id.recyclerViewTop);
        page = view.findViewById(R.id.my_pager) ;
        tabLayout = view.findViewById(R.id.my_tablayout);


        listItems = new ArrayList<>() ;
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item1,"Slider 1 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item2,"Slider 2 Title"));
//        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item3,"Slider 3 Title"));




        The_Slide_items_Pager_Adapter itemsPager_adapter = new The_Slide_items_Pager_Adapter(getActivity(), listItems);
        page.setAdapter(itemsPager_adapter);
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),2000,3000);
        tabLayout.setupWithViewPager(page,true);



        firebaseFirestore.collection("TopItems").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
//                    System.out.println(queryDocumentSnapshots.getDocuments());
                topItemDoc = queryDocumentSnapshots.getDocuments();
//                System.out.println(itemList);
//                itemList1 = new ArrayList<>();
//                itemList1.add(new Item("Demo","Demo","Demo","Demo"));
                topItem = new ArrayList<>();
                for (int i = 0; i < task.getResult().size(); i++) {
//                    new Item(itemList[i].getData());
                    System.out.println(topItemDoc.get(i).getData().get("name"));

                    topItem.add(new Item(topItemDoc.get(i).getData().get("name").toString(), topItemDoc.get(i).getData().get("descripton").toString(), topItemDoc.get(i).getData().get("price").toString(), topItemDoc.get(i).getData().get("image").toString(), topItemDoc.get(i).getData().get("doc_id").toString()));
//
//                   itemList1.add(itemList.get(i).getData());
                    System.out.println(topItemDoc.get(i).getData().get("name"));
                }
//                recyclerViewTop.setHasFixedSize(true);
                recyclerViewTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                if (topItem == null) {
                    System.out.println("No data");
                } else {
                    recyclerViewTop.setAdapter(new TopItemAdapter(topItem,getContext()));
                }
//                recyclerViewlatest.setAdapter(new ItemAdapter(itemList1, getContext()));

            }
        });

//        itemList1.add(new Item("Demo","Demo","Demo","Demo"));
        firebaseFirestore.collection("LatestItems").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
//                    System.out.println(queryDocumentSnapshots.getDocuments());
                itemList3 = queryDocumentSnapshots.getDocuments();
//                System.out.println(itemList);
//                itemList1 = new ArrayList<>();
//                itemList1.add(new Item("Demo","Demo","Demo","Demo"));
                itemList2 = new ArrayList<>();
                for (int i = 0; i < task.getResult().size(); i++) {
//                    new Item(itemList[i].getData());
                    System.out.println(itemList3.get(i).getData().get("name"));

                    itemList2.add(new Item(itemList3.get(i).getData().get("name").toString(), itemList3.get(i).getData().get("descripton").toString(), itemList3.get(i).getData().get("price").toString(), itemList3.get(i).getData().get("image").toString(), itemList3.get(i).getData().get("doc_id").toString()));
//
//                   itemList1.add(itemList.get(i).getData());
                    System.out.println(itemList3.get(i).getData().get("name"));
                }
                recyclerViewlatest.setHasFixedSize(true);
                recyclerViewlatest.setLayoutManager(new LinearLayoutManager(getContext()));
                if (itemList2 == null) {
                    System.out.println("No data");
                } else {
                    recyclerViewlatest.setAdapter(new LatestItemAdapter(itemList2, getContext()));
                }
//                recyclerViewlatest.setAdapter(new ItemAdapter(itemList1, getContext()));

            }
        });


        firebaseFirestore.collection("Items").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
//                    System.out.println(queryDocumentSnapshots.getDocuments());
                itemList = queryDocumentSnapshots.getDocuments();
//                System.out.println(itemList);
//                itemList1 = new ArrayList<>();
//                itemList1.add(new Item("Demo","Demo","Demo","Demo"));
                itemList1 = new ArrayList<>();
                for (int i = 0; i < task.getResult().size(); i++) {
//                    new Item(itemList[i].getData());
                    System.out.println(itemList.get(i).getData().get("name"));

                    itemList1.add(new Item(itemList.get(i).getData().get("name").toString(), itemList.get(i).getData().get("descripton").toString(), itemList.get(i).getData().get("price").toString(), itemList.get(i).getData().get("image").toString(), itemList.get(i).getData().get("doc_id").toString()));
//
//                   itemList1.add(itemList.get(i).getData());
                    System.out.println(itemList.get(i).getData().get("name"));
                }
                recyclerViewall.setHasFixedSize(true);
                recyclerViewall.setLayoutManager(new LinearLayoutManager(getContext()));

                if (itemList1 == null) {
                    System.out.println("No data");
                } else {
                    recyclerViewall.setAdapter(new ItemAdapter(itemList1, getContext()));
                }
//                recyclerViewall.setAdapter(new ItemAdapter(itemList1, getContext()));

            }
        });


        return view;
    }
}