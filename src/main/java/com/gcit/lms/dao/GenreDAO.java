package com.gcit.lms.dao;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
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
public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{

    

    //ADD GENRE
    public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
       template.update("insert into tbl_genre (genre_name) values (?)", new Object[] {genre.getGenreName()});
    }

    //UPDATE GENRE
    public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException{
    	template.update("update tbl_genre set genre_name = ? where genre_id = ?", new Object[] {genre.getGenreName(),genre.getGenreId()});
    }

    //DELETE GENRE
    public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException{
    	template.update("delete from tbl_genre where genre_id = ?", new Object[] {genre.getGenreId()});
    }

    //READ ALL GENRES
    public List<Genre> readAllGenre() throws ClassNotFoundException, SQLException{
        return (List<Genre>) template.query("select * from tbl_genre", this);
    }
    public List<Genre> readGenrebyBook(Integer bookId){
        List<Genre> genres = template.query("select * from tbl_genre where genre_id IN(select bookId from tbl_book_genres where bookId= ?)", new Object[] {bookId}, this);
        return genres;
    }



    @Override
    public List<Genre> extractData(ResultSet rs) throws SQLException {
        List<Genre> genres = new ArrayList<Genre>();
        while(rs.next()){
            Genre genr = new Genre();
            genr.setGenreId(rs.getInt("genre_id"));
            genr.setGenreName(rs.getString("genre_name"));
            genres.add(genr);
        }
        return genres;
    }

}
