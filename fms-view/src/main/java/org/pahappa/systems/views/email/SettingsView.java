package org.pahappa.systems.views.email;

import org.pahappa.systems.core.services.RequisitionService;
import org.pahappa.systems.core.services.TemplateService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.pahappa.systems.models.Template;
import org.pahappa.systems.models.TemplateType;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean(name = "settingsView")
@SessionScoped
public class SettingsView {

    private boolean accountCreationTempPreview;
    private String accountCreationTempValue;
    private String accountCreationTempPreviewValue;

    private boolean reqApprovalTempPreview;
    private String reqApprovalTempValue;
    private String reqApprovalTempPreviewValue;

    private boolean tempPreview;
    private String tempValue;
    private String tempPreviewValue;
    private List<Template> templateList;
    private TemplateType[] templateTypes;
    private Template selectedTemplate;
    private TemplateType selectedTemplateType;

    private boolean reqRejectionTempPreview;
    private String reqRejectionTempValue;
    private String reqRejectionTempPreviewValue;

    private TemplateService templateService;
    private RequisitionService requisitionService;


    private Account testAccount;
    private User testUser;
    private Requisition testRequisition;

    @PostConstruct
    public void init(){
        this.requisitionService = ApplicationContextProvider.getBean(RequisitionService.class);

        
        this.templateTypes = TemplateType.values();

       

        this.accountCreationTempPreview = false;
        this.accountCreationTempPreviewValue = "";

        this.reqApprovalTempPreview = false;
        this.reqApprovalTempPreviewValue = "";

        this.reqRejectionTempPreview = false;
        this.reqRejectionTempPreviewValue = "";

        this.templateService = ApplicationContextProvider.getBean(TemplateService.class);
        this.templateList = this.templateService.getTemplates();

        this.populateTemplates();

    }


    public void loadTemplate(){
        if(this.selectedTemplateType != null){
            Template template = this.templateService.getTemplate(this.selectedTemplateType);

            if(template == null)
                this.tempValue = "";
            else
                this.tempValue = template.getValue();

            this.tempPreviewValue = "";
            this.tempPreview = false;
        }
        else
            System.out.println("didn't select a template");
    }

    private void populateTemplates(){
        for(Template template: this.templateList){
            switch (template.getTemplate()){
                case ACCOUNT_CREATION:
                    this.accountCreationTempValue = template.getValue();
                    break;
                case APPROVED:
                    this.reqApprovalTempValue = template.getValue();
                    break;
                case REJECTED:
                    this.reqRejectionTempValue = template.getValue();
                    break;
                default:
                    break;
            }
        }
    }

   




    public void showTempPreview(){
        this.setTempPreview(true);

        this.setTempPreviewValue(this.templateService.populateMessage(this.tempValue, this.testAccount, this.testRequisition, "12323QWERT"));

    }

    public void saveTemp(){

        Template template = new Template();
        template.setTemplate(this.selectedTemplateType);
        template.setValue(this.tempValue);

        template = this.templateService.updateTemplate(template);
        this.tempValue = template.getValue();


    }



    public void showAccountTempPreview(){
        this.setAccountCreationTempPreview(true);

        this.setAccountCreationTempPreviewValue(this.templateService.populateMessage(this.accountCreationTempValue, this.testAccount, null, null));

    }




    public void saveAccountTemp(){

        Template template = new Template();
        template.setTemplate(TemplateType.ACCOUNT_CREATION);
        template.setValue(this.accountCreationTempValue);

        template = this.templateService.updateTemplate(template);
        this.accountCreationTempValue = template.getValue();

    }



    public void showReqAppTempPreview(){

        this.setReqApprovalTempPreview(true);
//        this.setReqApprovalTempPreviewValue(this.reqApprovalTempValue);
        this.testRequisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        this.setReqApprovalTempPreviewValue(this.templateService.populateMessage(this.reqApprovalTempValue, null, this.testRequisition, null));
    }

    public void saveReqAppTemp(){

        Template template = new Template();
        template.setTemplate(TemplateType.APPROVED);
        template.setValue(this.reqApprovalTempValue);

        template = this.templateService.updateTemplate(template);
        this.reqApprovalTempValue = template.getValue();

    }


    public void showReqRejectTempPreview(){
        this.setReqRejectionTempPreview(true);
//        this.setReqRejectionTempPreviewValue(this.reqRejectionTempValue);
        this.testRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        this.setReqRejectionTempPreviewValue(this.templateService.populateMessage(this.reqRejectionTempValue, null, this.testRequisition, null));
    }

    public void saveReqRejectTemp(){

        Template template = new Template();
        template.setTemplate(TemplateType.REJECTED);
        template.setValue(this.reqRejectionTempValue);

        template = this.templateService.updateTemplate(template);
        this.reqRejectionTempValue = template.getValue();

    }


    // Getters


    public TemplateType getSelectedTemplateType() {
        return selectedTemplateType;
    }

    public String getAccountCreationTempPreviewValue() {
        return accountCreationTempPreviewValue;
    }

    public boolean isAccountCreationTempPreview() {
        return accountCreationTempPreview;
    }

    public String getAccountCreationTempValue() {
        return accountCreationTempValue;
    }

    public boolean isReqApprovalTempPreview() {
        return reqApprovalTempPreview;
    }

    public String getReqApprovalTempValue() {
        return reqApprovalTempValue;
    }

    public String getReqApprovalTempPreviewValue() {
        return reqApprovalTempPreviewValue;
    }

    public boolean isReqRejectionTempPreview() {
        return reqRejectionTempPreview;
    }

    public String getReqRejectionTempValue() {
        return reqRejectionTempValue;
    }

    public String getReqRejectionTempPreviewValue() {
        return reqRejectionTempPreviewValue;
    }

 

    public boolean isTempPreview() {
        return tempPreview;
    }

    public String getTempValue() {
        return tempValue;
    }

    public String getTempPreviewValue() {
        return tempPreviewValue;
    }

    public List<Template> getTemplateList() {
        return templateList;
    }

    public Template getSelectedTemplate() {
        return selectedTemplate;
    }

    public TemplateType[] getTemplateTypes() {
        return templateTypes;
    }

// Setters


    public void setSelectedTemplateType(TemplateType selectedTemplateType) {
        this.selectedTemplateType = selectedTemplateType;
    }

    public void setSelectedTemplate(Template selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

 

    public void setAccountCreationTempValue(String accountCreationTempValue) {
        this.accountCreationTempValue = accountCreationTempValue;
    }

    private void setAccountCreationTempPreview(boolean accountCreationTempPreview) {
        this.accountCreationTempPreview = accountCreationTempPreview;
    }

    private void setAccountCreationTempPreviewValue(String accountCreationTempPreviewValue) {
        this.accountCreationTempPreviewValue = accountCreationTempPreviewValue;
    }


    public void setReqApprovalTempPreview(boolean reqApprovalTempPreview) {
        this.reqApprovalTempPreview = reqApprovalTempPreview;
    }

    public void setReqApprovalTempValue(String reqApprovalTempValue) {
        this.reqApprovalTempValue = reqApprovalTempValue;
    }

    public void setReqApprovalTempPreviewValue(String reqApprovalTempPreviewValue) {
        this.reqApprovalTempPreviewValue = reqApprovalTempPreviewValue;
    }

    public void setReqRejectionTempPreview(boolean reqRejectionTempPreview) {
        this.reqRejectionTempPreview = reqRejectionTempPreview;
    }

    public void setReqRejectionTempValue(String reqRejectionTempValue) {
        this.reqRejectionTempValue = reqRejectionTempValue;
    }

    public void setReqRejectionTempPreviewValue(String reqRejectionTempPreviewValue) {
        this.reqRejectionTempPreviewValue = reqRejectionTempPreviewValue;
    }

    public void setTempPreview(boolean tempPreview) {
        this.tempPreview = tempPreview;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public void setTempPreviewValue(String tempPreviewValue) {
        this.tempPreviewValue = tempPreviewValue;
    }
}
