package com.ruoyi.common.utils;

import com.github.f4b6a3.ulid.UlidCreator;

/**
 * @author WangJiang
 * @since 2023/1/9 16:37
 */
public class UlidUtil {

    public static String getUlid() {
        return UlidCreator.getUlid().toString();
    }

}
