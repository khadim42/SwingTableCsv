package com.kh.dao;

import com.kh.config.DbConnection;
import com.kh.model.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    public List<Person> getAllPersons(){
        List<Person> personList = new ArrayList<>();
        String query = "Select * from person limit 1,20";
        try {
            ResultSet rs = DbConnection.getConnection().prepareStatement(query).executeQuery();

            long id = 1;
            while(rs.next()){
                Person person = new Person();

                person.setId(id++);
                person.setFirstName(rs.getString("firstName"));
                person.setLastName(rs.getString("lastName"));
                person.setDob(rs.getString(3));
                person.setHeight(rs.getDouble(4));
                person.setAge(rs.getInt(5));
                personList.add(person);

            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }

    public List<Person> getAllPersonsBySize(int start, int size){
        List<Person> personList = new ArrayList<>();
        String query = "Select * from person limit "+start+","+size;
        try {
            ResultSet rs = DbConnection.getConnection().prepareStatement(query).executeQuery();

            long id = 1;
            while(rs.next()){
                Person person = new Person();

                person.setId(id++);
                person.setFirstName(rs.getString("firstName"));
                person.setLastName(rs.getString("lastName"));
                person.setDob(rs.getString(3));
                person.setHeight(rs.getDouble(4));
                person.setAge(rs.getInt(5));
                personList.add(person);

            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }

    public List<Person> getAllPersonsBySizeAndFilters(int start, int size, String firstName, String lastName, String dob, double height, int age){
        List<Person> personList = new ArrayList<>();
        String query = "Select * from person where 1 = 1 ";
        if(!firstName.isEmpty()){
            query+=" and firstName like ?";
        }
        if(!lastName.isEmpty()){
            query+=" and lastName like ?";
        }
        if(!dob.isEmpty()){
            query+=" and \"Date Of Birth\" like ?";
        }

        if(height>0){
            query+=" and \"Height (cm)\" = ?";
        }
        if(age>0){
            query+=" and age =?";
        }

        query+=" limit "+start+","+size;

        try {
            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(query);
            int pIndex=0;
            if(!firstName.isEmpty()) {
                preparedStatement.setString(++pIndex,  "%" + firstName + "%");
            }
            if(!lastName.isEmpty()) {
                preparedStatement.setString(++pIndex, "%" + lastName + "%");
            }
            if(!dob.isEmpty()) {
                preparedStatement.setString(++pIndex, "%" + dob + "%");
            }
            if(height>0) {
                preparedStatement.setDouble(++pIndex,   height );
            }

            if(age>0) {
                preparedStatement.setInt(++pIndex,  age );
            }

//            ResultSet rs = DbConnection.getConnection().prepareStatement(query).executeQuery();
            ResultSet rs = preparedStatement.executeQuery();

            long id = 1;
            while(rs.next()){
                Person person = new Person();

                person.setId(id++);
                person.setFirstName(rs.getString("firstName"));
                person.setLastName(rs.getString("lastName"));
                person.setDob(rs.getString(3));
                person.setHeight(rs.getDouble(4));
                person.setAge(rs.getInt(5));
                personList.add(person);
                System.out.println(person);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }
    public List<Person> getAllPersonsByFilters(String firstName, String lastName, String dob, double height, int age){
        List<Person> personList = new ArrayList<>();
        String query = "Select * from person where 1 = 1 ";
        if(!firstName.isEmpty()){
            query+=" and firstName like ?";
        }
        if(!lastName.isEmpty()){
            query+=" and lastName like ?";
        }
        if(!dob.isEmpty()){
            query+=" and \"Date Of Birth\" like ?";
        }

        if(height>0){
            query+=" and \"Height (cm)\" = ?";
        }
        if(age>0){
            query+=" and age =?";
        }

        try {
            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(query);
            int pIndex=0;
            if(!firstName.isEmpty()) {
                preparedStatement.setString(++pIndex,  "%" + firstName + "%");
            }
            if(!lastName.isEmpty()) {
                preparedStatement.setString(++pIndex, "%" + lastName + "%");
            }
            if(!dob.isEmpty()) {
                preparedStatement.setString(++pIndex, "%" + dob + "%");
            }
            if(height>0) {
                preparedStatement.setDouble(++pIndex,   height );
            }

            if(age>0) {
                preparedStatement.setInt(++pIndex,  age );
            }

//            ResultSet rs = DbConnection.getConnection().prepareStatement(query).executeQuery();
            ResultSet rs = preparedStatement.executeQuery();

            long id = 1;
            while(rs.next()){
                Person person = new Person();

                person.setId(id++);
                person.setFirstName(rs.getString("firstName"));
                person.setLastName(rs.getString("lastName"));
                person.setDob(rs.getString(3));
                person.setHeight(rs.getDouble(4));
                person.setAge(rs.getInt(5));
                personList.add(person);
                System.out.println(person);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }



    public long getTotalRecords(){
        long total = 0;
        try {
            String queryForAllCount = "select count(*) from person";
            ResultSet rs = DbConnection.getConnection().createStatement().executeQuery(queryForAllCount);

            if(rs.next()) {
                total = rs.getLong(1);
            }

         rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    public long getTotalRecordsByFilters(String firstName, String lastName, String dob, double height, int age){
        long total = 0;
//        String queryForAllCount = "select count(*) from person";
        String query = "select count(*) from person where 1 = 1 ";
        if(!firstName.isEmpty()){
            query+=" and firstName like ?";
        }
        if(!lastName.isEmpty()){
            query+=" and lastName like ?";
        }
        if(!dob.isEmpty()){
            query+=" and \"Date Of Birth\" like ?";
        }

        if(height>0){
            query+=" and \"Height (cm)\" = ?";
        }
        if(age>0){
            query+=" and age =?";
        }

        try {
            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(query);
            int pIndex=0;
            if(!firstName.isEmpty()) {
                preparedStatement.setString(++pIndex,  "%" + firstName + "%");
            }
            if(!lastName.isEmpty()) {
                preparedStatement.setString(++pIndex, "%" + lastName + "%");
            }
            if(!dob.isEmpty()) {
                preparedStatement.setString(++pIndex, "%" + dob + "%");
            }
            if(height>0) {
                preparedStatement.setDouble(++pIndex,   height );
            }

            if(age>0) {
                preparedStatement.setInt(++pIndex,  age );
            }

//            ResultSet rs = DbConnection.getConnection().prepareStatement(query).executeQuery();
            ResultSet rs = preparedStatement.executeQuery();


            if(rs.next()) {
                total = rs.getLong(1);
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return total;
    }

}
