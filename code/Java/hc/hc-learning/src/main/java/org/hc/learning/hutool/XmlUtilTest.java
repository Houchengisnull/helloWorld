package org.hc.learning.hutool;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import javax.xml.xpath.XPathConstants;

/**
 * 使用hutool处理xml
 * 读取xml标签的值
 */
@Slf4j
public class XmlUtilTest {

    public static void main(String[] args) {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<returnsms> \n" +
                "  <returnstatus>Success（成功）</returnstatus>  \n" +
                "  <message>ok</message>  \n" +
                "  <remainpoint>1490</remainpoint>  \n" +
                "  <taskID>885</taskID>  \n" +
                "  <successCounts>1</successCounts> \n" +
                "</returnsms>";
        Document document = XmlUtil.parseXml(xmlStr);
        /*Document docResult= XmlUtil.readXML(xmlFile);*/
        // 通过XPath方式解析xml, 结果为“ok”
        String value = (String) XmlUtil.getByXPath("//returnsms/message", document, XPathConstants.STRING);
        log.debug("result {}", value);
    }

}
