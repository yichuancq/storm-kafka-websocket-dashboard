package web.socket.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import web.socket.demo.application.DataManager;

@Component
public class FreshMapTask {

    @Autowired
    private DataManager dataManager;

    /**
     * 刷新环形图
     */
    @Scheduled(fixedRate = 2000)
    public void executeRefreshBarTask() {
        System.out.println("刷新环形图");
        dataManager.refreshBar();
    }

    /**
     * 刷新地图
     */
    @Scheduled(fixedRate = 5000)
    public void executeRefreshMapTask() {
        System.out.println("刷新地图");
        dataManager.refreshMap();
    }

    /**
     * 动态计算内存使用
     */
    @Scheduled(fixedRate = 500)
    public void executPushMemoryToWebTask() {
        System.out.println("动态计算内存使用");
        dataManager.pushMemoryToWeb();


    }

}
