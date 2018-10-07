package web.socket.demo.application;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.socket.demo.demain.PushEventService;
import web.socket.demo.demain.pushInfo.Data;
import web.socket.demo.infrastructure.jpa.DataRepository;
import web.socket.demo.utils.ToolsUtil;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataManager {
    //推送消息
    @Autowired
    private PushEventService pushEventService;
    // 数据库保存

    @Autowired
    private DataRepository dataRepository;

    /**
     * 地图推送
     */
    public void refreshMap() {

        //设置固定城市
        String[] stations = {"北京", "成都", "重庆", "贵阳", "上海", "厦门", "南京"};
        // 获取固定个数的随机数
        String areaName = stations[new Random().nextInt(stations.length)];
        int amount = new Random().nextInt(stations.length) + 1;

        Data data = new Data(ToolsUtil.getNextCode(), areaName, amount);

        // 一次产生一个数据,保存到数据库
        dataRepository.save(data);
        // 汇总统计
        List<Data> areaList = dataRepository.findAll();
        //分组求和
        Map<String, LongSummaryStatistics> collect = areaList.stream().collect(Collectors.groupingBy(Data::getName,
                Collectors.summarizingLong(Data::getValue)));

        List<Data> sumStatistics = new ArrayList<>();
        for (Map.Entry<String, LongSummaryStatistics> entry : collect.entrySet()) {
            LongSummaryStatistics longSummaryStatistics = entry.getValue();
            //
            Data temp = new Data(entry.getKey(), Math.toIntExact(longSummaryStatistics.getSum()));
//            Data temp = new Data(entry.getKey(), Math.toIntExact(longSummaryStatistics.getSum() / stations.length));
            System.out.println("key->>" + entry.getKey());
            System.out.println("求和:" + longSummaryStatistics.getSum());
            System.out.println("求平均" + longSummaryStatistics.getAverage());
            System.out.println("求最大:" + longSummaryStatistics.getMax());
            System.out.println("求最小:" + longSummaryStatistics.getMin());
            System.out.println("求总数:" + longSummaryStatistics.getCount());
            sumStatistics.add(temp);
        }
        pushEventService.refreshMap(sumStatistics);


    }

    /**
     *
     */
    public void refreshBar() {
        List<Data> sumStatistics = new ArrayList<>();
        // 汇总统计
        List<Data> areaList = dataRepository.findAll();
        //分组求和
        Map<String, LongSummaryStatistics> collect = areaList.stream()
                .collect(Collectors.groupingBy(Data::getName, Collectors.summarizingLong(Data::getValue)));
        for (Map.Entry<String, LongSummaryStatistics> entry : collect.entrySet()) {
            LongSummaryStatistics longSummaryStatistics = entry.getValue();
            //
            Data temp = new Data(entry.getKey(), Math.toIntExact(longSummaryStatistics.getSum()));
            System.out.println("key->>" + entry.getKey());
            System.out.println("求和:" + longSummaryStatistics.getSum());
            sumStatistics.add(temp);
        }
        List<String> labs = new ArrayList<>();
        for (Data dataDTO : sumStatistics) {
            labs.add(dataDTO.getName());
        }
        pushEventService.refreshBar(sumStatistics, labs);


    }

    /**
     * 内存仪表盘
     */
    public void pushMemoryToWeb() {

        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        float totalMemory = operatingSystemMXBean.getTotalPhysicalMemorySize() / 1024 / 1024;
        float canUse = operatingSystemMXBean.getFreePhysicalMemorySize() / 1024 / 1024;
        float rate = (canUse / totalMemory) * 100;
        System.out.println("系统物理内存总计：" + totalMemory + "MB");
        System.out.println("系统物理可用内存总计：" + canUse + "MB");
        System.out.println("rate:" + rate + "100%");
        pushEventService.pushMemoryValue(rate);
    }

}
