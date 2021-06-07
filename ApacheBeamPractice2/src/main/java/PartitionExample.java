import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

class MyCityPartition implements Partition.PartitionFn<String> {
    @Override
    public int partitionFor(String elem, int numPartitions) {
        String arr[] = elem.split(",");

        if(arr[3].equals("Los Angeles")) {
            return 0;
        }
        else if(arr[3].equals("Phoenix")) {
            return 1;
        }
        else {
            return 2;
        }
    }
}

public class PartitionExample {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        PCollection<String> pCustList = pipeline.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/Partition.csv"));
        PCollectionList<String> partition = pCustList.apply(Partition.of(3,new MyCityPartition()));
        PCollection<String> p0 = partition.get(0);
        PCollection<String> p1 = partition.get(1);
        PCollection<String> p2 = partition.get(2);
        p0.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/p0").withNumShards(1).withSuffix(".csv"));
        p1.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/p1").withNumShards(1).withSuffix(".csv"));
        p2.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/p2").withNumShards(1).withSuffix(".csv"));
        pipeline.run();
    }
}
