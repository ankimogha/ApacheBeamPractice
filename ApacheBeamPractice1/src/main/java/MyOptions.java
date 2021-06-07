import org.apache.beam.sdk.options.PipelineOptions;

public interface MyOptions extends PipelineOptions {
    void setInputFile(String inputFile);
    String getInputFile();

    void setOutputFile(String inputFile);
    String getOutputFile();

    void setExtension(String extension);
    String getExtension();
}
