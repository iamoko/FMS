package org.pahappa.systems.core.services.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

import org.sers.webutils.server.core.utils.ApplicationSettingsUtils;
import org.sers.webutils.server.core.utils.DefaultApplicationSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomApplicationSettings extends DefaultApplicationSettings {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final NumberFormat DOUBLE_FORMATTER = new DecimalFormat("##,###,###,###.0000");
    private static final NumberFormat INTEGER_FORMATTER = new DecimalFormat("##,###,###,###");
//    private static final DateFormat DATE_FORMATTER = new DateFormat("dd MM yy");
    private static final boolean EXECUTE_BACKGROUND_TASKS = true;
    public static final String BG_JOBS_CHECKSUM = "FMS App";
    public static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    @PostConstruct
    public void init() {
        ApplicationSettingsUtils.Util.createApplicationSettings(this);
        super.setExecuteBackgroundJobs(EXECUTE_BACKGROUND_TASKS);
        super.setBgJobsCheckSum(BG_JOBS_CHECKSUM);
        log.info(this.getClass().getName() + " - Initialized default application settings");
    }
    @Override
    public String getOrganizationName() {
        return "FMS App";
    }
    @Override
    public int getMaximumRecordsPerPage() {
        return 10;
    }
    @Override
    public List<String> getErrorMessageRecipients() {
        return Arrays.asList("amokoivan@gmail.com", "");
    }
    @Override
    public String getDefaultMailSenderAddress() {
        return "amokoivan2@gmail.com";
    }
    @Override
    public String getDefaultMailSenderPassword() {
        return "0788066367";
    }
    @Override
    public String getDefaultMailSenderSmtpHost() {
        return "smpt.gmail.com";
    }
    @Override
    public String getDefaultMailSenderSmtpPort() {
        return "587";
    }
    @Override
    public String getDefaultClientFeedbackMail() {
        return "xyz@gmail.com";
    }
    @Override
    public String getDefaultSuperUserEmail() {
        return "amokoivan@gmail.com";
    }
    @Override
    public String getDefaultSuperUserPhoneNumber() {
        return "256705531898";
    }
    @Override
    public NumberFormat getDoubleNumberFormater() {
        return DOUBLE_FORMATTER;
    }
    @Override
    public NumberFormat getIntegerNumberFormater() {
        return INTEGER_FORMATTER;
    }
    @Override
    public String getPasswordChangeToken() {
        return super.passwordChangeToken;
    }
}

