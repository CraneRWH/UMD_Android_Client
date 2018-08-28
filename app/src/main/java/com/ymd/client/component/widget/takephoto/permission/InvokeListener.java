package com.ymd.client.component.widget.takephoto.permission;


import com.ymd.client.component.widget.takephoto.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
