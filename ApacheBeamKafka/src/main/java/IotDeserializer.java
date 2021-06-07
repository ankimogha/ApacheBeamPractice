import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class IotDeserializer implements Deserializer<IotEvent> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public IotEvent deserialize(String s, byte[] args1) {
        ObjectMapper om = new ObjectMapper();
        IotEvent iotEvent=null;
        try {
            iotEvent = om.readValue(args1, IotEvent.class);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return iotEvent;
    }

    @Override
    public IotEvent deserialize(String topic, Headers headers, byte[] data) {
        return null;
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
