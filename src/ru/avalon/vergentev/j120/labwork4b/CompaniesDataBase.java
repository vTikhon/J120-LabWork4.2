package ru.avalon.vergentev.j120.labwork4b;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CompaniesDataBase extends JPanel implements KeyListener {
    private final StringBuilder phoneNumber = new StringBuilder();
    File fileCompanies = new File("companies.dat");
    Properties dataCompanies = new Properties();

    private final JLabel dateRegCompany = new JLabel("Date of registration: ", SwingConstants.RIGHT);
    private final JLabel dateRegTextCompany = new JLabel(String.valueOf(new Date()));
    private final JLabel phoneCompany = new JLabel("Phone number: ", SwingConstants.RIGHT);
    private final JTextField phoneTextCompany = new JTextField();
    private final JLabel nameCompany = new JLabel("Company name: ", SwingConstants.RIGHT);
    private final JTextField nameTextCompany = new JTextField();
    private final JLabel addressCompany = new JLabel("Address: ", SwingConstants.RIGHT);
    private final JTextField addressTextCompany = new JTextField();
    private final JLabel director = new JLabel("Director name: ", SwingConstants.RIGHT);
    private final JTextField directorTextCompany = new JTextField();
    private final JLabel contactPersonCompany = new JLabel("Contact person name: ", SwingConstants.RIGHT);
    private final JTextField contactPersonTextCompany = new JTextField();
    private final JButton addCompany = new JButton("Add");
    private final JLabel empty1Company = new JLabel("");
    private final JLabel phoneForRemoverCompany = new JLabel("Phone for remove company: ", SwingConstants.RIGHT);
    private final JTextField phoneTextForRemoverCompany = new JTextField();
    private final JButton removeCompany = new JButton("Remove");
    private final JLabel empty2Company = new JLabel("");
    private final JButton showCompanies = new JButton("Show companies");
    private final JLabel empty3Company = new JLabel("");

    private final String[] titleTableCompanies = {"PHONE", "DATE", "COMPANY NAME", "ADDRESS", "DIRECTOR NAME", "CONTACT PERSON"};
    protected JFrame frameForTable  = new JFrame();


    public CompaniesDataBase() {
        setLayout(new GridLayout(10,2));
        add(dateRegCompany);
        add(dateRegTextCompany);
        add(phoneCompany);
        add(phoneTextCompany);
        phoneTextCompany.addKeyListener(this);
        add(nameCompany);
        add(nameTextCompany);
        add(addressCompany);
        add(addressTextCompany);
        add(director);
        add(directorTextCompany);
        add(contactPersonCompany);
        add(contactPersonTextCompany);
        add(empty1Company);
        add(addCompany);
        addCompany.addActionListener(e -> {algorithmIfAddClientButtonIsPushed();});
        add(phoneForRemoverCompany);
        add(phoneTextForRemoverCompany);
        add(empty2Company);
        add(removeCompany);
        removeCompany.addActionListener(e -> {algorithmIfRemoveBookButtonIsPushed();});
        add(empty3Company);
        add(showCompanies);
        showCompanies.addActionListener(e -> {algorithmIfShowClientsButtonIsPushed();});
    }

    private void addTable () {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
        } else {
            frameForTable = new JFrame();
            JTable table = new JTable(getDataFromPropertiesForTable(), titleTableCompanies);
            JScrollPane scrollPane = new JScrollPane(table);
            frameForTable.setBounds(30, 40, 1000, 400);
            frameForTable.add(scrollPane);
        }
    }

    private void algorithmIfAddClientButtonIsPushed() {
        Company company = new Company();
        if (phoneNumber.length() == 0) {
            JOptionPane.showMessageDialog(null, "Phone number must have a number", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else if (dataCompanies.containsKey(phoneTextCompany.getText())) {
            JOptionPane.showMessageDialog(null, "That phone has another client", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {
            phoneTextCompany.getText();
            company.setDateReg(dateRegTextCompany.getText().replaceAll("'", ""));
            company.setNameCompany(nameTextCompany.getText().replaceAll("'", ""));
            company.setAddressCompany(addressTextCompany.getText().replaceAll("'", ""));
            company.setDirectorName(directorTextCompany.getText());
            company.setContactPersonName(contactPersonCompany.getText());
            dataCompanies.setProperty(phoneTextCompany.getText(), String.valueOf(company));
            phoneTextCompany.setText("");
            phoneNumber.setLength(0);
            dateRegTextCompany.setText(String.valueOf(new Date()));
            nameTextCompany.setText("");
            addressTextCompany.setText("");
            contactPersonTextCompany.setText("");
        }
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
            algorithmIfShowClientsButtonIsPushed();
        }
    }

    private void algorithmIfRemoveBookButtonIsPushed() {
        int resultRemove = JOptionPane.showConfirmDialog(null, "Are you sure want to remove that client from data base ?", "WARNING", JOptionPane.OK_CANCEL_OPTION);
        if (resultRemove == JOptionPane.OK_OPTION) {
            dataCompanies.remove(phoneTextForRemoverCompany.getText());
            phoneTextForRemoverCompany.setText("");
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

    //формируем данные для таблицы
    private String[][] getDataFromPropertiesForTable() {
        String[][] arrayData = new String[dataCompanies.size()][];
        int k = 0;
        for (Object i : dataCompanies.keySet()) {
            Company company = new Company();
            String [] dataEachClientParameters = ((String)dataCompanies.get(i)).split("'");
            for (int j = 1; j < dataEachClientParameters.length; j = j + 2) {
                if      (j == 1) company.setDateReg(dataEachClientParameters[j]);
                else if (j == 3) company.setNameCompany(dataEachClientParameters[j]);
                else if (j == 5) company.setAddressCompany(dataEachClientParameters[j]);
                else if (j == 7) company.setDirectorName(dataEachClientParameters[j]);
                else if (j == 9) company.setContactPersonName(dataEachClientParameters[j]);
            }
            arrayData[k] = new String[]{(String) i, company.getDateReg(), company.getNameCompany(), company.getAddressCompany(), company.getDirectorName(), company.getContactPersonName()};
            k++;
        }
        return arrayData;
    }

    //метод читающий файл и возвращающий данные в память компьютера в виде Properties
    public Properties loadCompaniesData () {
        try {
            if (!fileCompanies.exists()) fileCompanies.createNewFile();
            FileReader fileReader = new FileReader(fileCompanies);
            dataCompanies.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataCompanies;
    }

    public void storeDataCompanies () {
        try {
            FileWriter fileWriter = new FileWriter(fileCompanies, false);
            dataCompanies.store(fileWriter, null);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_0: phoneNumber.append(0); break;
            case KeyEvent.VK_1: phoneNumber.append(1); break;
            case KeyEvent.VK_2: phoneNumber.append(2); break;
            case KeyEvent.VK_3: phoneNumber.append(3); break;
            case KeyEvent.VK_4: phoneNumber.append(4); break;
            case KeyEvent.VK_5: phoneNumber.append(5); break;
            case KeyEvent.VK_6: phoneNumber.append(6); break;
            case KeyEvent.VK_7: phoneNumber.append(7); break;
            case KeyEvent.VK_8: phoneNumber.append(8); break;
            case KeyEvent.VK_9: phoneNumber.append(9); break;
            case KeyEvent.VK_BACK_SPACE: phoneNumber.deleteCharAt(phoneTextCompany.getCaretPosition()); break;
        }
        phoneTextCompany.setText(String.valueOf(phoneNumber));
    }
}
