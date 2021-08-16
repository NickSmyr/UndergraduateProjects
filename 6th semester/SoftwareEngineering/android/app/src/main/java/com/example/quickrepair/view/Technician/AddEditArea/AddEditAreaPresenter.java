package com.example.quickrepair.view.Technician.AddEditArea;

import com.example.quickrepair.dao.AreaDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.view.Base.BasePresenter;

import java.util.ArrayList;

public class AddEditAreaPresenter extends BasePresenter<AddEditAreaView>
{
    private TechnicianDAO technicianDAO;
    private AreaDAO areaDAO;

    private Technician technician;
    private ArrayList<String> areas;

    /**
     * Set the technician DAO for the presenter.
     *
     * @param technicianDAO The technician DAO.
     */
    public void setTechnicianDAO(TechnicianDAO technicianDAO)
    {
        this.technicianDAO = technicianDAO;
    }

    /**
     * Set the area DAO for the presenter.
     *
     * @param areaDAO The area DAO.
     */
    void setAreaDAO(AreaDAO areaDAO)
    {
        this.areaDAO = areaDAO;
    }

    /**
     * Load the technician in case of edit.
     *
     * @param id The technician's id.
     */
    public void setTechnician(Integer id)
    {
        technician = technicianDAO.find(id);
    }

    /**
     * Initialize the view.
     */
    public void setUpDataSource()
    {
        view.setAreaList(new ArrayList<>(areaDAO.getAreas()), "Επιλέξτε περιοχή");

        areas = new ArrayList<>(technician.getAreas());
        view.setSelectedArea(areas);
    }

    /**
     * Add a new area to the technician.
     *
     * @param areaID the area id.
     */
    void addArea(Integer areaID)
    {
        if (areaID == 0)
        {
            view.showErrorMessage("No area selected", "You have to add a valid area.");
            return;
        }

        String area = areaDAO.getAreas().get(areaID - 1);

        if (technician.getAreas().contains(area))
        {
            view.showErrorMessage("Area already exist", "This area is already selected.");
            return;
        }
        else
        {
            technician.getAreas().add(area);
        }

        areas.add(area);
        view.setSelectedArea(areas);
    }

    /**
     * Remove an area from the technician.
     *
     * @param areaID The area's id to be removed.
     */
    void removeArea(int areaID)
    {
        technician.getAreas().remove(areas.remove(areaID));
        view.setSelectedArea(areas);
    }
}
