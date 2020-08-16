package com.leaderboard.rankingscoreplatform.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.leaderboard.rankingscoreplatform.model.Score;
import com.leaderboard.rankingscoreplatform.model.User;
import com.leaderboard.rankingscoreplatform.service.RankingScoreService;


@RestController
@io.swagger.annotations.Api(value="post", description=" Post Operations Service")
public class RankingScoreController {

	@Autowired
	RankingScoreService rankingScoreService;
	
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
	public ResponseEntity<Object> getHealthCheck()
			throws IOException, InterruptedException {
		return rankingScoreService.getHealthCheck();
	}
	
	@RequestMapping(value = "/user/create/", method = RequestMethod.POST)
	public ResponseEntity<Object> addUserToPlatform(@RequestBody User user)
			throws IOException, InterruptedException {
		return rankingScoreService.addUser(user);
	}
	
	@RequestMapping(value = "/user/bulkcreate/{user_count}", method = RequestMethod.POST)
	public ResponseEntity<Object> addBulkUserToPlatform(@PathVariable Integer user_count)
			throws IOException, InterruptedException {
		return rankingScoreService.bulkCreateUser(user_count);
	}
	
	@RequestMapping(value = "/user/profile/{user_id}", method = RequestMethod.GET)
	public Optional<User> retrieveUserFromPlatform(@PathVariable String user_id)
			throws IOException, InterruptedException {
		return rankingScoreService.getUser(user_id);
	}
	
	@RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
	public Set<TypedTuple<Object>> accessLeaderBoard()
			throws IOException, InterruptedException {
		return rankingScoreService.getLeaderBoard();
	}
	
	@RequestMapping(value = "/leaderboard/{country_iso_code}", method = RequestMethod.GET)
	public List<TypedTuple<Object>> accessLeaderBoardByCountryCode(@PathVariable String country_iso_code)
			throws IOException, InterruptedException {
		return rankingScoreService.getLeaderBoardByCountryCode(country_iso_code);
	}
	
	@RequestMapping(value = "/score/submit/", method = RequestMethod.POST)
	public ResponseEntity<String> submitScoreToUser(@RequestBody Score score)
			throws IOException, InterruptedException {
		return rankingScoreService.submitScore(score);
	}
	

}
