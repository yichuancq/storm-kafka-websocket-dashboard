import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yichuan on 2018/2/3.
 */

public class WordCountBolt extends BaseBasicBolt {

    private Map<String,Integer> counters = new HashMap<String, Integer>();

    //该方法只会被调用一次，用来初始化
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
    }

    /**
     * 将collector中的元素存放在成员变量counters（Map）中
     * 如果counters中已经存在钙元素，getValue并对value进行累加操作
     * @param input
     * @param collector
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        String str = (String)input.getValueByField("word");
        Integer num = input.getIntegerByField("num");
        System.out.println("----------------------"+Thread.currentThread().getId() + "    "+ str);
        if(!counters.containsKey(str)){
            counters.put(str,num);
        }else{
            Integer c = counters.get(str) + num;
            counters.put(str,c);
        }
        System.out.println("WordCountBolt 统计单词："+counters);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}