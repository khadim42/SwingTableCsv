package com.kh.ui;

import com.kh.model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FiltersPanel extends JPanel {
    private int x;
    private int y;

    JTextField firstNameField;
    JTextField lastNameField;
    JTextField dobField;
    JTextField heightField;
    JTextField ageField;

    JButton searchButton;

    JButton clearButton;

    JProgressBar progressBar;

    PagePanel pagePanel;

    private Task task;

    public static boolean isFromFilter = false;

    public FiltersPanel(int x, int y, PagePanel pagePanel) {
        this.x = x;
        this.y = y;
        this.pagePanel = pagePanel;
        setLayout(null);
        setBounds(x,y,x+900,y+80);

        // Set Text Fields and Buttons
        setTextFieldsAndButtons();

        // add event on clear button
        addActionOnClearButton();

        // add event on Search button
        addActionOnSearchButton();


    }

    private void setTextFieldsAndButtons(){

        int fieldWidth = 80;
        // First Name Field
        final JLabel firstNameLabel = new JLabel("First Name", JLabel.CENTER);		// Create JLabel
        firstNameLabel.setBounds(x+5, y, x+fieldWidth, 25);												// Set size and position JLabel
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(x+5,y+25,x+fieldWidth,25);
        add(firstNameField);
        // Distance between fields
        int distance = 145;

        // Last Name Field
        final JLabel lastNameLabel = new JLabel("Last Name", JLabel.CENTER);		// Create JLabel
        lastNameLabel.setBounds(x+distance, y, x+fieldWidth, 25);												// Set size and position JLabel
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(x+distance,y+25,x+fieldWidth,25);
        add(lastNameField);

        // dob  Field
        final JLabel dobLabel = new JLabel("Date Of Birth", JLabel.CENTER);		// Create JLabel
        dobLabel.setBounds(x+(distance*2), y, x+fieldWidth, 25);												// Set size and position JLabel
        add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(x+(distance*2),y+25,x+fieldWidth,25);
        add(dobField);

        // Height  Field
        final JLabel heightLabel = new JLabel("Height (cm)", JLabel.CENTER);		// Create JLabel
        heightLabel.setBounds(x+(distance*3), y, x+fieldWidth, 25);												// Set size and position JLabel
        add(heightLabel);

        heightField = new JTextField();
        heightField.setBounds(x+(distance*3),y+25,x+fieldWidth,25);
        add(heightField);

        // Age  Field
        final JLabel ageLabel = new JLabel("Age", JLabel.CENTER);		// Create JLabel
        ageLabel.setBounds(x+(distance*4), y, x+fieldWidth-30, 25);												// Set size and position JLabel
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(x+(distance*4)-10,y+25,x+fieldWidth-30,25);
        add(ageField);

        // Add  Search Button
        searchButton = new JButton("Search");
        searchButton.setBounds(x+(distance*5)-50,y+10,x+fieldWidth,40);
        add(searchButton);

        // Add Clear Button
        clearButton = new JButton("Clear");
        clearButton.setBounds(x+(distance*6)-40,y+10,x+fieldWidth-50,40);
        add(clearButton);

        progressBar = new JProgressBar();
        progressBar.setBounds(x+(distance*2)+20,  + y+55, 300,25);
        progressBar.setVisible(false);
        progressBar.setStringPainted(true);
        add(progressBar);
    }

    void addActionOnClearButton(){
        clearButton.addActionListener(e -> {

            clearFiltersAndRepopulateData();
            isFromFilter = false;
        });
    }

    void addActionOnSearchButton(){
        searchButton.addActionListener(e -> {
            progressBar.setVisible(true);
            searchButton.setEnabled(false);
            isFromFilter = true;

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Instances of javax.swing.SwingWorker are not reusuable, so
            // we create new instances as needed.
            task = new Task();
            task.addPropertyChangeListener(evt -> {

                if ("progress" == evt.getPropertyName()) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar.setValue(progress);
                }
            });

            task.execute();

        });
    }

    void searchAndPopulateDataByFilters(){
        int age = 0;
        double height = 0.0;
        Random random = new Random();

        int pValue=10;
        progressBar.setValue(pValue);
        try {
            age = ageField.getText().isEmpty()?0:Integer.valueOf(ageField.getText());
            pValue +=random.nextInt(30);
            progressBar.setValue(pValue);
        }catch (NumberFormatException numberFormatException){
            JOptionPane.showMessageDialog(null,"Invalid text in age field. Please Enter numeric value");
        }

        try {
            height = heightField.getText().isEmpty()?0.0:Double.valueOf(heightField.getText());
            pValue +=random.nextInt(40);
            progressBar.setValue(pValue);
        }catch (NumberFormatException numberFormatException){
            JOptionPane.showMessageDialog(null,"Invalid text in height field. Please Enter numeric value");
        }

//        personTableModel.populateTableByFilters(firstNameField.getText(), lastNameField.getText(), dobField.getText(),
//                height, age);
        pagePanel.personFilter = new Person(0,firstNameField.getText(), lastNameField.getText(), dobField.getText(),
                height, age);
        pagePanel.getCountByFilter();
        pagePanel.getPageDataForFilter(1);


        progressBar.setValue(100);

    }

    void clearFiltersAndRepopulateData(){
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        heightField.setText("");
        ageField.setText("");
//        personTableModel.populateTable();
        pagePanel.clearPage();
    }


    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            // Initialize progress property.
            setProgress(0);
            searchAndPopulateDataByFilters();
//            while (progress < 100) {
//                // Sleep for up to one second.
//                try {
//                    Thread.sleep(random.nextInt(100));
//                } catch (InterruptedException ignore) {
//                }
//                // Make random progress.
//                progress += random.nextInt(20);
//                setProgress(Math.min(progress, 100));
//            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            searchButton.setEnabled(true);
            setCursor(null); // turn off the wait cursor
            progressBar.setVisible(false);
        }
    }
}
