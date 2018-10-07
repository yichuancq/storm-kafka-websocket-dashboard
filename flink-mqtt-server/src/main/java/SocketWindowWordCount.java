import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

@SuppressWarnings("serial")
public class SocketWindowWordCount {

    public static void main(String[] args) {

        // the port to connect to
        final int port;
        try {
            final ParameterTool params = ParameterTool.fromArgs(args);
            // port = 8082;
            port = params.getInt("port");
            // get the execution environment
            final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

            // get input data by connecting to the socket
            DataStream<String> text = env.socketTextStream("localhost", port, "\n");

            // parse the data, group it, window it, and aggregate the counts
            DataStream<WordWithCount> windowCounts = text
                    .flatMap(new FlatMapFunction<String, WordWithCount>() {
                        public void flatMap(String value, Collector<WordWithCount> out) {
                            for (String word : value.split("\\s")) {
                                out.collect(new WordWithCount(word, 1L));
                            }
                        }
                    })
                    .keyBy("word")
                    .timeWindow(Time.seconds(5), Time.seconds(1))
                    .reduce(new ReduceFunction<WordWithCount>() {
                        public WordWithCount reduce(WordWithCount a, WordWithCount b) {
                            return new WordWithCount(a.word, a.count + b.count);
                        }
                    });

//          //使用一个并行度
            windowCounts.print().setParallelism(1);
            //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
            env.execute("Socket Window WordCount");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    //Data type for words with count
    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }
}
