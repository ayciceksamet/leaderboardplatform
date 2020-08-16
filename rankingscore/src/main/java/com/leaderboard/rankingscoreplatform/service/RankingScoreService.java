package com.leaderboard.rankingscoreplatform.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.leaderboard.rankingscoreplatform.model.Score;
import com.leaderboard.rankingscoreplatform.model.User;

public interface RankingScoreService {
	
	 public abstract ResponseEntity<Object>   getHealthCheck();
	 
	 public abstract ResponseEntity<Object>   addUser(@RequestBody User user);

	 public abstract ResponseEntity<Object>   bulkCreateUser(Integer user_count);

	 public abstract ResponseEntity<String>   submitScore(Score score);

	 public abstract Set<TypedTuple<Object>>  getLeaderBoard();

	 public abstract List<TypedTuple<Object>> getLeaderBoardByCountryCode(String country_iso_code);
	 
	 public abstract Optional<User> 	      getUser(String user_id);


}
