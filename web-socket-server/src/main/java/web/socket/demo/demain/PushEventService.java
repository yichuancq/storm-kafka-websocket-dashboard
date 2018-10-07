package web.socket.demo.demain;

import web.socket.demo.demain.pushInfo.Data;

import java.util.List;

public interface PushEventService {


    /**
     * 地图刷新
     *
     * @param dataDTOS
     */
    void refreshMap(List<Data> dataDTOS);

    /**
     * 条形图刷新
     *
     * @param dataDTOS
     * @param strings
     */
    void refreshBar(List<Data> dataDTOS, List<String> strings);


    /**
     * 推送内存占用
     *
     * @param values
     */
    void pushMemoryValue(float values);
}
