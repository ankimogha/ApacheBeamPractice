import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;

public class LocalFileExample {
    public static void main(String[] args) {
        MyOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOptions.class);
        Pipeline pipeline = Pipeline.create(options);
        //Code to read file using PCollection object.
        PCollection<String> output = pipeline.apply(TextIO.read().from(options.getInputFile()));
        //Code to write in File using PCollection object.
        output.apply(TextIO.write().to(options.getOutputFile()).withNumShards(1).withSuffix(options.getExtension()));
        pipeline.run();
//        Hard Coding file source and destination
//        Pipeline pipeline = Pipeline.create();
//        //Code to read file using PCollection object.
//        PCollection<String> output = pipeline.apply(TextIO.read().from("/home/knoldus/eclipse-workspace/section2/input.csv"));
//        //Code to write in File using PCollection object.
//        output.apply(TextIO.write().to("/home/knoldus/eclipse-workspace/section2/output.csv").withNumShards(1).withSuffix(".csv"));
//        pipeline.run();
    }
}
