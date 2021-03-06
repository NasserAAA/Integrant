package com.example.demo;


import java.util.Date;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class TwitterEvent extends ApplicationEvent {
	private String tweet_id;
	private String tweet_text;
	private double sentiment_score;
	private Date created_at;
	private String tweet_tag;
	
	public TwitterEvent(Object source,String tweet_id,String tweet_text,double sentiment_score, Date created_at, String tweet_tag)
	{	super(source);
		this.tweet_id=tweet_id;
		this.tweet_text=tweet_text;
		this.sentiment_score=sentiment_score;
		this.created_at=created_at;
		this.tweet_tag=tweet_tag;
	}
}
