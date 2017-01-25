package eventcoordinator2017.myevent.ui.register;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import eventcoordinator2017.myevent.R;
import eventcoordinator2017.myevent.databinding.ActivityRegisterBinding;

/**
 * Created by Mark Jansen Calderon on 1/11/2017.
 */

public class RegisterActivity extends MvpViewStateActivity<RegisterView, RegisterPresenter> implements RegisterView, TextWatcher {
    private ActivityRegisterBinding binding;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setRetainInstance(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setView(getMvpView());
        binding.btnSubmit.setAlpha(.5f);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Start of MvpViewStateActivity
     ***/

    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @NonNull
    @Override
    public ViewState<RegisterView> createViewState() {
        setRetainInstance(true);
        return new RegisterViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        initializeViewStateValues();

    }


    /***
     * End of MvpViewStateActivity
     ***/


    @Override
    public void onSubmit() {
        presenter.register(
                binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString(),
                binding.etConfirmPass.getText().toString(),
                binding.etFName.getText().toString(),
                binding.etLName.getText().toString(),
                binding.etBday.getText().toString(),
                binding.etContact.getText().toString(),
                binding.etAddress.getText().toString(),
                binding.etCity.getText().toString(),
                binding.etZipcode.getText().toString(),
                binding.etCountry.getText().toString()
        );
    }

    @Override
    public void showAlert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEditTextValue(String email, String password, String confirmPassword, String firstName, String lastName, String birthday, String contact, String address, String city, String zipCode, String country) {
        binding.etEmail.setText(email);
        binding.etPassword.setText(password);
        binding.etConfirmPass.setText(confirmPassword);
        binding.etFName.setText(firstName);
        binding.etLName.setText(lastName);
        binding.etBday.setText(birthday);
        binding.etContact.setText(contact);
        binding.etAddress.setText(address);
        binding.etCity.setText(city);
        binding.etZipcode.setText(zipCode);
        binding.etCountry.setText(country);
    }

    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Signing up...");
        }
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog != null) progressDialog.dismiss();
    }


    @Override
    public void onRegistrationSuccess() {
        new AlertDialog.Builder(this)
                .setTitle("Register Successful")
                .setMessage("Go Back to Login Activity")
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RegisterActivity.this.finish();
                    }
                })
                .show();
    }


    //checked
    @Override
    public void onChecked() {
        presenter.onChecked(binding.checkBoxAgree.isChecked());
    }


    @Override
    public void onCheckedIsTrue() {
        binding.btnSubmit.setEnabled(true);
        binding.btnSubmit.setAlpha(1);
    }

    @Override
    public void onCheckedIsFalse() {
        binding.btnSubmit.setEnabled(false);
        binding.btnSubmit.setAlpha(.5f);

    }

    @Override
    public void onBirthdayClicked() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                binding.etBday.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    /***
     * End of RegisterView
     ***/

    /***
     * Start of TextWatcher
     ***/

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        initializeViewStateValues();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /***
     * End of TextWatcher
     ***/

    private void initializeViewStateValues() {
        RegisterViewState registerViewState = (RegisterViewState) getViewState();
        registerViewState.setEmail(binding.etEmail.getText().toString());
        registerViewState.setPassword(binding.etPassword.getText().toString());
        registerViewState.setConfirmPassword(binding.etConfirmPass.getText().toString());
        registerViewState.setFirstName(binding.etFName.getText().toString());
        registerViewState.setLastName(binding.etLName.getText().toString());
        registerViewState.setContact(binding.etContact.getText().toString());
        registerViewState.setBirthday(binding.etContact.getText().toString());
        registerViewState.setAddress(binding.etAddress.getText().toString());
        registerViewState.setCity(binding.etCity.getText().toString());
        registerViewState.setZipCode(binding.etZipcode.getText().toString());
        registerViewState.setCountry(binding.etCountry.getText().toString());

    }

}
