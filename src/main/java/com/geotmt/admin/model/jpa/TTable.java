package com.geotmt.admin.model.jpa;

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
public class TTable {
    @Id()
    @Column(name = "T_ID", columnDefinition = "NUMERIC(18)", nullable = false)
    private Long tId;
    @Column(name = "T_DATE", columnDefinition = "NUMERIC(14)", nullable = false)
    private Long tDate;
    @Column(name = "T_TXT", columnDefinition = "NUMERIC(3)", nullable = false)
    private Long tTxt;

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Long gettDate() {
        return tDate;
    }

    public void settDate(Long tDate) {
        this.tDate = tDate;
    }

    public Long gettTxt() {
        return tTxt;
    }

    public void settTxt(Long tTxt) {
        this.tTxt = tTxt;
    }
}
