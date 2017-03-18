package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;


public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>>{
	public List<BookLoans> readAllBookLoans() throws ClassNotFoundException, SQLException{
        return  template.query("select * from tbl_library_loans",this);
    }

    public List<Book> readBookBycardNo(Integer cardNo, Integer branchId) throws ClassNotFoundException, SQLException{
    	List<Book> books = new ArrayList<Book>();
       books = (List<Book>) template.queryForList("select * from tbl_book as b where b.bookId in " +
               "(select bookId from tbl_book_loans where cardNo=? and branchId = ? and dateIn is NULL)", new Object[] {cardNo, branchId}, Book.class );
   
       return books;
    }
    public void deleteBookLoan(Integer cardNo, Integer bookId){
	    template.update("delete from tbl_book_loans where cardNo = ? , bookId = ?", new Object[]{cardNo, bookId});
    }
    public void deleteBookLoan1(Integer bookId, Integer branchId, Integer cardNo){
        template.update("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?", new Object[]{bookId, branchId, cardNo});
    }
    public void checkIn(Integer bookId, Integer cardNo) throws ClassNotFoundException, SQLException {
       
       Integer branchId = template.queryForObject("select branchId from tbl_book_loans where bookId=? and cardNo = ? and dateIn is NULL", new Object[] {bookId,cardNo}, Integer.class);
        System.out.println("branch id: "+branchId);
        template.update("delete from tbl_book_loans where bookId=? and branchId=? and cardNo = ?",new Object[] {bookId,branchId,cardNo});
        System.out.println("book returned.");
       template.update("update tbl_book_copies set noOfCopies = noOfCopies+1 where bookId=? and branchId =?",new Object[] {bookId,branchId});
    }

    public void checkOut(int bookId, int cardNo, int branchId) throws ClassNotFoundException, SQLException {
        System.out.println(bookId + "incheckin");
        System.out.println(cardNo + "incheckin");
        System.out.println(branchId + "incheckin");

       template.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate ) "
                + "values( ?, ?, ?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 15 DAY))",new Object[] {bookId,branchId,cardNo});
        System.out.println("entry added to the book loans table");
       template.update("update tbl_book_copies set noOfCopies = noOfCopies-1 where bookId=? and branchId =?", new Object[] {bookId,branchId});

    }


    @Override
    public List<BookLoans> extractData(ResultSet rs) throws SQLException {

    	List<BookLoans> bloans = new ArrayList<BookLoans>();
        while(rs.next()){
            BookLoans bl = new BookLoans();
            Book b = new Book();
            Branch br = new Branch();
            Borrower borr = new Borrower();
            b.setBookId(rs.getInt("bookId"));
            bl.setBook(b);
            borr.setCardNo(rs.getInt("cardNo"));
            bl.setBorrower(borr);
            br.setBranchId(rs.getInt("branchId"));
            bl.setBranch(br);
            bl.setDateIn(rs.getDate("dateIn"));
            bl.setDateOut(rs.getDate("dateOut"));
            bl.setDueDate(rs.getDate("dueDate"));
            bloans.add(bl);
        }

        return bloans;
    }


    

}

