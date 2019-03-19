package ua.study.awesome.androidlessons.testtask_skysoft.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.study.awesome.androidlessons.testtask_skysoft.R;
import ua.study.awesome.androidlessons.testtask_skysoft.data.response.BankList;
import ua.study.awesome.androidlessons.testtask_skysoft.presenters.BankPresenter;
import ua.study.awesome.androidlessons.testtask_skysoft.presenters.PresenterInterface;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.MainActivity;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.adapter.BankAdapter;

public class FragmentBank extends Fragment {

    private PresenterInterface presenter;

    private String title;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_banks)
    RecyclerView recyclerBanks;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public static FragmentBank newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);

        FragmentBank fragmentBank = new FragmentBank();
        fragmentBank.setArguments(bundle);

        return fragmentBank;
    }

    private void readBundle(Bundle bundle){
        if(bundle != null){
            title = bundle.getString("TITLE");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bank,container, false);
        Unbinder unbinder = ButterKnife.bind(this, v);

        init();
        presenter.loadBank();

        return v;
    }

    public void init(){

        presenter = new BankPresenter();
        presenter.attachView(this);

        recyclerBanks.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.loadBank();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        readBundle(getArguments());
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(String.format("%s", title));
    }

    public void onBanksLoaded(BankList bankList){
        BankAdapter adapter = new BankAdapter(getContext());
        adapter.setBanks(bankList.getBankList());
        recyclerBanks.setAdapter(adapter);
    }

    public void makeItInvisibleProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showToast(){
        Toast.makeText(getContext(), "loading error", Toast.LENGTH_LONG ).show();
    }
}