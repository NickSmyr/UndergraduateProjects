package com.example.quickrepair.view.Technician.RegisterTechnician;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.SpecialtyDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.domain.User;
import com.example.quickrepair.view.Base.BasePresenter;

import java.util.ArrayList;

public class TechnicianRegisterPresenter extends BasePresenter<TechnicianRegisterView>
{
    private CustomerDAO customerDAO;
    private TechnicianDAO technicianDAO;
    private SpecialtyDAO specialtyDAO;

    Technician technician;

    /**
     * Load the technician in case of edit.
     *
     * @param id The technician's id.
     */
    public void setTechnician(int id)
    {
        technician = technicianDAO.find(id);
    }

    /**
     * Try to register a technician with the given information.
     *
     * @param name The name of the technician.
     * @param surname The surname of the technician.
     * @param phoneNumber The phone number of the technician.
     * @param email The email of the technician.
     * @param AFM The AFM of the technician.
     * @param accountNumber The bank account number of the technician.
     * @param username The username of the technician.
     * @param password The password of the technician.
     * @param specialityID The speciality id of the technician.
     */
    void registerTechnician(String name, String surname, String phoneNumber, String email, String AFM, String accountNumber, String username, String password, int specialityID, int notificationMethodID)
    {
        if (technician == null || technician.getUid() == 0)
        {
            for (Technician technician : technicianDAO.findAll())
            {
                if (technician.getUsername().equals(username))
                {
                    view.showErrorMessage("Username already exist", "This username is already in use by another user.");
                    return;
                }
                else if (technician.getAFM().equals(AFM))
                {
                    view.showErrorMessage("Duplicate AFM", "Please make sure you do not have already an account and you have typed the correct AFM.");
                    return;
                }
            }

            for (Customer customer : customerDAO.findAll())
            {
                if (customer.getUsername().equals(username))
                {
                    view.showErrorMessage("Username already exist", "This username is already in use by another user.");
                    return;
                }
            }

            technician = new Technician();
        }

        try
        {
            technician.setTechnicianInfo(name, surname, phoneNumber, email, accountNumber, username);
            technician.setPassword(password);
            technician.setAFM(AFM);
        }
        catch (Exception e)
        {
            view.showErrorMessage("Invalid value", e.getMessage());
            return;
        }

        Specialty speciality = specialtyDAO.find(specialityID);

        if (speciality == null)
        {
            view.showErrorMessage("No speciality selected", "You must choose a speciality.");
            return;
        }

        if (technician.getSpecialty() == null || technician.getSpecialty().getUid() != specialityID)
        {
            technician.setSpecialty(speciality);
        }

        if (notificationMethodID < 1)
        {
            view.showErrorMessage("No notification methods selected", "You must choose a notification method.");
            return;
        }

        technician.setNotificationMethod(User.NotificationMethod.values()[notificationMethodID - 1]);

        if (technician.getUid() == 0)
        {
            technician.setUid(technicianDAO.nextId());
            technicianDAO.save(technician);
        }

        view.onSuccessfulRegister(technician.getUid());
    }

    /**
     * Set the customer DAO for the presenter.
     *
     * @param customerDAO The customer DAO.
     */
    void setCustomerDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * Set the technician DAO for the presenter.
     *
     * @param technicianDAO The technician DAO.
     */
    void setTechnicianDAO(TechnicianDAO technicianDAO)
    {
        this.technicianDAO = technicianDAO;
    }

    /**
     * Set the speciality DAO for the presenter.
     *
     * @param specialityDAO The speciality DAO.
     */
    void setSpecialtyDAO(SpecialtyDAO specialityDAO)
    {
        this.specialtyDAO = specialityDAO;
    }

    /**
     * Initialize the view.
     */
    void setUpDataSource()
    {
        ArrayList<String> specialities = new ArrayList<>();
        for (Specialty speciality : specialtyDAO.findAll())
        {
            specialities.add(speciality.getName().trim());
        }
        view.setSpecialityList(specialities, "Επιλέξτε ειδικότητα");

        ArrayList<String> notificationMethods = new ArrayList<>();
        for (User.NotificationMethod method : User.NotificationMethod.values())
        {
            notificationMethods.add(method.name().trim());
        }
        view.setNotificationList(notificationMethods, "Επιλέξτε τρόπο ενημέρωσης");

        if (technician != null)
        {
            view.setName(technician.getName());
            view.setSurname(technician.getSurname());
            view.setEmail(technician.getEmail());
            view.setPhoneNumber(technician.getPhoneNumber());
            view.setAccountNumber(technician.getBankAccount());
            view.setUsername(technician.getUsername());
            view.setPassword(technician.getPassword());
            view.setAFM(technician.getAFM());
            view.setSpecialityID(technician.getSpecialty().getUid());
            view.setNotificationMethodID(technician.getNotificationMethod().ordinal() + 1);
        }
    }
}
