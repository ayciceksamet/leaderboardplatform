package com.leaderboard.rankingscoreplatform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.javafaker.Faker;
import com.leaderboard.rankingscoreplatform.model.Score;
import com.leaderboard.rankingscoreplatform.model.User;
import com.leaderboard.rankingscoreplatform.repository.RankingScorePlatformRepository;

@Service
public class RankingScoreServiceImpl implements RankingScoreService {

	private static final String KEYSCORE = "users-score";

	@Autowired
	RankingScorePlatformRepository userRepository;

	@Autowired
	public RedisTemplate<Object, Object> template;

	@Override
	public ResponseEntity<Object> getHealthCheck() {
		return ResponseEntity.ok("Platform is running !");
	}

	@Override
	public ResponseEntity<Object> addUser(@RequestBody User user) {

		UUID uuid = UUID.randomUUID();

		User userCreated = new User(uuid.toString(), user.getDisplayName(), user.getPoints(), user.getRank(),
				user.getCountry());

		userRepository.save(userCreated);

		template.opsForZSet().add(KEYSCORE, userCreated, user.getPoints());

		return ResponseEntity.ok("User created successfully !");
		
	}

	@Override
	public Optional<User> getUser(@PathVariable String user_id) {

		Optional<User> userCreated = userRepository.findById(user_id);

		userCreated.get().setRank(template.opsForZSet().reverseRank(KEYSCORE, userCreated.get()) + 1);

		return userCreated;
	}

	@Override
	public Set<TypedTuple<Object>> getLeaderBoard() {

		Set<TypedTuple<Object>> userCreated = template.opsForZSet().reverseRangeWithScores(KEYSCORE, 0,
				userRepository.count());

		CountDownLatch latch = new CountDownLatch((int) userRepository.count() - 1);

		ExecutorService threadExecutor = Executors.newFixedThreadPool(100);

		for (TypedTuple<Object> userIT : userCreated) {

			threadExecutor.execute(new Runnable() {

				@Override
				public void run() {

					User user = (User) userIT.getValue();

					user.setRank(template.opsForZSet().reverseRank(KEYSCORE, user) + 1);

					latch.countDown();

				}
			});

		}

		try {
			latch.await();
		} catch (InterruptedException E) {
			// handle
		}

		return userCreated;
	}

	@Override
	public List<TypedTuple<Object>> getLeaderBoardByCountryCode(@PathVariable String country_iso_code) {

		Set<TypedTuple<Object>> userCreated = template.opsForZSet().reverseRangeWithScores(KEYSCORE, 0,
				userRepository.count());

		List<TypedTuple<Object>> userList = userCreated.stream()
				.filter(userOne -> ((User) userOne.getValue()).getCountry().equalsIgnoreCase(country_iso_code))
				.collect(Collectors.toList());

		userList.parallelStream().forEach(user -> ((User) user.getValue())
				.setRank(template.opsForZSet().reverseRank(KEYSCORE, (User) user.getValue()) + 1));

		return userList;
	}

	@Override
	public ResponseEntity<String> submitScore(@RequestBody Score score) {

		Optional<User> userFetched = userRepository.findById(score.getUser_id());

		Long newScore = userFetched.get().getPoints() + score.getScore_worth();

		User userCreated = new User(score.getUser_id(), userFetched.get().getDisplayName(), newScore,
				userFetched.get().getRank(), userFetched.get().getCountry());

		template.opsForZSet().add(KEYSCORE, userCreated, newScore);

		userRepository.save(userCreated);

		return ResponseEntity
				.ok("Score" + score.getScore_worth() + " is submitted to " + score.getUser_id() + " successfully !");
	}
	
	@Override
	public ResponseEntity<Object> bulkCreateUser(@PathVariable Integer user_count) {

		if (user_count == null || user_count.intValue() == 0) {
			return ResponseEntity.badRequest().body("You have to specify the count !");
		}

		int MAX_POINT_VALUE = 1000000;

		List<String> countryNames = new ArrayList<>();

		countryNames.add("tr");
		countryNames.add("us");
		countryNames.add("fr");
		countryNames.add("sw");

		CountDownLatch latch = new CountDownLatch(user_count.intValue());

		ExecutorService threadExecutor = Executors.newFixedThreadPool(50);

		for (int i = 0; i < user_count.intValue(); i++) {

			threadExecutor.execute(new Runnable() {

				@Override
				public void run() {

					UUID uuid = UUID.randomUUID();

					Faker faker = new Faker();

					Long point = Integer.toUnsignedLong((new Random().nextInt(MAX_POINT_VALUE) * 5));

					User userCreated = new User(uuid.toString(), faker.name().firstName(), point,
							Integer.toUnsignedLong(1), countryNames.get(new Random().nextInt(countryNames.size())));

					userRepository.save(userCreated);

					template.opsForZSet().add(KEYSCORE, userCreated, point);

					latch.countDown();

				}
			});

		}

		try {
			latch.await();
		} catch (InterruptedException E) {
			// handle
		}

		return ResponseEntity.ok("Bulk " + user_count + " users created successfully !");
	}

}
