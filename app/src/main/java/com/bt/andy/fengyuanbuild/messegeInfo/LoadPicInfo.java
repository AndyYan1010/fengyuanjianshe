package com.bt.andy.fengyuanbuild.messegeInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/3/6 17:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LoadPicInfo {

    /**
     * status : 01
     * message : 成功
     * FATTACHMENTNAME : IMG_20180129_095450.jpg
     * FEXTNAME : .jpg
     * bufferstr : wD9k=
     */

    private String status;
    private String message;
    private String FATTACHMENTNAME;
    private String FEXTNAME;
    private String bufferstr;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFATTACHMENTNAME() {
        return FATTACHMENTNAME;
    }

    public void setFATTACHMENTNAME(String FATTACHMENTNAME) {
        this.FATTACHMENTNAME = FATTACHMENTNAME;
    }

    public String getFEXTNAME() {
        return FEXTNAME;
    }

    public void setFEXTNAME(String FEXTNAME) {
        this.FEXTNAME = FEXTNAME;
    }

    public String getBufferstr() {
        return bufferstr;
    }

    public void setBufferstr(String bufferstr) {
        this.bufferstr = bufferstr;
    }
}
