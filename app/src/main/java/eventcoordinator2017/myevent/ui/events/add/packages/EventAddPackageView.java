package eventcoordinator2017.myevent.ui.events.add.packages;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import eventcoordinator2017.myevent.model.data.Package;

/**
 * Created by Mark Jansen Calderon on 1/26/2017.
 */

public interface EventAddPackageView extends MvpView{

    void showAlert(String message);

    void onPackageClicked(Package aPackage);

    void onPackageAvail(Package aPackage);

    void askForBudget(String budget);

    void clearBudget();

    void onPhotoClicked();

    void onDateClicked(int id);

    void onNext();

    void startLoading();

    void stopLoading();

    void setPackages(List<Package> packageList);
}