package com.example.silvertiger.service;

import com.example.silvertiger.dto.BoardDto;
import com.example.silvertiger.entity.BoardEntity;
import com.example.silvertiger.jwt.JwtTokenProvider;
import com.example.silvertiger.repository.AccountRepository;
import com.example.silvertiger.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BoardService {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private String getUser(HttpServletRequest httpServletRequest) {
        String jwt = jwtTokenProvider.resolveToken(httpServletRequest);
        return jwtTokenProvider.getUserPk(jwt);
    }

    public BoardDto save(HttpServletRequest httpServletRequest, BoardDto boardDto){
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
        boardEntity.setMember(accountRepository.findById(String.valueOf(Long.parseLong(getUser(httpServletRequest)))).orElse(null));
        boardRepository.save(boardEntity);
        return findById(boardDto.getId());
    }
    public BoardDto update(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);
        return findById(boardDto.getId());
    }

    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList){
            boardDtoList.add(BoardDto.toBoardDto(boardEntity));
        }
        return boardDtoList;
    }

    @Transactional
    public void updateHits(Long id){
        boardRepository.updateHits(id);
    }

    public BoardDto findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
            return boardDto;
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
