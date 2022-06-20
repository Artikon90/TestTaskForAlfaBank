package com.artikon90.testtaskforalfabank.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
@Builder
public class Gif {
    private Map<String, Object> data;
    private Map<String, String> meta;
}
