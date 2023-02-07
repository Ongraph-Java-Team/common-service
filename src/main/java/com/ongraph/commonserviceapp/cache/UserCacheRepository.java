package com.ongraph.commonserviceapp.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ongraph.commonserviceapp.constants.Constant;
import com.ongraph.commonserviceapp.model.UserDetails;

@Repository
public class UserCacheRepository {
	
	private HashOperations<String, Integer, UserDetails>hashOperations;
	
	public UserCacheRepository(
			@Qualifier(Constant.USER_DETAILS_REDIS_BEAN) 
			RedisTemplate<String, UserDetails> redisTemplate) {
		this.hashOperations=redisTemplate.opsForHash();
	}
	
	public UserDetails findByUserName(String userName) {
		return hashOperations.get(Constant.USER_DETAILS_KEY, userName.hashCode());
	}
	
	public void saveOrUpdate(UserDetails userDetails) {
		hashOperations.put(Constant.USER_DETAILS_KEY, userDetails.getUserName().hashCode(), userDetails);
	}
	
}
