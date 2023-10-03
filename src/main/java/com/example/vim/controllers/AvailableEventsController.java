package com.example.vim.controllers;

import com.example.vim.dao.EventosDao;
import com.example.vim.models.dto.EventsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/availabelEvents/")
@RequiredArgsConstructor
public class AvailableEventsController {
    private final EventosDao eventosDao;

    @GetMapping("/apiEvents")
    public ResponseEntity<List<EventsDto>> getConferences(){
        return new ResponseEntity<>(eventosDao.getPublishedEvents(), HttpStatusCode.valueOf(200));
    }
}
