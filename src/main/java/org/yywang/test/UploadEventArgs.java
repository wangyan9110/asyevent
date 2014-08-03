package org.yywang.test;

import org.yywang.asyevent.base.EventArgs;

/**
 * 上传图片Upload
 */
public class UploadEventArgs extends EventArgs {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
