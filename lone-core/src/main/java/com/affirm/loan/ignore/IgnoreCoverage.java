package com.affirm.loan.ignore;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author indranil dey
 * This is used by Cobertura Pluging for
 * ignore some method or class for Code Coverage
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreCoverage {
}