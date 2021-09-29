package com.cristi.infra;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cristi.core.Movie;
import com.cristi.core.MovieRepository;


@Repository
@Primary
public class PostgresMovieRepo implements MovieRepository{
	private final JdbcTemplate jdbc;
	
	@Autowired
	public PostgresMovieRepo(JdbcTemplate jt) {
		this.jdbc=jt;
	}
	
	@Override
	public Movie getMovie(String id) { 
		//asta e ca sa ne dea unul singur, singurul care se protiveste
		//cu codul imdb data ca parametru la cale /movies/tt9343989
		String sql = "SELECT * FROM reco WHERE codimdb=?";
		return jdbc.queryForObject(sql,  this::movie, id);	
	}

	@Override
	public String getReco(String id) {
		String sql = "SELECT * FROM reco WHERE codimdb=?";
		return jdbc.query(sql, row -> {
			row.next();
			return (row.getString("recommended")).toString();
		},id );

	}

	@Override
	public List<Movie> findByTitle(String title) {
		String sql = "SELECT * FROM reco WHERE title LIKE ?;";
		return jdbc.query(sql, this::movie, "%"+title+"%");
	}
	
	private Movie movie(ResultSet row, int number) {
		try {
			return new Movie(
					row.getString("codimdb"),
					row.getString("title"),
					row.getDouble("rating")
								);
		}catch(SQLException e){
			throw new IllegalStateException(e);
		}
	}
		/*
		String sql = "SELECT * FROM reco WHERE title LIKE '%"+title+"%';";
		List<Map<String, Object>> rows = jdbc.queryForList(sql);
		List<Movie> myList = new ArrayList<>();
		
		for(Map row : rows) {
			myList.add(new Movie(
					(String) row.get("codimdb"),
					(String) row.get("title"),
					((BigDecimal) row.get("rating")).doubleValue()
								));
		}
		
		return myList;
		*/

		/*
		String sql = "SELECT * FROM reco WHERE title LIKE '%"+title+"%';";
		List<Movie> myList = new ArrayList<>();

		return jdbc.query(sql, row -> { 
			while(row.next()) // un fel de ++k, trece deja la urmatorul row
			{
				myList.add(new Movie(
						row.getString("codimdb"),
						row.getString("title"),
						row.getDouble("rating")
									));
			} // end while
			return myList;
		} // end lambda expression
		);
		*/
		
		//System.out.println("Merge si asta");
	}


