package com.gcit.lms.dao;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Branch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Created by shash on 2/25/2017.
 */
public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>>{

   
    @SuppressWarnings("unchecked")
    //Reading BOOK COPIES BY BRANCH ID
    public List<BookCopies> readBookCopiesByBranchID(Integer branchId) throws ClassNotFoundException, SQLException {
        List<BookCopies> brr =  template.query("select * from tbl_book_copies where branchId = ?", new Object[] {branchId}, this);
        if(brr!=null && brr.size() >0){
            return brr;
        }
        return null;
    }
    public BookCopies getBookCopies(Integer bookId, Integer branchId){
        List<BookCopies> copies = new ArrayList<BookCopies>();
        copies = template.query("select * from tbl_book_copies where bookId = ? and branchId = ?", new Object[]{bookId, branchId}, this);
        if(copies != null && !copies.isEmpty()){
            return copies.get(0);
        }
        return null;
    }
  //Updating authors by name.
  	public void updateBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
  		 template.update("UPDATE tbl_book_copies SET bookId = ?, branchId = ?, noOfCopies = ? WHERE bookId = ? AND branchId= ?", new Object[] {bc.getBook().getBookId(),bc.getBranch().getBranchId(),bc.getNoOfCopies(),bc.getBook().getBookId(),bc.getBranch().getBranchId()});
  		}


   //DISPLAYS BOOK COPIES BY BRANCH ID 
public List<BookCopies> displayCopies(Integer branchId) throws SQLException, ClassNotFoundException {
        return  template.query("select * from tbl_book_copies where branchId = ?", new Object[] {branchId}, this);
}
    @Override
    public List<BookCopies> extractData(ResultSet rs) throws SQLException {
        List<BookCopies> bookcopies = new ArrayList<BookCopies>();

        while(rs.next()){
            BookCopies bc = new BookCopies();
            bc.setNoOfCopies(rs.getInt("noOfCopies"));

            Book b = new Book();
            b.setBookId(rs.getInt("bookId"));
            bc.setBook(b);

            Branch lib = new Branch();
            lib.setBranchId(rs.getInt("branchId"));
            bc.setBranch(lib);

            //RETRIEVE THE BOOK DETIALS HERE

            bookcopies.add(bc);

        }
        return bookcopies;
    }
   
}


