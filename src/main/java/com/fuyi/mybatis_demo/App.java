package com.fuyi.mybatis_demo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.fuyi.mybatis_demo.domain.User;
import com.fuyi.mybatis_demo.mapper.UserMapper;

/**
 * Hello world!
 *
 */
public class App {
	public SqlSessionFactory sessionFactory = null;

	@Before
	public void testBefore() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testSelectOne() {
		SqlSession openSession = sessionFactory.openSession();
		User user = openSession.selectOne("com.fuyi.mybatis_demo.mapper.UserMapper.findById", 3);
		System.out.println(user);
		
		/*int count = openSession.selectOne("test.selectCount");
		System.out.println(count);*/
	}
	
	//删除多条，整形数组
	@Test
	public void testBatchDeleteByIds() {
		int[] ids = new int[]{1, 2};
		SqlSession session = sessionFactory.openSession();
		session.delete("test.batchDeleteByIds", ids);
		session.commit();
	}
	
	//删除多条，List
	@Test
	public void batchDeleteListByIds() {
		List<String> ids = new ArrayList<String>();
		ids.add("1");
		ids.add("22");
		
		SqlSession session = sessionFactory.openSession();
		session.delete("test.batchDeleteListByIds", ids);
		session.commit();
	}
	
	//删除多条，Map
	@Test
	public void batchDeleteMapByIds() {
		Map<String, int[]> map = new HashMap<String, int[]>();
		int[] ids = new int[]{1, 44};
		map.put("ids", ids);
		
		SqlSession session = sessionFactory.openSession();
		session.delete("test.batchDeleteMapByIds", map);
		session.commit();
	}
	
	//Mapper接口开发
	@Test
	public void testMapper() {
		SqlSession openSession = sessionFactory.openSession();
		UserMapper mapper = openSession.getMapper(UserMapper.class);
		User user = mapper.findById(5);
		System.out.println(user);
		
		openSession.close();
		
		openSession = sessionFactory.openSession();
		User user1 = openSession.getMapper(UserMapper.class).findById(5);
		System.out.println(user1);
	}
	
	@Test
	public void testInsert() {
		SqlSession openSession = sessionFactory.openSession();
		
		User user = new User();
		user.setName("haha");
		user.setAge(123);
		//user.setId(444);
		openSession.insert("com.fuyi.mybatis_demo.mapper.UserMapper.insert", user);
		openSession.commit();
		
		System.out.println(user.getId());
	}
	
	@Test
	public void testJDBC() {
		Connection connection = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			// 1. 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			
			// 2. 获取连接
			String url = "jdbc:mysql://localhost:3306/mybatis_demo";
			connection = DriverManager.getConnection(url, "root", "123456");
			
			// 3. 创建pstat,并设置参数，以1开始
			String sql = "select * from tb_user where id = ?";
			pstat = connection.prepareStatement(sql);
			pstat.setString(1, "aaa");
			pstat.setInt(2, 123);
			
			/**
			 * 4. 执行sql
			 * 
			 * executeQuery(): 返回结果集ResultSet
			 * execute(): 返回是否执行成功boolean
			 */
			rs = pstat.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			// 5. 关闭相关资源
			try {
				if(connection != null) connection.close();
				if(pstat != null) pstat.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
