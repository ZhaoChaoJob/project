package com.geotmt.db.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 -- DROP TABLE IF EXISTS T_TABLE;
 CREATE TABLE T_TABLE (
 T_ID 		NUMERIC(18) ,
 T_DATE 	NUMERIC(14) ,
 T_TXT		NUMERIC(3)
 ) ENGINE=MYISAM;
 ALTER TABLE T_TABLE ADD PRIMARY KEY (T_ID) ;
 */
@Entity
@Table(name="T_TABLE")
@Getter
@Setter
public class TTable {
    @Id()
    @Column(name = "T_ID", columnDefinition = "NUMERIC(18)", nullable = false)
    private Long tId;
    @Column(name = "T_DATE", columnDefinition = "NUMERIC(14)", nullable = false)
    private Long tDate;
    @Column(name = "T_TXT", columnDefinition = "NUMERIC(3)", nullable = false)
    private Long tTxt;
}
