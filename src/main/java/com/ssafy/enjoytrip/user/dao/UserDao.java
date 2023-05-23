package com.ssafy.enjoytrip.user.dao;

import com.ssafy.enjoytrip.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserDao {

    void insert(User user);

    int existsByEmail(String email);

    int existsByNickname(String nickname);

    User findById(int id);

    int update(User user);

    int updatePwd(User user);

    int deleteById(int id);

    User findByEmail(String email);

    public void saveRefreshToken(Map<String, Object> map) throws SQLException;

    public Object getRefreshToken(int userid) throws SQLException;

    public void deleteRefreshToken(Map<String, Object> map) throws SQLException;
}
