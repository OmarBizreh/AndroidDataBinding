package omarbizreh.com.trainingapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import omarbizreh.com.trainingapp.Adapters.UsersAdapter;
import omarbizreh.com.trainingapp.DataModels.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private RecyclerView mUsersRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    public UsersAdapter mAdapter;


    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.InitializeComponent();
    }

    private void InitializeComponent() {
        this.mUsersRecycler = (RecyclerView) getView().findViewById(R.id.users_recycler);
        this.mLayoutManager = new LinearLayoutManager(getContext());
        this.mAdapter = new UsersAdapter();

        this.mUsersRecycler.setLayoutManager(this.mLayoutManager);
        this.mUsersRecycler.setAdapter(this.mAdapter);
    }
}
