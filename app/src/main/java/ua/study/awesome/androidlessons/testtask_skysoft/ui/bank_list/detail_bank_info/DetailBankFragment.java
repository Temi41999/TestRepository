package ua.study.awesome.androidlessons.testtask_skysoft.ui.bank_list.detail_bank_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import ua.study.awesome.androidlessons.testtask_skysoft.R;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.bank_list.data.Entity.BanksEntity;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.MainActivity;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.BaseFragment;

public class DetailBankFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = DetailBankFragment.class.getSimpleName();

    private int number;

    @BindView(R.id.detail_info)
    TextView tvDetail;

    @BindView(R.id.btn_sort_array)
    Button btn_sort_array;

    @BindView(R.id.btn_sort_list)
    Button btn_sort_list;

    public static DetailBankFragment newInstance(int number) {
        Bundle bundle = new Bundle();
        bundle.putInt("Number", number);

        DetailBankFragment detailBankFragment = new DetailBankFragment();
        detailBankFragment.setArguments(bundle);

        return detailBankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getInt("Number");
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        showDetails();

    }

    @Override
    public int getLayoutId() {
        return R.layout.detail_bank_fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Objects.requireNonNull(getActivity()).onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void init() {

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle("Detail Info");

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    void showDetails() {
        Realm mRealm = Realm.getDefaultInstance();
        final RealmResults<BanksEntity> result = mRealm.where(BanksEntity.class).findAll();

        int s = result.size();
        tvDetail.setText("Тип " + result.get(number).getType() + "\n"
                + result.get(number).getFullAddressEn() + "\n"
                + result.get(number).getFullAddressRu() + "\n"
                + result.get(number).getFullAddressUa() + "\n"
                + result.get(number).getPlaceRu() + "\n"
                + result.get(number).getPlaceUa() + "\n"
                + "Широта - : " + result.get(number).getLatitude() + "\n"
                + "Довгота - : " + result.get(number).getLongitude() + "\n"
                + "Робочий час : " + "\n" + result.get(number).getTw().getMon() + "\n"
                + "Понеділок: " + result.get(number).getTw().getTue() + "\n"
                + "Вівторок: " + result.get(number).getTw().getWed() + "\n"
                + "Середа: " + result.get(number).getTw().getThu() + "\n"
                + "Четвер: " + result.get(number).getTw().getWed() + "\n"
                + "П'ятниця: " + result.get(number).getTw().getFri() + "\n"
                + "Субота: " + result.get(number).getTw().getSat() + "\n"
                + "Неділя: " + result.get(number).getTw().getSun() + "\n"
                + "Свята: " + result.get(number).getTw().getHol());
    }

    private static final String TAG = "myLogs";

    @OnClick(R.id.btn_sort_array)
    void ClickToInsertSortArray() {

        int[] array = new int[10];
        int temp, j;
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        Log.d(TAG, "Sort array: " + array[0] + " "
                + array[1] + " " + array[2] + " " + array[3] + " "
                + array[4] + " " + array[5] + " " + array[6] + " "
                + array[7] + " " + array[8] + " " + array[9] + " buffer: ");

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                temp = array[i + 1];
                array[i + 1] = array[i];
                array[i] = temp;
                j = i;
                while (j > 0 && temp < array[j - 1]) {
                    array[j] = array[j - 1];
                    j--;
                }
                array[j] = temp;

                Log.d(TAG, "Sort array: " + array[0] + " "
                        + array[1] + " " + array[2] + " " + array[3] + " "
                        + array[4] + " " + array[5] + " " + array[6] + " "
                        + array[7] + " " + array[8] + " " + array[9] + " buffer: " + temp);

            }
        }
    }

    @OnClick(R.id.btn_sort_list)
    void ClickToInsertSortList() {

        ArrayList<Integer> list = new ArrayList<>();
        int temp, j;

        for (int i = 0; i < (10 + (int) (Math.random() * 15)); i++) {
            list.add((int) (Math.random() * 100));
        }

        Log.d(TAG, "Sort array: " + list + " buffer: ");

        for (int i = 0; i < (list.size() - 1); i++) {
            if (list.get(i) > list.get(i + 1)) {
                temp = list.get(i + 1);
                list.set((i + 1), list.get(i));
                list.set(i, temp);
                j = i;
                while (j > 0 && temp < list.get(j - 1)) {

                    list.set(j, list.get(j - 1));
                    j--;
                }
                list.set(j, temp);

                Log.d(TAG, "Sort list: " + list + " buffer: " + temp);
            }
        }
    }
}