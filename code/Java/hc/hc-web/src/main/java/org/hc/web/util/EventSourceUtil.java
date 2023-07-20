package org.hc.web.util;

/**
 * 服务器推送EventSource工具
 */
public class EventSourceUtil {

    /**
     *
     * 1. EventSource使用\n作为分割符
     * 2. EventSource已定义4个字段:id,retry,data,event
     * 3. 结尾使用两个'\n'
     *
     * @param id
     * @param event 缺省为message
     * @param data
     * @param retry 重新连接的时间
     * @return
     */
    public static String getEventSourceMessage(String id, String event, String data, String retry) {
        StringBuilder builder = new StringBuilder();
        builder.append("id:").append(id).append(System.lineSeparator());
        builder.append("event:").append(event).append(System.lineSeparator());
        builder.append("data:").append(data).append(System.lineSeparator());
        builder.append("retry:").append(retry).append(System.lineSeparator());

        builder.append(System.lineSeparator());
        return builder.toString();
    }

}
