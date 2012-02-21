public class DateCalculator{
    public String currentDate() {
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("E yyyy.MM.dd");

    return "Current Date:" + df.format(date);
    }




}

