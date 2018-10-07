package web.socket.demo.demain;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.socket.demo.demain.pushInfo.Data;
import web.socket.demo.demain.pushInfo.RoteEnum;

import java.util.*;

@Service
public class PushEventServiceImpl implements PushEventService {

    private final SocketIOServer socketIoServer;
    private static ArrayList<UUID> listClient = new ArrayList<UUID>();

    @Autowired
    private PushEventServiceImpl(SocketIOServer socketIoServer) {
        this.socketIoServer = socketIoServer;
        this.socketIoServer.start();
        // 启动监听
        System.out.println("socket.io启动成功！");
    }

    @Override
    public void pushMemoryValue(float values) {

        Map<String, Object> map = new HashMap<>();
        map.put("data", values);
        for (UUID clientId : listClient) {
            if (socketIoServer.getClient(clientId) == null) continue;
            socketIoServer.getClient(clientId).sendEvent(RoteEnum.event_memory.name(), map);
        }
    }

    /**
     * 地图刷新
     *
     * @param dataDTOS
     */
    @Override
    public void refreshMap(List<Data> dataDTOS) {
        Map<String, Object> map = new HashMap<>();
        map.put("specialMap", dataDTOS);
        for (UUID clientId : listClient) {
            if (socketIoServer.getClient(clientId) == null) continue;
            socketIoServer.getClient(clientId).sendEvent(RoteEnum.event_map.name(), map);
        }
    }

    /**
     * 条形图刷新
     *
     * @param dataDTOS
     */
    @Override
    public void refreshBar(List<Data> dataDTOS, List<String> strings) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", dataDTOS);
        map.put("tag", strings);
        for (UUID clientId : listClient) {
            if (socketIoServer != null) {
                if (socketIoServer.getClient(clientId) == null) continue;
                socketIoServer.getClient(clientId).sendEvent(RoteEnum.event_bar.name(), map);
            }

        }
    }


    @OnConnect
    public void onConnect(SocketIOClient client) {
        listClient.add(client.getSessionId());
        System.out.println("客户端:" + client.getSessionId() + "已连接");
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("客户端:" + client.getSessionId() + "断开连接");
    }


}
