import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

class PrintElem extends SimpleFunction<GenericRecord,Void> {
    @Override
    public Void apply(GenericRecord input) {
        System.out.println(input.get("SessionId"));
        System.out.println("Session ID : " + input.get("SessionId"));
        System.out.println("User ID : " + input.get("UserId"));
        System.out.println("User Name : " + input.get("UserName"));
        System.out.println("Video ID : " + input.get("VideoId"));
        System.out.println("Duration : " + input.get("Duration"));
        System.out.println("Sex : " + input.get("Sex"));
        return null;
    }
}
public class ParquetIOWriteExample {
    public static void main (String[] args) {
        Pipeline pipeline = Pipeline.create();
        Schema schema = BeamCustUtil.getSchema();
        PCollection<GenericRecord> pOutput = pipeline.apply(TextIO.read(schema).from("/home/knoldus/IdeaProjects/ApacheBeamPractice5/user.csv"));
        pOutput.apply(MapElements.via(new PrintElem()));

    }
}
