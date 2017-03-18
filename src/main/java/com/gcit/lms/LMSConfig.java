package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;

@EnableTransactionManagement
@Configuration
public class LMSConfig {
	  private String driver = "com.mysql.jdbc.Driver";
	    private String url = "jdbc:mysql://localhost/library";
	    private String user =  "root";
	    private String password = "shashi";
	    
	    @Bean
	    public BasicDataSource datasource()
	    {
	    	BasicDataSource ds = new BasicDataSource();
	    	ds.setDriverClassName(driver);
	    	ds.setUrl(url);
	    	ds.setUsername(user);
	    	ds.setPassword(password);
	    	return ds;
	    	
	    }
	    @Bean
	    public JdbcTemplate tempate()
	    {
	    	JdbcTemplate template = new JdbcTemplate(datasource());
	    	return template;
	    	
	    }
	    @Bean
	    public PlatformTransactionManager txManager()
	    {
	    	DataSourceTransactionManager tx = new DataSourceTransactionManager(datasource());
	    	return tx;
	    }
	    
	    @Bean
	    public AuthorDAO adao(){
	    	return new AuthorDAO();
	    }
	
	    @Bean
	    public BookCopiesDAO bcdao(){
	    	return new BookCopiesDAO();
	    }
	    
	    @Bean
	    public BookDAO bdao(){
	    	return new BookDAO();
	    }
	    
	    @Bean
	    public BookLoansDAO bldao(){
	    	return new BookLoansDAO();
	    }
	    
	    @Bean
	    public BorrowerDAO bodao(){
	    	return new BorrowerDAO();
	    }
	    
	    @Bean
	    public BranchDAO brdao(){
	    	return new BranchDAO();
	    }
	    
	    @Bean
	    public GenreDAO gdao(){
	    	return new GenreDAO();
	    }
	    
	    @Bean
	    public PublisherDAO pdao(){
	    	return new PublisherDAO();
	    }
	    

	    }


