package com.cos.everytimeandroid.domain.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // persistence
public class User {

	@Id //pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrease
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	private String email;
	private String nickname;	
	private String userRole;
	private String university;
	private Integer entranceYear;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
