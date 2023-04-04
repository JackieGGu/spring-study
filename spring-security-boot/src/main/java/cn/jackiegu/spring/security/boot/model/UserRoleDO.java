package cn.jackiegu.spring.security.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户角色实体类
 *
 * @author JackieGu
 * @date 2021/7/8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDO {

    private Long userId;

    private String username;

    private String realName;

    private String nickName;

    private Long roleId;

    private String RoleName;

    private String RoleCode;
}
