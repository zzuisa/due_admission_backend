package cn.zzuisa.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginMember {


    @ApiModelProperty(example = "admin",value = "账号")
    private String username;
    @ApiModelProperty(example = "admin",value = "密码")
    private String password;
}
