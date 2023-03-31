package com.example.silvertiger.entity;

import com.example.silvertiger.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_table")

public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    //엔티티 객체를 옮겨 담기 위해 객체를 생성
    public static BoardEntity toSaveEntity(BoardDto boardDto){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDto.getBoardWriter());
        boardEntity.setBoardPass(boardDto.getBoardPass());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContents(boardDto.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDto.getId());
        boardEntity.setBoardWriter(boardDto.getBoardWriter());
        boardEntity.setBoardPass(boardDto.getBoardPass());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContents(boardDto.getBoardContents());
        boardEntity.setBoardHits(boardDto.getBoardHits());
        return boardEntity;
    }

    public void setMember(Account account) {
        this.user = account;
    }
}
