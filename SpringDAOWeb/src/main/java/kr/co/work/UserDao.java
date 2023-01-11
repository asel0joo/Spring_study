package kr.co.work;

import java.sql.SQLException;


public interface UserDao {

	User selectUser(String id);
	void deleteAll() throws SQLException;
	int insertUser(User user) throws SQLException;
	int updateUser(User user);
}
