package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{

	//ADD AUTHOR
    public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
       template.update("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
    }
    public Integer getAuthorIdByName(String authorName){
        authorName ="%"+authorName+"%";
      return  template.queryForObject("select authorId from tbl_author where authorName like ? ", new Object[] {authorName}, Integer.class);
    }
    public List<Author> getAuthorByBook(Integer bookId){
        return template.query("select * from tbl_author as a INNER JOIN tbl_book_authors as b on a.authorId = b.authorId where b.bookId = ?", new
                Object[] {bookId}, this);
    }

    //ADD TABLE BOOK AUTHORS
    public void addTblAuthorBookList(Integer authorId, Integer[] bookIds){
        for(int i=0;i<bookIds.length;i++){
            template.update("insert into tbl_book_authors(bookId, authorId) values(?, ?) ", new Object[]{bookIds[i], authorId});

        }
    }
    public void addTblBookAuthors(Integer bookId, Integer authorId){
        template.update("insert into tbl_book_authors(bookId, authorId) values(?, ?) ", new Object[]{bookId, authorId});
    }

    //UPDATE AUTHOR
    public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
        template.update("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
    }

    //DELETE AUTHOR
    public void deleteAuthor(Integer authorId) throws ClassNotFoundException, SQLException{
      template.update("delete from tbl_author where authorId = ?", new Object[] {authorId});
    }

    //READ ALL AUTHORS
    public List<Author> readAllAuthors(int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        return template.query("select * from tbl_author", this);
    }
    public List<Author> readAuthorbyBook(Integer bookId){
        List<Author> authors = template.query("select * from tbl_author where authorId IN(select bookId from tbl_book_authors where bookId= ?)", new Object[] {bookId}, this);
        return authors;
    }

    //READ AUTHORS BY NAME
    public List<Author> readAuthorsByName(String name) throws ClassNotFoundException, SQLException{
        //setPageNo(pageNo);
        name="%"+name+"%";
        return  template.query("select * from tbl_author where authorName like ?", new Object[] {name}, this);
    }

    //READ AUTHOR BY ID
    public Author readAuthorsByID(Integer authorId) throws ClassNotFoundException, SQLException{
        List<Author> authors = template.query("select * from tbl_author where authorId =?", new Object[] {authorId}, this);
        if(authors!=null && authors.size()>0){
            return authors.get(0);
        }
        return null;
    }


   //GET COUNT OF AUTHORS
    public Integer getCount() throws ClassNotFoundException, SQLException{
    	Integer getCount = 0;
       getCount = template.queryForObject("select count(*) from tbl_author", Integer.class );
       return getCount;
    }

    public Integer addAuthorWithID(Author author) throws ClassNotFoundException, SQLException{
        final String authorName = author.getAuthorName();
        final String INSERT_SQL = "insert into tbl_author (authorName) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "bookId" });
                ps.setString(1, authorName);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }


    //READ AUTHORS BY NAME
    public List<Author> readAuthorsByAuthorName(String name,int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        name="%"+name+"%";
        return template.query("select * from tbl_author where authorName like ?", new Object[] {name}, this);
    }

    //READ AUTHORS BY BOOK TITLE
    public List<Author> readAuthorsByBookTitle(String name,int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        name="%"+name+"%";
        return (List<Author>) template.query("select * from tbl_author a inner join tbl_book_authors ba on ba.authorId = a.authorId inner join tbl_book b on b.bookId = ba.bookId where b.title like ?", new Object[] {name}, this);
    }

    //READ AUTHORS BY NAME OR TITLE
    public List<Author> readAuthorsByBookTitleorName(String name,int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        name="%"+name+"%";
        return template.query("select * from tbl_author a inner join tbl_book_authors ba on ba.authorId = a.authorId inner join tbl_book b on b.bookId = ba.bookId where b.title like ? or a.authorName like ?", new Object[] {name,name}, this);
    }

    @Override
    public List<Author> extractData(ResultSet rs) throws SQLException {
        List<Author> authors = new ArrayList<Author>();
        while(rs.next()){
            Author a = new Author();
            a.setAuthorId(rs.getInt("authorId"));
            a.setAuthorName(rs.getString("authorName"));
            authors.add(a);

        }
        return authors;
    }

}


