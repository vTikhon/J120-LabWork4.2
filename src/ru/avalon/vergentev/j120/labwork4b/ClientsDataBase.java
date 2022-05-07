package ru.avalon.vergentev.j120.labwork4b;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class ClientsDataBase extends JFrame implements WindowListener, ActionListener {
    JLabel phone = new JLabel("Phone number: ", SwingConstants.RIGHT);
    JTextField phoneText = new JTextField();
    JLabel dateReg = new JLabel("Date of registration: ", SwingConstants.RIGHT);
    JTextField dateRegText = new JTextField();
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

        add(phone);
        addTextField(phoneText);
        add(dateReg);
        addTextField(dateRegText);
        add(name);
        addTextField(nameText);
        add(address);
        addTextField(addressText);

        add(empty1);
        addButton(add);

        add(phoneForRemover);
        addTextField(phoneTextForRemover);
        add(empty2);
        addButton(remove);

        add(empty3);
        addButton(showClients);
    }

    private void addButton (JButton button) {
        button.addActionListener(this);
        add(button);
    }

    private void addTextField (JTextField field) {
        field.addActionListener(this);
        add(field);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) algorithmIfAddClientButtonIsPushed();
        else if (e.getSource() == remove) algorithmIfRemoveBookButtonIsPushed();
        else if (e.getSource() == showClients) algorithmIfShowClientsButtonIsPushed();
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
            dateRegText.setText("");
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
        addTable();
        frameForTable.setVisible(!frameForTable.isShowing());
    }

    private String[][] getDataFromPropertiesForTable() {
        String[][] arrayData = new String[data.size()][];
        int k = 0;
        for (Object i : data.keySet()) {
            Client client = new Client();
            String [] dataEachBookParameter = ((String)data.get(i)).split("'");
            for (int j = 1; j < dataEachBookParameter.length; j = j + 2) {
                if      (j == 1) client.setDateReg(dataEachBookParameter[j]);
                else if (j == 3) client.setName(dataEachBookParameter[j]);
                else if (j == 5) client.setAddress(dataEachBookParameter[j]);
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
