import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.joda.time.Duration;

public class RealTimeStreamingETL {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        // Building connection between kafka and apache beam
        pipeline.apply(KafkaIO.<Long,IotEvent>read()
                .withBootstrapServers("localhost:9092")
                .withTopic("beamtopic")
                .withKeyDeserializer(LongDeserializer.class)
                .withValueDeserializer(IotDeserializer.class)
                .withoutMetadata()
        )
                .apply(Values.<IotEvent>create())
                .apply(Window.<IotEvent>into(FixedWindows.of(Duration.standardSeconds(10))))
                .apply(ParDo.of(new DoFn<IotEvent, String>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                        if(c.element().getTemperature()>80.0) {
                            c.output(c.element().getDeviceId());
                        }
                    }
                }))
                .apply(Count.perElement())
                // writing data into database
//                .apply(JdbcIO.<KV<String,Long>>write().withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
//                        .create("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/beamdb?useSSL=false")
//                        .withUsername("root").withPassword("root"))
//                        .withStatement("insert into event values (?,?) ")
//                        .withPreparedStatementSetter(new PreparedStatementSetter<KV<String,Long>>() {
//
//                            public void setParameters(KV<String,Long> element, PreparedStatement preparedStatement) throws Exception {
//                                preparedStatement.setString(1, element.getKey());
//                                preparedStatement.setLong(2, element.getValue());
//                            }
//                        })
//                )
                .apply(ParDo.of(new DoFn<KV<String,Long>,Void>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                         System.out.println(c.element());
                    }
                }));
        pipeline.run();
    }
}
