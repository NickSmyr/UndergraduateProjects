package com.example.quickrepair.view.Technician.AddEditArea;

import com.example.quickrepair.view.Base.BaseView;

import java.util.List;

public interface AddEditAreaView extends BaseView
{
    /**
     * Populate the list holding all available areas.
     *
     * @param areaList The list of areas.
     * @param defaultName A default area placeholder.
     */
    void setAreaList(List<String> areaList, String defaultName);

    /**
     * Populate the list holding the technician's areas.
     *
     * @param selectedArea The list of the technician's areas.
     */
    void setSelectedArea(List<String> selectedArea);
}
