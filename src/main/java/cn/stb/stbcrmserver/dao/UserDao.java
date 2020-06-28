package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    int addUser(User user);

    int modifyUser(User user);

    int deleteUserById(String userId);

    List<User> queryUserByOperatorIdAndUserType(Map map);
}
