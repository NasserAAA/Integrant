package com.example.demo.validations;

import javax.validation.GroupSequence;
 
@GroupSequence({FirstOrder.class, SecondOrder.class})
public interface ValidationSequence {
}