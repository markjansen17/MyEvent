package eventcoordinator2017.myevent.ui.profile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import eventcoordinator2017.myevent.R;
import eventcoordinator2017.myevent.app.Constants;
import eventcoordinator2017.myevent.databinding.ActivityProfileBinding;
import eventcoordinator2017.myevent.model.data.User;
import eventcoordinator2017.myevent.utils.PermissionsActivity;
import eventcoordinator2017.myevent.utils.PermissionsChecker;
import io.realm.Realm;

/**
 * Created by Mark Jansen Calderon on 1/11/2017.
 */

public class ProfileActivity extends MvpActivity<ProfileView, ProfilePresenter> implements ProfileView {

    private ActivityProfileBinding binding;
    private Realm realm;
    private int PICK_IMAGE_REQUEST = 1;
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    PermissionsChecker checker;
    private String TAG = ProfileActivity.class.getSimpleName();
    private File userImage;
    private ProgressDialog progressDialog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //setRetainInstance(true);
        realm = Realm.getDefaultInstance();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setView(getMvpView());
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = realm.where(User.class).findFirst();
        binding.setUser(user);

        presenter.onStart();

        /**
         * Permission Checker Initialized
         */
        checker = new PermissionsChecker(this);

        Glide.with(this)
                .load(Constants.URL_IMAGE + user.getImage())
                .dontAnimate()
                .placeholder(R.drawable.ic_gallery)
                .error(R.drawable.ic_gallery)
                .into(binding.userImage);

    }

    /***
     * Start of MvpViewStateActivity
     ***/

    @NonNull
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }


    /***
     * End of MvpViewStateActivity
     ***/


    /***
     * Start of ProfileView
     ***/
    @Override
    public void onEdit() {
        if (userImage != null) {
            presenter.updateUserWithImage(userImage, user.getUserId() + "",
                    binding.firstName.getText().toString(),
                    binding.lastName.getText().toString(),
                    binding.contact.getText().toString(),
                    binding.birthday.getText().toString(),
                    binding.address.getText().toString());
        } else {
            presenter.updateUser(user.getUserId() + "",
                    binding.firstName.getText().toString(),
                    binding.lastName.getText().toString(),
                    binding.contact.getText().toString(),
                    binding.birthday.getText().toString(),
                    binding.address.getText().toString());
        }
    }

    @Override
    public void showAlert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void finishAct() {
        finish();
        showAlert("Profile Updated");
    }

    @Override
    public void onBirthdayClicked() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                binding.birthday.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onPhotoClicked() {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of file system options.
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    /***
     * End of ProfileView
     ***/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            cursor.close();
            userImage = new File(s);
            // eventUri = eventImage.getPath();
            Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ic_gallery)
                    .error(R.drawable.ic_gallery)
                    .into(binding.userImage);

        } else {

            Log.d(TAG, "Selecting Image Error");
        }
    }


    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:
                onEdit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
