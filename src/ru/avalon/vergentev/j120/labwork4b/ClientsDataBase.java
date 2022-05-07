package ru.avalon.vergentev.j120.labwork4b;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ClientsDataBase extends JFrame implements WindowListener {
    JLabel phone = new JLabel("Phone number: ", SwingConstants.RIGHT);
    JTextField phoneText = new JTextField();
    JLabel dateReg = new JLabel("Date of registration: ", SwingConstants.RIGHT);
    JLabel dateRegText = new JLabel(String.valueOf(new Date()));
//    JTextField dateRegText = new JTextField();
    JLabel name = new JLabel("Name: ", SwingConstants.RIGHT);
    JTextField nameText = new JTextField();
    JLabel address = new JLabel("Address: ", SwingConstants.RIGHT);
    JTextField addressText = new JTextField();

    JButton add = new JButton("Add");
    JButton empty1 = new JButton("");
    JLabel phoneForRemover = new JLabel("Phone for remove client: ", SwingConstants.RIGHT);
    JTextField phoneTextForRemover = new JTextField();
    JButton remove = new JButton("Remove");
    JButton empty2 = new JButton("");
    JButton showClients = new JButton("Show books");
    JButton empty3 = new JButton("");

    File file = new File("books.dat");
    Properties data = new Properties();

    String[] column = {"PHONE", "DATE", "NAME", "ADDRESS"};
    JFrame frameForTable  = new JFrame();
    JTable table;
    JScrollPane scrollPane;


    public ClientsDataBase() throws HeadlessException {
        setTitle("Books accounting");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9,2));
        addWindowListener(this);
        addComponents();
    }

    private void addComponents () {
        add(phone);
        add(phoneText);
        add(dateReg);
        add(dateRegText);
        add(name);
        add(nameText);
        add(address);
        add(addressText);
        add(empty1);
        add(add);
        add.addActionListener(e -> {algorithmIfAddClientButtonIsPushed();});
        add(phoneForRemover);
        add(phoneTextForRemover);
        add(empty2);
        add(remove);
        remove.addActionListener(e -> {algorithmIfRemoveBookButtonIsPushed();});
        add(empty3);
        add(showClients);
        showClients.addActionListener(e -> {algorithmIfShowClientsButtonIsPushed();});
    }

    private void addTable () {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
        } else {
            frameForTable = new JFrame();
            table = new JTable(getDataFromPropertiesForTable(), column);
            scrollPane = new JScrollPane(table);
            frameForTable.setBounds(30, 40, 600, 600);
            frameForTable.add(scrollPane);
        }
    }

    private void algorithmIfAddClientButtonIsPushed() {
        Client client = new Client();
        phoneText.getText();
        client.setDateReg(dateRegText.getText().replaceAll("'", ""));
        client.setName(nameText.getText().replaceAll("'", ""));
        client.setAddress(addressText.getText().replaceAll("'", ""));
        if (!data.containsKey(phoneText.getText())) {
            data.setProperty(phoneText.getText(), String.valueOf(client));
            phoneText.setText("");
            dateRegText.setText(String.valueOf(new Date()));
            nameText.setText("");
            addressText.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "That phone has another client", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        }
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
            algorithmIfShowClientsButtonIsPushed();
        }
    }

    private void algorithmIfRemoveBookButtonIsPushed() {
        int resultRemove = JOptionPane.showConfirmDialog(null, "Are you sure want to remove that client from data base ?", "WARNING", JOptionPane.OK_CANCEL_OPTION);
        if (resultRemove == JOptionPane.OK_OPTION) {
            data.remove(phoneTextForRemover.getText());
            phoneTextForRemover.setText("");
        }
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
            algorithmIfShowClientsButtonIsPushed();
        }
    }

    private void algorithmIfShowClientsButtonIsPushed() {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
        } else {
            addTable();
            frameForTable.setVisible(!frameForTable.isShowing());
        }
    }

    private String[][] getDataFromPropertiesForTable() {
        String[][] arrayData = new String[data.size()][];
        int k = 0;
        for (Object i : data.keySet()) {
            Client client = new Client();
            String [] dataEachClientParameters = ((String)data.get(i)).split("'");
            for (int j = 1; j < dataEachClientParameters.length; j = j + 2) {
                if      (j == 1) client.setDateReg(dataEachClientParameters[j]);
                else if (j == 3) client.setName(dataEachClientParameters[j]);
                else if (j == 5) client.setAddress(dataEachClientParameters[j]);
            }
            arrayData[k] = new String[]{(String) i, client.getDateReg(), client.getName(), client.getAddress()};
            k++;
        }
        return arrayData;
    }

    @Override
    public void windowOpened(WindowEvent e) {loadData();}

    //метод читающий файл и возвращающий данные в память компьютера в виде Properties
    public Properties loadData () {
        try {
            if (!file.exists()) file.createNewFile();
            FileReader fileReader = new FileReader(file);
            data.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        storeData();
        frameForTable.dispose();
    }

    public void storeData () {
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            data.store(fileWriter, null);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
