package org.pahappa.systems.core.services.impl;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sers.webutils.model.bgtasks.BackgroundTask;
import org.sers.webutils.model.bgtasks.constants.TaskType;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.migrations.Migration;
import org.sers.webutils.server.core.service.BackgroundTaskService;
import org.sers.webutils.server.core.service.TaskCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class MailContentScheduler {
	@Autowired
	BackgroundTaskService backgroundTaskService;
	@Autowired
	TaskCreatorService taskCreatorService;
	@Migration(orderNumber = 1)
	public void sendPackageMessages3() {
		taskToSendMessages3();
	}
	public void taskToSendMessages3() {
        System.err.println("================Into Task Scheduler=====================");
                BackgroundTask backgroundTask = new BackgroundTask();
		backgroundTask.setName("Send package content to subscribers");
		backgroundTask.setTaskType(TaskType.Interval);
		backgroundTask.setIntervalInMinutes(2);
		backgroundTask.setPackageName("org.pahappa.systems.server.service.impl");
		backgroundTask.setClassName("CustomMailService");
		backgroundTask.setMethodName("sendWelcomeEmail1");
            try {
                backgroundTaskService.saveOutsideSecurityContext(backgroundTask);
                taskCreatorService.ensureExecuted();
                System.err.println("================Saved Task=====================");
            } catch (ValidationFailedException ex) {
                Logger.getLogger(MailContentScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}