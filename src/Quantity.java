public class Quantity{
    private  double amount;
    private String units;
    public Quantity(){
	this(-42, "");
    }
   public Quantity(double amount, String units){
	this.amount=amount;
	this.units=units;
    }

    public double getAmount(){
	return amount;
    } 
    public String getUnits(){
	return units;
    }
    public void setAmount(double amount){
	this.amount = amount;
    }
    public void  setUnits(String units){
	this.units = units;
    }
}