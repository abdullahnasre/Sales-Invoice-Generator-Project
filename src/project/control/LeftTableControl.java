package project.control;
import project.model.InvoiceHeader;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LeftTableControl extends DefaultTableModel {

    private String[] cols= {"No.","Date","Customer","Total"};
    private ArrayList<InvoiceHeader> invoices;

  public LeftTableControl(ArrayList<InvoiceHeader> invoices){
      this.invoices=invoices;
  }

    public int getColumnCount(){

      return cols.length;
    }

    public String getColumnName(int column){
      return cols[column];
    }

    public ArrayList<InvoiceHeader> getInvoices()
    {
        return invoices;
    }

    public int getRowCount(){
      if (this.invoices==null) {invoices = new ArrayList<>();}
      return invoices.size();
    }


    @Override
    public Object getValueAt(int row, int column) {
        InvoiceHeader inv = invoices.get(row);
        switch (column)
        {
            case 0 : return inv.getNum();
            case 1 : return inv.getDate();
            case 2 : return inv.getCustomer();
            case 3: return inv.getTotal();
        }
        return null;

    }
}
