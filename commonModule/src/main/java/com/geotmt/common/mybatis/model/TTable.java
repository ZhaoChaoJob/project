package com.geotmt.common.mybatis.model;

import java.io.Serializable;

public class TTable implements Serializable {
    private Long tId;

    private Long tDate;

    private Short tTxt;

    private static final long serialVersionUID = 1L;

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

    public Short gettTxt() {
        return tTxt;
    }

    public void settTxt(Short tTxt) {
        this.tTxt = tTxt;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TTable other = (TTable) that;
        return (this.gettId() == null ? other.gettId() == null : this.gettId().equals(other.gettId()))
            && (this.gettDate() == null ? other.gettDate() == null : this.gettDate().equals(other.gettDate()))
            && (this.gettTxt() == null ? other.gettTxt() == null : this.gettTxt().equals(other.gettTxt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gettId() == null) ? 0 : gettId().hashCode());
        result = prime * result + ((gettDate() == null) ? 0 : gettDate().hashCode());
        result = prime * result + ((gettTxt() == null) ? 0 : gettTxt().hashCode());
        return result;
    }
}