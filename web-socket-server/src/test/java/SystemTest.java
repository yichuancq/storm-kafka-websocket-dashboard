import com.sun.management.OperatingSystemMXBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.socket.demo.SocketApp;

import java.lang.management.ManagementFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocketApp.class)
public class SystemTest {

    @Test
    public void testMem() {
        StringBuffer sb = new StringBuffer();
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        sb.append("系统物理内存总计：" + operatingSystemMXBean.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
        sb.append("系统物理可用内存总计：" + operatingSystemMXBean.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
        //  return sb.toString();
        System.out.println("" + sb.toString());
    }

}
