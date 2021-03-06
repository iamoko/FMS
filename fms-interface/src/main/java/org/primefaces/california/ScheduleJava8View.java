package org.primefaces.california;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.core.services.NonWorkingDaysService;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.models.NonWorkingDays;
import org.pahappa.systems.models.RequisitionPerDay;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.core.utils.DateUtils;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean
@ViewScoped
public class ScheduleJava8View implements Serializable {

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;

    private String timeFormat;

    LocalDateTime start, end;
    private CalendarService calendarService;
    private NonWorkingDaysService nonWorkingDaysService;
    Calendar calendar;


    public void eventCategory(ScheduleModel eventModel, String title, Date startDate, Date endDate) {
        this.start = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        this.end = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        DefaultScheduleEvent event = DefaultScheduleEvent.builder()
                .title(title)
                .startDate(start)
                .endDate(end)
                .styleClass("amoko")
                .build();
        eventModel.addEvent(event);
    }

    public void eventCategorySpecific(ScheduleModel eventModel, Date startDate, Date endDate) {
        this.start = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        this.end = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        DefaultScheduleEvent event = DefaultScheduleEvent.builder()
                .title("Non Working Day")
                .startDate(start)
                .endDate(end)
                .styleClass("ivan")
                .build();
        eventModel.addEvent(event);
    }


    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        this.calendarService = ApplicationContextProvider.getBean(CalendarService.class);
        for (Calendar cal : this.calendarService.getDates()) {
            eventCategory(eventModel, cal.getComment(), cal.getStartDate(), cal.getEndDate());
//    		System.out.println(cal.getStartDate()+" "+cal.getEndDate());
        }

        if (!SharedAppData.getLoggedInUser().hasAdministrativePrivileges()) {
            this.nonWorkingDaysService = ApplicationContextProvider.getBean(NonWorkingDaysService.class);
            for (NonWorkingDays cal : this.nonWorkingDaysService.getCalendar(SharedAppData.getLoggedInUser())) {
                eventCategorySpecific(eventModel, cal.getStartDate(), cal.getEndDate());
            }
        }


        this.lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(LocalDateTime start, LocalDateTime end) {
                for (int i = 1; i <= 5; i++) {
                    LocalDateTime random = getRandomDateTime(start);
                    addEvent(DefaultScheduleEvent.builder().title("Lazy Event " + i).startDate(random).endDate(random.plusHours(3)).build());
                }
            }

        };
    }

    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }


    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }


    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public Date convertToDate(LocalDateTime dateTime) {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            today = sdf.parse(sdf.format(java.sql.Timestamp.valueOf(dateTime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }

    public void deleteEvent() {
        System.out.println("Title " + event.getTitle());
        System.out.println("Start date " + convertToDate(event.getStartDate()));
        System.out.println("End Date " + convertToDate(event.getEndDate()));
    }

    public void addEvent() {

        Boolean failed = false;
        String calendar_title = event.getTitle();
        DepositService depositService = ApplicationContextProvider.getBean(DepositService.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar calendarDay = java.util.Calendar.getInstance();
        java.util.Calendar calendarDate = java.util.Calendar.getInstance();

        for (Deposit deposit : depositService.getTransactions()) {
            calendarDay.setTime(deposit.getDepositDate());
            if (dateFormat.format(calendarDate.getTime()).equals(dateFormat.format(calendarDay.getTime()))) {
                failed = true;
            }

        }

        for (Date date : DateUtils.getDates(convertToDate(event.getStartDate()), convertToDate(event.getEndDate()), 1)) {
            calendarDate.setTime(date);
            for (Deposit deposit : depositService.getTransactions()) {
                calendarDay.setTime(deposit.getDepositDate());
                if (dateFormat.format(calendarDate.getTime()).equals(dateFormat.format(calendarDay.getTime()))) {
                    failed = true;
                }

            }

            if (!failed) {
                System.out.println("Title " + calendar_title);
                System.out.println("Start date " + date);
                System.out.println("End Date " + date);

                Calendar calendar = new Calendar();
                calendar.setStartDate(date);
                calendar.setEndDate(date);
                calendar.setComment(calendar_title);
                this.calendarService.setDate(calendar);

                if (event.isAllDay()) {
                    //see https://github.com/primefaces/primefaces/issues/1164
                    if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                        event.setEndDate(event.getEndDate().plusDays(1));
                    }
                }

                if (event.getId() == null)
                    eventModel.addEvent(event);
                else {
                    eventModel.updateEvent(event);
                    System.out.println(event);
                }
                event = new DefaultScheduleEvent();
            }
            failed = false;
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Action Successful", calendar_title + " Holiday Set where necessary");
        PrimeFaces.current().dialog().showMessageDynamic(message);


    }

    public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
        event = selectEvent.getObject();

    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();

    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean isShowWeekends() {
        return showWeekends;
    }

    public void setShowWeekends(boolean showWeekends) {
        this.showWeekends = showWeekends;
    }

    public boolean isTooltip() {
        return tooltip;
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isAllDaySlot() {
        return allDaySlot;
    }

    public void setAllDaySlot(boolean allDaySlot) {
        this.allDaySlot = allDaySlot;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }


}
