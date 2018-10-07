//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.connectors.rabbitmq.RMQSource;
//import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;
//
//public class FlinkDemo {
//
//    public static void main(String[] args) throws Exception {
//
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        final RMQConnectionConfig connectionConfig = new RMQConnectionConfig.Builder()
//                .setHost("localhost")
//                .setPort(15672)
//                .setVirtualHost("/")
//                .setUserName("yichuan")
//                .setPassword("123456")
//                //.setConnectionTimeout(2000)
//                .build();
//
//        final DataStream<String> stream = env
//                .addSource(new RMQSource<String>(
//                        connectionConfig,            // config for the RabbitMQ connection
//                        "hello",          // name of the RabbitMQ queue to consume
//                        true,         // use correlation ids; can be false if only at-least-once is required
//                        new SimpleStringSchema()))    // deserialization schema to turn messages into Java objects
//                .setParallelism(1);
//
//        stream.print();
//        // non-parallel source is only required for exactly-once
//        //  env.execute("FlinkDemo");
//
//    }
//}
