package com.github.jascase901.mealcraft.system;

public class Events{
    String eventName;
    String description;
    int date=0;

    /** makes calendar object statically*/
    public Events() {

	eventName = "";
	date = 20120101;
    description = "";
    }


    public Events(String inputEvent) {
        eventName = inputEvent;
	date = 01012012;
	description = "";
    }

    public Events(int inputDate) {

	eventName = "";
	date = inputDate;
	description = "";
    }

    public Events(String inputEvent, int inputDate) {

	eventName = inputEvent;
	date = inputDate;
	description = "";
    
    }
    
    public Events(String inputEvent, int inputDate, String inputDescript) {

	eventName = inputEvent;
	date = inputDate;
	description = inputDescript;
    }
}



    /** opens calendar to edit */
	/** public openCalendar() {
    
    }
	*/

