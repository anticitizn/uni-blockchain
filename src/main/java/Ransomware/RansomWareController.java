package Ransomware;

import com.google.common.eventbus.EventBus;

public class RansomWareController {
    private final EventBus eventBus;
    private String type;

    public RansomWareController() {
        this.eventBus = new EventBus("Number01");
    }

    public void addSubscriber(Subscriber subscriber){ eventBus.register(subscriber);}

    public void encryption(){
        eventBus.post(new EncryptionEvent());
    }
    public void decryption(){
        eventBus.post(new DecryptionEvent());
    }
    public void delete(){eventBus.post(new DeleteEvent());}


}
