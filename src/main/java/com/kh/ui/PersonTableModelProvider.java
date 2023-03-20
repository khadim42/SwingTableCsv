package com.kh.ui;

import com.kh.dao.PersonDao;
import com.kh.model.Person;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PersonTableModelProvider {
    final static int PAGE_SIZE = 20;
    static public DefaultTableModel fetchBySize(int start, int size) {

        System.out.println("Inside Person TableModel Provider");

        DefaultTableModel model =
                new DefaultTableModel(
                        new String[]{"Id", "First Name",
                                "Last Name", "Date of Birth",
                                "Height (cm)","Age"}, 0);

            List<Person> personList = new PersonDao().getAllPersonsBySize(start,size);

            for(Person person:personList){
                model.addRow(new Object[]{person.getId(), person.getFirstName(), person.getLastName(),
                        person.getDob(),person.getHeight(),person.getAge()});
            }


        return model;
    }

    static public DefaultTableModel fetchBySizeAndFilters(int start, int size,String firstName, String lastName, String dob, double height, int age) {

        System.out.println("Inside Person TableModel Provider");

        DefaultTableModel model =
                new DefaultTableModel(
                        new String[]{"Id", "First Name",
                                "Last Name", "Date of Birth",
                                "Height (cm)","Age"}, 0);

        List<Person> personList =
                new PersonDao().getAllPersonsBySizeAndFilters(
                        start,size,firstName,lastName,dob,height,age);

        for(Person person:personList){
            model.addRow(new Object[]{person.getId(), person.getFirstName(), person.getLastName(),
                    person.getDob(),person.getHeight(),person.getAge()});
        }


        return model;
    }
}
