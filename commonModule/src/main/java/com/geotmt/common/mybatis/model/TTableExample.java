package com.geotmt.common.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TTableExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TTableExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andTIdIsNull() {
            addCriterion("T_ID is null");
            return (Criteria) this;
        }

        public Criteria andTIdIsNotNull() {
            addCriterion("T_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTIdEqualTo(Long value) {
            addCriterion("T_ID =", value, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdNotEqualTo(Long value) {
            addCriterion("T_ID <>", value, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdGreaterThan(Long value) {
            addCriterion("T_ID >", value, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdGreaterThanOrEqualTo(Long value) {
            addCriterion("T_ID >=", value, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdLessThan(Long value) {
            addCriterion("T_ID <", value, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdLessThanOrEqualTo(Long value) {
            addCriterion("T_ID <=", value, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdIn(List<Long> values) {
            addCriterion("T_ID in", values, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdNotIn(List<Long> values) {
            addCriterion("T_ID not in", values, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdBetween(Long value1, Long value2) {
            addCriterion("T_ID between", value1, value2, "tId");
            return (Criteria) this;
        }

        public Criteria andTIdNotBetween(Long value1, Long value2) {
            addCriterion("T_ID not between", value1, value2, "tId");
            return (Criteria) this;
        }

        public Criteria andTDateIsNull() {
            addCriterion("T_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTDateIsNotNull() {
            addCriterion("T_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTDateEqualTo(Long value) {
            addCriterion("T_DATE =", value, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateNotEqualTo(Long value) {
            addCriterion("T_DATE <>", value, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateGreaterThan(Long value) {
            addCriterion("T_DATE >", value, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateGreaterThanOrEqualTo(Long value) {
            addCriterion("T_DATE >=", value, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateLessThan(Long value) {
            addCriterion("T_DATE <", value, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateLessThanOrEqualTo(Long value) {
            addCriterion("T_DATE <=", value, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateIn(List<Long> values) {
            addCriterion("T_DATE in", values, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateNotIn(List<Long> values) {
            addCriterion("T_DATE not in", values, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateBetween(Long value1, Long value2) {
            addCriterion("T_DATE between", value1, value2, "tDate");
            return (Criteria) this;
        }

        public Criteria andTDateNotBetween(Long value1, Long value2) {
            addCriterion("T_DATE not between", value1, value2, "tDate");
            return (Criteria) this;
        }

        public Criteria andTTxtIsNull() {
            addCriterion("T_TXT is null");
            return (Criteria) this;
        }

        public Criteria andTTxtIsNotNull() {
            addCriterion("T_TXT is not null");
            return (Criteria) this;
        }

        public Criteria andTTxtEqualTo(Short value) {
            addCriterion("T_TXT =", value, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtNotEqualTo(Short value) {
            addCriterion("T_TXT <>", value, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtGreaterThan(Short value) {
            addCriterion("T_TXT >", value, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtGreaterThanOrEqualTo(Short value) {
            addCriterion("T_TXT >=", value, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtLessThan(Short value) {
            addCriterion("T_TXT <", value, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtLessThanOrEqualTo(Short value) {
            addCriterion("T_TXT <=", value, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtIn(List<Short> values) {
            addCriterion("T_TXT in", values, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtNotIn(List<Short> values) {
            addCriterion("T_TXT not in", values, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtBetween(Short value1, Short value2) {
            addCriterion("T_TXT between", value1, value2, "tTxt");
            return (Criteria) this;
        }

        public Criteria andTTxtNotBetween(Short value1, Short value2) {
            addCriterion("T_TXT not between", value1, value2, "tTxt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}