package org.hc.learning.net.抓包;

// https://www.jianshu.com/p/5e7d8494621f
import java.io.IOException;
import jpcap.*;
import jpcap.packet.*;

/**
 * Jpcap 抓包
 * @author Administrator
 *
 * windows环境下 下载Jpacp = jpacp.dll + jpacp.jar
 * jpacp.dll 放在${java_home}/jre/bin目录下
 * jpacp.jar 放在${java_home}/jre/lib/ext目录下
 *
 */
public class JpcapApp {
	
	public static void main(String[] args) throws IOException {
		String propertys = System.getProperty("java.library.path");
		String[] propertyArray = propertys.split(";");
		for (String property : propertyArray) {
			System.out.println(property);
		}
		capturePacket(1, 10);
	}
	
	/**
	 * 获取网络接口列表
	 * Java本身有相关NetInterface api
	 * 但该api并不具备抓包能力
	 */
	public static void showDevices() {
		/**
		 * 返回设备列表
		 */
		 /*NetworkInterface[] deviceArray = JpcapCaptor.getDeviceList();*/
	}
	
	public static void capturePacket(int index, int timeSet) throws IOException {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		/**
		 * openDevice(NetworkInterface intrface, int snaplen, boolean promisc, int to_ms)
		 * interface 网络接口
		 * snaplen
		 * 	从每个报文中截取snaplen字节的数据, 而不是缺省的65535。
		 * 	如果我们指定过小的snaplen值，获取的数据报将被截断（亦即我们获得的不是一个完整的数据报，只是原始数据报的一部分） ，
		 * 	在输出行中会有类似[|proto]这样的输出，这里的proto是截断发生处的协议层名称。
		 * 	注意，采用更大的捕捉范围既增加了处理报文的时间，又相应的减少了报文的缓冲数量，可能导致报文的丢失。
		 * 	你应该把snaplen设的尽量小，只要能够容纳你需要的协议信息就可以了。
		 * 	设置snaplen参数值为0，即是设置为默认值65535。 
		 * 
		 * promisc
		 * 是否开启物理网卡的混杂模式,从而达到侦听多个IP的能力。
		 *  
		 * to_ms
		 */
        JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
        
        // 设置过滤器
        captor.setFilter("ip", true);
        Packet packet;
        long timeStart = System.currentTimeMillis();
        while ((timeStart + timeSet * 60 * 10) >= System.currentTimeMillis()) {
            packet = captor.getPacket();
            if (packet != null) {
                /*String message = packet.toString().split("\\s+")[1];*/
                System.out.println(packet);
            }
        }
       /* CounterPackets cp = new CounterPackets();
        cp.readPackets();
        cp.print();
        System.out.println("PacketNumbers:" + counter);*/
	}
}
