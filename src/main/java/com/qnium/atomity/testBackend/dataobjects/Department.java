/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnium.atomity.testBackend.dataobjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;

/**
 *
 * @author Drozhin
 */
@DatabaseTable(tableName = "departments")
public class Department
{
    public static final String ID = "id";
    @DatabaseField(generatedId = true, columnName = ID)
    public long id;
    
    public static final String NAME = "name";
    @DatabaseField(columnName = NAME)
    public String name;
    
    public static final String REGISTRATION_DATE = "registration_date";
    @DatabaseField(columnName = REGISTRATION_DATE)
    public Instant registrationDate;    
}
