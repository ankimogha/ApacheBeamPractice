import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;

import java.util.ArrayList;
import java.util.List;

public class InmemoryExample {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        PCollection<CustomerEntity> pList = pipeline.apply(Create.of(getCustomers()));
        // Code to convert Java collections to PCollection objects.
        PCollection<String> pStrList = pList.apply(MapElements.into(TypeDescriptors.strings()).via((CustomerEntity cust) -> cust.getName()));
        pStrList.apply(TextIO.write().to("/home/knoldus/eclipse-workspace/section2/customers").withNumShards(1).withSuffix(".csv"));
        pipeline.run();
    }

    static List<CustomerEntity> getCustomers() {
        CustomerEntity c1 = new CustomerEntity("1001","Ankit");
        CustomerEntity c2 = new CustomerEntity("1002","Mogha");

        List<CustomerEntity> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);

        return list;
    }
}
