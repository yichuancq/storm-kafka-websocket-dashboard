import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.socket.demo.SocketApp;
import web.socket.demo.demain.pushInfo.Data;
import web.socket.demo.infrastructure.jpa.DataRepository;
import web.socket.demo.utils.ToolsUtil;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocketApp.class)
public class DataTest {


    @Autowired
    private DataRepository dataRepository;

    @Test
    public void testSave() {

        String[] stations = {"北京", "成都", "重庆", "贵阳", "上海", "厦门"};
        // 获取固定个数的随机数
        List<Data> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            String areaName = stations[new Random().nextInt(stations.length)];
            int amount = new Random().nextInt(stations.length) + 1;
            Data data = new Data(ToolsUtil.getNextCode(), areaName, amount);
            dataList.add(data);
        }
        dataRepository.save(dataList);
        // 汇总统计
        //分组\
        List<Data> areaList = dataRepository.findAll();
       // Map<String, List<Data>> map = areaList.stream().collect(Collectors.groupingBy(Data::getName));
//        for (Map.Entry<String, List<Data>> entry : map.entrySet()) {
//            System.out.println("分组" + entry);
//        }
        //分组求和
        Map<String, LongSummaryStatistics> collect = areaList.stream().collect(Collectors.groupingBy(Data::getName,
                Collectors.summarizingLong(Data::getValue)));
        for (Map.Entry<String, LongSummaryStatistics> entry : collect.entrySet()) {
            LongSummaryStatistics longSummaryStatistics = entry.getValue();
            System.out.println("key->>" + entry.getKey());
            System.out.println("求和:" + longSummaryStatistics.getSum());
            System.out.println("求平均" + longSummaryStatistics.getAverage());
            System.out.println("求最大:" + longSummaryStatistics.getMax());
            System.out.println("求最小:" + longSummaryStatistics.getMin());
            System.out.println("求总数:" + longSummaryStatistics.getCount());
        }
    }

}
