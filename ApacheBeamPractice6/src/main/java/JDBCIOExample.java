import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

public class JDBCIOExample {
    public static void main(String[] args) {

        Pipeline pipeline = Pipeline.create();

        PCollection<String> pOutput = pipeline.apply(JdbcIO.<String>read().
                withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
                        .create("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/products?useSSL=false")
                        .withUsername("root")
                        .withPassword("root"))
                .withQuery("SELECT name, city, currency from product_info WHERE name = ? ")
                .withCoder(StringUtf8Coder.of())
                .withStatementPreparator(new JdbcIO.StatementPreparator() {

                    public void setParameters(PreparedStatement preparedStatement) throws Exception {
                        // TODO Auto-generated method stub
                        preparedStatement.setString(1, "iphone");
                    }
                })
                .withRowMapper(new JdbcIO.RowMapper<String>() {

                    public String mapRow(ResultSet resultSet) throws Exception {
                        return resultSet.getString(1)+","+resultSet.getString(2)+","+resultSet.getString(3);
                    }
                })
        );


        pOutput.apply(TextIO.write().to("C:\\Beam\\jdbc_output.csv").withNumShards(1).withSuffix(".csv"));

        pipeline.run();
    }
}
