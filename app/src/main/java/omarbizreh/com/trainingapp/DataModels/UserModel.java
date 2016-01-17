package omarbizreh.com.trainingapp.DataModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import omarbizreh.com.trainingapp.BR;

/**
 * Created by Omar on 1/16/2016.
 */
public class UserModel extends BaseObservable {

    private String DisplayName;
    private String PhoneNumber;

    public UserModel(String DisplayName, String PhoneNumber) {
        this.setDisplayName(DisplayName);
        this.setPhoneNumber(PhoneNumber);
    }

    @Bindable
    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        this.DisplayName = displayName;
        notifyPropertyChanged(BR.displayName);
    }

    @Bindable
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }
}
