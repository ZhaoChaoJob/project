package com.geotmt.admin.model.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * Created by geo on 2018/9/13. */
@Document(collection="Persion")
public class Persion {
    private String _id ;
    private String name ;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
