//package com.chinaunicom.sirbee.config.shiro;
//
//import com.chinaunicom.sirbee.dao.model.Menu;
//import com.chinaunicom.sirbee.dao.model.Role;
//import com.chinaunicom.sirbee.dao.model.User;
//import com.chinaunicom.sirbee.dao.model.result.ResultDTO;
//import com.chinaunicom.sirbee.service.ShiroViewService;
//import com.chinaunicom.sirbee.service.UserSingleTableService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * 自定义实现 ShiroRealm，包含认证和授权两大模块
// *
// * @author MrBird
// */
//@Component("shiroRealm")
//public class ShiroRealm extends AuthorizingRealm {
//
//    @Autowired
//    private UserSingleTableService userSingleTableService;
//    @Autowired
//    private ShiroViewService shiroViewService;
//
//
//    /**
//     * 授权模块，获取用户角色和权限
//     *
//     * @param principal principal
//     * @return AuthorizationInfo 权限信息
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        String userId = user.getUserId();
//
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//
//        // 获取用户角色集
//        ResultDTO userRoleResult = shiroViewService.findUserRole(userId);
//        List<Role> roleList = (List<Role>) userRoleResult.getData();
//        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
//        simpleAuthorizationInfo.setRoles(roleSet);
//
//        // 获取用户权限集
//        ResultDTO userPermissionsResult = shiroViewService.findUserPermissions(userId);
//        List<Menu> permissionList = (List<Menu>) userPermissionsResult.getData();
////        List<Menu> permissionList = this.menuService.findUserPermissions(userName);
//        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
//        simpleAuthorizationInfo.setStringPermissions(permissionSet);
//        return simpleAuthorizationInfo;
//    }
//
//    /**
//     * 用户认证
//     *
//     * @param token AuthenticationToken 身份认证 token
//     * @return AuthenticationInfo 身份认证信息
//     * @throws AuthenticationException 认证相关异常
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//
//        // 获取用户输入的用户名和密码
//        String userId = (String) token.getPrincipal();
//        String password = new String((char[]) token.getCredentials());
//
//        // 通过用户名到数据库查询用户信息
//        ResultDTO resultDTO = userSingleTableService.userJudgeLogin(userId, password);
//        if (!(resultDTO.getCode().equals("0"))){
//            throw new UnknownAccountException("用户名或密码错误！");
//        }
//
//        User user= (User) resultDTO.getData();
//        return new SimpleAuthenticationInfo(user,password,userId);
//
//
//
////        User user = this.userService.findByName(userName);
////        if (user == null)
////            throw new UnknownAccountException("用户名或密码错误！");
////        if (!password.equals(user.getPassword()))
////            throw new IncorrectCredentialsException("用户名或密码错误！");
////        if (User.STATUS_LOCK.equals(user.getStatus()))
////            throw new LockedAccountException("账号已被锁定,请联系管理员！");
////        return new SimpleAuthenticationInfo(user, password, getName());
//    }
//
//    /**
//     * 清除权限缓存
//     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
//     * 然后调用其clearCache方法。
//     */
//    public void clearCache() {
//        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
//        super.clearCache(principals);
//    }
//
//}
