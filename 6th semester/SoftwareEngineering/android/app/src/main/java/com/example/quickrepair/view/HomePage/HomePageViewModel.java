package com.example.quickrepair.view.HomePage;

import androidx.lifecycle.ViewModel;

public class HomePageViewModel extends ViewModel {
    private HomePagePresenter presenter;

    public HomePageViewModel(){
        super();
        //create Presenter
        presenter = new HomePagePresenter();
    }

    public HomePagePresenter getPresenter() {
        return presenter;
    }
    
    @Override
    protected void onCleared() {
        super.onCleared();
        // avoid leaking activity for any reason
        presenter.clearView();
    }

}
