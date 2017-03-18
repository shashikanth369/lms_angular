package com.gcit.lms;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.gcit.lms.dao.*;
import com.gcit.lms.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests for the application home page.
 */
@CrossOrigin(origins="http://localhost:8000")
@RestController
public class HomeController {


	@Autowired
	AuthorDAO adao;

	@Autowired
	BookDAO bdao;

	@Autowired
	BookCopiesDAO bcdao;

	@Autowired
	BookLoansDAO bldao;

	@Autowired
	BorrowerDAO bodao;

	@Autowired
	BranchDAO brdao;

	@Autowired
	GenreDAO gdao;

	@Autowired
	PublisherDAO pdao;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthors(Locale locale, Model model) {
		List<Author> authors = new ArrayList<Author>();
		try {
			authors =  adao.readAllAuthors(1);
			if(authors != null && !authors.isEmpty()) {
				for (Author a : authors) {
					a.setBooks(bdao.readBookbyAuthor(a.getAuthorId()));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;

	}
	@Transactional
	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces = "application/json")
	public Author initAuthor() throws SQLException, ClassNotFoundException {
		return new Author();
	}
	@Transactional
	@RequestMapping(value = "/initBook", method = RequestMethod.GET, produces = "application/json")
	public Book initBook() throws SQLException, ClassNotFoundException {
		return new Book();
	}
	@Transactional
	@RequestMapping(value = "/initPublisher", method = RequestMethod.GET, produces = "application/json")
	public Publisher initPublisher() throws SQLException, ClassNotFoundException {
		return new Publisher();
	}
	@Transactional
	@RequestMapping(value = "/initGenre", method = RequestMethod.GET, produces = "application/json")
	public Genre initGenre() throws SQLException, ClassNotFoundException {
		return new Genre();
	}
	@Transactional
	@RequestMapping(value = "/initBranch", method = RequestMethod.GET, produces = "application/json")
	public Branch initBranch() throws SQLException, ClassNotFoundException {
		return new Branch();
	}
	@Transactional
	@RequestMapping(value = "/initBorrower", method = RequestMethod.GET, produces = "application/json")
	public Borrower initBorrower() throws SQLException, ClassNotFoundException {
		return new Borrower();
	}
	@Transactional
	@RequestMapping(value = "/initBookLoans", method = RequestMethod.GET, produces = "application/json")
	public BookLoans initBookLoans() throws SQLException, ClassNotFoundException {
		return new BookLoans();
	}
	@Transactional
	@RequestMapping(value = "/initBookCopies", method = RequestMethod.GET, produces = "application/json")
	public BookCopies initBookCopies() throws SQLException, ClassNotFoundException {
		return new BookCopies();
	}
	@Transactional
	@RequestMapping(value = "/deleteAuthor/{authorId}", method = RequestMethod.GET)
	public void deleteAuthor(@PathVariable Integer authorId) {
		try {

			System.out.println("*******"+authorId);
			adao. deleteAuthor(authorId);
			System.out.println("authorId");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/readAuthorById/{authorId}", method = RequestMethod.GET, produces = "application/json")
	public Author readAuthorById(@PathVariable int authorId){
		Author author = new Author();
		try {
			author = adao.readAuthorsByID(authorId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return author;
	}
	@RequestMapping(value = "/readAuthorByBookTitle/{title}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> readAuthorByBookTitle(@PathVariable String title){
		List<Author> authors = new ArrayList<Author>();
		try {
			authors = adao.readAuthorsByBookTitle(title, 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}
	@RequestMapping(value = "/getAuthorByBookId/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> getAuthorByBookId(@PathVariable Integer bookId){
		List<Author> authors = new ArrayList<Author>();
		authors = adao.getAuthorByBook(bookId);
		return authors;
	}

	@RequestMapping(value = "/searchauthors/{authorName}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthorsByName(@PathVariable String authorName) throws ClassNotFoundException, SQLException {
		//System.out.println("PageNo: "+pageNo);
		List<Author> authors = adao.readAuthorsByAuthorName(authorName, 1);
		if (authors != null && !authors.isEmpty()) {
			for (Author a : authors) {
				a.setBooks(bdao.readBookbyAuthor(a.getAuthorId()));
			}
		}
		return authors;
	}
	@RequestMapping(value = "/searchBorrower/{name}", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> readBorrowerByName(@PathVariable String name){
		List<Borrower> borrowers= null;
		try {
			borrowers = bodao.readBorrowerByName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowers;
	}
	@Transactional
	@RequestMapping(value="/addTblBookAuthors/{bid}/{aid}", method = RequestMethod.GET, consumes = "application/json")
	public String addTblBookAuthors(@PathVariable int bid, @PathVariable int aid){
		adao.addTblBookAuthors(bid, aid);
		return "Updated successfully";
	}
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void addAuthor(@RequestBody Author author) {
		try {

			Integer authId = adao.addAuthorWithID(author);
			System.out.println(authId);
			int[] bids = author.getBookIds();

			if(bids != null) {
				for (int i = 0; i < bids.length; i++) {

					adao.addTblBookAuthors( bids[i], authId);
				}


//				for (int i = 0; i < bids.length; i++) {
//					Author a = new Author();
//					a = adao.readAuthorsByID(authId);
//					a.setBooks((List<Book>) bdao.readBookByID(bids[i]));
//				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

//	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
//	public String addAuthor1(@RequestParam("authorName") String authorName, @RequestParam("bookIds") Integer[] bookIds, Model model) {
//		String path = "administrator";
//		String message = null;
//		if (authorName == null || authorName.length() < 3 || authorName.length() > 45) {
//			message = "Author Name should be between 3-45 chars in length and it cannot be empty.";
//			path = "addauthor";
//		} else {
//			Author author = new Author();
//			List<Book> books = new ArrayList<Book>();
//			author.setAuthorName(authorName);
//			author.setBooks(books);
//			try {
//				adminService.createAuthor(author);
//				path = "viewauthors";
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			if (bookIds != null && bookIds.length > 0) {
//				for (int i = 0; i < bookIds.length; i++) {
//					Book b = new Book();
//					b.setBookId((bookIds[i]));
//					books.add(b);
//				}
//				Integer ID = adminService.getAuthorIdByName(authorName);
//				for (Integer i : bookIds) {
//					adminService.addTblBookAuthors(i, ID);
//				}
//			}
//		}
//		return path;
//	}
	@RequestMapping(value = "/searchBook/{title}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> searchBook(@PathVariable String title){
		List<Book> books = new ArrayList<Book>();
		try {
			books = bdao.readBooksByName(title, 1);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return books;
	}
	@RequestMapping(value = "/readBookByBranchId/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBookByBranchId(@PathVariable Integer branchId){
		List<Book> books = new ArrayList<Book>();
		try {
			books = bdao.readBookByBranchID(branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	@Transactional
	@RequestMapping(value = "/deleteBranch/{branchId}", method = RequestMethod.GET)
	public void deleteBranch(@PathVariable Integer branchId, Model model) {

		try {
			brdao.deleteBranch(branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Transactional
	@RequestMapping(value = "/updateBranch", method = RequestMethod.POST, consumes = "application/json")
	public void updateBranch(@RequestBody Branch branch) {

			try {
				brdao.updateBranch(branch);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();

			}
	}

	@Transactional
	@RequestMapping(value = "/updateBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public void updateBookCopies(@RequestBody BookCopies bookCopies) {
			try {
				bcdao.updateBookCopies(bookCopies);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	@RequestMapping(value = "/readBookCopiesByBranchID/{branchId}", method = RequestMethod.GET, consumes = "application/json")
	public List<BookCopies> readBookCopiesByBranchID(@PathVariable Integer branchId){
		List<BookCopies> copies = new ArrayList<BookCopies>();
		try {
			copies = bcdao.readBookCopiesByBranchID(branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return copies;

	}

	@Transactional
	@RequestMapping(value = "/addBranch", method = RequestMethod.POST, consumes = "application/json" )
	public void addBranch(@RequestBody Branch branch) {

			try {
				brdao.addBranch(branch);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	@RequestMapping(value = "/readAllBranches", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> readAllBranches(){
		List<Branch> branches = new ArrayList<Branch>();
		try {
			branches = brdao.readAllBranches(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branches;
	}
	@RequestMapping(value = "/readBranchByID/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public Branch readBranchByID(@PathVariable Integer branchId){
		Branch branch = new Branch();
		try {
			branch = brdao.readBranchByID(branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branch;
	}
	@RequestMapping(value = "/readBookCopies/{bookId}/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public BookCopies readBookCopies(@PathVariable int bookId,@PathVariable int branchId ){
		BookCopies bc = new BookCopies();
		bc = bcdao.getBookCopies(bookId, branchId);
		return bc;
	}

@RequestMapping(value = "/readBranchByName/{branchName}", method = RequestMethod.GET, produces = "application/json")
public List<Branch> readBranchByName(@PathVariable String branchName){
		List<Branch> branches = new ArrayList<Branch>();
	try {
		branches = brdao.readBranchByName(branchName);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return branches;
}

	@Transactional
	@RequestMapping(value = "/addpublishers", method = RequestMethod.POST, consumes = "application/json")
	public void addpublisher(@RequestBody Publisher publisher) {
		try {
			pdao.addPublisher(publisher);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {

		}
	}
	@RequestMapping(value = "/readPublisherByID/{publisherId}", method = RequestMethod.GET, produces = "application/json")
	public Publisher readPublisherByID(@PathVariable int publisherId){

		try {
			 return pdao.readPublisherByID(publisherId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	@RequestMapping(value = "/readPublishersByName/{pubName}", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> readPublishersByName(@PathVariable String pubName){
		List<Publisher> publishers = new ArrayList<Publisher>();
		try {
			publishers = pdao.readPublishersByName(pubName, 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}

	@Transactional
	@RequestMapping(value = "/addGenre", method = RequestMethod.POST, consumes = "application/json")
	public void addGenre(@RequestBody Genre genre){
		try {
			gdao.addGenre(genre);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
@RequestMapping(value = "/readAllGenre", method = RequestMethod.GET, produces = "application/json")
public List<Genre> readAllGenre(){
		List<Genre> genres = new ArrayList<Genre>();
	try {
		genres = gdao.readAllGenre();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return genres;
}
	@RequestMapping(value = "/viewpublishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> viewpublisher(Locale locale, Model model) {
		List<Publisher> publishers = new ArrayList<Publisher>();
		try {
			publishers = pdao.readAllPublishers(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return publishers;
	}

	@Transactional
	@RequestMapping(value = "/updatePublisher", method = RequestMethod.POST, consumes = "application/json")
	public void updatePublisher(@RequestBody Publisher publisher) {

			try {
				pdao.updatePublisher(publisher);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Transactional
	@RequestMapping(value = "/deletePublisher/{pulisherId}", method = RequestMethod.GET)
	public void deletePublisher(@PathVariable int publisherId) {

		try {
			pdao.deletePublisher(publisherId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/viewbooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> viewbook(Locale locale, Model model) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = bdao.readAllBooks();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(books!=null && !books.isEmpty()){
			for(Book b : books){
				b.setAuthors(adao.getAuthorByBook(b.getBookId()));
			}
			for(Book c : books){
				c.setGenres(gdao.readGenrebyBook(c.getBookId()));
			}
		}
		return books;

	}

	@Transactional
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST)
	public void updateBook(@RequestBody Book book) {

		try {
			bdao.updateBook(book);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "deleteBook/{bookId}", method = RequestMethod.GET)
	public void deleteBook(@PathVariable Integer bookId) {

		try {
			bdao.deleteBook(bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	@Transactional
	@RequestMapping(value = "/addbooks", method = RequestMethod.POST, consumes = "application/json")
	public void addBookWithID(@RequestBody Book book) throws SQLException, ClassNotFoundException {
		Integer bookId = bdao.addBookWithID(book);
		int[] aids = book.getAuthorIds();
		int[] gids = book.getGenreIds();
		if (aids != null) {
			for (int i = 0; i < aids.length; i++) {

				adao.addTblBookAuthors(bookId, aids[i]);
			}
			for (int i = 0; i < gids.length; i++) {

				bdao.addTblBookGenres(gids[i], bookId);
			}


		}
	}
//	@RequestMapping(value = "addBook", method = RequestMethod.POST, consumes = "application/json")
//	public String addBook(@RequestBody Book book) {
//
//
//			try {
//				bdao.addBook(book);
//				if(book.getAuthors() != null) {
//					for (Author a : book.getAuthors()) {
//						bdao.addBookAuthor(book, a);
//					}
//				}
//				if(book.getGenres() != null){
//
//
//				for (Genre ge : book.getGenres()) {
//					bdao.addBookGenre(book, ge);
//				}
//				}
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		return "Book added successfully";
//
//	}
	@RequestMapping(value = "/readBookByBranchID/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBookByBranchID(@PathVariable Integer branchId){
	List<Book> books = new ArrayList<Book>();
		try {
			books = bdao.readBookByBranchID(branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	@RequestMapping(value = "/readBooksByName/{title}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBooksByName(@PathVariable String title){
		List<Book> books = new ArrayList<Book>();
		try {
			books = bdao.readBooksByName(title, 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	@RequestMapping(value = "/readBooksByAuthor/{authorName}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBooksByAuthor(@PathVariable String authorName){
		List<Book> books = new ArrayList<Book>();
		try {
			books = bdao.readBooksByAuthor(authorName, 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}


	@RequestMapping(value = "/viewBorrower", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> viewborrower(Locale locale, Model model){
		List<Borrower> borrowers = new ArrayList<Borrower>();
		try {
			borrowers = bodao.readAllBorrowers(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return borrowers;
	}

	@Transactional
@RequestMapping(value = "updateBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void updateBorrower(@RequestBody Borrower borrower) {
		try {
			bodao.updateBorrower(borrower);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	@Transactional
@RequestMapping(value = "deleteBorrower/{cardNo}", method = RequestMethod.GET)
	public void deleteBorrower(@PathVariable Integer cardNo){
		Borrower bor = new Borrower();
		bor.setCardNo(cardNo);
	try {
		bodao.deleteBorrower(bor);
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
}
	@Transactional
@RequestMapping(value = "/addBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void addBorrower(@RequestBody Borrower borrower){
	try {
		System.out.println("In Home Controller");
		bodao.addBorrower(borrower);
		System.out.println("In Home Controller after add");

	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}

}
@RequestMapping(value = "/readBorrowerByID/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public Borrower readBorrowerByID(@PathVariable Integer cardNo){
		Borrower borrower = new Borrower();
	try {
		borrower = bodao.readBorrowerByID(cardNo);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return borrower;
}
	@RequestMapping(value = "/displayBookCopies/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<BookCopies> displayBookCopies(@PathVariable Integer branchId)  {
		List<BookCopies> bc = new ArrayList<BookCopies>();
		int i=0;
		int j=0;
		try {
			bc = bcdao.displayCopies(branchId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		if(bc != null && !bc.isEmpty()) {
//			int ids[] = new int[bc.size()];
//
//				for (BookCopies bcs : bc) {
//					ids[i] = bcs.getBook().getBookId();
//					try {
//						bcs.setBook(bdao.readBookByID(ids[i]));
//						i++;
//					} catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//
//				}
//			return bc;
//		}
		return bc;
	}
	@RequestMapping(value = "/CheckCard/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public Integer CheckCard(@PathVariable Integer cardNo){
		Integer num = null;
		try {
			num = bodao.CheckCard(cardNo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
//	@RequestMapping(value = "/readBookBycardNo/{cardNo}", method = RequestMethod.GET, produces = "application/json")
//	public List<Book> readBookBycardNo(@PathVariable Integer cardNo){
//		List<Book> books = new ArrayList<Book>();
//		try {
//			books = bldao.readBookBycardNo(cardNo);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return books;
//	}
	@RequestMapping(value = "/readBookByID/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public Book readBookByID(@PathVariable Integer bookId){
		Book b = new Book();
		try {
			b = bdao.readBookByID(bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	@RequestMapping(value = "/readAllBookLoans", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoans> readAllBookLoans(){
		List<BookLoans> loans = new ArrayList<BookLoans>();
		try {
			loans = bldao.readAllBookLoans();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loans;
	}
	@RequestMapping(value = "/deleteBookLoan1/{bookId}/{branchId}/{cardNo}", method = RequestMethod.GET)
	public void deleteBookLoan1(@PathVariable int bookId, @PathVariable int branchId, @PathVariable int cardNo){
		bldao.deleteBookLoan1(bookId, branchId, cardNo);
	}
	@RequestMapping(value = "/readBookBycardNo/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBookBycardNo(@PathVariable int branchId, @PathVariable int cardNo){
		List<Book> books = new ArrayList<Book>();
		try {
			System.out.println(cardNo+"*"+branchId);
			books = bdao.readBookBycardNo(cardNo, branchId);
			System.out.println(cardNo+"**"+branchId);
			if(books != null && !books.isEmpty()){
				return books;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return null;
	}
//	@RequestMapping(value = "deleteBookLoan/", )
	@RequestMapping(value = "/bookCheckIn/{bookId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public boolean bookCheckIn(@PathVariable Integer bookId, @PathVariable Integer cardNo)
			throws ClassNotFoundException, SQLException {

		bldao.checkIn(bookId, cardNo);
		return false;
	}
	@RequestMapping(value = "/bookCheckOut/{bookId}/{cardNo}/branchId", method = RequestMethod.GET, produces = "application/json")
	public void bookCheckOut(@PathVariable int bookId, @PathVariable int cardNo, @PathVariable int branchId)
			 {
				 try {
					 bldao.checkOut(bookId, cardNo, branchId);
				 } catch (ClassNotFoundException e) {
					 e.printStackTrace();
				 } catch (SQLException e) {
					 e.printStackTrace();
				 }

			 }
}

