import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SplitSentenceBolt extends BaseBasicBolt {
    /**
     * 该方法只会被调用一次，用来初始化
     *
     * @param stormConf
     * @param context
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
    }

    /**
     * 接收的参数是RandomSentenceSpout发出的句子，即input的内容是句子
     * execute 方法将句子切割形成的单词发出
     *
     * @param input
     * @param collector
     */
    public void execute(Tuple input, BasicOutputCollector collector) {


        String sentence = (String) input.getValueByField("sentence");
        String[] words = sentence.split(" ");
        for (String word : words) {
            word = word.trim();
            if (!word.equals("") || word != null) {
                word = word.toLowerCase();
                System.out.println("SplitSentenceBolt 切割单词：" + word);
                collector.emit(new Values(word, 1));
            }
        }
    }

    /**
     * 消息源可以发射多条消息流stream,多条消息可以理解为多种类型的数据
     *
     * @param declarer
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "num"));
    }
}
