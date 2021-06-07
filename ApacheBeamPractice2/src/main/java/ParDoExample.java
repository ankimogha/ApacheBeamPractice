import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

class CustFilter extends DoFn<String,String> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        String line = c.element();
        String arr[] = line.split(",");

        if(arr[3].equals("Los Angeles")) {
            c.output(line);
        }
    }
}

public class ParDoExample {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        PCollection<String> pCustList = pipeline.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer_pardo.csv"));
        PCollection<String> pOutput = pCustList.apply(ParDo.of(new CustFilter()));
        pOutput.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer_pardo_output").withHeader("Id,Name,Last Name,City").withNumShards(1).withSuffix(".csv"));
        pipeline.run();
    }
}
