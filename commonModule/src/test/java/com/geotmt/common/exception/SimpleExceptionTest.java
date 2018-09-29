package com.geotmt.common.exception;

import org.junit.Test;


/**
 * Created by geo on 2018/9/29.
 */
public class SimpleExceptionTest {

    @Test
    public void testException(){

        try {
            throw new SimpleException(BizStatusCode.R_DB_NO_RECORD);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}