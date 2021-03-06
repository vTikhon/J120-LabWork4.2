package ru.avalon.vergentev.j120.labwork4b;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ClientsDataBase extends JPanel implements KeyListener {
    private final StringBuilder phoneNumber = new StringBuilder();
    File fileClients = new File("clients.dat");
    Properties dataClients = new Properties();

    private final JLabel dateReg = new JLabel("Date of registration: ", SwingConstants.RIGHT);
    private final JLabel dateRegText = new JLabel(String.valueOf(new Date()));
    private final JLabel phone = new JLabel("Phone number: ", SwingConstants.RIGHT);
    private final JTextField phoneText = new JTextField();
    private final JLabel name = new JLabel("Name: ", SwingConstants.RIGHT);
    private final JTextField nameText = new JTextField();
    private final JLabel address = new JLabel("Address: ", SwingConstants.RIGHT);
    private final JTextField addressText = new JTextField();
    private final JLabel dateOfBirth = new JLabel("Date of birth: ", SwingConstants.RIGHT);
    private final JTextField dateOfBirthText = new JTextField();
    private final JButton add = new JButton("Add");
    private final JLabel empty1 = new JLabel("");
    private final JLabel phoneForRemover = new JLabel("Phone for remove client: ", SwingConstants.RIGHT);
    private final JTextField phoneTextForRemover = new JTextField();
    private final JButton remove = new JButton("Remove");
    private final JLabel empty2 = new JLabel("");
    private final JButton showClients = new JButton("Show clients");
    private final JLabel empty3 = new JLabel("");

    private JComboBox<Integer[]> yearComboBox, monthComboBox, dayComboBox;
    private final String[] titleTableClients = {"PHONE", "DATE", "NAME", "ADDRESS", "DATE OF BIRTH", "AGE"};
    protected JFrame frameForTable  = new JFrame();


    public ClientsDataBase() {
        setLayout(new GridLayout(9,2));
        add(dateReg);
        add(dateRegText);
        add(phone);
        add(phoneText);
        phoneText.addKeyListener(this);
        add(name);
        add(nameText);
        add(address);
        add(addressText);
        add(dateOfBirth);
        JPanel panelForDateOfBirthInClients = new JPanel();
        add(panelForDateOfBirthInClients);
        panelForDateOfBirthInClients.setLayout(new FlowLayout());
        yearComboBox = new JComboBox(getYearsArray());
        monthComboBox = new JComboBox(getMonthsArray());
        dayComboBox = new JComboBox(getDaysArray());
        panelForDateOfBirthInClients.add(yearComboBox);
        panelForDateOfBirthInClients.add(monthComboBox);
        monthComboBox.addItemListener(e -> {
            panelForDateOfBirthInClients.remove(dayComboBox);
            dayComboBox = new JComboBox(getDaysArray());
            panelForDateOfBirthInClients.add(dayComboBox);
            panelForDateOfBirthInClients.revalidate();
        });
        panelForDateOfBirthInClients.add(dayComboBox);
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

    //???????????? ???????????????????? ?????????? ?????? JComboBox
    private Integer[] getYearsArray () {
        int arrayYearLength = (int)(new Date().getTime()/1000/86400/365) + 70;
        Integer[] yearsArray = new Integer[arrayYearLength + 1];
        for (int i = 0; i < yearsArray.length; i++) {
            yearsArray[i] = 1900 + i;
        }
        return yearsArray;
    }

    //???????????? ???????????????????? ?????????????? ?????? JComboBox
    private Integer[] getMonthsArray () {
        Integer[] monthsArray = new Integer[12];
        for (int i = 0; i < monthsArray.length; i++) {
            monthsArray[i] = i+1;
        }
        return monthsArray;
    }

    //?????????????????? ???????????? ???????? ?????? JComboBox ???????????????????? ???? ??????
    private Integer[] getDaysArray () {
        Integer[] daysArray;
        if      ((int)monthComboBox.getSelectedItem() == 2 && (int)yearComboBox.getSelectedItem() % 4 == 0) {daysArray = new Integer[29];}
        else if ((int)monthComboBox.getSelectedItem() == 2 && (int)yearComboBox.getSelectedItem() % 4 != 0) {daysArray = new Integer[28];}
        else if ((int)monthComboBox.getSelectedItem() == 4) {daysArray = new Integer[30];}
        else if ((int)monthComboBox.getSelectedItem() == 6) {daysArray = new Integer[30];}
        else if ((int)monthComboBox.getSelectedItem() == 9) {daysArray = new Integer[30];}
        else if ((int)monthComboBox.getSelectedItem() == 11) {daysArray = new Integer[30];}
        else {daysArray = new Integer[31];}
        for (int i = 0; i < daysArray.length; i++) {daysArray[i] = i+1;}
        return daysArray;
    }

    private void addTable () {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
        } else {
            frameForTable = new JFrame();
            JTable table = new JTable(getDataFromPropertiesForTable(), titleTableClients);
            JScrollPane scrollPane = new JScrollPane(table);
            frameForTable.setBounds(30, 40, 1000, 400);
            frameForTable.add(scrollPane);
        }
    }

    private void algorithmIfAddClientButtonIsPushed() {
        Client client = new Client();
        if (phoneNumber.length() == 0) {
            JOptionPane.showMessageDialog(null, "Phone number must have a number", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else if (dataClients.containsKey(phoneText.getText())) {
            JOptionPane.showMessageDialog(null, "That phone has another client", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else if (getAge() < 0) {
            JOptionPane.showMessageDialog(null, "Person has not been borned", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {
            phoneText.getText();
            client.setDateReg(dateRegText.getText().replaceAll("'", ""));
            client.setName(nameText.getText().replaceAll("'", ""));
            client.setAddress(addressText.getText().replaceAll("'", ""));
            client.setDateOfBirth(dayComboBox.getSelectedItem() + "." + monthComboBox.getSelectedItem() + "." + yearComboBox.getSelectedItem());
            client.setAge(String.valueOf(getAge()/365));
            dataClients.setProperty(phoneText.getText(), String.valueOf(client));
            phoneText.setText("");
            phoneNumber.setLength(0);
            dateRegText.setText(String.valueOf(new Date()));
            nameText.setText("");
            addressText.setText("");
            dateOfBirthText.setText("");
        }
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
            algorithmIfShowClientsButtonIsPushed();
        }
    }

    private void algorithmIfRemoveBookButtonIsPushed() {
        int resultRemove = JOptionPane.showConfirmDialog(null, "Are you sure want to remove that client from data base ?", "WARNING", JOptionPane.OK_CANCEL_OPTION);
        if (resultRemove == JOptionPane.OK_OPTION) {
            dataClients.remove(phoneTextForRemover.getText());
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

    //?????????????????? ???????????? ?????? ??????????????
    private String[][] getDataFromPropertiesForTable() {
        String[][] arrayData = new String[dataClients.size()][];
        int k = 0;
        for (Object i : dataClients.keySet()) {
            Client client = new Client();
            String [] dataEachClientParameters = ((String)dataClients.get(i)).split("'");
            for (int j = 1; j < dataEachClientParameters.length; j = j + 2) {
                if      (j == 1) client.setDateReg(dataEachClientParameters[j]);
                else if (j == 3) client.setName(dataEachClientParameters[j]);
                else if (j == 5) client.setAddress(dataEachClientParameters[j]);
                else if (j == 7) client.setDateOfBirth(dataEachClientParameters[j]);
                else if (j == 9) client.setAge(dataEachClientParameters[j]);
            }
            arrayData[k] = new String[]{(String) i, client.getDateReg(), client.getName(), client.getAddress(), client.getDateOfBirth(), client.getAge()};
            k++;
        }
        return arrayData;
    }

    //?????????????? ??????????????
    public int getAge() {
        int daysFrom1900ToToday = (int)(new Date().getTime()/1000/86400) + 25567; //25567 = ??????-???? ???????? ?? 1.1.1900 ???? 1.1.1970 (?? ?????????????? ???????????????????? ?? ???????????????????? ??????????)
        int daysFrom1900ToChoosedYear = ((int)yearComboBox.getSelectedItem() - 1900)*365 + (((int)yearComboBox.getSelectedItem() - 1900)/4 + 1);
        int checkMonth = (int)monthComboBox.getSelectedItem();
        int daysFromJanuaryToChoosedMonth;
        if (checkMonth == 1 || checkMonth == 3 || checkMonth == 5 || checkMonth == 7 || checkMonth == 8 || checkMonth == 10 || checkMonth == 12) {
            daysFromJanuaryToChoosedMonth = ((int)monthComboBox.getSelectedItem() - 1)*31;
        } else if ((int)monthComboBox.getSelectedItem() == 2 && (int)yearComboBox.getSelectedItem() % 4 == 0) {
            daysFromJanuaryToChoosedMonth = ((int)monthComboBox.getSelectedItem() - 1)*29;
        } else if ((int)monthComboBox.getSelectedItem() != 2 && (int)yearComboBox.getSelectedItem() % 4 == 0) {
            daysFromJanuaryToChoosedMonth = ((int)monthComboBox.getSelectedItem() - 1)*28;
        } else {
            daysFromJanuaryToChoosedMonth = ((int)monthComboBox.getSelectedItem() - 1)*30;
        }
        int daysFromFirstToChoosedDay =  (int)dayComboBox.getSelectedItem() - 1;
        int daysFrom1900ToSelectedDate = (daysFrom1900ToChoosedYear + daysFromJanuaryToChoosedMonth + daysFromFirstToChoosedDay);
        return (daysFrom1900ToToday - daysFrom1900ToSelectedDate);
    }

    //?????????? ???????????????? ???????? ?? ???????????????????????? ???????????? ?? ???????????? ???????????????????? ?? ???????? Properties
    public Properties loadClientsData () {
        try {
            if (!fileClients.exists()) fileClients.createNewFile();
            FileReader fileReader = new FileReader(fileClients);
            dataClients.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataClients;
    }

    public void storeDataClients () {
        try {
            FileWriter fileWriter = new FileWriter(fileClients, false);
            dataClients.store(fileWriter, null);
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
            case KeyEvent.VK_BACK_SPACE: phoneNumber.deleteCharAt(phoneText.getCaretPosition()); break;
        }
        phoneText.setText(String.valueOf(phoneNumber));
    }
}
