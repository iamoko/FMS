package org.pahappa.systems.models;

public enum TemplateType {

    NEW_REQUISITION("NEW REQUISITION"), PENDING_REVIEW("PENDING REVIEW"), APPROVED("APPROVED"), REJECTED("REJECTED"),
    PENDING_ACKNOWLEDGEMENT("PENDING ACKNOWLEDGEMENT"), ACCOUNT_CREATION("ACCOUNT CREATION"), 
    CREDIT_ACCOUNT("CREDIT ACCOUNT"), DEBIT_ACCOUNT("DEBIT ACCOUNT"),
    ACKNOWLEDGED("ACKNOWLEDGED"), PROFILE_UPDATE("PROFILE UPDATE"), ACCOUNT_UPDATE("ACCOUNT UPDATE");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }

    TemplateType(String s) {
        name = s;
    }

    public static TemplateType getConstantFromString(String string) {

        if(string.equalsIgnoreCase("NEW REQUISITION"))
            return TemplateType.NEW_REQUISITION;

        if(string.equalsIgnoreCase("APPROVED"))
            return TemplateType.APPROVED;

        if(string.equalsIgnoreCase("REJECTED"))
            return TemplateType.REJECTED;

        if(string.equalsIgnoreCase("PENDING ACKNOWLEDGEMENT"))
            return TemplateType.PENDING_ACKNOWLEDGEMENT;

        if(string.equalsIgnoreCase("ACKNOWLEDGED"))
            return TemplateType.ACKNOWLEDGED;

        if(string.equalsIgnoreCase("ACCOUNT CREATION"))
            return TemplateType.ACCOUNT_CREATION;

        if(string.equalsIgnoreCase("PENDING REVIEW"))
            return TemplateType.PENDING_REVIEW;

        if(string.equalsIgnoreCase("PROFILE UPDATE"))
            return TemplateType.PROFILE_UPDATE;

        if(string.equalsIgnoreCase("ACCOUNT UPDATE"))
            return TemplateType.ACCOUNT_UPDATE;

        if(string.equalsIgnoreCase("CREDIT ACCOUNT"))
            return TemplateType.CREDIT_ACCOUNT;

        if(string.equalsIgnoreCase("DEBIT ACCOUNT"))
            return TemplateType.DEBIT_ACCOUNT;

        return null;


    }
}
