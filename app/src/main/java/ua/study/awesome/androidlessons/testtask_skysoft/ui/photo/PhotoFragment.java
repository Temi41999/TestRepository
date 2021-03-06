package ua.study.awesome.androidlessons.testtask_skysoft.ui.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import ua.study.awesome.androidlessons.testtask_skysoft.BuildConfig;
import ua.study.awesome.androidlessons.testtask_skysoft.R;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.BaseFragment;
import ua.study.awesome.androidlessons.testtask_skysoft.ui.MainActivity;

import static android.app.Activity.RESULT_OK;

public class PhotoFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = PhotoFragment.class.getSimpleName();

    File directory;
    File photoDirectory;

    @BindView(R.id.btn_Photo)
    Button btnPhoto;

    @BindView(R.id.iv_Photo)
    ImageView ivPhoto;

    private final int TYPE_PHOTO = 1;

    private final int REQUEST_CODE_PHOTO = 1;

    final String TAG = "myLogs";

    private static final String ARG_TITLE = "TITLE";

    private String title;

    public static PhotoFragment getInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);

        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);

        return photoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createDirectory();
        init();
    }

    private void init(){
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu_white);

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle(String.format("%s", title));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @OnClick(R.id.btn_Photo)
    public void onClickPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                } else {
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity) Objects.requireNonNull(getActivity())).drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Uri generateFileUri(int type) {
        Uri outputFileUri = null;
        File file = null;
        if (type == TYPE_PHOTO) {
            file = new File(photoDirectory, "photo_"
                    + System.currentTimeMillis() + ".jpg");
            outputFileUri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()), BuildConfig.APPLICATION_ID + ".provider", file);
        }
        return outputFileUri;
    }

    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "TestTask_SkySoft");
        if (!directory.exists())
            directory.mkdirs();

        photoDirectory = new File(directory, "Photo");
        if (!photoDirectory.exists())
            photoDirectory.mkdirs();
    }
}