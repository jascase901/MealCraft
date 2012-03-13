/**
 * Copyright (c) 2005-2006 Adam Lane
 * 
 * Licensed under the Academic Free License version 1.2
 */
package com.github.jascase901.mealcraft.gui;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicArrowButton;

/** This date chooser provides a combo box for selecting dates.  The date can be typed into
 * the input field, or selected from a combo popup which provides a GUI calendar date
 * picker.  The text date parsing is unique in that numerous date formats can be handled by the
 * text date parser. */
@SuppressWarnings("serial")
public class JDateChooser extends JPanel {
    /** Stores the last date formatter that successfully parsed a date */
    protected static DateFormat lastDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    /** A set of date formatters that could be used for parsing dates */
    protected static final SimpleDateFormat[] DATE_FORMATS = new SimpleDateFormat[] {
        new SimpleDateFormat("M/d/yyyy"),
        new SimpleDateFormat("M-d-yyyy"),
        new SimpleDateFormat("M d yyyy"),
        new SimpleDateFormat("MM/dd/yyyy"),
        new SimpleDateFormat("MM-dd-yyyy"),
        new SimpleDateFormat("MM dd yyyy"),
        new SimpleDateFormat("MMM d, yyyy"),
        new SimpleDateFormat("MMM d,yyyy"),
        new SimpleDateFormat("MMM d yyyy"),
        new SimpleDateFormat("MMMM d, yyyy"),
        new SimpleDateFormat("MMMM d,yyyy"),
        new SimpleDateFormat("MMMM d yyyy"),
        new SimpleDateFormat("MMM dd, yyyy"),
        new SimpleDateFormat("MMM dd,yyyy"),
        new SimpleDateFormat("MMM dd yyyy"),
        new SimpleDateFormat("MMMM dd, yyyy"),
        new SimpleDateFormat("MMMM dd,yyyy"),
        new SimpleDateFormat("MMMM dd yyyy"),
        new SimpleDateFormat("d MMM, yyyy"),
        new SimpleDateFormat("d MMM,yyyy"),
        new SimpleDateFormat("d MMM yyyy"),
        new SimpleDateFormat("d MMMM, yyyy"),
        new SimpleDateFormat("d MMMM,yyyy"),
        new SimpleDateFormat("d MMMM yyyy"),
        new SimpleDateFormat("dd MMM, yyyy"),
        new SimpleDateFormat("dd MMM,yyyy"),
        new SimpleDateFormat("dd MMM yyyy"),
        new SimpleDateFormat("dd MMMM, yyyy"),
        new SimpleDateFormat("dd MMMM,yyyy"),
        new SimpleDateFormat("dd MMMM yyyy"),
        new SimpleDateFormat("yyyy-M-d"),
        new SimpleDateFormat("yyyy/M/d"),
        new SimpleDateFormat("yyyy M d"),
        new SimpleDateFormat("yyyy MMM d"),
        new SimpleDateFormat("yyyy MMMM d"),
        new SimpleDateFormat("yyyy-MM-dd"),
        new SimpleDateFormat("yyyy/MM/dd"),
        new SimpleDateFormat("yyyy MM dd"),
        new SimpleDateFormat("yyyy MMM dd"),
        new SimpleDateFormat("yyyy MMMM dd")
    };

    /** Internal state varialble - date formatter for current local date format */
    protected DateFormat currentDateFormat = lastDateFormat;
    
    /** Internal state varialble - the original selected date */
    protected Date originalDate = null;
    
    /** Internal state varialble - the current selected date */
    protected Date date = null;
    
    /** Internal state varialble - flag indicating whether the popup is visible or not */
    protected boolean datePickerVisible = false;

    /** Date field for editing the date manually */
    protected JTextField dateField;
    
    /** The combo box toggle button */
    protected JButton comboBoxButton;
    
    /** The popup window date chooser window*/
    protected JWindow datePickerWindow;
    
    /** The date picker within the popup window */
    protected JDatePicker datePicker;
    
    private Color colorBackground = Color.WHITE;
    private Color colorBackgroundError = new Color(0xFFAAAA);
    private Color colorBackgroundChange = new Color(0xFFFFAA);
    
    /** Construct a JDateChooser initialized with selected date. */    
    public JDateChooser() {
        dateField = new JTextField();
        comboBoxButton = new BasicArrowButton(BasicArrowButton.SOUTH, UIManager.getColor("ComboBox.buttonBackground"), UIManager.getColor("ComboBox.buttonShadow"), UIManager.getColor("ComboBox.buttonDarkShadow"), UIManager.getColor("ComboBox.buttonHighlight"));
        comboBoxButton.setName("ComboBox.arrowButton");
        comboBoxButton.addActionListener(onComboButtonClick);
        
        dateField.addComponentListener(componentListener);
        dateField.getDocument().addDocumentListener(dateFieldDocumentListener);
        dateField.setBackground(colorBackground);
        dateField.setPreferredSize(new Dimension(125, dateField.getPreferredSize().height));
        
        this.setLayout(new ComboBoxLayout());
        this.add(dateField);
        this.add(comboBoxButton);
        this.addAncestorListener(ancestorListener);
    }
    
    // UI FUNCTIONALITY    
	private Action onComboButtonClick = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (datePickerVisible == false) {
                showDatePicker();
            } else {
                hideDatePicker();
            }
        }
    };

	private Action onSelectDate = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Date date = datePicker.getDate();
            dateField.setText(currentDateFormat.format(date));
            if (e.getActionCommand().equalsIgnoreCase("Clicked")) {
                hideDatePicker();
                ActionEvent event = new ActionEvent(JDateChooser.this, ActionEvent.ACTION_PERFORMED, "changed", System.currentTimeMillis(), 0);
                fireActionPerformed(event);
            }
        }
    };

    private DocumentListener dateFieldDocumentListener = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { onTextChange(); }
        public void removeUpdate(DocumentEvent e) { onTextChange(); }
        public void changedUpdate(DocumentEvent e) { onTextChange(); }
    };
    
    private ComponentListener componentListener = new ComponentListener() {
        public void componentResized(ComponentEvent e) { hideDatePicker(); }
        public void componentMoved(ComponentEvent e) { hideDatePicker(); }
        public void componentShown(ComponentEvent e) { hideDatePicker(); }
        public void componentHidden(ComponentEvent e) { hideDatePicker(); }
    };
    
    private WindowFocusListener windowFocusListener = new WindowFocusListener() {
        public void windowGainedFocus(WindowEvent e) { }
        public void windowLostFocus(WindowEvent e) { hideDatePicker(); }
    };
    
    private AncestorListener ancestorListener = new AncestorListener() {
        public void ancestorAdded(AncestorEvent event){ hideDatePicker(); }
        public void ancestorRemoved(AncestorEvent event){ hideDatePicker(); }
        public void ancestorMoved(AncestorEvent event){
            if (event.getSource() != datePickerWindow) {
                hideDatePicker();
            }
        }
    };

    /** Find the frame parent of the component. 
     * @param component the component whose frame is needed */
    private Frame getFrame(Component component) {
        if (component == null) component = this;
        if (component.getParent() instanceof Frame) {
            return (Frame) component.getParent();
        }
        return getFrame(component.getParent());
    }
    
    /** Toggle the visiblity of the date picker selection popup.  This is used as a response to the
     * combo box toggle button, but it can be manually toggled as well if there is some need. */
    public void showDatePicker() {
        if (datePickerWindow == null) {
            datePickerWindow = new JWindow(getFrame(null));
            datePickerWindow.setAlwaysOnTop(true);
            datePicker = new JDatePicker();
            datePickerWindow.getContentPane().add(datePicker);
            datePickerWindow.addWindowFocusListener(windowFocusListener);
            datePicker.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            datePicker.addActionListener(onSelectDate);
            datePicker.setFocusable(true);
        }
        if (datePickerVisible == false) {
            Date date = getDate();
            if (date != null || datePicker.getDate() != null) datePicker.setDate(date);
            Dimension max = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int x = JDateChooser.this.getWidth()-250+JDateChooser.this.getLocationOnScreen().x;
            int y = JDateChooser.this.getLocationOnScreen().y+JDateChooser.this.getHeight();
            int w = 250;
            int h = 220;
            if (x + w > max.width) x = max.width - w;
            if (x < 0) x = 0;
            if (y + h > max.height) y = JDateChooser.this.getLocationOnScreen().y - h;
            if (y < 0) y = 0;
            datePickerWindow.setLocation(x, y);
            datePickerWindow.setSize(w, h);
            datePickerWindow.setVisible(true);
            datePickerWindow.requestFocus();
            datePickerVisible = true;
        }
    }

    /** Hide the date picker popup if it is currently visible. */
    public void hideDatePicker() {
        if (datePickerVisible == true) {
            datePickerWindow.setVisible(false);
            datePickerVisible = false;
        }
    }
    
    /** Handle parsing the text field date and updating the rest of the UI accorindingly. */
    private void onTextChange() {
        String text = dateField.getText();
        date = null;
        for (SimpleDateFormat dateFormat : DATE_FORMATS) {
            try {
                Date parsedDate = dateFormat.parse(text);
                if (text.equals(dateFormat.format(parsedDate))) {
                    date = parsedDate;
                    currentDateFormat = dateFormat;
                    lastDateFormat = dateFormat;
                    break;
                }
            } catch (Exception ex) {
                // continue
            }
        }
        if (date == null && text.length() > 0) {
            dateField.setBackground(colorBackgroundError);
        } else {
            if (isDateChanged()) {
                dateField.setBackground(colorBackgroundChange);
            } else {
                dateField.setBackground(colorBackground);
            }
        }
        
        ActionEvent event = new ActionEvent(JDateChooser.this, ActionEvent.ACTION_PERFORMED, "changed", System.currentTimeMillis(), 0);
        fireActionPerformed(event);
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
    /** Set the current selected date of the date chooser.  The date may be null.
     * This will update the text field and the rest of the UI to reflect the change.
     * @param the newly current selected date */
    public void setDate(Date date) {
        if (date != null) {
            dateField.setText(currentDateFormat.format(date));
        } else {
            dateField.setText("");
        }
        onTextChange();
    }
    
    /** Set the originally selected date.  Used in detecting changes in the date
     * selection of the component.
     * @param date the originally selected date */
    public void setOriginalDate(Date date) {
        this.originalDate = date;
        onTextChange();
    }
    
    /** Get the currently chosen date.
     * @return the date currently selected, or null if no date is selected (or if text date is invalid) */
    public Date getDate() {
        return (Date) date;
    }
    
    /** Check to see if the currently selected date differs from the original date holder
     * variable. 
     * @return true if the date selection has changed, false otherwise */
    public boolean isDateChanged() { 
        if (date == null && originalDate == null) return false;
        if (originalDate == null) return true;
        return (date != null && !date.equals(originalDate));
    }
    
    // COMBO BOX UI
    /** This class performs the ComboBox style layout of the component elements. */
    private class ComboBoxLayout implements LayoutManager {
        public void addLayoutComponent(String name, Component component) {}
        public void removeLayoutComponent(Component component) {}

        public Dimension preferredLayoutSize(Container parent) {
            Dimension dimension = new Dimension(comboBoxButton.getPreferredSize().width + dateField.getPreferredSize().width, combineDimensions(new Dimension[] { comboBoxButton.getPreferredSize(), dateField.getPreferredSize() }, true).height);
            return dimension;
        }

        public Dimension minimumLayoutSize(Container parent) {
            return comboBoxButton.getMinimumSize();
        }

        protected Rectangle rectangleForCurrentValue() {
            int width = JDateChooser.this.getWidth();
            int height = JDateChooser.this.getHeight();
            Insets insets = getInsets();
            int buttonSize = height - (insets.top + insets.bottom);
            if (comboBoxButton != null) {
                buttonSize = comboBoxButton.getWidth();
            }
            return new Rectangle(insets.left, insets.top, width - (insets.left + insets.right + buttonSize), height - (insets.top + insets.bottom));
        }

        public void layoutContainer(Container parent) {
            int width = JDateChooser.this.getWidth();
            int height = JDateChooser.this.getHeight();

            Insets insets = getInsets();
            int buttonSize = height - (insets.top + insets.bottom);
            Rectangle cvb;

            if (comboBoxButton != null) {
                comboBoxButton.setBounds(width - (insets.right + buttonSize), insets.top, buttonSize, buttonSize);
            }
            if (dateField != null) {
                cvb = rectangleForCurrentValue();
                dateField.setBounds(cvb);
            }
        }
    }

    /** Takes a set of dimensions and derives a maximum dimension that can be applied to other components. */
    private static Dimension combineDimensions(Dimension[] dimensions, boolean maximum) {
        if (maximum) {
            int w = 0;
            int h = 0;
            int size = dimensions.length;
            for (int i = 0; i < size; i++) {
                if (dimensions[i].width > w) w = dimensions[i].width;
                if (dimensions[i].height > h) h = dimensions[i].height;
            }
            return new Dimension(w, h);
        } else {
            int w = 100000;
            int h = 100000;
            int size = dimensions.length;
            for (int i = 0; i < size; i++) {
                if (dimensions[i].width < w) w = dimensions[i].width;
                if (dimensions[i].height < h) h = dimensions[i].height;
            }
            return new Dimension(w, h);
        }
    }
    
    /** Try to determine if an event is within the popup window so that it
     * can be automatically closed if an event occurs outside of it.
     * @param source source component
     * @return true is source component is a descendent of the JDateChooser */
    @SuppressWarnings("unused")
	private static boolean isInPopup(Component source) {
        for (Component component = source; component != null; component = component.getParent()) {
            if (component instanceof Applet || component instanceof Window) {
                break;
            } else if (component instanceof JDateChooser) {
                return true;
            }
        }
        return false;
    }
}
