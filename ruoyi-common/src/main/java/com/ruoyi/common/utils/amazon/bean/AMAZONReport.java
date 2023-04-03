package com.ruoyi.common.utils.amazon.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用于接收AMAZON的Feed接口上传后获得的processingReport
 *
 * */
public class AMAZONReport {


    @Schema(description = "异常状态描述")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String errorInfo;



    //feed的状态，有DONE、CANCELLED、FATAL还有中间态IN_PROCESSING
    @Schema(description = "feed状态，DONE为完成状态，IN_PROCESSING表示正在上传中")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String status;

    //通常是ProcessingReport处理报告
    private String messageType;


    //ProcessingReport
    private String DocumentTransactionID;
    //ProcessingSummary 'complete'表示已完成
    private String statusCode;

    private String messagesProcessed;

    @Schema(description = "上传成功的数量")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String messagesSuccessful;

    //不为0时说明构造或者上传存在问题
    @Schema(description = "上传错误的数量")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String messagesWithError;


    @Schema(description = "上传报错的数量")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String messagesWithWarning;

    //Result 是个list，暂不写入，重要的是里面的description


    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getDocumentTransactionID() {
        return DocumentTransactionID;
    }

    public void setDocumentTransactionID(String documentTransactionID) {
        DocumentTransactionID = documentTransactionID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessagesProcessed() {
        return messagesProcessed;
    }

    public void setMessagesProcessed(String messagesProcessed) {
        this.messagesProcessed = messagesProcessed;
    }

    public String getMessagesSuccessful() {
        return messagesSuccessful;
    }

    public void setMessagesSuccessful(String messagesSuccessful) {
        this.messagesSuccessful = messagesSuccessful;
    }

    public String getMessagesWithError() {
        return messagesWithError;
    }

    public void setMessagesWithError(String messagesWithError) {
        this.messagesWithError = messagesWithError;
    }

    public String getMessagesWithWarning() {
        return messagesWithWarning;
    }

    public void setMessagesWithWarning(String messagesWithWarning) {
        this.messagesWithWarning = messagesWithWarning;
    }
}
