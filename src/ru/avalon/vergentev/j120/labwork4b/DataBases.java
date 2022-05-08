package ru.avalon.vergentev.j120.labwork4b;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DataBases extends JFrame implements WindowListener {
    ClientsDataBase clientsDataBase = new ClientsDataBase();
    CompaniesDataBase companiesDataBase = new CompaniesDataBase();

    public DataBases() throws HeadlessException {
        setTitle("Books accounting");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane);
        tabbedPane.setBounds(5, 5, 375,550);
        tabbedPane.add( "Clients", clientsDataBase);
        tabbedPane.add("Companies", companiesDataBase);
        addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        clientsDataBase.loadClientsData();
        companiesDataBase.loadCompaniesData();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        clientsDataBase.storeDataClients();
        companiesDataBase.storeDataCompanies();
        clientsDataBase.frameForTable.dispose();
        companiesDataBase.frameForTable.dispose();
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
