package project.view;
import project.control.LeftTableControl;
import project.control.RightTableControl;
import project.model.InvoiceHeader;
import project.model.InvoiceLine;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesInvoiceFrame extends JFrame implements ActionListener,ListSelectionListener {

    private JMenuBar mb;
    private JMenu menu;
    private JMenuItem load;
    private JMenuItem save;
    private JLabel lT;
    private JTable rightTable;
    private ArrayList<ArrayList<String>> data= new ArrayList<>();
    private JLabel invNo;
    private JTextField invoiceD;
    private JTextField CustomerN;
    private JLabel invTot;
    private JButton cancel;
    private JButton Save;

    public JTable leftTable;
    private ArrayList<ArrayList<String>> data1= new ArrayList<>();
    private JButton createNew;
    private JButton delete;


    public SalesInvoiceFrame( String title ){

        super(title);
        mb= new JMenuBar();
        menu= new JMenu("File");
        load= new JMenuItem("Load File"); load.setActionCommand("load"); load.addActionListener(this);
        save= new JMenuItem("Save File"); save.setActionCommand("save"); save.addActionListener(this);

        setJMenuBar(mb);
        mb.add(menu);
        menu.add(load);
        menu.add(save);

        JPanel main = new JPanel();
        main.setLayout(new GridLayout(1,2));

        JPanel left = new JPanel();
        left.setLayout(new GridLayout(1,1));
       JPanel right = new JPanel();
       right.setLayout(new GridLayout(1,2));

       JPanel ll= new JPanel();
       ll.setLayout(new BorderLayout(15,15));

       JPanel rr= new JPanel();
       rr.setLayout(new BorderLayout(15,15));

       JPanel r= new JPanel();
       r.setLayout(new BoxLayout(r,BoxLayout.Y_AXIS));

       JPanel r1= new JPanel();
       r1.setLayout(new FlowLayout());


       JPanel f=new JPanel();
       f.setLayout(new GridBagLayout() );
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.HORIZONTAL;

        lT = new JLabel("     Innvoice Table");
        ll.add(lT, BorderLayout.NORTH);

        ArrayList<String> row1= new ArrayList<>();
        row1.add(null);
        row1.add(null);
        row1.add(null);
        row1.add(null);
        ArrayList<String> row2= new ArrayList<>();
        row2.add(null);
        row2.add(null);
        row2.add(null);
        row2.add(null);
        ArrayList<String> row3= new ArrayList<>();
        row3.add(null);
        row3.add(null);
        row3.add(null);
        row3.add(null);

        data.add(row1);
        data.add(row2);
        data.add(row3);

        MyTableModel TLeft = new MyTableModel(new String[]{"No.","Date","Customer","Total"}, data);
        leftTable = new JTable(TLeft);
        leftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftTable.getSelectionModel().addListSelectionListener(this);
        ll.add(new JScrollPane(leftTable), BorderLayout.CENTER);


        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER,25,10));

        createNew = new JButton("Create New Invoice"); createNew.setActionCommand("create"); createNew.addActionListener(this);
        bottom.add(createNew);
        delete = new JButton("Delete Invoice"); delete.setActionCommand("delete"); delete.addActionListener(this);
        bottom.add(delete);

        ll.add(bottom, BorderLayout.SOUTH);

        c.gridx=0;
        c.gridy=0;
        f.add(new JLabel("Invoice Number"), c);

        c.gridx=1;
        c.gridy=0;
        invNo = new JLabel("");
        f.add(invNo, c); //r.add( new JLabel("no"));

        c.gridx=0;
        c.gridy=1;
        f.add(new JLabel("Invoice Date   "), c);

        invoiceD= new JTextField(20);
        c.gridx=1;
        c.gridy=1;
        f.add(invoiceD, c);

        c.gridx=0;
        c.gridy=2;
        f.add(new JLabel("Customer Name   "), c);

        c.gridx=1;
        c.gridy=2;
        CustomerN= new JTextField(20);
        f.add(CustomerN, c);

        c.gridx=0;
        c.gridy=3;
        f.add(new JLabel("Invoice Total   "), c);

        c.gridx=1;
        c.gridy=3;
        invTot =new JLabel("  ");
        f.add(invTot, c);

        rr.add(f, BorderLayout.NORTH);

        r.add(new JLabel("Invoice Items  "));

       ArrayList<String> rrow1= new ArrayList<>();
       rrow1.add(null);
       rrow1.add(null);
       rrow1.add(null);
       rrow1.add(null);
       rrow1.add(null);
       ArrayList<String> rrow2= new ArrayList<>();
        rrow2.add(null);
        rrow2.add(null);
        rrow2.add(null);
        rrow2.add(null);
        rrow2.add(null);
    ArrayList<String> rrow3= new ArrayList<>();
    rrow3.add(null);
    rrow3.add(null);
    rrow3.add(null);
    rrow3.add(null);
    rrow3.add(null);

     data1.add(rrow1);
     data1.add(rrow2);
     data1.add(rrow3);

    MyTableModel TRight = new MyTableModel( new String[]{"No.","Item Name","Item Price","Count","Item Total"}, data1 );
    rightTable = new JTable(TRight);
    r.add(new JScrollPane(rightTable));
    rr.add(r,BorderLayout.CENTER );

        JPanel bott = new JPanel();
        bott.setLayout(new FlowLayout(FlowLayout.CENTER,25,10));
        Save = new JButton("Create"); Save.setActionCommand("saveButt"); Save.addActionListener(this);
        bott.add(Save);
        cancel = new JButton("Cancel"); cancel.setActionCommand("cancel"); cancel.addActionListener(this);
        bott.add(cancel);
        rr.add(bott, BorderLayout.SOUTH);


      left.add(ll);
      right.add(rr);
      main.add(left);
      main.add(right);
      add(main);
      setSize(800,500);
      setLocation(250,100);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
}

    @Override
    public void valueChanged(ListSelectionEvent e) {
         LeftTableRowSelected();
    }


    private ArrayList<InvoiceHeader> Invoices = new ArrayList<>();
    private ArrayList<InvoiceLine> lines = new ArrayList<>();
    private LeftTableControl headerModel;
    private RightTableControl lineModel;
    private SimpleDateFormat dd= new SimpleDateFormat("dd-mm-yyyy");
    private LeftTableDialogue headerDialogue;
    private RightTableDialogue lineDialogue;
    @Override
    public void actionPerformed(ActionEvent e) {

    if(e.getActionCommand()=="load")
    {
        try { loadfile();}
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Wrong File Format (Choose .csv file)","Error Message",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); }
    }

    else if(e.getActionCommand()=="save") {
        try{saveFile();}
        catch (IOException nf){System.out.println(nf);}

    }
    else if(e.getActionCommand()=="create") {createInvoice();}
    else if(e.getActionCommand()=="delete") {
        try {deleteInvoice();}
    catch (Exception file){
            JOptionPane.showMessageDialog(this,"Please select an invoice to delete","Error Message",JOptionPane.ERROR_MESSAGE);
    }
    }
    else if(e.getActionCommand()=="saveButt") {createItem();}
    else if(e.getActionCommand()=="cancel") {
        try {deleteItem();}
    catch (Exception item) {JOptionPane.showMessageDialog(this,"Please select an item to delete","Error Message",JOptionPane.ERROR_MESSAGE);
    }
    }
    else if(e.getActionCommand()=="createOk") {createInvoiceOk ();}
    else if(e.getActionCommand()=="createCancel") {createInvoiceCancel ();}
    else if(e.getActionCommand()=="createItemOk") {createItemOk ();}
    else if(e.getActionCommand()=="CancelItem") {createItemCancel ();}
    }
   private void loadfile() throws Exception{
        Invoices.clear();
        JOptionPane.showMessageDialog(this,"Please select invoice header file ", "Invoice Header",JOptionPane.WARNING_MESSAGE);
        JFileChooser file= new JFileChooser();
        int option = file.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION)
        {
            File selected = file.getSelectedFile();
            FileReader fr= new FileReader(selected);
            BufferedReader buffer = new BufferedReader(fr);
            String inv= null;
            while ((inv = buffer.readLine()) != null) {
                String[] invoSegments = inv.split(",");
                String invNoStr = invoSegments[0];
                String invDateStr = invoSegments[1];
                String customerN = invoSegments[2];
                int invNo = Integer.parseInt(invNoStr);   // from string to integer
                Date invDate = dd.parse(invDateStr);
                InvoiceHeader header = new InvoiceHeader(invNo, invDate,customerN);
                Invoices.add(header);
            }

               buffer.close();
               fr.close();
               //reading files for the right table
               JOptionPane.showMessageDialog(this,"Please select invoice line file", "Invoice Line",JOptionPane.WARNING_MESSAGE);
               file= new JFileChooser();
               option = file.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                selected = file.getSelectedFile();
                fr= new FileReader(selected);
                buffer = new BufferedReader(fr);
                while ((inv = buffer.readLine()) != null) {
                    String[] invoLines = inv.split(",");
                    String invNoStr = invoLines[0];
                    String item = invoLines[1];
                    String priceStr = invoLines[2];
                    String countStr = invoLines[3];
                    int invNo = Integer.parseInt(invNoStr);   // from string to integer
                    double price = Double.parseDouble(priceStr);
                    int count = Integer.parseInt(countStr);
                    InvoiceHeader header = findByNumber(invNo);
                    InvoiceLine invline = new InvoiceLine(item ,price, count, header);
                    header.addLines(invline);
                }
            }
            buffer.close();
            fr.close();
            headerModel = new LeftTableControl(Invoices);
            leftTable.setModel(headerModel);
            leftTable.validate();

            for (InvoiceHeader inv1: Invoices) {
                System.out.println(
                      "Invoice num: "   +inv1.getNum()+ " {"
                );
                System.out.println("Date:  " +inv1.getDate() + " ,  Name:  " +inv1.getCustomer());
                for (InvoiceLine item: inv1.getLines()) {
                    System.out.println(item.getItemName()+ " , " +item.getItemPrice()+ " , " + item.getCount() );

                }
                System.out.println("}");
            }

        }
    } 
     private void saveFile () throws IOException {
         JOptionPane.showMessageDialog(this, "Save invoice header file ", "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
         JFileChooser fs = new JFileChooser();
         fs.setDialogTitle("Choose File Save");
         int userSelection = fs.showSaveDialog(this);
         if (userSelection == JFileChooser.APPROVE_OPTION) {
             File fileToSave = fs.getSelectedFile();
             FileWriter fw = new FileWriter(fileToSave + ".csv");
             BufferedWriter bw = new BufferedWriter(fw);
             for (int i = 0; i < leftTable.getRowCount(); i++) {
                 for (int j = 0; j < leftTable.getColumnCount(); j++) {

                     bw.write(leftTable.getValueAt(i, j).toString() + ",");
                 }
                 bw.newLine();
             }
             JOptionPane.showMessageDialog(this, "Saved Successfully ", "Save Message", JOptionPane.INFORMATION_MESSAGE);
             bw.close();
             fw.close();
          //Saving invoice line table


           JOptionPane.showMessageDialog(this, "Save invoice line file ", "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
             fs = new JFileChooser();
             fs.setDialogTitle("Choose File Save");
             userSelection = fs.showSaveDialog(this);
             if (userSelection == JFileChooser.APPROVE_OPTION) {
                 fileToSave = fs.getSelectedFile();
                 fw = new FileWriter(fileToSave + ".csv");
                 bw = new BufferedWriter(fw);
                 for (InvoiceHeader inv: Invoices) {
                     for (InvoiceLine item: inv.getLines()) {
                         bw.write(inv.getNum()+ "," +item.getItemName()+ "," +item.getItemPrice()+ "," + item.getCount());
                         bw.newLine();
                     }

                 }
                 JOptionPane.showMessageDialog(this, "Saved Successfully ", "Save Message", JOptionPane.INFORMATION_MESSAGE);
                 bw.close();
                 fw.close();

             }





         }
     }

     private void createInvoice (){
        headerDialogue = new LeftTableDialogue (this);
        headerDialogue.setLocationRelativeTo(null);
        headerDialogue.setTitle("Create New Invoice");
        headerDialogue.setVisible(true);
     }
     private void deleteInvoice () throws Exception {
         leftTable.setModel(headerModel);
         int x = leftTable.getSelectedRow();
         Invoices.remove(x);
         headerModel.fireTableDataChanged();
    }

     private void createItem () {
         lineDialogue = new RightTableDialogue(this);
         lineDialogue.setLocationRelativeTo(null);
         lineDialogue.setTitle("Create New Invoice Item");
         lineDialogue.setVisible(true);

     }
     private void deleteItem () throws Exception{
        rightTable.setModel(lineModel);
        int x = rightTable.getSelectedRow();
        lines.remove(x);
        lineModel.fireTableDataChanged();
    }


     public InvoiceHeader findByNumber(int no)
     {
         for (InvoiceHeader header : Invoices) { if (header.getNum() == no) {return header;}  }
         return null;
     }

    private void LeftTableRowSelected() {
     leftTable.setModel(headerModel);
     int selectedRow= leftTable.getSelectedRow();
     if (selectedRow != -1)
     {
         InvoiceHeader row = headerModel.getInvoices().get(selectedRow);
         invNo.setText(""+row.getNum());
         CustomerN.setText(row.getCustomer());
         invoiceD.setText(row.getDate().toString());
         invTot.setText(""+row.getTotal());
         lines = row.getLines();
         lineModel = new RightTableControl(lines);
         rightTable.setModel(lineModel);
         lineModel.fireTableDataChanged();
     }
    }

    private void createInvoiceOk() {

        String customer = headerDialogue.getCustomerName().getText();
        String invDateStr = headerDialogue.getInvoDate().getText();
        Date invDate = new Date();
        try{
            invDate = dd.parse(invDateStr);
        } catch(ParseException ex){
            JOptionPane.showMessageDialog(this,"Wrong Data Format","Error Message",JOptionPane.ERROR_MESSAGE);

        }

        headerDialogue.setVisible(false);
        int num = getLastNum()+1;
        InvoiceHeader newInv = new InvoiceHeader(num,invDate,customer);
        Invoices.add(newInv);
        headerModel.fireTableDataChanged();

    }
    private void createInvoiceCancel(){headerDialogue.setVisible(false); }
    private void createItemOk () {
        String itemName = lineDialogue.getItemName().getText();
        String itemCountStr = lineDialogue.getItemCount().getText();
        String itemPriceStr= lineDialogue.getItemPrice().getText();
        lineDialogue.setVisible(false);

        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice= Double.parseDouble(itemPriceStr);
        InvoiceHeader invoiceHeader = Invoices.get(leftTable.getSelectedRow());
        InvoiceLine line = new InvoiceLine(itemName,itemPrice,itemCount, invoiceHeader );
        invoiceHeader.addLines(line);
        lineModel.fireTableDataChanged();
        headerModel.fireTableDataChanged();


    }
    private void createItemCancel(){lineDialogue.setVisible(false);}
    private int getLastNum(){
        int num= 0;
        for (InvoiceHeader header : Invoices) {
            if (header.getNum() > num)
            {
                num = header.getNum();
            }

        }
        return num;
    }

}
