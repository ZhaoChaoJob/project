package com.geotmt.admin.model.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * Created by geo on 2018/9/13. */
@Document(collection="Persion")
@Getter
@Setter
public class Persion {
    private String _id ;
    private String name ;
}
