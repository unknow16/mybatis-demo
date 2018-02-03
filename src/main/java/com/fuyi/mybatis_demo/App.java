package com.fuyi.mybatis_demo;

import java.io.InputStream;
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
		User user = openSession.selectOne("test.findById", 3);
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
}
