package com.bt.andy.fengyuanbuild.messegeInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/2/21 16:23
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AccountMemberInfo {

    /**
     * Result : {"ResponseStatus":null,"Result":{"Id":100024,"msterID":100024,"DocumentStatus":"C","ForbidStatus":"A","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计","Description":" ","Address":" "}],"Name":[{"Key":2052,"Value":"席会计"}],"Number":"001","Description":[{"Key":2052,"Value":" "}],"CreateOrgId_Id":1,"CreateOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"},"UseOrgId_Id":1,"UseOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"},"CreatorId_Id":100007,"CreatorId":{"Id":100007,"Name":"席会计","UserAccount":"001"},"ModifierId_Id":100007,"ModifierId":{"Id":100007,"Name":"席会计","UserAccount":"001"},"CreateDate":"2019-01-06T15:22:26.763","FModifyDate":"2019-02-13T15:46:44.72","ForbidDate":null,"ForbidderId_Id":0,"ForbidderId":null,"FStaffNumber":"001","Photo":null,"AuditorId_Id":100007,"AuditorId":{"Id":100007,"Name":"席会计","UserAccount":"001"},"AuditDate":"2019-02-13T15:46:48.117","IsHR":false,"Address":[{"Key":2052,"Value":" "}],"Mobile":" ","Email":" ","FTel":" ","StaffId_Id":101875,"StaffId":{"Id":101875,"msterID":101875,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计"}],"Name":[{"Key":2052,"Value":"席会计"}],"ForbidStatus":"A","StartDate":"2019-02-11T00:00:00","Position_Id":101874,"Position":{"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}]},"StaffNumber":"001"},"PersonId_Id":100025,"PersonId":{"Id":100025,"Number":"BDRY201901060001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计","Address":" "}],"Name":[{"Key":2052,"Value":"席会计"}],"Photo":null,"Mobile":" ","FTel":" ","Email":" ","Address":[{"Key":2052,"Value":" "}]},"FirstCardID_Id":0,"FirstCardID":null,"FIsSHR":false,"PostEntity":[{"Id":100001,"Seq":1,"Post_Id":101874,"Post":{"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}],"FDept_Id":100023,"FDept":{"Id":100023,"msterID":100023,"Number":"BM000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}]}},"StaffStartDate":"2019-02-11T00:00:00","PostDept_Id":100023,"PostDept":{"Id":100023,"msterID":100023,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部","FullName":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}],"Number":"BM000001","UseOrgId_Id":1,"UseOrgId":{"Id":1,"Number":"100","MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]},"FullName":[{"Key":2052,"Value":"财务部"}]},"StaffDocumentStatus":"","StaffForbidStatus":"A","StaffDetails":101875,"IsFirstPost":true,"WorkOrgId_Id":1,"WorkOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"}}],"SHRMapEntity":[{"Id":100001,"FSHRNumber":" ","FShrId":" ","MultiLanguageText":[],"FSHRName":[],"FSHRStatus":" "}],"EmpinfoBank":[{"Id":100001,"Country_Id":" ","Country":null,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"OpenBankName":"上海市工商银行七宝支行"}],"OpenBankName":[{"Key":2052,"Value":"上海市工商银行七宝支行"}],"BankCode":"6222021001081006238","BankHolder":"席会计","CurrencyId_Id":0,"CurrencyId":null,"IsDefault":true,"Description":" ","OpenAddressRec":" ","CNAPS":" ","BankTypeRec_Id":0,"BankTypeRec":null,"FTextBankDetail":" "}]}}
     */

    private ResultBeanX Result;

    public ResultBeanX getResult() {
        return Result;
    }

    public void setResult(ResultBeanX Result) {
        this.Result = Result;
    }

    public static class ResultBeanX {
        /**
         * ResponseStatus : null
         * Result : {"Id":100024,"msterID":100024,"DocumentStatus":"C","ForbidStatus":"A","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计","Description":" ","Address":" "}],"Name":[{"Key":2052,"Value":"席会计"}],"Number":"001","Description":[{"Key":2052,"Value":" "}],"CreateOrgId_Id":1,"CreateOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"},"UseOrgId_Id":1,"UseOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"},"CreatorId_Id":100007,"CreatorId":{"Id":100007,"Name":"席会计","UserAccount":"001"},"ModifierId_Id":100007,"ModifierId":{"Id":100007,"Name":"席会计","UserAccount":"001"},"CreateDate":"2019-01-06T15:22:26.763","FModifyDate":"2019-02-13T15:46:44.72","ForbidDate":null,"ForbidderId_Id":0,"ForbidderId":null,"FStaffNumber":"001","Photo":null,"AuditorId_Id":100007,"AuditorId":{"Id":100007,"Name":"席会计","UserAccount":"001"},"AuditDate":"2019-02-13T15:46:48.117","IsHR":false,"Address":[{"Key":2052,"Value":" "}],"Mobile":" ","Email":" ","FTel":" ","StaffId_Id":101875,"StaffId":{"Id":101875,"msterID":101875,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计"}],"Name":[{"Key":2052,"Value":"席会计"}],"ForbidStatus":"A","StartDate":"2019-02-11T00:00:00","Position_Id":101874,"Position":{"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}]},"StaffNumber":"001"},"PersonId_Id":100025,"PersonId":{"Id":100025,"Number":"BDRY201901060001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计","Address":" "}],"Name":[{"Key":2052,"Value":"席会计"}],"Photo":null,"Mobile":" ","FTel":" ","Email":" ","Address":[{"Key":2052,"Value":" "}]},"FirstCardID_Id":0,"FirstCardID":null,"FIsSHR":false,"PostEntity":[{"Id":100001,"Seq":1,"Post_Id":101874,"Post":{"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}],"FDept_Id":100023,"FDept":{"Id":100023,"msterID":100023,"Number":"BM000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}]}},"StaffStartDate":"2019-02-11T00:00:00","PostDept_Id":100023,"PostDept":{"Id":100023,"msterID":100023,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部","FullName":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}],"Number":"BM000001","UseOrgId_Id":1,"UseOrgId":{"Id":1,"Number":"100","MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]},"FullName":[{"Key":2052,"Value":"财务部"}]},"StaffDocumentStatus":"","StaffForbidStatus":"A","StaffDetails":101875,"IsFirstPost":true,"WorkOrgId_Id":1,"WorkOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"}}],"SHRMapEntity":[{"Id":100001,"FSHRNumber":" ","FShrId":" ","MultiLanguageText":[],"FSHRName":[],"FSHRStatus":" "}],"EmpinfoBank":[{"Id":100001,"Country_Id":" ","Country":null,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"OpenBankName":"上海市工商银行七宝支行"}],"OpenBankName":[{"Key":2052,"Value":"上海市工商银行七宝支行"}],"BankCode":"6222021001081006238","BankHolder":"席会计","CurrencyId_Id":0,"CurrencyId":null,"IsDefault":true,"Description":" ","OpenAddressRec":" ","CNAPS":" ","BankTypeRec_Id":0,"BankTypeRec":null,"FTextBankDetail":" "}]}
         */

        private Object ResponseStatus;
        private ResultBean Result;

        public Object getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(Object ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public ResultBean getResult() {
            return Result;
        }

        public void setResult(ResultBean Result) {
            this.Result = Result;
        }

        public static class ResultBean {
            /**
             * Id : 100024
             * msterID : 100024
             * DocumentStatus : C
             * ForbidStatus : A
             * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"席会计","Description":" ","Address":" "}]
             * Name : [{"Key":2052,"Value":"席会计"}]
             * Number : 001
             * Description : [{"Key":2052,"Value":" "}]
             * CreateOrgId_Id : 1
             * CreateOrgId : {"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"}
             * UseOrgId_Id : 1
             * UseOrgId : {"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"}
             * CreatorId_Id : 100007
             * CreatorId : {"Id":100007,"Name":"席会计","UserAccount":"001"}
             * ModifierId_Id : 100007
             * ModifierId : {"Id":100007,"Name":"席会计","UserAccount":"001"}
             * CreateDate : 2019-01-06T15:22:26.763
             * FModifyDate : 2019-02-13T15:46:44.72
             * ForbidDate : null
             * ForbidderId_Id : 0
             * ForbidderId : null
             * FStaffNumber : 001
             * Photo : null
             * AuditorId_Id : 100007
             * AuditorId : {"Id":100007,"Name":"席会计","UserAccount":"001"}
             * AuditDate : 2019-02-13T15:46:48.117
             * IsHR : false
             * Address : [{"Key":2052,"Value":" "}]
             * Mobile :
             * Email :
             * FTel :
             * StaffId_Id : 101875
             * StaffId : {"Id":101875,"msterID":101875,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计"}],"Name":[{"Key":2052,"Value":"席会计"}],"ForbidStatus":"A","StartDate":"2019-02-11T00:00:00","Position_Id":101874,"Position":{"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}]},"StaffNumber":"001"}
             * PersonId_Id : 100025
             * PersonId : {"Id":100025,"Number":"BDRY201901060001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"席会计","Address":" "}],"Name":[{"Key":2052,"Value":"席会计"}],"Photo":null,"Mobile":" ","FTel":" ","Email":" ","Address":[{"Key":2052,"Value":" "}]}
             * FirstCardID_Id : 0
             * FirstCardID : null
             * FIsSHR : false
             * PostEntity : [{"Id":100001,"Seq":1,"Post_Id":101874,"Post":{"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}],"FDept_Id":100023,"FDept":{"Id":100023,"msterID":100023,"Number":"BM000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}]}},"StaffStartDate":"2019-02-11T00:00:00","PostDept_Id":100023,"PostDept":{"Id":100023,"msterID":100023,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部","FullName":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}],"Number":"BM000001","UseOrgId_Id":1,"UseOrgId":{"Id":1,"Number":"100","MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]},"FullName":[{"Key":2052,"Value":"财务部"}]},"StaffDocumentStatus":"","StaffForbidStatus":"A","StaffDetails":101875,"IsFirstPost":true,"WorkOrgId_Id":1,"WorkOrgId":{"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"}}]
             * SHRMapEntity : [{"Id":100001,"FSHRNumber":" ","FShrId":" ","MultiLanguageText":[],"FSHRName":[],"FSHRStatus":" "}]
             * EmpinfoBank : [{"Id":100001,"Country_Id":" ","Country":null,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"OpenBankName":"上海市工商银行七宝支行"}],"OpenBankName":[{"Key":2052,"Value":"上海市工商银行七宝支行"}],"BankCode":"6222021001081006238","BankHolder":"席会计","CurrencyId_Id":0,"CurrencyId":null,"IsDefault":true,"Description":" ","OpenAddressRec":" ","CNAPS":" ","BankTypeRec_Id":0,"BankTypeRec":null,"FTextBankDetail":" "}]
             */

            private int Id;
            private int                              msterID;
            private String                           DocumentStatus;
            private String                           ForbidStatus;
            private String                           Number;
            private int                              CreateOrgId_Id;
            private CreateOrgIdBean                  CreateOrgId;
            private int                              UseOrgId_Id;
            private UseOrgIdBean                     UseOrgId;
            private int                              CreatorId_Id;
            private CreatorIdBean                    CreatorId;
            private int                              ModifierId_Id;
            private ModifierIdBean                   ModifierId;
            private String                           CreateDate;
            private String                           FModifyDate;
            private Object                           ForbidDate;
            private int                              ForbidderId_Id;
            private Object                           ForbidderId;
            private String                           FStaffNumber;
            private Object                           Photo;
            private int                              AuditorId_Id;
            private AuditorIdBean                    AuditorId;
            private String                           AuditDate;
            private boolean                          IsHR;
            private String                           Mobile;
            private String                           Email;
            private String                           FTel;
            private int                              StaffId_Id;
            private StaffIdBean                      StaffId;
            private int                              PersonId_Id;
            private PersonIdBean                     PersonId;
            private int                              FirstCardID_Id;
            private Object                           FirstCardID;
            private boolean                          FIsSHR;
            private List<MultiLanguageTextBeanXXXXX> MultiLanguageText;
            private List<NameBeanXXXXX>              Name;
            private List<DescriptionBean>            Description;
            private List<AddressBeanX>               Address;
            private List<PostEntityBean>             PostEntity;
            private List<SHRMapEntityBean>           SHRMapEntity;
            private List<EmpinfoBankBean>            EmpinfoBank;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getMsterID() {
                return msterID;
            }

            public void setMsterID(int msterID) {
                this.msterID = msterID;
            }

            public String getDocumentStatus() {
                return DocumentStatus;
            }

            public void setDocumentStatus(String DocumentStatus) {
                this.DocumentStatus = DocumentStatus;
            }

            public String getForbidStatus() {
                return ForbidStatus;
            }

            public void setForbidStatus(String ForbidStatus) {
                this.ForbidStatus = ForbidStatus;
            }

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public int getCreateOrgId_Id() {
                return CreateOrgId_Id;
            }

            public void setCreateOrgId_Id(int CreateOrgId_Id) {
                this.CreateOrgId_Id = CreateOrgId_Id;
            }

            public CreateOrgIdBean getCreateOrgId() {
                return CreateOrgId;
            }

            public void setCreateOrgId(CreateOrgIdBean CreateOrgId) {
                this.CreateOrgId = CreateOrgId;
            }

            public int getUseOrgId_Id() {
                return UseOrgId_Id;
            }

            public void setUseOrgId_Id(int UseOrgId_Id) {
                this.UseOrgId_Id = UseOrgId_Id;
            }

            public UseOrgIdBean getUseOrgId() {
                return UseOrgId;
            }

            public void setUseOrgId(UseOrgIdBean UseOrgId) {
                this.UseOrgId = UseOrgId;
            }

            public int getCreatorId_Id() {
                return CreatorId_Id;
            }

            public void setCreatorId_Id(int CreatorId_Id) {
                this.CreatorId_Id = CreatorId_Id;
            }

            public CreatorIdBean getCreatorId() {
                return CreatorId;
            }

            public void setCreatorId(CreatorIdBean CreatorId) {
                this.CreatorId = CreatorId;
            }

            public int getModifierId_Id() {
                return ModifierId_Id;
            }

            public void setModifierId_Id(int ModifierId_Id) {
                this.ModifierId_Id = ModifierId_Id;
            }

            public ModifierIdBean getModifierId() {
                return ModifierId;
            }

            public void setModifierId(ModifierIdBean ModifierId) {
                this.ModifierId = ModifierId;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getFModifyDate() {
                return FModifyDate;
            }

            public void setFModifyDate(String FModifyDate) {
                this.FModifyDate = FModifyDate;
            }

            public Object getForbidDate() {
                return ForbidDate;
            }

            public void setForbidDate(Object ForbidDate) {
                this.ForbidDate = ForbidDate;
            }

            public int getForbidderId_Id() {
                return ForbidderId_Id;
            }

            public void setForbidderId_Id(int ForbidderId_Id) {
                this.ForbidderId_Id = ForbidderId_Id;
            }

            public Object getForbidderId() {
                return ForbidderId;
            }

            public void setForbidderId(Object ForbidderId) {
                this.ForbidderId = ForbidderId;
            }

            public String getFStaffNumber() {
                return FStaffNumber;
            }

            public void setFStaffNumber(String FStaffNumber) {
                this.FStaffNumber = FStaffNumber;
            }

            public Object getPhoto() {
                return Photo;
            }

            public void setPhoto(Object Photo) {
                this.Photo = Photo;
            }

            public int getAuditorId_Id() {
                return AuditorId_Id;
            }

            public void setAuditorId_Id(int AuditorId_Id) {
                this.AuditorId_Id = AuditorId_Id;
            }

            public AuditorIdBean getAuditorId() {
                return AuditorId;
            }

            public void setAuditorId(AuditorIdBean AuditorId) {
                this.AuditorId = AuditorId;
            }

            public String getAuditDate() {
                return AuditDate;
            }

            public void setAuditDate(String AuditDate) {
                this.AuditDate = AuditDate;
            }

            public boolean isIsHR() {
                return IsHR;
            }

            public void setIsHR(boolean IsHR) {
                this.IsHR = IsHR;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getFTel() {
                return FTel;
            }

            public void setFTel(String FTel) {
                this.FTel = FTel;
            }

            public int getStaffId_Id() {
                return StaffId_Id;
            }

            public void setStaffId_Id(int StaffId_Id) {
                this.StaffId_Id = StaffId_Id;
            }

            public StaffIdBean getStaffId() {
                return StaffId;
            }

            public void setStaffId(StaffIdBean StaffId) {
                this.StaffId = StaffId;
            }

            public int getPersonId_Id() {
                return PersonId_Id;
            }

            public void setPersonId_Id(int PersonId_Id) {
                this.PersonId_Id = PersonId_Id;
            }

            public PersonIdBean getPersonId() {
                return PersonId;
            }

            public void setPersonId(PersonIdBean PersonId) {
                this.PersonId = PersonId;
            }

            public int getFirstCardID_Id() {
                return FirstCardID_Id;
            }

            public void setFirstCardID_Id(int FirstCardID_Id) {
                this.FirstCardID_Id = FirstCardID_Id;
            }

            public Object getFirstCardID() {
                return FirstCardID;
            }

            public void setFirstCardID(Object FirstCardID) {
                this.FirstCardID = FirstCardID;
            }

            public boolean isFIsSHR() {
                return FIsSHR;
            }

            public void setFIsSHR(boolean FIsSHR) {
                this.FIsSHR = FIsSHR;
            }

            public List<MultiLanguageTextBeanXXXXX> getMultiLanguageText() {
                return MultiLanguageText;
            }

            public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXX> MultiLanguageText) {
                this.MultiLanguageText = MultiLanguageText;
            }

            public List<NameBeanXXXXX> getName() {
                return Name;
            }

            public void setName(List<NameBeanXXXXX> Name) {
                this.Name = Name;
            }

            public List<DescriptionBean> getDescription() {
                return Description;
            }

            public void setDescription(List<DescriptionBean> Description) {
                this.Description = Description;
            }

            public List<AddressBeanX> getAddress() {
                return Address;
            }

            public void setAddress(List<AddressBeanX> Address) {
                this.Address = Address;
            }

            public List<PostEntityBean> getPostEntity() {
                return PostEntity;
            }

            public void setPostEntity(List<PostEntityBean> PostEntity) {
                this.PostEntity = PostEntity;
            }

            public List<SHRMapEntityBean> getSHRMapEntity() {
                return SHRMapEntity;
            }

            public void setSHRMapEntity(List<SHRMapEntityBean> SHRMapEntity) {
                this.SHRMapEntity = SHRMapEntity;
            }

            public List<EmpinfoBankBean> getEmpinfoBank() {
                return EmpinfoBank;
            }

            public void setEmpinfoBank(List<EmpinfoBankBean> EmpinfoBank) {
                this.EmpinfoBank = EmpinfoBank;
            }

            public static class CreateOrgIdBean {
                /**
                 * Id : 1
                 * MultiLanguageText : [{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}]
                 * Name : [{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]
                 * Number : 100
                 */

                private int Id;
                private String                      Number;
                private List<MultiLanguageTextBean> MultiLanguageText;
                private List<NameBean>              Name;

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

                public List<MultiLanguageTextBean> getMultiLanguageText() {
                    return MultiLanguageText;
                }

                public void setMultiLanguageText(List<MultiLanguageTextBean> MultiLanguageText) {
                    this.MultiLanguageText = MultiLanguageText;
                }

                public List<NameBean> getName() {
                    return Name;
                }

                public void setName(List<NameBean> Name) {
                    this.Name = Name;
                }

                public static class MultiLanguageTextBean {
                    /**
                     * PkId : 1
                     * LocaleId : 2052
                     * Name : 上海冯源建设发展有限公司
                     */

                    private int PkId;
                    private int    LocaleId;
                    private String Name;

                    public int getPkId() {
                        return PkId;
                    }

                    public void setPkId(int PkId) {
                        this.PkId = PkId;
                    }

                    public int getLocaleId() {
                        return LocaleId;
                    }

                    public void setLocaleId(int LocaleId) {
                        this.LocaleId = LocaleId;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }
                }

                public static class NameBean {
                    /**
                     * Key : 2052
                     * Value : 上海冯源建设发展有限公司
                     */

                    private int Key;
                    private String Value;

                    public int getKey() {
                        return Key;
                    }

                    public void setKey(int Key) {
                        this.Key = Key;
                    }

                    public String getValue() {
                        return Value;
                    }

                    public void setValue(String Value) {
                        this.Value = Value;
                    }
                }
            }

            public static class UseOrgIdBean {
                /**
                 * Id : 1
                 * MultiLanguageText : [{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}]
                 * Name : [{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]
                 * Number : 100
                 */

                private int Id;
                private String                       Number;
                private List<MultiLanguageTextBeanX> MultiLanguageText;
                private List<NameBeanX>              Name;

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

                public List<MultiLanguageTextBeanX> getMultiLanguageText() {
                    return MultiLanguageText;
                }

                public void setMultiLanguageText(List<MultiLanguageTextBeanX> MultiLanguageText) {
                    this.MultiLanguageText = MultiLanguageText;
                }

                public List<NameBeanX> getName() {
                    return Name;
                }

                public void setName(List<NameBeanX> Name) {
                    this.Name = Name;
                }

                public static class MultiLanguageTextBeanX {
                    /**
                     * PkId : 1
                     * LocaleId : 2052
                     * Name : 上海冯源建设发展有限公司
                     */

                    private int PkId;
                    private int    LocaleId;
                    private String Name;

                    public int getPkId() {
                        return PkId;
                    }

                    public void setPkId(int PkId) {
                        this.PkId = PkId;
                    }

                    public int getLocaleId() {
                        return LocaleId;
                    }

                    public void setLocaleId(int LocaleId) {
                        this.LocaleId = LocaleId;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }
                }

                public static class NameBeanX {
                    /**
                     * Key : 2052
                     * Value : 上海冯源建设发展有限公司
                     */

                    private int Key;
                    private String Value;

                    public int getKey() {
                        return Key;
                    }

                    public void setKey(int Key) {
                        this.Key = Key;
                    }

                    public String getValue() {
                        return Value;
                    }

                    public void setValue(String Value) {
                        this.Value = Value;
                    }
                }
            }

            public static class CreatorIdBean {
                /**
                 * Id : 100007
                 * Name : 席会计
                 * UserAccount : 001
                 */

                private int Id;
                private String Name;
                private String UserAccount;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getUserAccount() {
                    return UserAccount;
                }

                public void setUserAccount(String UserAccount) {
                    this.UserAccount = UserAccount;
                }
            }

            public static class ModifierIdBean {
                /**
                 * Id : 100007
                 * Name : 席会计
                 * UserAccount : 001
                 */

                private int Id;
                private String Name;
                private String UserAccount;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getUserAccount() {
                    return UserAccount;
                }

                public void setUserAccount(String UserAccount) {
                    this.UserAccount = UserAccount;
                }
            }

            public static class AuditorIdBean {
                /**
                 * Id : 100007
                 * Name : 席会计
                 * UserAccount : 001
                 */

                private int Id;
                private String Name;
                private String UserAccount;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getUserAccount() {
                    return UserAccount;
                }

                public void setUserAccount(String UserAccount) {
                    this.UserAccount = UserAccount;
                }
            }

            public static class StaffIdBean {
                /**
                 * Id : 101875
                 * msterID : 101875
                 * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"席会计"}]
                 * Name : [{"Key":2052,"Value":"席会计"}]
                 * ForbidStatus : A
                 * StartDate : 2019-02-11T00:00:00
                 * Position_Id : 101874
                 * Position : {"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}]}
                 * StaffNumber : 001
                 */

                private int Id;
                private int                            msterID;
                private String                         ForbidStatus;
                private String                         StartDate;
                private int                            Position_Id;
                private PositionBean                   Position;
                private String                         StaffNumber;
                private List<MultiLanguageTextBeanXXX> MultiLanguageText;
                private List<NameBeanXXX>              Name;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public int getMsterID() {
                    return msterID;
                }

                public void setMsterID(int msterID) {
                    this.msterID = msterID;
                }

                public String getForbidStatus() {
                    return ForbidStatus;
                }

                public void setForbidStatus(String ForbidStatus) {
                    this.ForbidStatus = ForbidStatus;
                }

                public String getStartDate() {
                    return StartDate;
                }

                public void setStartDate(String StartDate) {
                    this.StartDate = StartDate;
                }

                public int getPosition_Id() {
                    return Position_Id;
                }

                public void setPosition_Id(int Position_Id) {
                    this.Position_Id = Position_Id;
                }

                public PositionBean getPosition() {
                    return Position;
                }

                public void setPosition(PositionBean Position) {
                    this.Position = Position;
                }

                public String getStaffNumber() {
                    return StaffNumber;
                }

                public void setStaffNumber(String StaffNumber) {
                    this.StaffNumber = StaffNumber;
                }

                public List<MultiLanguageTextBeanXXX> getMultiLanguageText() {
                    return MultiLanguageText;
                }

                public void setMultiLanguageText(List<MultiLanguageTextBeanXXX> MultiLanguageText) {
                    this.MultiLanguageText = MultiLanguageText;
                }

                public List<NameBeanXXX> getName() {
                    return Name;
                }

                public void setName(List<NameBeanXXX> Name) {
                    this.Name = Name;
                }

                public static class PositionBean {
                    /**
                     * Id : 101874
                     * msterID : 101874
                     * Number : GW000001
                     * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}]
                     * Name : [{"Key":2052,"Value":"财务总监"}]
                     */

                    private int Id;
                    private int                           msterID;
                    private String                        Number;
                    private List<MultiLanguageTextBeanXX> MultiLanguageText;
                    private List<NameBeanXX>              Name;

                    public int getId() {
                        return Id;
                    }

                    public void setId(int Id) {
                        this.Id = Id;
                    }

                    public int getMsterID() {
                        return msterID;
                    }

                    public void setMsterID(int msterID) {
                        this.msterID = msterID;
                    }

                    public String getNumber() {
                        return Number;
                    }

                    public void setNumber(String Number) {
                        this.Number = Number;
                    }

                    public List<MultiLanguageTextBeanXX> getMultiLanguageText() {
                        return MultiLanguageText;
                    }

                    public void setMultiLanguageText(List<MultiLanguageTextBeanXX> MultiLanguageText) {
                        this.MultiLanguageText = MultiLanguageText;
                    }

                    public List<NameBeanXX> getName() {
                        return Name;
                    }

                    public void setName(List<NameBeanXX> Name) {
                        this.Name = Name;
                    }

                    public static class MultiLanguageTextBeanXX {
                        /**
                         * PkId : 100001
                         * LocaleId : 2052
                         * Name : 财务总监
                         */

                        private int PkId;
                        private int    LocaleId;
                        private String Name;

                        public int getPkId() {
                            return PkId;
                        }

                        public void setPkId(int PkId) {
                            this.PkId = PkId;
                        }

                        public int getLocaleId() {
                            return LocaleId;
                        }

                        public void setLocaleId(int LocaleId) {
                            this.LocaleId = LocaleId;
                        }

                        public String getName() {
                            return Name;
                        }

                        public void setName(String Name) {
                            this.Name = Name;
                        }
                    }

                    public static class NameBeanXX {
                        /**
                         * Key : 2052
                         * Value : 财务总监
                         */

                        private int Key;
                        private String Value;

                        public int getKey() {
                            return Key;
                        }

                        public void setKey(int Key) {
                            this.Key = Key;
                        }

                        public String getValue() {
                            return Value;
                        }

                        public void setValue(String Value) {
                            this.Value = Value;
                        }
                    }
                }

                public static class MultiLanguageTextBeanXXX {
                    /**
                     * PkId : 100001
                     * LocaleId : 2052
                     * Name : 席会计
                     */

                    private int PkId;
                    private int    LocaleId;
                    private String Name;

                    public int getPkId() {
                        return PkId;
                    }

                    public void setPkId(int PkId) {
                        this.PkId = PkId;
                    }

                    public int getLocaleId() {
                        return LocaleId;
                    }

                    public void setLocaleId(int LocaleId) {
                        this.LocaleId = LocaleId;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }
                }

                public static class NameBeanXXX {
                    /**
                     * Key : 2052
                     * Value : 席会计
                     */

                    private int Key;
                    private String Value;

                    public int getKey() {
                        return Key;
                    }

                    public void setKey(int Key) {
                        this.Key = Key;
                    }

                    public String getValue() {
                        return Value;
                    }

                    public void setValue(String Value) {
                        this.Value = Value;
                    }
                }
            }

            public static class PersonIdBean {
                /**
                 * Id : 100025
                 * Number : BDRY201901060001
                 * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"席会计","Address":" "}]
                 * Name : [{"Key":2052,"Value":"席会计"}]
                 * Photo : null
                 * Mobile :
                 * FTel :
                 * Email :
                 * Address : [{"Key":2052,"Value":" "}]
                 */

                private int Id;
                private String                          Number;
                private Object                          Photo;
                private String                          Mobile;
                private String                          FTel;
                private String                          Email;
                private List<MultiLanguageTextBeanXXXX> MultiLanguageText;
                private List<NameBeanXXXX>              Name;
                private List<AddressBean>               Address;

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

                public Object getPhoto() {
                    return Photo;
                }

                public void setPhoto(Object Photo) {
                    this.Photo = Photo;
                }

                public String getMobile() {
                    return Mobile;
                }

                public void setMobile(String Mobile) {
                    this.Mobile = Mobile;
                }

                public String getFTel() {
                    return FTel;
                }

                public void setFTel(String FTel) {
                    this.FTel = FTel;
                }

                public String getEmail() {
                    return Email;
                }

                public void setEmail(String Email) {
                    this.Email = Email;
                }

                public List<MultiLanguageTextBeanXXXX> getMultiLanguageText() {
                    return MultiLanguageText;
                }

                public void setMultiLanguageText(List<MultiLanguageTextBeanXXXX> MultiLanguageText) {
                    this.MultiLanguageText = MultiLanguageText;
                }

                public List<NameBeanXXXX> getName() {
                    return Name;
                }

                public void setName(List<NameBeanXXXX> Name) {
                    this.Name = Name;
                }

                public List<AddressBean> getAddress() {
                    return Address;
                }

                public void setAddress(List<AddressBean> Address) {
                    this.Address = Address;
                }

                public static class MultiLanguageTextBeanXXXX {
                    /**
                     * PkId : 100001
                     * LocaleId : 2052
                     * Name : 席会计
                     * Address :
                     */

                    private int PkId;
                    private int    LocaleId;
                    private String Name;
                    private String Address;

                    public int getPkId() {
                        return PkId;
                    }

                    public void setPkId(int PkId) {
                        this.PkId = PkId;
                    }

                    public int getLocaleId() {
                        return LocaleId;
                    }

                    public void setLocaleId(int LocaleId) {
                        this.LocaleId = LocaleId;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }

                    public String getAddress() {
                        return Address;
                    }

                    public void setAddress(String Address) {
                        this.Address = Address;
                    }
                }

                public static class NameBeanXXXX {
                    /**
                     * Key : 2052
                     * Value : 席会计
                     */

                    private int Key;
                    private String Value;

                    public int getKey() {
                        return Key;
                    }

                    public void setKey(int Key) {
                        this.Key = Key;
                    }

                    public String getValue() {
                        return Value;
                    }

                    public void setValue(String Value) {
                        this.Value = Value;
                    }
                }

                public static class AddressBean {
                    /**
                     * Key : 2052
                     * Value :
                     */

                    private int Key;
                    private String Value;

                    public int getKey() {
                        return Key;
                    }

                    public void setKey(int Key) {
                        this.Key = Key;
                    }

                    public String getValue() {
                        return Value;
                    }

                    public void setValue(String Value) {
                        this.Value = Value;
                    }
                }
            }

            public static class MultiLanguageTextBeanXXXXX {
                /**
                 * PkId : 100001
                 * LocaleId : 2052
                 * Name : 席会计
                 * Description :
                 * Address :
                 */

                private int PkId;
                private int    LocaleId;
                private String Name;
                private String Description;
                private String Address;

                public int getPkId() {
                    return PkId;
                }

                public void setPkId(int PkId) {
                    this.PkId = PkId;
                }

                public int getLocaleId() {
                    return LocaleId;
                }

                public void setLocaleId(int LocaleId) {
                    this.LocaleId = LocaleId;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getDescription() {
                    return Description;
                }

                public void setDescription(String Description) {
                    this.Description = Description;
                }

                public String getAddress() {
                    return Address;
                }

                public void setAddress(String Address) {
                    this.Address = Address;
                }
            }

            public static class NameBeanXXXXX {
                /**
                 * Key : 2052
                 * Value : 席会计
                 */

                private int Key;
                private String Value;

                public int getKey() {
                    return Key;
                }

                public void setKey(int Key) {
                    this.Key = Key;
                }

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }
            }

            public static class DescriptionBean {
                /**
                 * Key : 2052
                 * Value :
                 */

                private int Key;
                private String Value;

                public int getKey() {
                    return Key;
                }

                public void setKey(int Key) {
                    this.Key = Key;
                }

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }
            }

            public static class AddressBeanX {
                /**
                 * Key : 2052
                 * Value :
                 */

                private int Key;
                private String Value;

                public int getKey() {
                    return Key;
                }

                public void setKey(int Key) {
                    this.Key = Key;
                }

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }
            }

            public static class PostEntityBean {
                /**
                 * Id : 100001
                 * Seq : 1
                 * Post_Id : 101874
                 * Post : {"Id":101874,"msterID":101874,"Number":"GW000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}],"Name":[{"Key":2052,"Value":"财务总监"}],"FDept_Id":100023,"FDept":{"Id":100023,"msterID":100023,"Number":"BM000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}]}}
                 * StaffStartDate : 2019-02-11T00:00:00
                 * PostDept_Id : 100023
                 * PostDept : {"Id":100023,"msterID":100023,"MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部","FullName":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}],"Number":"BM000001","UseOrgId_Id":1,"UseOrgId":{"Id":1,"Number":"100","MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]},"FullName":[{"Key":2052,"Value":"财务部"}]}
                 * StaffDocumentStatus :
                 * StaffForbidStatus : A
                 * StaffDetails : 101875
                 * IsFirstPost : true
                 * WorkOrgId_Id : 1
                 * WorkOrgId : {"Id":1,"MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}],"Number":"100"}
                 */

                private int Id;
                private int           Seq;
                private int           Post_Id;
                private PostBean      Post;
                private String        StaffStartDate;
                private int           PostDept_Id;
                private PostDeptBean  PostDept;
                private String        StaffDocumentStatus;
                private String        StaffForbidStatus;
                private int           StaffDetails;
                private boolean       IsFirstPost;
                private int           WorkOrgId_Id;
                private WorkOrgIdBean WorkOrgId;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public int getSeq() {
                    return Seq;
                }

                public void setSeq(int Seq) {
                    this.Seq = Seq;
                }

                public int getPost_Id() {
                    return Post_Id;
                }

                public void setPost_Id(int Post_Id) {
                    this.Post_Id = Post_Id;
                }

                public PostBean getPost() {
                    return Post;
                }

                public void setPost(PostBean Post) {
                    this.Post = Post;
                }

                public String getStaffStartDate() {
                    return StaffStartDate;
                }

                public void setStaffStartDate(String StaffStartDate) {
                    this.StaffStartDate = StaffStartDate;
                }

                public int getPostDept_Id() {
                    return PostDept_Id;
                }

                public void setPostDept_Id(int PostDept_Id) {
                    this.PostDept_Id = PostDept_Id;
                }

                public PostDeptBean getPostDept() {
                    return PostDept;
                }

                public void setPostDept(PostDeptBean PostDept) {
                    this.PostDept = PostDept;
                }

                public String getStaffDocumentStatus() {
                    return StaffDocumentStatus;
                }

                public void setStaffDocumentStatus(String StaffDocumentStatus) {
                    this.StaffDocumentStatus = StaffDocumentStatus;
                }

                public String getStaffForbidStatus() {
                    return StaffForbidStatus;
                }

                public void setStaffForbidStatus(String StaffForbidStatus) {
                    this.StaffForbidStatus = StaffForbidStatus;
                }

                public int getStaffDetails() {
                    return StaffDetails;
                }

                public void setStaffDetails(int StaffDetails) {
                    this.StaffDetails = StaffDetails;
                }

                public boolean isIsFirstPost() {
                    return IsFirstPost;
                }

                public void setIsFirstPost(boolean IsFirstPost) {
                    this.IsFirstPost = IsFirstPost;
                }

                public int getWorkOrgId_Id() {
                    return WorkOrgId_Id;
                }

                public void setWorkOrgId_Id(int WorkOrgId_Id) {
                    this.WorkOrgId_Id = WorkOrgId_Id;
                }

                public WorkOrgIdBean getWorkOrgId() {
                    return WorkOrgId;
                }

                public void setWorkOrgId(WorkOrgIdBean WorkOrgId) {
                    this.WorkOrgId = WorkOrgId;
                }

                public static class PostBean {
                    /**
                     * Id : 101874
                     * msterID : 101874
                     * Number : GW000001
                     * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"财务总监"}]
                     * Name : [{"Key":2052,"Value":"财务总监"}]
                     * FDept_Id : 100023
                     * FDept : {"Id":100023,"msterID":100023,"Number":"BM000001","MultiLanguageText":[{"PkId":100001,"LocaleId":2052,"Name":"财务部"}],"Name":[{"Key":2052,"Value":"财务部"}]}
                     */

                    private int Id;
                    private int                                msterID;
                    private String                             Number;
                    private int                                FDept_Id;
                    private FDeptBean                          FDept;
                    private List<MultiLanguageTextBeanXXXXXXX> MultiLanguageText;
                    private List<NameBeanXXXXXXX>              Name;

                    public int getId() {
                        return Id;
                    }

                    public void setId(int Id) {
                        this.Id = Id;
                    }

                    public int getMsterID() {
                        return msterID;
                    }

                    public void setMsterID(int msterID) {
                        this.msterID = msterID;
                    }

                    public String getNumber() {
                        return Number;
                    }

                    public void setNumber(String Number) {
                        this.Number = Number;
                    }

                    public int getFDept_Id() {
                        return FDept_Id;
                    }

                    public void setFDept_Id(int FDept_Id) {
                        this.FDept_Id = FDept_Id;
                    }

                    public FDeptBean getFDept() {
                        return FDept;
                    }

                    public void setFDept(FDeptBean FDept) {
                        this.FDept = FDept;
                    }

                    public List<MultiLanguageTextBeanXXXXXXX> getMultiLanguageText() {
                        return MultiLanguageText;
                    }

                    public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXXXX> MultiLanguageText) {
                        this.MultiLanguageText = MultiLanguageText;
                    }

                    public List<NameBeanXXXXXXX> getName() {
                        return Name;
                    }

                    public void setName(List<NameBeanXXXXXXX> Name) {
                        this.Name = Name;
                    }

                    public static class FDeptBean {
                        /**
                         * Id : 100023
                         * msterID : 100023
                         * Number : BM000001
                         * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"财务部"}]
                         * Name : [{"Key":2052,"Value":"财务部"}]
                         */

                        private int Id;
                        private int                               msterID;
                        private String                            Number;
                        private List<MultiLanguageTextBeanXXXXXX> MultiLanguageText;
                        private List<NameBeanXXXXXX>              Name;

                        public int getId() {
                            return Id;
                        }

                        public void setId(int Id) {
                            this.Id = Id;
                        }

                        public int getMsterID() {
                            return msterID;
                        }

                        public void setMsterID(int msterID) {
                            this.msterID = msterID;
                        }

                        public String getNumber() {
                            return Number;
                        }

                        public void setNumber(String Number) {
                            this.Number = Number;
                        }

                        public List<MultiLanguageTextBeanXXXXXX> getMultiLanguageText() {
                            return MultiLanguageText;
                        }

                        public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXXX> MultiLanguageText) {
                            this.MultiLanguageText = MultiLanguageText;
                        }

                        public List<NameBeanXXXXXX> getName() {
                            return Name;
                        }

                        public void setName(List<NameBeanXXXXXX> Name) {
                            this.Name = Name;
                        }

                        public static class MultiLanguageTextBeanXXXXXX {
                            /**
                             * PkId : 100001
                             * LocaleId : 2052
                             * Name : 财务部
                             */

                            private int PkId;
                            private int    LocaleId;
                            private String Name;

                            public int getPkId() {
                                return PkId;
                            }

                            public void setPkId(int PkId) {
                                this.PkId = PkId;
                            }

                            public int getLocaleId() {
                                return LocaleId;
                            }

                            public void setLocaleId(int LocaleId) {
                                this.LocaleId = LocaleId;
                            }

                            public String getName() {
                                return Name;
                            }

                            public void setName(String Name) {
                                this.Name = Name;
                            }
                        }

                        public static class NameBeanXXXXXX {
                            /**
                             * Key : 2052
                             * Value : 财务部
                             */

                            private int Key;
                            private String Value;

                            public int getKey() {
                                return Key;
                            }

                            public void setKey(int Key) {
                                this.Key = Key;
                            }

                            public String getValue() {
                                return Value;
                            }

                            public void setValue(String Value) {
                                this.Value = Value;
                            }
                        }
                    }

                    public static class MultiLanguageTextBeanXXXXXXX {
                        /**
                         * PkId : 100001
                         * LocaleId : 2052
                         * Name : 财务总监
                         */

                        private int PkId;
                        private int    LocaleId;
                        private String Name;

                        public int getPkId() {
                            return PkId;
                        }

                        public void setPkId(int PkId) {
                            this.PkId = PkId;
                        }

                        public int getLocaleId() {
                            return LocaleId;
                        }

                        public void setLocaleId(int LocaleId) {
                            this.LocaleId = LocaleId;
                        }

                        public String getName() {
                            return Name;
                        }

                        public void setName(String Name) {
                            this.Name = Name;
                        }
                    }

                    public static class NameBeanXXXXXXX {
                        /**
                         * Key : 2052
                         * Value : 财务总监
                         */

                        private int Key;
                        private String Value;

                        public int getKey() {
                            return Key;
                        }

                        public void setKey(int Key) {
                            this.Key = Key;
                        }

                        public String getValue() {
                            return Value;
                        }

                        public void setValue(String Value) {
                            this.Value = Value;
                        }
                    }
                }

                public static class PostDeptBean {
                    /**
                     * Id : 100023
                     * msterID : 100023
                     * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"Name":"财务部","FullName":"财务部"}]
                     * Name : [{"Key":2052,"Value":"财务部"}]
                     * Number : BM000001
                     * UseOrgId_Id : 1
                     * UseOrgId : {"Id":1,"Number":"100","MultiLanguageText":[{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}],"Name":[{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]}
                     * FullName : [{"Key":2052,"Value":"财务部"}]
                     */

                    private int Id;
                    private int                                  msterID;
                    private String                               Number;
                    private int                                  UseOrgId_Id;
                    private UseOrgIdBeanX                        UseOrgId;
                    private List<MultiLanguageTextBeanXXXXXXXXX> MultiLanguageText;
                    private List<NameBeanXXXXXXXXX>              Name;
                    private List<FullNameBean>                   FullName;

                    public int getId() {
                        return Id;
                    }

                    public void setId(int Id) {
                        this.Id = Id;
                    }

                    public int getMsterID() {
                        return msterID;
                    }

                    public void setMsterID(int msterID) {
                        this.msterID = msterID;
                    }

                    public String getNumber() {
                        return Number;
                    }

                    public void setNumber(String Number) {
                        this.Number = Number;
                    }

                    public int getUseOrgId_Id() {
                        return UseOrgId_Id;
                    }

                    public void setUseOrgId_Id(int UseOrgId_Id) {
                        this.UseOrgId_Id = UseOrgId_Id;
                    }

                    public UseOrgIdBeanX getUseOrgId() {
                        return UseOrgId;
                    }

                    public void setUseOrgId(UseOrgIdBeanX UseOrgId) {
                        this.UseOrgId = UseOrgId;
                    }

                    public List<MultiLanguageTextBeanXXXXXXXXX> getMultiLanguageText() {
                        return MultiLanguageText;
                    }

                    public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXXXXXX> MultiLanguageText) {
                        this.MultiLanguageText = MultiLanguageText;
                    }

                    public List<NameBeanXXXXXXXXX> getName() {
                        return Name;
                    }

                    public void setName(List<NameBeanXXXXXXXXX> Name) {
                        this.Name = Name;
                    }

                    public List<FullNameBean> getFullName() {
                        return FullName;
                    }

                    public void setFullName(List<FullNameBean> FullName) {
                        this.FullName = FullName;
                    }

                    public static class UseOrgIdBeanX {
                        /**
                         * Id : 1
                         * Number : 100
                         * MultiLanguageText : [{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}]
                         * Name : [{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]
                         */

                        private int Id;
                        private String                              Number;
                        private List<MultiLanguageTextBeanXXXXXXXX> MultiLanguageText;
                        private List<NameBeanXXXXXXXX>              Name;

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

                        public List<MultiLanguageTextBeanXXXXXXXX> getMultiLanguageText() {
                            return MultiLanguageText;
                        }

                        public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXXXXX> MultiLanguageText) {
                            this.MultiLanguageText = MultiLanguageText;
                        }

                        public List<NameBeanXXXXXXXX> getName() {
                            return Name;
                        }

                        public void setName(List<NameBeanXXXXXXXX> Name) {
                            this.Name = Name;
                        }

                        public static class MultiLanguageTextBeanXXXXXXXX {
                            /**
                             * PkId : 1
                             * LocaleId : 2052
                             * Name : 上海冯源建设发展有限公司
                             */

                            private int PkId;
                            private int    LocaleId;
                            private String Name;

                            public int getPkId() {
                                return PkId;
                            }

                            public void setPkId(int PkId) {
                                this.PkId = PkId;
                            }

                            public int getLocaleId() {
                                return LocaleId;
                            }

                            public void setLocaleId(int LocaleId) {
                                this.LocaleId = LocaleId;
                            }

                            public String getName() {
                                return Name;
                            }

                            public void setName(String Name) {
                                this.Name = Name;
                            }
                        }

                        public static class NameBeanXXXXXXXX {
                            /**
                             * Key : 2052
                             * Value : 上海冯源建设发展有限公司
                             */

                            private int Key;
                            private String Value;

                            public int getKey() {
                                return Key;
                            }

                            public void setKey(int Key) {
                                this.Key = Key;
                            }

                            public String getValue() {
                                return Value;
                            }

                            public void setValue(String Value) {
                                this.Value = Value;
                            }
                        }
                    }

                    public static class MultiLanguageTextBeanXXXXXXXXX {
                        /**
                         * PkId : 100001
                         * LocaleId : 2052
                         * Name : 财务部
                         * FullName : 财务部
                         */

                        private int PkId;
                        private int    LocaleId;
                        private String Name;
                        private String FullName;

                        public int getPkId() {
                            return PkId;
                        }

                        public void setPkId(int PkId) {
                            this.PkId = PkId;
                        }

                        public int getLocaleId() {
                            return LocaleId;
                        }

                        public void setLocaleId(int LocaleId) {
                            this.LocaleId = LocaleId;
                        }

                        public String getName() {
                            return Name;
                        }

                        public void setName(String Name) {
                            this.Name = Name;
                        }

                        public String getFullName() {
                            return FullName;
                        }

                        public void setFullName(String FullName) {
                            this.FullName = FullName;
                        }
                    }

                    public static class NameBeanXXXXXXXXX {
                        /**
                         * Key : 2052
                         * Value : 财务部
                         */

                        private int Key;
                        private String Value;

                        public int getKey() {
                            return Key;
                        }

                        public void setKey(int Key) {
                            this.Key = Key;
                        }

                        public String getValue() {
                            return Value;
                        }

                        public void setValue(String Value) {
                            this.Value = Value;
                        }
                    }

                    public static class FullNameBean {
                        /**
                         * Key : 2052
                         * Value : 财务部
                         */

                        private int Key;
                        private String Value;

                        public int getKey() {
                            return Key;
                        }

                        public void setKey(int Key) {
                            this.Key = Key;
                        }

                        public String getValue() {
                            return Value;
                        }

                        public void setValue(String Value) {
                            this.Value = Value;
                        }
                    }
                }

                public static class WorkOrgIdBean {
                    /**
                     * Id : 1
                     * MultiLanguageText : [{"PkId":1,"LocaleId":2052,"Name":"上海冯源建设发展有限公司"},{"PkId":100001,"LocaleId":1033,"Name":" "},{"PkId":100002,"LocaleId":3076,"Name":" "}]
                     * Name : [{"Key":2052,"Value":"上海冯源建设发展有限公司"},{"Key":1033,"Value":" "},{"Key":3076,"Value":" "}]
                     * Number : 100
                     */

                    private int Id;
                    private String                                Number;
                    private List<MultiLanguageTextBeanXXXXXXXXXX> MultiLanguageText;
                    private List<NameBeanXXXXXXXXXX>              Name;

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

                    public List<MultiLanguageTextBeanXXXXXXXXXX> getMultiLanguageText() {
                        return MultiLanguageText;
                    }

                    public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXXXXXXX> MultiLanguageText) {
                        this.MultiLanguageText = MultiLanguageText;
                    }

                    public List<NameBeanXXXXXXXXXX> getName() {
                        return Name;
                    }

                    public void setName(List<NameBeanXXXXXXXXXX> Name) {
                        this.Name = Name;
                    }

                    public static class MultiLanguageTextBeanXXXXXXXXXX {
                        /**
                         * PkId : 1
                         * LocaleId : 2052
                         * Name : 上海冯源建设发展有限公司
                         */

                        private int PkId;
                        private int    LocaleId;
                        private String Name;

                        public int getPkId() {
                            return PkId;
                        }

                        public void setPkId(int PkId) {
                            this.PkId = PkId;
                        }

                        public int getLocaleId() {
                            return LocaleId;
                        }

                        public void setLocaleId(int LocaleId) {
                            this.LocaleId = LocaleId;
                        }

                        public String getName() {
                            return Name;
                        }

                        public void setName(String Name) {
                            this.Name = Name;
                        }
                    }

                    public static class NameBeanXXXXXXXXXX {
                        /**
                         * Key : 2052
                         * Value : 上海冯源建设发展有限公司
                         */

                        private int Key;
                        private String Value;

                        public int getKey() {
                            return Key;
                        }

                        public void setKey(int Key) {
                            this.Key = Key;
                        }

                        public String getValue() {
                            return Value;
                        }

                        public void setValue(String Value) {
                            this.Value = Value;
                        }
                    }
                }
            }

            public static class SHRMapEntityBean {
                /**
                 * Id : 100001
                 * FSHRNumber :
                 * FShrId :
                 * MultiLanguageText : []
                 * FSHRName : []
                 * FSHRStatus :
                 */

                private int Id;
                private String  FSHRNumber;
                private String  FShrId;
                private String  FSHRStatus;
                private List<?> MultiLanguageText;
                private List<?> FSHRName;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getFSHRNumber() {
                    return FSHRNumber;
                }

                public void setFSHRNumber(String FSHRNumber) {
                    this.FSHRNumber = FSHRNumber;
                }

                public String getFShrId() {
                    return FShrId;
                }

                public void setFShrId(String FShrId) {
                    this.FShrId = FShrId;
                }

                public String getFSHRStatus() {
                    return FSHRStatus;
                }

                public void setFSHRStatus(String FSHRStatus) {
                    this.FSHRStatus = FSHRStatus;
                }

                public List<?> getMultiLanguageText() {
                    return MultiLanguageText;
                }

                public void setMultiLanguageText(List<?> MultiLanguageText) {
                    this.MultiLanguageText = MultiLanguageText;
                }

                public List<?> getFSHRName() {
                    return FSHRName;
                }

                public void setFSHRName(List<?> FSHRName) {
                    this.FSHRName = FSHRName;
                }
            }

            public static class EmpinfoBankBean {
                /**
                 * Id : 100001
                 * Country_Id :
                 * Country : null
                 * MultiLanguageText : [{"PkId":100001,"LocaleId":2052,"OpenBankName":"上海市工商银行七宝支行"}]
                 * OpenBankName : [{"Key":2052,"Value":"上海市工商银行七宝支行"}]
                 * BankCode : 6222021001081006238
                 * BankHolder : 席会计
                 * CurrencyId_Id : 0
                 * CurrencyId : null
                 * IsDefault : true
                 * Description :
                 * OpenAddressRec :
                 * CNAPS :
                 * BankTypeRec_Id : 0
                 * BankTypeRec : null
                 * FTextBankDetail :
                 */

                private int Id;
                private String                                 Country_Id;
                private Object                                 Country;
                private String                                 BankCode;
                private String                                 BankHolder;
                private int                                    CurrencyId_Id;
                private Object                                 CurrencyId;
                private boolean                                IsDefault;
                private String                                 Description;
                private String                                 OpenAddressRec;
                private String                                 CNAPS;
                private int                                    BankTypeRec_Id;
                private Object                                 BankTypeRec;
                private String                                 FTextBankDetail;
                private List<MultiLanguageTextBeanXXXXXXXXXXX> MultiLanguageText;
                private List<OpenBankNameBean>                 OpenBankName;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getCountry_Id() {
                    return Country_Id;
                }

                public void setCountry_Id(String Country_Id) {
                    this.Country_Id = Country_Id;
                }

                public Object getCountry() {
                    return Country;
                }

                public void setCountry(Object Country) {
                    this.Country = Country;
                }

                public String getBankCode() {
                    return BankCode;
                }

                public void setBankCode(String BankCode) {
                    this.BankCode = BankCode;
                }

                public String getBankHolder() {
                    return BankHolder;
                }

                public void setBankHolder(String BankHolder) {
                    this.BankHolder = BankHolder;
                }

                public int getCurrencyId_Id() {
                    return CurrencyId_Id;
                }

                public void setCurrencyId_Id(int CurrencyId_Id) {
                    this.CurrencyId_Id = CurrencyId_Id;
                }

                public Object getCurrencyId() {
                    return CurrencyId;
                }

                public void setCurrencyId(Object CurrencyId) {
                    this.CurrencyId = CurrencyId;
                }

                public boolean isIsDefault() {
                    return IsDefault;
                }

                public void setIsDefault(boolean IsDefault) {
                    this.IsDefault = IsDefault;
                }

                public String getDescription() {
                    return Description;
                }

                public void setDescription(String Description) {
                    this.Description = Description;
                }

                public String getOpenAddressRec() {
                    return OpenAddressRec;
                }

                public void setOpenAddressRec(String OpenAddressRec) {
                    this.OpenAddressRec = OpenAddressRec;
                }

                public String getCNAPS() {
                    return CNAPS;
                }

                public void setCNAPS(String CNAPS) {
                    this.CNAPS = CNAPS;
                }

                public int getBankTypeRec_Id() {
                    return BankTypeRec_Id;
                }

                public void setBankTypeRec_Id(int BankTypeRec_Id) {
                    this.BankTypeRec_Id = BankTypeRec_Id;
                }

                public Object getBankTypeRec() {
                    return BankTypeRec;
                }

                public void setBankTypeRec(Object BankTypeRec) {
                    this.BankTypeRec = BankTypeRec;
                }

                public String getFTextBankDetail() {
                    return FTextBankDetail;
                }

                public void setFTextBankDetail(String FTextBankDetail) {
                    this.FTextBankDetail = FTextBankDetail;
                }

                public List<MultiLanguageTextBeanXXXXXXXXXXX> getMultiLanguageText() {
                    return MultiLanguageText;
                }

                public void setMultiLanguageText(List<MultiLanguageTextBeanXXXXXXXXXXX> MultiLanguageText) {
                    this.MultiLanguageText = MultiLanguageText;
                }

                public List<OpenBankNameBean> getOpenBankName() {
                    return OpenBankName;
                }

                public void setOpenBankName(List<OpenBankNameBean> OpenBankName) {
                    this.OpenBankName = OpenBankName;
                }

                public static class MultiLanguageTextBeanXXXXXXXXXXX {
                    /**
                     * PkId : 100001
                     * LocaleId : 2052
                     * OpenBankName : 上海市工商银行七宝支行
                     */

                    private int PkId;
                    private int    LocaleId;
                    private String OpenBankName;

                    public int getPkId() {
                        return PkId;
                    }

                    public void setPkId(int PkId) {
                        this.PkId = PkId;
                    }

                    public int getLocaleId() {
                        return LocaleId;
                    }

                    public void setLocaleId(int LocaleId) {
                        this.LocaleId = LocaleId;
                    }

                    public String getOpenBankName() {
                        return OpenBankName;
                    }

                    public void setOpenBankName(String OpenBankName) {
                        this.OpenBankName = OpenBankName;
                    }
                }

                public static class OpenBankNameBean {
                    /**
                     * Key : 2052
                     * Value : 上海市工商银行七宝支行
                     */

                    private int Key;
                    private String Value;

                    public int getKey() {
                        return Key;
                    }

                    public void setKey(int Key) {
                        this.Key = Key;
                    }

                    public String getValue() {
                        return Value;
                    }

                    public void setValue(String Value) {
                        this.Value = Value;
                    }
                }
            }
        }
    }
}
