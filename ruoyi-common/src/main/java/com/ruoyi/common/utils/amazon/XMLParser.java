package com.ruoyi.common.utils.amazon;

import com.ruoyi.common.utils.amazon.bean.AMAZONReport;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

public class XMLParser {


    public static AMAZONReport parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Document document = builder.parse(inputStream);
        Element root = document.getDocumentElement();//获取整个树
        Node processingReportNode = getNodeByTagName(root, "ProcessingReport"); // 获取ProcessingReport节点
        Node processingSummaryNode = getNodeByTagName(root, "ProcessingSummary"); // 获取ProcessingSummary节点
        AMAZONReport amazonReport = new AMAZONReport();
        amazonReport.setDocumentTransactionID(getNodeValueByTagName(processingReportNode, "DocumentTransactionID")); // 获取Result节点的子节点的值
        amazonReport.setStatusCode(getNodeValueByTagName(processingReportNode, "StatusCode"));

        amazonReport.setMessagesProcessed(getNodeValueByTagName(processingSummaryNode, "MessagesProcessed"));
        amazonReport.setMessagesSuccessful(getNodeValueByTagName(processingSummaryNode, "MessagesSuccessful"));
        amazonReport.setMessagesWithError(getNodeValueByTagName(processingSummaryNode, "MessagesWithError"));
        amazonReport.setMessagesWithWarning(getNodeValueByTagName(processingSummaryNode, "MessagesWithWarning"));

        return amazonReport;
    }



    public static Node getNodeByTagName(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return nodeList.item(0);
    }


    public static String getNodeValueByTagName(Node parentNode, String tagName) {
        Node node = getNodeByTagName((Element)parentNode, tagName);
        return node != null ? node.getTextContent() : "";
    }




}
