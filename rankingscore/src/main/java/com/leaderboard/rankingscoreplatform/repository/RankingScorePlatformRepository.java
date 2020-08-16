package com.leaderboard.rankingscoreplatform.repository;

import org.springframework.data.repository.CrudRepository;

import com.leaderboard.rankingscoreplatform.model.User;

public interface RankingScorePlatformRepository extends CrudRepository<User, String>  {

}
