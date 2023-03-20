package com.kh.ui;

import com.kh.config.DbConnection;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    public MainFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1080,800);										// Set size and position  GUI
        setTitle("GUI TEST KHADIM 2222");										// Set Title
        getContentPane().setLayout(null);

        // Menu
        JMenuBar menuBar = new JMenuBar();							// Create Menubar
        JMenu menu1 = new JMenu("File");							// Create Menu 'File'
        JMenuItem menu1_1 = new JMenuItem("Open File");				// MenuItem ' Open File'
        menu1.add(menu1_1);											// add MenuItem in Menu
        menuBar.add(menu1);											// add Menu in MenuItem
        setJMenuBar(menuBar);

        // Table
        JTable table = new JTable();				// Create JTable
        getContentPane().add(table);				// add JTable in GUI


        // ScrollPane
        JScrollPane scroll = new JScrollPane(table);		// Create Scroll in Jtable
        scroll.setBounds(84, 150, 900, 400);
        getContentPane().add(scroll);// add Scroll in GUI
        PagePanel pagePanel = new PagePanel(table);
        pagePanel.setBounds(200,550,600,200);
        getContentPane().add(pagePanel);
        getContentPane().add(new FiltersPanel(40,30, pagePanel));


        menu1_1.addActionListener(new ActionListener() { 			// Action Even for  Menu
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();			// Choose File
                FileFilter filter = new FileNameExtensionFilter(	// File Type : ' Text,CSV,JSON'
                        "Text/CSV file", "txt", "csv");
                fileopen.addChoosableFileFilter(filter);
                int ret = fileopen.showDialog(null, "Choose file");		// Open File
                if (ret == JFileChooser.APPROVE_OPTION ) {
                    File file = fileopen.getSelectedFile();
//                    personTableModel.populateTable(file);
                    DbConnection.initConnection(file.getAbsolutePath().replace("\\","\\\\"));
                    pagePanel.initRecords();
                }
            }
        });
    }
}
