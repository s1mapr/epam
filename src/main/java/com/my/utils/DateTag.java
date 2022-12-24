package com.my.utils;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;
/**
 * DateTag util
 */
public class DateTag extends TagSupport {

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.print(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}