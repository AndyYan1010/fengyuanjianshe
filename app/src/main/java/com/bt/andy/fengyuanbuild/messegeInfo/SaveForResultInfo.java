package com.bt.andy.fengyuanbuild.messegeInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/31 16:49
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SaveForResultInfo {

    /**
     * Result : {"ResponseStatus":{"IsSuccess":true,"Errors":[],"SuccessEntitys":[{"Id":100029,"Number":"FKSQ000004","DIndex":0}],"SuccessMessages":[],"MsgCode":0},"Id":100029,"Number":"FKSQ000004","NeedReturnData":[{}]}
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
         * ResponseStatus : {"IsSuccess":true,"Errors":[],"SuccessEntitys":[{"Id":100029,"Number":"FKSQ000004","DIndex":0}],"SuccessMessages":[],"MsgCode":0}
         * Id : 100029
         * Number : FKSQ000004
         * NeedReturnData : [{}]
         */

        private ResponseStatusBean ResponseStatus;
        private int                      Id;
        private String                   Number;
        private List<NeedReturnDataBean> NeedReturnData;

        public ResponseStatusBean getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(ResponseStatusBean ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public List<NeedReturnDataBean> getNeedReturnData() {
            return NeedReturnData;
        }

        public void setNeedReturnData(List<NeedReturnDataBean> NeedReturnData) {
            this.NeedReturnData = NeedReturnData;
        }

        public static class ResponseStatusBean {
            /**
             * IsSuccess : true
             * Errors : []
             * SuccessEntitys : [{"Id":100029,"Number":"FKSQ000004","DIndex":0}]
             * SuccessMessages : []
             * MsgCode : 0
             */

            private boolean IsSuccess;
            private int                      MsgCode;
            private List<?>                  Errors;
            private List<SuccessEntitysBean> SuccessEntitys;
            private List<?>                  SuccessMessages;

            public boolean isIsSuccess() {
                return IsSuccess;
            }

            public void setIsSuccess(boolean IsSuccess) {
                this.IsSuccess = IsSuccess;
            }

            public int getMsgCode() {
                return MsgCode;
            }

            public void setMsgCode(int MsgCode) {
                this.MsgCode = MsgCode;
            }

            public List<?> getErrors() {
                return Errors;
            }

            public void setErrors(List<?> Errors) {
                this.Errors = Errors;
            }

            public List<SuccessEntitysBean> getSuccessEntitys() {
                return SuccessEntitys;
            }

            public void setSuccessEntitys(List<SuccessEntitysBean> SuccessEntitys) {
                this.SuccessEntitys = SuccessEntitys;
            }

            public List<?> getSuccessMessages() {
                return SuccessMessages;
            }

            public void setSuccessMessages(List<?> SuccessMessages) {
                this.SuccessMessages = SuccessMessages;
            }

            public static class SuccessEntitysBean {
            }
        }

        public static class NeedReturnDataBean {
        }
    }
}
