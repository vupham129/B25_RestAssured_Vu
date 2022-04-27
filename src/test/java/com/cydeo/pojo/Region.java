package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // from lombok dependency
@Setter
@ToString
public class Region {

    @JsonProperty("region_id")
    private int regionId;
    @JsonProperty("region_name")
    private String regionName ;
    private List<Link> links;


}
