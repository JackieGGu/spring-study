package cn.jackiegu.spring.security.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 角色权限实体类
 *
 * @author JackieGu
 * @date 2021/7/8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionDO {

    private Long roleId;

    private String roleName;

    private String roleCode;

    private Long permissionId;

    private String permissionName;

    private String permissionUrl;

}
