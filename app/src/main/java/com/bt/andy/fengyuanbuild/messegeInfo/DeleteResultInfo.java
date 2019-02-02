package com.bt.andy.fengyuanbuild.messegeInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/2/1 10:46
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class DeleteResultInfo {

    /**
     * Result : {"ResponseStatus":{"ErrorCode":"","IsSuccess":"false","Errors":[{"FieldName":"","Message":"","DIndex":0}],"SuccessEntitys":[{"Id":"","Number":"","DIndex":0}],"SuccessMessages":[{"FieldName":"","Message":"","DIndex":0}],"MsgCode":""}}
     */

    private ResultBean Result;

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * ResponseStatus : {"ErrorCode":"","IsSuccess":"false","Errors":[{"FieldName":"","Message":"","DIndex":0}],"SuccessEntitys":[{"Id":"","Number":"","DIndex":0}],"SuccessMessages":[{"FieldName":"","Message":"","DIndex":0}],"MsgCode":""}
         */

        private ResponseStatusBean ResponseStatus;

        public ResponseStatusBean getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(ResponseStatusBean ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public static class ResponseStatusBean {
            /**
             * ErrorCode :
             * IsSuccess : false
             * Errors : [{"FieldName":"","Message":"","DIndex":0}]
             * SuccessEntitys : [{"Id":"","Number":"","DIndex":0}]
             * SuccessMessages : [{"FieldName":"","Message":"","DIndex":0}]
             * MsgCode :
             */

            private String ErrorCode;
            private String                    IsSuccess;
            private String                    MsgCode;
            private List<ErrorsBean>          Errors;
            private List<SuccessEntitysBean>  SuccessEntitys;
            private List<SuccessMessagesBean> SuccessMessages;

            public String getErrorCode() {
                return ErrorCode;
            }

            public void setErrorCode(String ErrorCode) {
                this.ErrorCode = ErrorCode;
            }

            public String getIsSuccess() {
                return IsSuccess;
            }

            public void setIsSuccess(String IsSuccess) {
                this.IsSuccess = IsSuccess;
            }

            public String getMsgCode() {
                return MsgCode;
            }

            public void setMsgCode(String MsgCode) {
                this.MsgCode = MsgCode;
            }

            public List<ErrorsBean> getErrors() {
                return Errors;
            }

            public void setErrors(List<ErrorsBean> Errors) {
                this.Errors = Errors;
            }

            public List<SuccessEntitysBean> getSuccessEntitys() {
                return SuccessEntitys;
            }

            public void setSuccessEntitys(List<SuccessEntitysBean> SuccessEntitys) {
                this.SuccessEntitys = SuccessEntitys;
            }

            public List<SuccessMessagesBean> getSuccessMessages() {
                return SuccessMessages;
            }

            public void setSuccessMessages(List<SuccessMessagesBean> SuccessMessages) {
                this.SuccessMessages = SuccessMessages;
            }

            public static class ErrorsBean {
                /**
                 * FieldName :
                 * Message :
                 * DIndex : 0
                 */

                private String FieldName;
                private String Message;
                private int    DIndex;

                public String getFieldName() {
                    return FieldName;
                }

                public void setFieldName(String FieldName) {
                    this.FieldName = FieldName;
                }

                public String getMessage() {
                    return Message;
                }

                public void setMessage(String Message) {
                    this.Message = Message;
                }

                public int getDIndex() {
                    return DIndex;
                }

                public void setDIndex(int DIndex) {
                    this.DIndex = DIndex;
                }
            }

            public static class SuccessEntitysBean {
                /**
                 * Id :
                 * Number :
                 * DIndex : 0
                 */

                private String Id;
                private String Number;
                private int    DIndex;

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getNumber() {
                    return Number;
                }

                public void setNumber(String Number) {
                    this.Number = Number;
                }

                public int getDIndex() {
                    return DIndex;
                }

                public void setDIndex(int DIndex) {
                    this.DIndex = DIndex;
                }
            }

            public static class SuccessMessagesBean {
                /**
                 * FieldName :
                 * Message :
                 * DIndex : 0
                 */

                private String FieldName;
                private String Message;
                private int    DIndex;

                public String getFieldName() {
                    return FieldName;
                }

                public void setFieldName(String FieldName) {
                    this.FieldName = FieldName;
                }

                public String getMessage() {
                    return Message;
                }

                public void setMessage(String Message) {
                    this.Message = Message;
                }

                public int getDIndex() {
                    return DIndex;
                }

                public void setDIndex(int DIndex) {
                    this.DIndex = DIndex;
                }
            }
        }
    }
}
