import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

public class FlatternExample { //Flatten is used merge multiple files into one file
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        PCollection<String> pCustList1 = pipeline.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer_1.csv"));
        PCollection<String> pCustList2 = pipeline.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer_2.csv"));
        PCollection<String> pCustList3 = pipeline.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer_3.csv"));
        PCollectionList<String> list = PCollectionList.of(pCustList1).and(pCustList2).and(pCustList3);
        PCollection<String> merged =  list.apply(Flatten.pCollections());
        merged.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/customer_flatteren_output").withHeader("d,Name,Last Name,City").withNumShards(1).withSuffix(".csv"));
        pipeline.run();
    }
}
