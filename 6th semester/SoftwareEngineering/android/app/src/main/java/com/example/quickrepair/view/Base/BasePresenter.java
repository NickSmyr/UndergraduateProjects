package com.example.quickrepair.view.Base;

public abstract class BasePresenter<V extends BaseView>
{
    protected V view;

    /**
     * Set the view for the presenter.
     *
     * @param view The view.
     */
    public void setView(V view)
    {
        this.view = view;
    }

    /**
     * Clear the view of the presenter.
     */
    public void clearView()
    {
        this.view = null;
    }
}
