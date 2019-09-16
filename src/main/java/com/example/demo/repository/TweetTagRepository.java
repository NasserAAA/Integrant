package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TweetTag;

public interface TweetTagRepository extends JpaRepository<TweetTag, Integer> {
	TweetTag findByTweetTagName(String tweet_tag_name);
}
