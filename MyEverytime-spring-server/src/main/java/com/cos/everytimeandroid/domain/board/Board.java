package com.cos.everytimeandroid.domain.board;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.cos.everytimeandroid.domain.reply.Reply;
import com.cos.everytimeandroid.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // persistence
public class Board {
	
	@Id //pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrease
	private Long id;
	
	private String title;
    private String content;
     
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY )
    @JsonIgnoreProperties({"board"})
    private List<Reply> replys; 
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
     
    private String nickname = "익명"; 
    private Boolean anonymous;
    
    @ColumnDefault("0")
    private int like_num;
    
    @ColumnDefault("0")
    private int comment_num;

    @CreationTimestamp
    private Timestamp createDate;     
}
