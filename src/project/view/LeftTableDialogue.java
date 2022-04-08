package project.view;
import javax.swing.*;
import java.awt.*;

public class LeftTableDialogue extends JDialog {
      private JLabel customerNameLbl;
      private JTextField customerName;
      private JLabel invoDateLbl;
      private JTextField invoDate;
      private JButton okBtn;
      private JButton cancelBtn;

    public LeftTableDialogue(SalesInvoiceFrame frame) {

        customerNameLbl = new JLabel("Customer Name : ");
        customerName = new JTextField(20);
        invoDateLbl = new JLabel("Invoice Date : ");
        invoDate = new JTextField(20);
        okBtn = new JButton("Ok"); okBtn.setActionCommand("createOk"); ; okBtn.addActionListener(frame);
        cancelBtn = new JButton("Cancel"); cancelBtn.setActionCommand("createCancel"); cancelBtn.addActionListener(frame);

        setLayout(new GridLayout(3,2));

        add(invoDateLbl);
        add(invoDate);
        add(customerNameLbl);
        add(customerName);
        add(okBtn);
        add(cancelBtn);

        pack();
    }

    public JTextField getCustomerName() {
        return customerName;
    }

    public JTextField getInvoDate() {
        return invoDate;
    }
}
