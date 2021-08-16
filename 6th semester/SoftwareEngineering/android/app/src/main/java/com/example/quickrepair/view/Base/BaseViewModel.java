package com.example.quickrepair.view.Base;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<P extends BasePresenter> extends ViewModel
{
    protected P presenter;

    /**
     * Default constructor.
     */
    protected BaseViewModel()
    {
        presenter = createPresenter();
    }

    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    protected abstract P createPresenter();

    /**
     * Get the presenter associated with the view model.
     *
     * @return The presenter associated with the view model.
     */
    public P getPresenter()
    {
        return presenter;
    }

    /**
     * Clear any references to other components.
     */
    @Override
    protected void onCleared()
    {
        super.onCleared();

        presenter.clearView();
    }
}
