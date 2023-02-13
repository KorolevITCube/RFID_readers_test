import com.SysiotNetJavaDriver.Transmission;
import com.nlmk.troweltracking.rfidcontroller.model.tag.RfidTagData;
import com.nlmk.troweltracking.rfidcontroller.service.RfidService;
import com.nlmk.troweltracking.rfidcontroller.service.impl.RfidServiceImpl;

public class Main {
    public static void main(String[] args){
        Transmission provider = new com.SysiotNetJavaDriver.Transmission();
        provider.init();
        RfidService rfidReaderService = new RfidServiceImpl(provider);
        var socketHandle = rfidReaderService.connect("192.168.0.200", 200);
        rfidReaderService.startMultiTagReading0xC1(socketHandle,1,4,0);
        var counter = 1000;
            while(counter > 0){
                RfidTagData data = rfidReaderService.getMultiTagData0xC1(socketHandle);
                var message = "";
                if(data != null){
                    message = data.getEpc();
                }else{
                    message = "Tag data is null";
                }
                System.out.println(message);
                counter--;
            }
        rfidReaderService.stopMultiTagReading0xC0(socketHandle);
        rfidReaderService.disconnect(socketHandle);
    }
}
