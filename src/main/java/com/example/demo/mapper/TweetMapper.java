package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.Mapper;

import com.example.demo.dto.TweetDto;
import com.example.demo.model.Tweet;

@Mapper(componentModel="spring")
public interface TweetMapper {
	TweetDto tweetToDto(Tweet tweet);
	Tweet dtoToTweet(TweetDto tweetDto);
	ArrayList<TweetDto> tweetsToDtos(Collection<Tweet> tweets);
	Collection<Tweet> dtosToTweets(ArrayList<TweetDto> tweetDtos);
}
