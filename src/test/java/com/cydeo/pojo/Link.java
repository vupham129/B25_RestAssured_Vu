package com.cydeo.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // from lombok dependency
@Setter
@ToString
public class Link {

    private String rel;
    private String href;

}
