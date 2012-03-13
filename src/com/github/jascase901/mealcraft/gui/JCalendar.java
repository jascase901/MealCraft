/**
 * Copyright (c) 2005-2006 Adam Lane
 * 
 * Licensed under the Academic Free License version 1.2
 */
package com.github.jascase901.mealcraft.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;

/** This class renders and provides some UI interaction for a single calendar month.  When rendered, it
 * stretches to fit the area the component must occupy.  It provides action events for clicks and
 * cell changes.  It is intended to be used in more complex components to provide date selection, or
 * presentation capability. */
public class JCalendar extends JPanel {
    /** This action event name indicates that the cursor has moved between date cells
     * within the calendar. */
    public static final String ACTION_CURSOR_MOVED = "cursorMoved";
    
    /** This action event name indicates that the calendar has been clicked.  */
    public static final String ACTION_CLICKED = "clicked";

    /** This class reduced Java's Date class into a simpler format for comparison purposes
     * I effectively strips time out of the date and compares the simple text representation,
     * but can return a Date object if necessary. */
    public static class BasicDate implements Serializable {
        private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        private String textDate;
        public BasicDate(Date date) { textDate = format.format(date == null ? new Date(0) : date); }
        public Date getDate() { 
            try { 
                return format.parse(textDate);
            } catch (ParseException e) {
                return null;
            }
        }
        public int hashCode() { return textDate.hashCode(); }
        public boolean equals(Object o) {
            if (o instanceof BasicDate) return textDate.equals(((BasicDate)o).textDate);
            return false;
        }
    }

    /** Short day-of-the-week labels used along the calendar heading */
    private static String[] DAY_OF_WEEK_LABELS;
    static {
        // initialize the day-of-week labels using a date formatter
        // so as to be pre-localized
        List<String> dayList = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        Calendar calendar = Calendar.getInstance();
        for (int day = 1; day <= 7; day++) {
            calendar.set(Calendar.DAY_OF_WEEK, day);
            dayList.add(dateFormat.format(calendar.getTime()));
        }
        DAY_OF_WEEK_LABELS = dayList.toArray(new String[7]);
    }
    
    /** Internal state variable - current visible month */
    private int month;
    /** Internal state variable - current visible year */
    private int year;
    /** Internal state variable - true if mouse cursor is currently over a date */
    private boolean useRolloverHighlight = true;
    /** Internal state variable - the date the mouse cursor is currently over */
    private BasicDate cursorDate = null;
    /** Internal state variable - the date that the mouse was over when the button was first pressed */
    private BasicDate beginClickDate = null;
    /** Internal state variable - a map that describes which date cells should be displayed using a custom color */
    private HashMap<BasicDate, Color> dateHighlightMap = new HashMap<BasicDate, Color>();

    private Color colorNeighborMonthBackground = Color.WHITE;
    private Color colorNeighborDayLabel = Color.LIGHT_GRAY;
    private Color colorMonthBorderBackground = Color.GRAY;
    private Color colorMonthBackground = Color.WHITE;
    private Color colorDayHighlightBackground = Color.BLUE;
    private Color colorDayLabel = Color.BLACK;
    private Color colorDayHighlightLabel = Color.WHITE;
    private Color colorDayOfWeekLabel = Color.LIGHT_GRAY;
    private Color colorWeekOfYearLabel = Color.LIGHT_GRAY;
    
    /** Construct a JCalendar initialized to display the current month and year. */
    public JCalendar() {
        // initialize with current month and year
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        // pick a preferred size
        setPreferredSize(new Dimension(200, 150));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());

        // mouse interactions
        this.addMouseMotionListener(mouseMotionListener);
        this.addMouseListener(mouseListener);
    }

    // UI FUNCTIONALITY    
    private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {}

        public void mouseMoved(MouseEvent e) {
            beginClickDate = null;
            if (isEnabled()) {
                long oldDate = cursorDate == null ? 0 : cursorDate.getDate().getTime();
                Date mouseOverDate = getDate(e.getX(), e.getY());
                long newDate = mouseOverDate == null ? 0 : mouseOverDate.getTime();
                if (oldDate != newDate) {
                    cursorDate = new BasicDate(mouseOverDate);
                    ActionEvent event = new ActionEvent(JCalendar.this, ActionEvent.ACTION_PERFORMED, ACTION_CURSOR_MOVED, System.currentTimeMillis(), 0);
                    fireActionPerformed(event);
                    repaint();
                }
            }
        }
    };

    private MouseListener mouseListener = new MouseListener() {
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
            if (isEnabled()) {
                beginClickDate = cursorDate;
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (isEnabled() && beginClickDate != null) {
                Date mouseOverDate = getDate(e.getX(), e.getY());
                if (mouseOverDate != null && beginClickDate.equals(new BasicDate(mouseOverDate))) {
                    ActionEvent event = new ActionEvent(JCalendar.this, ActionEvent.ACTION_PERFORMED, ACTION_CLICKED, System.currentTimeMillis(), 0);
                    fireActionPerformed(event);
                }
                beginClickDate = null;
            }
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {
            if (isEnabled()) {
                cursorDate = null;
                ActionEvent event = new ActionEvent(JCalendar.this, ActionEvent.ACTION_PERFORMED, ACTION_CURSOR_MOVED, System.currentTimeMillis(), 0);
                fireActionPerformed(event);
                repaint();
            }
        }
    };

    /** Toggle whether or to highlight the date when the mouse rolls over it.
     * @param useRolloverHighlight if true, rollover highlighting is enabled. */
    public void setUsesRolloverHighlight(boolean useRolloverHighlight) { this.useRolloverHighlight = useRolloverHighlight; }

    /** Find the date value for the given X, Y coordinates relative to the top-left
     * corner of the calendar.  Used to find which cell the mouse is over.
     * @param dateX the x coordinate used in the lookup
     * @param dateY the y coordinate used in the lookup
     * @return the date of the cell intersected by the X, Y coordinates */
    public Date getDate(int dateX, int dateY) {
        Date returnDate = null;

        int width = getWidth();
        int height = getHeight();
        double cellWidth = width / 8.0;
        double cellHeight = height / 7.0;

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.add(Calendar.DATE, -(date.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY));

        for (int iRow = 0; iRow < 7 && returnDate == null; iRow++) {
            for (int iColumn = 0; iColumn < 8 && returnDate == null; iColumn++) {
                int y = (int) Math.ceil(iRow * cellHeight);
                int x = (int) Math.ceil(iColumn * cellWidth);
                int w = (int) Math.ceil(cellWidth);
                int h = (int) Math.ceil(cellHeight);
                Rectangle rectangle = new Rectangle(x, y, w, h);
                if (iRow == 0 || iColumn == 0) {} else {
                    if (iColumn > 0) {
                        if (rectangle.contains(dateX, dateY)) {
                            returnDate = new Date(date.getTimeInMillis());
                        }
                        date.add(Calendar.DATE, 1);
                    }
                }
            }
        }

        return new BasicDate(returnDate).getDate();
    }
    
    /** Paint the JCalendar.  Without the paint method, the component is pretty much nothing.
     * Labels days of the week, weeks of the year, and days of the month.  Also paints special
     * highlight colors in date cells.
     * @param g Graphics object used for painting. */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        double cellWidth = width / 8.0;
        double cellHeight = height / 7.0;

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.add(Calendar.DATE, -(date.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY));

        Font font = g.getFont();
        for (int iRow = 0; iRow < 7; iRow++) {
            for (int iColumn = 0; iColumn < 8; iColumn++) {
                int y = (int) Math.ceil(iRow * cellHeight);
                int x = (int) Math.ceil(iColumn * cellWidth);
                int w = (int) Math.ceil(cellWidth);
                int h = (int) Math.ceil(cellHeight);
                g.setClip(x, y, w, h);
                if (iRow == 0 || iColumn == 0) {
                    g.setFont(font);
                    FontMetrics fontMetrics = g.getFontMetrics();
                    g.setColor(colorMonthBorderBackground);
                    g.fillRect(x, y, w, h);
                    if (iColumn > 0) {
                        String day = DAY_OF_WEEK_LABELS[iColumn - 1];
                        int ascent = fontMetrics.getAscent();
                        Rectangle2D dayBounds = fontMetrics.getStringBounds(day, g);
                        int dayX = (int) (x + (cellWidth / 2) - (dayBounds.getWidth() / 2));
                        int dayY = y + (h/2) + (ascent/2);
                        g.setColor(colorDayOfWeekLabel);
                        g.drawString(day, dayX, dayY);
                    } else if (iRow > 0) {
                        int week = date.get(Calendar.WEEK_OF_YEAR);
                        String day = Integer.toString(week);
                        int ascent = fontMetrics.getAscent();
                        Rectangle2D dayBounds = fontMetrics.getStringBounds(day, g);
                        int dayX = (int) (x + (cellWidth / 2) - (dayBounds.getWidth() / 2));
                        int dayY = y + (h/2) + (ascent/2);
                        g.setColor(colorWeekOfYearLabel);
                        g.drawString(day, dayX, dayY);
                    }
                } else {
                    BasicDate dateObject = new BasicDate(new Date(date.getTimeInMillis()));
                    long oldDate = dateObject == null ? 0 : dateObject.getDate().getTime();
                    long newDate = cursorDate == null ? 0 : cursorDate.getDate().getTime();

                    boolean mouseOver = oldDate == newDate;
                    g.setColor(getBackground());
                    g.fillRect(x, y, w, h);

                    if (dateHighlightMap.get(dateObject) != null) {
                        Color color = dateHighlightMap.get(dateObject);
                        g.setColor(color);
                        g.fillRect(x, y, w, h);
                    }

                    if (mouseOver && useRolloverHighlight) {
                        g.setColor(colorDayHighlightBackground);
                        g.fillRect(x, y, w, h);
                    }

                    if (iColumn > 0) {
                        Font newFont = font;
                        int monthOfYear = date.get(Calendar.MONTH);
                        int dayOfMonth = date.get(Calendar.DATE);
                        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
                        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                            newFont = newFont.deriveFont(Font.BOLD);
                        }
                        if (month == monthOfYear) {
                            g.setColor(colorDayLabel);
                        } else {
                            newFont = newFont.deriveFont(Font.ITALIC);
                            g.setColor(colorNeighborDayLabel);
                        }
                        if (mouseOver && useRolloverHighlight) {
                            g.setColor(colorDayHighlightLabel);
                        }

                        g.setFont(newFont);
                        FontMetrics fontMetrics = g.getFontMetrics();
                        String day = Integer.toString(dayOfMonth);
                        int ascent = fontMetrics.getAscent();
                        Rectangle2D dayBounds = fontMetrics.getStringBounds(day, g);
                        int dayX = (int) (x + (cellWidth / 2) - (dayBounds.getWidth() / 2));
                        int dayY = y + (h/2) + (ascent/2);
                        g.drawString(day, dayX, dayY);
                        date.add(Calendar.DATE, 1);
                    }
                }
            }
        }
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
    /** Set the date which determines the month and year rendered by
     * the calendar view.  The JCalendar object will use the month and
     * year contained in the Date object.
     * @param date the date used to set the month and year */
    public void setCalendarView(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        repaint();
    }

    /** Get a date which corresponds with the currently viewed month
     * on the JCalendar.  The date will be the beginning of the first
     * day of the viewed month and year. 
     * @return a date representing the currently viewed month in the calendar */
    public Date getCalendarView() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new BasicDate(new Date(calendar.getTimeInMillis())).getDate();
    }

    /** Provide a special highlight color for a particular date on the Calendar.
     * The color provided will be used to colorize the particular day on the
     * JCalendar with a custom color.
     * @param date the date to be highlighted
     * @param color the color to be used in highlighting the date cell */
    public void setDateHighlight(Date date, Color color) {
        BasicDate basicDate = new BasicDate(date);
        if (color == null) {
            dateHighlightMap.remove(basicDate);
        } else {
            dateHighlightMap.put(basicDate, color);
        }
    }

    /** Get the highlight color for a particular date if there is one.  This will
     * always return null until some highlights have been set for specific dates.
     * @param date the date for which the highlight color is desired
     * @return the color used to highlight the specified date */
    public Color getDateHighlight(Date date) {
        BasicDate basicDate = new BasicDate(date);
        return dateHighlightMap.get(basicDate);
    }

    /** Resets the highlights in the JCalendar so that no dates are highlighted. */
    public void clearDateHighlights() {
        dateHighlightMap.clear();
    }

    /** Get the date over which the mouse cursor is positioned or was at the beginning of 
     * a click.
     * @return the date that the mouse is over */
    public Date getCursorDate() {
        return cursorDate != null ? cursorDate.getDate() : null;
    }
}