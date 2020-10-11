package org.pahappa.systems.views.user;


import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.impl.CustomizedMailService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.security.service.impl.CustomSessionProvider;
import org.sers.webutils.server.core.service.RoleService;
import org.sers.webutils.server.core.service.SetupService;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.springframework.beans.BeansException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@ManagedBean(name = "userForm")
@SessionScoped
@ViewPath(path = HyperLinks.USERFORM)
public class UserForm extends WebFormView<User, UserForm, UsersView> {

    UserService userService;
    RoleService roleService;
    String repeatPassword;

    AccountService accountService;


    private Double entitledAllowance;
    Account account;

    List<Role> roles;
    private List<Gender> listOfGenders;
    private List<Country> listOfCountries;
    List<Role> selectedRoles;
    private Boolean updating = true;
    private List<Role> currentUserRoles;

    @Override
    public void persist() {
        System.out.println("creating user");
        try {
            if (this.updating) if (!super.model.getClearTextPassword().equals(this.repeatPassword))
                throw new ValidationFailedException("Passwords dont match");
            super.model.setRoles(new HashSet<>(this.currentUserRoles));
            super.getModel().addRole(roleService.getRoleByName(Role.DEFAULT_WEB_ACCESS_ROLE));
            super.model = this.userService.saveUser(super.model);
            if (this.updating) {
                System.out.println(this.account);
                StoreLogs.Log("User account for " + this.account.getUser().getFullName() + " has been created");
                this.account.setEntitledAllowance(this.entitledAllowance);
                this.account.setUser(super.model);
                this.accountService.save(this.account);
				MessageComposer.Compose("Action Successful", "User Account Updated");
            } else {
                CustomizedMailService customizedMailService = new CustomizedMailService(super.model, "Account Credited", "Your User Account has been Created, Please Login to Access the system");
                customizedMailService.start();
				MessageComposer.Compose("Action Successful", "User Account Created");
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageComposer.Failed("Action failed", e.getMessage());
        }

    }

    @Override
    public void beanInit() {
        resetModal();
        this.userService = ApplicationContextProvider.getBean(UserService.class);
        this.accountService = ApplicationContextProvider.getBean(AccountService.class);
        this.roleService = ApplicationContextProvider.getBean(RoleService.class);
        this.listOfGenders = Arrays.asList(Gender.values());
        this.listOfCountries = ApplicationContextProvider.getApplicationContext().getBean(SetupService.class)
                .getAllCountries();

        this.updating = true;
        this.repeatPassword = null;
        this.roles = roleService.getRoles();
    }

    @Override
    public void pageLoadInit() {
    }


    public void resetModal() {
        super.resetModal();
        super.model = new User();
        account = new Account();
        this.currentUserRoles = new ArrayList<>();
    }

    public void setFormProperties() {
        super.setFormProperties();
        this.account = accountService.getAccountByUser(super.model);
        this.currentUserRoles = new ArrayList<>(super.getModel().getRoles());
    }

    public void hideCreds(User user) {
        this.updating = user.getUsername() == null;
    }

    public Boolean getUpdating() {
        return updating;
    }

    public void setUpdating(Boolean updating) {
        this.updating = updating;
    }

    @Override
    public String getViewUrl() {
        return null;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<Role> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void deleteUser(User user) throws BeansException, OperationFailedException {
        ApplicationContextProvider.getApplicationContext().getBean(UserService.class).deleteUser(user);
    }

    /**
     * @return the entitledAllowance
     */
    public Double getEntitledAllowance() {
        return entitledAllowance;
    }

    /**
     * @param entitledAllowance the entitledAllowance to set
     */
    public void setEntitledAllowance(Double entitledAllowance) {
        this.entitledAllowance = entitledAllowance;
    }

    /**
     * @return the currentUserRoles
     */
    public List<Role> getCurrentUserRoles() {
        return currentUserRoles;
    }


    /**
     * @param currentUserRoles the currentUserRoles to set
     */
    public void setCurrentUserRoles(List<Role> currentUserRoles) {
        this.currentUserRoles = currentUserRoles;
    }

    /**
     * @return the listOfGenders
     */
    public List<Gender> getListOfGenders() {
        return listOfGenders;
    }

    /**
     * @param listOfGenders the listOfGenders to set
     */
    public void setListOfGenders(List<Gender> listOfGenders) {
        this.listOfGenders = listOfGenders;
    }

    /**
     * @return the listOfCountries
     */
    public List<Country> getListOfCountries() {
        return listOfCountries;
    }

    /**
     * @param listOfCountries the listOfCountries to set
     */
    public void setListOfCountries(List<Country> listOfCountries) {
        this.listOfCountries = listOfCountries;
    }
}