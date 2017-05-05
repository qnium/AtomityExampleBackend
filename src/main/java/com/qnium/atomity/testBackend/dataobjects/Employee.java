/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnium.atomity.testBackend.dataobjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qnium.common.validation.FieldValidator;
import com.qnium.common.validation.basic.ValidateMinLength;
import com.qnium.common.validation.basic.ValidateRegEx;
import com.qnium.common.validation.basic.ValidateRequired;
import com.qnium.common.validation.constant.RegExp;

/**
 *
 * @author Drozhin
 */
@DatabaseTable(tableName = "employees")
public class Employee
{
    public static final String ID = "id";
    @DatabaseField(generatedId = true, columnName = ID)
    public long id;
    
    public static final String EMAIL = "email";
    @DatabaseField(columnName = EMAIL)
    @FieldValidator(validator = ValidateRequired.class, errorMessage = "Please enter email")
    //@FieldValidator(validator = ValidateRegEx.class, param = RegExp.EMAIL, errorMessage = "Wrong email format")
    @FieldValidator(validator = ValidateMinLength.class, param = "3", errorMessage = "Min length %s")
    public String email;
    
    public static final String DEPARTMENT_ID = "department_id";
    @DatabaseField(columnName = DEPARTMENT_ID, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    @FieldValidator(validator = ValidateRequired.class, errorMessage = "Please select department")
    public Department department;    
    
    public static final String GENDER = "gender";
    @DatabaseField(columnName = GENDER)
    public int gender;
}
