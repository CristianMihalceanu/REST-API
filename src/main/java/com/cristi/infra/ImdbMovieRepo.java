package com.cristi.infra;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.cristi.ImbdSearchApplication;
import com.cristi.core.Movie;
import com.cristi.core.MovieRepository;

@Repository
public class ImdbMovieRepo implements MovieRepository {

    @Override
    public Movie getMovie(String id) {
    	
    	HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://imdb-api.com/en/API/Ratings/k_5fuvenoy/"+id))
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String body = response.body();
			return parse(body);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			throw new IllegalStateException(e);
		}
		
		
//		
//		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//				.thenApply(HttpResponse::body)
//				.thenApply(ImdbMovieRepo::parse)
//				.join();
//		
    }

	@Override
	public String getReco(String id) {
		return null;
	}

	public static Movie parse(String responseBody) {
    	
		JSONObject myJson = new JSONObject(responseBody);
		String id = myJson.getString("imDbId");
		String name = myJson.getString("title");
		double rating =myJson.getDouble("imDb");
		return new Movie(id, name, rating);
		
	}

	@Override
	public List<Movie> findByTitle(String title) {
		return Collections.emptyList();
	}
}
