import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class MapElementsExamples {
    public static void main(String[] args) {
        Pipeline p = Pipeline.create();

        PCollection<String> pCustList= p.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer.csv"));

        //Using TypeDescriptors

        PCollection<String> pOutput=pCustList.apply(MapElements.into(TypeDescriptors.strings()).via((String obj) -> obj.toUpperCase()));

        pOutput.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/cust_output").withNumShards(1).withSuffix(".csv"));

        p.run();
    }
}
