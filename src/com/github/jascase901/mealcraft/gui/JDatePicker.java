/**
 * Copyright (c) 2005-2006 Adam Lane
 * 
 * Licensed under the Academic Free License version 1.2
 */
package com.github.jascase901.mealcraft.gui;

import com.github.jascase901.mealcraft.gui.JCalendar.BasicDate;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** Provides some surrounding UI to the JCalendar to allow the month and year to be changed, as well
 * as selecting the date.  It also provides an internal date change tracking to show a feedback background
 * color indicating the date has change, or to be queried by other containers. */
public class JDatePicker extends JPanel {
    /** Number of days in each month.  Used to adjust the day of the month near the boundary dates when
     * switching the selected month. */ 
    private static int[] DAYS_IN_MONTH_LIST = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    
    /** Month labels used in selecting the month */
    private static String[] MONTH_LIST;
    static {
        List<String> monthList = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        Calendar calendar = Calendar.getInstance();
        for (int month = 0; month < 12; month++) {
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            monthList.add(dateFormat.format(calendar.getTime()));
        }
        MONTH_LIST = monthList.toArray(new String[12]);
    }

    /** Internal state variable - used to store the original date stored in the component */
    private Date originalDate = null;

    /** Internal state varialble - the actual current date stored in the component */
    private Date date = null;
    
    /** Combo box to select the month */
    private JComboBox monthComboBox;
    
    /** Spinner to select the year */
    private JSpinner yearSpinner;
    
    /** Calendar component for displaying and selecting the date */
    private JCalendar calendarComponent;

    private Color colorBackground = Color.WHITE;
    private Color colorBackgroundChanged = new Color(0xFFFFAA);
    private Color colorNewRange = Color.CYAN;
    private Color colorSelectedRange = Color.GREEN;
    private Color colorCursor = Color.BLUE;
    
    /** Construct a JDatePicker with no current selected date.  The month and
     * year defeault to the current month and year. */
    public JDatePicker() {
        monthComboBox = new JComboBox();
        yearSpinner = new JSpinner();
        calendarComponent = new JCalendar();

        monthComboBox.setModel(new DefaultComboBoxModel(MONTH_LIST));
        this.setLayout(new GridBagLayout());

        JPanel calendarRegion = new JPanel();
        calendarRegion.setBorder(BorderFactory.createRaisedBevelBorder());
        calendarRegion.setLayout(new GridBagLayout());
        calendarRegion.add(monthComboBox, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 2, 2));
        calendarRegion.add(yearSpinner, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 2, 2));
        calendarRegion.add(calendarComponent, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.add(calendarRegion, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calendarComponent.addActionListener(calendarActions);
        updateCalendarHighlights();

        yearSpinner.setModel(new SpinnerNumberModel(2005, 1900, 2100, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "####");
        yearSpinner.setEditor(editor);

        ActionListener monthActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCalendar();
            }
        };
        monthComboBox.addActionListener(monthActionListener);
        ChangeListener yearChangeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateCalendar();
            }
        };
        yearSpinner.getModel().addChangeListener(yearChangeListener);
        
        updateView();
    }

    // UI FUNCTIONALITY    
    private ActionListener calendarActions = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == JCalendar.ACTION_CURSOR_MOVED) {
                updateCalendarHighlights();
            } else if (e.getActionCommand() == JCalendar.ACTION_CLICKED) {
                date = calendarComponent.getCursorDate();
                updateStatus();
                updateCalendarHighlights();
                ActionEvent event = new ActionEvent(JDatePicker.this, ActionEvent.ACTION_PERFORMED, "clicked", System.currentTimeMillis(), 0);
                fireActionPerformed(event);
            }
        }
    };

    /** See if any of the internal components has focus (and this the date picker itself).
     * @return true if any internal components have focus */
    public boolean hasFocus() {
        boolean focused = false;
        if (isVisible()) {
            focused = focused || monthComboBox.hasFocus();
            focused = focused || yearSpinner.hasFocus();
            focused = focused || calendarComponent.hasFocus();
        }
        focused = focused || super.hasFocus();
        return focused;
    }

    /** Update the calendar view.  Get the selected month in the combo box, 
     * get the selected year in the spinner, and show the appropriate month
     * in the JCalendar view. */
    private void updateCalendar() {
        Calendar calendarDate = Calendar.getInstance();
        if (getDate() != null) {
            calendarDate.setTime(getDate());
        }
        
        int year = ((Integer) yearSpinner.getValue()).intValue();
        int month = monthComboBox.getSelectedIndex();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarComponent.setCalendarView(calendar.getTime());

        int dayInMonth = calendarDate.get(Calendar.DAY_OF_MONTH);
        if (dayInMonth > DAYS_IN_MONTH_LIST[month]) dayInMonth = DAYS_IN_MONTH_LIST[month];
        
        calendarDate.set(Calendar.DAY_OF_MONTH, 1);
        calendarDate.set(Calendar.YEAR, year);
        calendarDate.set(Calendar.MONTH, month);
        calendarDate.set(Calendar.DAY_OF_MONTH, dayInMonth);
        
        if (getDate() != null) {
            setDate(calendarDate.getTime());
        }
    }

    /** Highlight the currently selected date. */
    private void updateCalendarHighlights() {
        calendarComponent.clearDateHighlights();
        if (date != null) calendarComponent.setDateHighlight(date, colorSelectedRange);
        calendarComponent.repaint();
    }

    /** Provide special background highlight color if the current selection is different
     * from the original selection. */
    private void updateStatus() {
        if (isDateChanged()) {
            calendarComponent.setBackground(colorBackgroundChanged);
            repaint();
        } else {
            calendarComponent.setBackground(colorBackground);
            repaint();
        }
    }

    /**  Set the selected month in the combo box, and the selected year in the
     * spinner.  Update the calendar view to reflect the new selections, as 
     * well as the current date. */
    private void updateView() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarComponent.getCalendarView().getTime());
        monthComboBox.setSelectedItem(MONTH_LIST[calendar.get(Calendar.MONTH)]);
        yearSpinner.setValue(calendar.get(Calendar.YEAR));
        updateStatus();
        updateCalendarHighlights();
    }

    // ACTION LISTENER FUNCTIONALITY
    /** Add an action listener.  The JCalendar provides ActionEvents for clicks
     * as well as for moving the mouse between date cells.
     * @param listener the listener object */
    public final void addActionListener(ActionListener listener) {
        listenerList.add(ActionListener.class, listener);
    }

    /** Remove an action listener.
     * @param listener the listener object */
    public final void removeActionListener(ActionListener listener) {
        listenerList.remove(ActionListener.class, listener);
    }

    private final void fireActionPerformed(ActionEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ActionListener.class) {
                ((ActionListener) listeners[i + 1]).actionPerformed(event);
            }
        }
    }

    // DATE ACCESSORS
    /** See if the current component date is different than the
     * originally selected date.
     * @return returns true if the date has changed */
    public boolean isDateChanged() {
        if (originalDate != null) {
            return (!originalDate.equals(getDate()));
        }
        return false;
    }

    /** Sets the originally selected date.  This affects the <code>isDateChanged()</code>
     * method, as well as special highlighting to indicate that the date has changed.
     * @param date the originally selected date */
    public void setOriginalDate(Date date) {
        this.originalDate = new JCalendar.BasicDate(date).getDate();
        updateStatus();
    }

    /** Sets the currently selected date.  The calendar month and year UI is updated to
     * reflect the date, and the JCalendar view itself displays the specified date as
     * being highlighted selected.
     * @param date the date to be selected and highlighted in the UI */
    public void setDate(Date date) {
        if (date != null) {
            BasicDate oldDate = new JCalendar.BasicDate(this.date);
            this.date = new JCalendar.BasicDate(date).getDate();
            if (oldDate != null && !this.date.equals(oldDate)) {
                ActionEvent event = new ActionEvent(JDatePicker.this, ActionEvent.ACTION_PERFORMED, "changed", System.currentTimeMillis(), 0);
                fireActionPerformed(event);
            }
            calendarComponent.setCalendarView(date);
        } else {
            Date oldDate = this.date;
            this.date = null;
            calendarComponent.setCalendarView(oldDate == null ? new Date() : oldDate);
        }
        updateView();
    }

    /** Get the currently selected date.
     * @return return the currently selected date in the component, or null if no date is selected */
    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }
}
