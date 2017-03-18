package com.gcit.lms.dao;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Publisher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Created by shash on 2/25/2017.
 */
public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>>{

   //ADD PUBLISHER
    public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
        template.update("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)", new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone()});
    }


    //UPDATE PUBLISHER
    public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException{

    	 template.update("update tbl_publisher set publisherName = ?,publisherAddress=?,publisherPhone=? where publisherId = ?", new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(),publisher.getPublisherPhone(),publisher.getPublisherId()});

    }
    //DELETE PUBLISHER
    public void deletePublisher(Integer publisherId) throws ClassNotFoundException, SQLException{
    	 template.update("delete from tbl_publisher where publisherId = ?", new Object[] {publisherId});
    }
    //READ ALL PUBLISHERS
    public List<Publisher> readAllPublishers(int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        return  template.query("select * from tbl_publisher", this);
    }
   //READ PUBLISHERS BY ID
    public Publisher readPublisherByID(Integer publisherId) throws ClassNotFoundException, SQLException {
      List<Publisher> publishers = (List<Publisher>)  template.query("select * from tbl_publisher where publisherId=?",new Object[] {publisherId}, this);
        if(publishers != null && publishers.size()>0){
            return publishers.get(0);
        }
        return null;
    }

    //READ AUTHORS BY NAME
    public List<Publisher> readPublishersByName(String name,int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        name="%"+name+"%";
        return (List<Publisher>) template.query("select * from tbl_publisher where publisherName like ?", new Object[] {name}, this);
    }

    //GET COUNT OF NUMBER OF PUBLISHERS
    public Integer getCount() throws ClassNotFoundException, SQLException{
       Integer getCount = template.queryForObject("select count(*) from tbl_publisher", Integer.class);
       return getCount;
    }

    List<Publisher> publisher =  new ArrayList<Publisher>();
    @Override
    public List<Publisher> extractData(ResultSet rs) throws SQLException {

        while(rs.next()){
            Publisher pub = new Publisher();
            pub.setPublisherId(rs.getInt("publisherId"));
            pub.setPublisherName(rs.getString("publisherName"));
            pub.setPublisherAddress(rs.getString("publisherAddress"));
            pub.setPublisherPhone(rs.getString("publisherPhone"));

            publisher.add(pub);
        }
        return publisher;
    }

}

