package com.fun.web.controller.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fun.common.core.domain.R;
import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fun.common.core.controller.BaseController;
import com.fun.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 用户测试方法
 *
 * @author mrdjun
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/test/user")
public class TestController extends BaseController {
    private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();

    static {
        users.put(1, new UserEntity(1, "admin1", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "admin2", "admin123", "15666666666"));
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public R userList() {
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        return R.success(userList);
    }

    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{userId}")
    public R getUser(@PathVariable Integer userId) {
        if (!users.isEmpty() && users.containsKey(userId)) {
            return R.success(users.get(userId));
        } else {
            return error("用户不存在");
        }
    }

    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
    @PostMapping("/save")
    public R save(UserEntity user) {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
            return error("用户ID不能为空");
        }
        return R.success(users.put(user.getUserId(), user));
    }

    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
    @PutMapping("/update")
    public R update(UserEntity user) {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
            return error("用户ID不能为空");
        }
        if (users.isEmpty() || !users.containsKey(user.getUserId())) {
            return error("用户不存在");
        }
        users.remove(user.getUserId());
        return R.success(users.put(user.getUserId(), user));
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{userId}")
    public R delete(@PathVariable Integer userId) {
        if (!users.isEmpty() && users.containsKey(userId)) {
            users.remove(userId);
            return success();
        } else {
            return error("用户不存在");
        }
    }
}

@Data
@ApiModel("用户实体")
class UserEntity {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户手机")
    private String mobile;

    public UserEntity() {

    }

    public UserEntity(Integer userId, String username, String password, String mobile) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }
}
