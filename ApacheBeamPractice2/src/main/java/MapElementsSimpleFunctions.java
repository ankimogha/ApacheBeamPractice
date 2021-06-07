import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

class User extends SimpleFunction<String,String> {
    @Override
    public String apply(String input) {
        String arr[] = input.split(",");
        String SId = arr[0];
        String UId= arr[1];
        String Uname= arr[2];
        String VId= arr[3];
        String duration= arr[4];
        String startTime= arr[5];
        String sex= arr[6];
        String output=" ";
        if(sex.equals("1")) {
            output=SId+","+UId+","+","+Uname+","+VId+","+duration+","+startTime+","+"M";
        } else if(sex.equals("2")){
            output=SId+","+UId+","+","+Uname+","+VId+","+duration+","+startTime+","+"F";
        } else {
            output= input;
        }
        return output;
    }
}

public class MapElementsSimpleFunctions {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        PCollection<String> pUserList = pipeline.apply(TextIO.read().from("/home/knoldus/IdeaProjects/ApacheBeamPractice2/user.csv"));
        // using SimpleFunction
        PCollection<String> pOutput = pUserList.apply(MapElements.via(new User()));
        pOutput.apply(TextIO.write().to("/home/knoldus/IdeaProjects/ApacheBeamPractice2/user_output").withNumShards(1).withSuffix(".csv"));
        pipeline.run();
    }
}
