package omarbizreh.com.trainingapp;

import android.databinding.ObservableArrayList;

import java.util.ArrayList;

import omarbizreh.com.trainingapp.DataModels.UserModel;

/**
 * Created by Omar on 1/16/2016.
 */
public class InstanceReferences {
    private static ObservableArrayList<UserModel> mUsers;

    public static ObservableArrayList<UserModel> getUsers() {
        if (InstanceReferences.mUsers == null) {
            InstanceReferences.mUsers = new ObservableArrayList<UserModel>();

            UserModel userModel = new UserModel("Omar", "111-111111");
            UserModel userModel1 = new UserModel("John", "222-222222");
            InstanceReferences.mUsers.add(userModel);
            InstanceReferences.mUsers.add(userModel1);
        }
        return InstanceReferences.mUsers;
    }
}
