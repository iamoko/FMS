package org.pahappa.systems.core.webControllers;

import org.sers.webutils.server.web.controllers.AbstractApplicationController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ApplicationController extends AbstractApplicationController {
    /**
     * handles the service login
     *
     * @param model
     * @return
     */
    @RequestMapping(value = { "/ServiceLogin" })
    public ModelAndView loginHandler(ModelMap model) {
        return new ModelAndView("loginView", model);
    }

    @RequestMapping(value = { "/Dashboard" })
    public ModelAndView dashboardHandler(ModelMap model) {
        return new ModelAndView("dashboardView", model);
    }

    @RequestMapping(value = { "/Requisitions" })
    public ModelAndView requisitionsHandler(ModelMap model) {
        return new ModelAndView("requisitionsView", model);
    }

    @RequestMapping(value = { "/CreateRequisition" })
    public ModelAndView createRequisitionHandler(ModelMap model) { return new ModelAndView("createRequisitionView", model); }

    @RequestMapping(value = { "/Accounts" })
    public ModelAndView accountsHandler(ModelMap model) {
        return new ModelAndView("accountsView", model);
    }

    @RequestMapping(value = { "/Calendar" })
    public ModelAndView calendarHandler(ModelMap model) { return new ModelAndView("Calendar", model); }


    @RequestMapping(value = { "/EmailTemplates" })
    public ModelAndView emailTemplatesHandler(ModelMap model) { return new ModelAndView("EmailTemplates", model); }

    @RequestMapping(value = { "/Deposits" })
    public ModelAndView depositsHandler(ModelMap model) { return new ModelAndView("Deposits", model); }

    @RequestMapping(value = { "/Withdraws" })
    public ModelAndView withdrawsHandler(ModelMap model) { return new ModelAndView("Withdraws", model); }

    @RequestMapping(value = { "/CreateAccount" })
    public ModelAndView createAccountHandler(ModelMap model) { return new ModelAndView("createAccountView", model); }

    @RequestMapping(value = { "/Users" })
    public ModelAndView userstHandler(ModelMap model) { return new ModelAndView("Users", model); }

    @RequestMapping(value = { "/UsersNonWorkingDays" })
    public ModelAndView usersnonWorkingDaystHandler(ModelMap model) { return new ModelAndView("UsersNonWorkingDays", model); }



}