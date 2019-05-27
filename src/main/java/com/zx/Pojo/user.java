package com.zx.Pojo;

import com.zx.annotation.StringToUnderLine;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 17:58
 * @note
 */
@Data
@AllArgsConstructor
public class user {
    @StringToUnderLine("user_name")
    private String userName;
}
