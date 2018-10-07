import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

public class FirstStorm {
    public static void main(String[] args) throws Exception {
        LocalCluster cluster = null;
        //集群提交
        //./storm jar ./com.test.learn.storm-1.0-SNAPSHOT-shaded.jar FirstStorm task
        try {

            Class class1 = FirstStorm.class;
            System.out.println("path:" + class1.getName());
            //Storm框架支持多语言，在Java环境下创建一个拓扑，需要使用TopologyBuilder进行构建
            TopologyBuilder builder = new TopologyBuilder();
            //RandomSentenceSpout类，在已知的英文句子中，随机发送一条句子出去
            builder.setSpout("spout1", new RandomSentenceSpout(), 3);
            //SplitSentenceBolt类，主要是将一行一行的文本内容切割成单词
            builder.setBolt("split1", new SplitSentenceBolt(), 9).shuffleGrouping("spout1");
            //WordCountBolt类，对单词出现的次数进行统计
            builder.setBolt("count2", new WordCountBolt(), 3).fieldsGrouping("split1", new Fields("word"));
            //启动topology的配置信息
            Config conf = new Config();
            //TOPOLOGY_DEBUG(setDebug),当他被设置成true的话，storm会记录下每个组件所发射的每条消息
            //这在本地环境调试topology很有用。但是在线上这么做的话，会影响性能
            conf.setDebug(false);
            conf.setNumWorkers(5);
            //
            conf.setMaxTaskParallelism(3);
            cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            Utils.sleep(1000);

        } catch (Exception ex) {
            cluster.killTopology("FirstStorm");
            cluster.shutdown();
            ex.printStackTrace();
            System.exit(0);
        }

    }

}