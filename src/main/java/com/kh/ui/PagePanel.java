package com.kh.ui;

import com.kh.dao.PersonDao;
import com.kh.model.Person;
import com.kh.util.Pagination;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.kh.ui.PersonTableModelProvider.PAGE_SIZE;

public class PagePanel extends JPanel implements ActionListener {

    double tableRowCount;
    int totalPages;
    int currentPage;
    int startRow;
    int numbers[];
    JButton[] buttons;
    JPanel pagingPanel;
    JTable recordsTable;

    JLabel totalRecords;

    public Person personFilter = new Person();
    PagePanel(JTable recordsTable){
        this.recordsTable = recordsTable;
        pagingPanel = new JPanel();

        add(pagingPanel);
        buttons = new JButton[9];

        totalRecords = new JLabel("Total : 0");
        add(totalRecords);

    }

    void initRecords(){
        DefaultTableModel dm = (DefaultTableModel) PersonTableModelProvider.fetchBySize(0, PAGE_SIZE);
        recordsTable.setModel(dm);
        getCount();
        getPageData(1);
    }

    void clearPage(){
        DefaultTableModel dm = (DefaultTableModel) PersonTableModelProvider.fetchBySize(0, PAGE_SIZE);
        recordsTable.setModel(dm);
        getCount();
        getPageData(1);
        personFilter = new Person();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("event called" + e.getSource());
        String pageNumber = "";
        if (e.getSource() instanceof JButton) {
            if (((JButton) e.getSource()).getText().equalsIgnoreCase("Start")) {
                pageNumber = "1";
            } else if (((JButton) e.getSource()).getText().equalsIgnoreCase("End")) {
                pageNumber = totalPages + "";
            } else if (((JButton) e.getSource()).getText().equalsIgnoreCase("<<")) {
                pageNumber = (currentPage - 1) + "";
            } else if (((JButton) e.getSource()).getText().equalsIgnoreCase(">>")) {
                pageNumber = (currentPage + 1) + "";
            } else {
                pageNumber = ((JButton) e.getSource()).getText();
            }
            System.out.println("hello " + pageNumber);
            //this.repaint();
            if(FiltersPanel.isFromFilter){
                    getPageDataForFilter(Integer.parseInt(pageNumber));
            }else {
                getPageData(Integer.parseInt(pageNumber));
            }
            getPaginationDetails();
        }
    }


    /*get total numbers of record in table*/
    public void getCount() {
        tableRowCount = new PersonDao().getTotalRecords();
        totalRecords.setText("Total : "+(new Double(tableRowCount)).longValue());
        if (tableRowCount > 0) {
            totalPages = (int) Math.ceil(tableRowCount / PAGE_SIZE);
            currentPage = 1;
            System.out.println("row count is " + tableRowCount + "page Count" + totalPages);
        } else {

            JOptionPane.showMessageDialog(null, "No Record to display");
        }
    }
    /*get total numbers of record in table*/
    public void getCountByFilter() {
        tableRowCount = new PersonDao().getTotalRecordsByFilters(
                personFilter.getFirstName(), personFilter.getLastName(),
                personFilter.getDob(),personFilter.getHeight(), personFilter.getAge());
        totalRecords.setText("Total : "+(new Double(tableRowCount)).longValue());
        if (tableRowCount > 0) {
            totalPages = (int) Math.ceil(tableRowCount / PAGE_SIZE);
            currentPage = 1;
            System.out.println("row count is " + tableRowCount + "page Count" + totalPages);
        } else {
            JOptionPane.showMessageDialog(null, "No Record to display");
        }
    }
    /*Get data from table based on page no*/
    public void getPageData(int pageNo) {
        currentPage = pageNo;
        //calculate starting row for pagination
        startRow = PAGE_SIZE * (pageNo - 1);
        DefaultTableModel dm = (DefaultTableModel) PersonTableModelProvider.fetchBySize(startRow, PAGE_SIZE);
        recordsTable.setModel(dm);
        getPaginationDetails();
    }

    public void getPageDataForFilter(int pageNo) {

        currentPage = pageNo;
        //calculate starting row for pagination
        startRow = PAGE_SIZE * (pageNo - 1);
        DefaultTableModel dm = (DefaultTableModel)
                PersonTableModelProvider.fetchBySizeAndFilters(startRow, PAGE_SIZE,
                        personFilter.getFirstName(), personFilter.getLastName(), personFilter.getDob(),
                        personFilter.getHeight(), personFilter.getAge());
        recordsTable.setModel(dm);
        getPaginationDetails();
    }
    // dynamically generate page numbers
    public void getPaginationDetails() {
        System.out.println("pagination details");
        int inc = 0;
        System.out.println(currentPage + "  " + totalPages);
        pagingPanel.removeAll();
        pagingPanel.revalidate();
        numbers = Pagination.getPageNos(currentPage, totalPages);
        buttons[0] = new JButton("Start");
        buttons[0].addActionListener(this);
        pagingPanel.add(buttons[0]);
        buttons[1] = new JButton("<<");
        buttons[1].addActionListener(this);
        pagingPanel.add(buttons[1]);
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 0) {
                buttons[i + 2] = new JButton(numbers[i] + "");
                buttons[i + 2].setBounds(500 + inc, 500, 50, 30);
                buttons[i + 2].addActionListener(this);
                pagingPanel.add(buttons[i + 2]);
                inc += 40;
            }
            if (numbers[i] == currentPage) {
                buttons[i + 2].setBackground(Color.BLUE);
                buttons[i + 2].setForeground(Color.WHITE);
            }
        }
        buttons[7] = new JButton(">>");
        buttons[7].addActionListener(this);
        pagingPanel.add(buttons[7]);
        buttons[8] = new JButton("End");
        buttons[8].addActionListener(this);
        pagingPanel.add(buttons[8]);
        /*if current page is 1 then disable start and previous button*/
        if (currentPage == 1) {
            buttons[0].setEnabled(false);
            buttons[1].setEnabled(false);
        }
        /*if current page is last then disable end and next button*/
        if (currentPage == PAGE_SIZE) {
            buttons[7].setEnabled(false);
            buttons[8].setEnabled(false);
        }
    }


}
